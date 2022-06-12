package shapes;
import java.awt.BasicStroke;
import java.awt.Color;
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
	private Shape transformedShape;
	private Color lineColor;
	private Color fillColor;
	private int thickness;

	private int px, py; //(����)
	private double cx, cy; //(������)
	private double xScale, yScale; //(�� ������)
	private AffineTransform affineTransform; //������ ���� ������ �ִ� ��, �׻� shape�� ���ؼ� ���
	
	//constructors
	public TShape clone() {
		try {
			return (TShape)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	};
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
	public TAnchors getAnchors() {
		return anchors;
	}
	public void setAnchors(TAnchors anchors) {
		this.anchors = anchors;
	}
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}
	public void setAffineTransform(AffineTransform affineTransform) {
		this.affineTransform = affineTransform;
	}
	public EAnchors getSelectedAnchor() {
		return this.anchors.geteSelectedAnchor();
	}
	public Shape getShape() {
		return shape;
	}
	public Color getLineColor() {
		return lineColor;
	}
	public Color getFillColor() {
		return fillColor;
	}
	
	//methods
	public abstract void prepareDrawing(int x, int y);
	public abstract void keepDrawing(int x, int y);
	public void addPoint(int x, int y) {}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	
	public void draw(Graphics2D graphics2D) {
		transformedShape = this.affineTransform.createTransformedShape(this.shape); //��� ���� ��������
			graphics2D.setColor(lineColor);
		if(thickness!=0) {
			graphics2D.setStroke(new BasicStroke(thickness));
		}
		graphics2D.draw(transformedShape);
		if(fillColor!=null) {
			graphics2D.setColor(fillColor);
			graphics2D.fill(transformedShape);
		}
		
		if(this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds()); //transformedShape �ϸ鼭 ��Ŀ �׸�����
		}
	}
	
	public void drawAnchors(Graphics2D graphics2D) {
			this.anchors.draw(graphics2D, transformedShape.getBounds()); //transformedShape �ϸ鼭 ��Ŀ �׸�����
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

}