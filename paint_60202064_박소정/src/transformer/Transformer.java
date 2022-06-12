package transformer;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import shapes.TAnchors;
import shapes.TShape;

public abstract class Transformer {
	
	//상속 받아서 자식들이 쓸 수 있게 protected로
	protected Shape shape;
	protected TShape selectedShape;
	protected TAnchors anchors;
	protected AffineTransform affineTransform;
	protected Point rotatePoints;
	protected Point rotateAnchorPoints;

	protected int px; //(전점)
	protected int py;
	protected double cx, cy; //(기준점)
	
	public Transformer(TShape selectedShape) {
		this.selectedShape = selectedShape;
		this.affineTransform = this.selectedShape.getAffineTransform();
		this.anchors = this.selectedShape.getAnchors();
		this.shape = this.selectedShape.getShape();
		this.rotateAnchorPoints = new Point();
		this.rotatePoints = new Point();
		
	}
	
	public abstract void prepare(int x, int y);
	public abstract void keepTransforming(int x, int y);
	public abstract void finalize(int x, int y);
	
}
