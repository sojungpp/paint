package shapes;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.awt.geom.Point2D;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable{
	//attributes (�� ������ ����)
	private static final long serialVersionUID = 1L;
	
	//component
	protected Shape shape;
	private TAnchors anchors;
	
	//working variables (����, ����)
	private boolean bSelected;
	private Point rotatePoints;
	private Point rotateAnchorPoints;

	private int px, py; //(����)
	private double cx, cy; //(������)
	private double xScale, yScale; //(�� ������)
	private AffineTransform affineTransform; //������ ���� ������ �ִ� ��, �׻� shape�� ���ؼ� ���
	
	//constructors
	public abstract TShape clone();
	public TShape() {
		this.bSelected = false;
		this.affineTransform = new AffineTransform(); //��� ���¸� ����ִ� ��
		this.affineTransform.setToIdentity(); //�ʱ�ȭ(�׵��)
		this.anchors = new TAnchors();
		this.rotateAnchorPoints = new Point();
		this.rotatePoints = new Point();
	}
	
	//setters and getters
	public boolean isSelected() {
		return this.bSelected;
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}

	//methods
	public abstract void keepDrawing(int x, int y);
	public abstract void prepareDrawing(int x, int y);
	public void addPoint(int x, int y) {}
	
	public void draw(Graphics2D graphics2D) {
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape); //��� ���� ��������
		graphics2D.draw(transformedShape);
		if(this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds()); //transformedShape �ϸ鼭 ��Ŀ �׸�����
		}
	}
	
	public boolean contains(int x, int y) {		
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape); 
		if(this.bSelected) {
//			this.eSelectedAnchor = this.anchors.contains(x, y);
			if(this.anchors.contains(x, y)) {
				return true;
			}
		}
		if(transformedShape.contains(x, y)) {
			this.anchors.seteSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}
	
	public EAnchors getSelectedAnchor() {
		return this.anchors.geteSelectedAnchor();
	}
	public Shape getShape() {
		return shape;
	}
	//move
	public void prepareMoving(int x, int y) {
		this.px = x;
		this.py = y;
	}
	
	public void keepMoving(int x, int y) {
		this.affineTransform.translate(x-this.px, y-this.py);
		this.px = x; //�ٽ� �����ؾ� move�� �� �Ͼ �� �Ÿ��� ���Ӱ� ����
		this.py = y;
	}
	public void finalizeMoving(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		this.affineTransform.setToIdentity();
	}
	
	//resize
	public void prepareResizing(int x, int y) {
		this.px = x;
		this.py = y;
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y);
		this.cx = resizeAnchorPoint.getX();
		this.cy = resizeAnchorPoint.getY();
	}
	
	public void keepResizing(int x, int y) {
		this.getResizeScale(x, y);
		this.affineTransform.translate(cx, cy); //��� �� ���� �̵�
		this.affineTransform.scale(xScale, yScale);
		this.affineTransform.translate(-cx, -cy); //��� �� ����ġ
		this.px = x; 
		this.py = y;
	}
	
	private void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.geteResizeAnchor();
		double w1 = px-cx;
		double w2 = x-cx;
		double h1 = py-cy;
		double h2 = y-cy;
		//����
		
		switch (eResizeAnchor) {
		case eNW: xScale = w2/w1; yScale= h2/h1; break;
		case eWW: xScale = w2/w1; yScale=1.0; break;
		case eSW: xScale = w2/w1; yScale = h2/h1; break;
		case eSS: xScale = 1.0; yScale = h2/h1; break;
		case eSE: xScale = w2/w1; yScale = h2/h1; break;
		case eEE: xScale = w2/w1; yScale=1.0; break;
		case eNE: xScale = w2/w1; yScale = h2/h1; break;
		case eNN: xScale = 1.0; yScale = h2/h1; break;
		default: break;
		}
	}
	
	public void finalizeResizing(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape); //������Ű�� �ʵ���
		this.affineTransform.setToIdentity(); //���� �����͸� �ʱ�ȭ ��Ű��
	}
	
	public void prepareRotating(int x, int y) {
		this.rotateAnchorPoints.x=x;
		this.rotateAnchorPoints.y=y;
	}
	
	public void keepRotating(int x, int y) {
		this.rotatePoints.x = (int)this.shape.getBounds().getCenterX();
		this.rotatePoints.y = (int)this.shape.getBounds().getCenterY();
		Point endPoints = new Point(x,y);
		double rotateAngle = Math.toRadians(rotateAngle(this.rotatePoints, this.rotateAnchorPoints,endPoints));
		this.affineTransform.setToRotation(rotateAngle, this.rotatePoints.getX(), this.rotatePoints.getY());		
	};
	
	//tan�� ���� ���ϱ�
	private double rotateAngle(Point rotatePoints, Point startPoints, Point endPoints) { 
		double startAngle = Math.toDegrees(Math.atan2(rotatePoints.x-startPoints.x, rotatePoints.y-startPoints.y));
		double endAngle = Math.toDegrees(Math.atan2(rotatePoints.x-endPoints.x, rotatePoints.y-endPoints.y));
		double rotateAngle = startAngle-endAngle;
		return rotateAngle;
	}
}