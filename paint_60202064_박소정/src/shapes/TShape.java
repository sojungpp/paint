package shapes;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.awt.geom.Point2D;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable{
	//attributes (잘 변하지 않음)
	private static final long serialVersionUID = 1L;
	
	//component
	protected Shape shape;
	private TAnchors anchors;
	
	//working variables (상태, 변함)
	private boolean bSelected;

	private int px, py; //(전점)
	private double cx, cy; //(기준점)
	private double xScale, yScale; //(몇 배인지)
	private AffineTransform affineTransform; //축적된 값을 가지고 있는 것, 항상 shape에 곱해서 사용
	
	//constructors
	public abstract TShape clone();
	public TShape() {
		this.bSelected = false;
		this.affineTransform = new AffineTransform(); //계속 상태를 집어넣는 곳
		this.affineTransform.setToIdentity(); //초기화(항등원)
		this.anchors = new TAnchors();
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
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape); //잠깐 도형 만들어놓기
		graphics2D.draw(transformedShape);
		if(this.bSelected) {
			this.anchors.draw(graphics2D, transformedShape.getBounds()); //transformedShape 하면서 앵커 그리도록
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
		this.px = x; //다시 세팅해야 move가 또 일어날 때 거리가 새롭게 계산됨
		this.py = y;
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
		this.affineTransform.translate(cx, cy); //계산 전 원점 이동
		this.affineTransform.scale(xScale, yScale);
		this.affineTransform.translate(-cx, -cy); //계산 후 원위치
		this.px = x; 
		this.py = y;
	}
	
	private void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.geteResizeAnchor();
		double w1 = px-cx;
		double w2 = x-cx;
		double h1 = py-cy;
		double h2 = y-cy;
		//배율
		
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
		this.shape = this.affineTransform.createTransformedShape(this.shape); //누적시키지 않도록
		this.affineTransform.setToIdentity(); //누적 데이터를 초기화 시키기
	}
	
	public void finalizeMoving(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		this.affineTransform.setToIdentity();
	};
}