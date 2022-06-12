package transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapes.TShape;

public class Rotator extends Transformer {

	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y) {
		this.rotatePoints.setLocation(this.shape.getBounds2D().getCenterX(), this.shape.getBounds2D().getCenterY());
		this.rotateAnchorPoints.setLocation(x,y);
	}

	@Override
	public void keepTransforming(int x, int y) {
		Point endPoints = new Point(x,y);
		double rotateAngle = Math.toRadians(rotateAngle(this.rotatePoints, this.rotateAnchorPoints,endPoints));
		this.affineTransform.setToRotation(rotateAngle, this.rotatePoints.getX(), this.rotatePoints.getY());
	}

	private double rotateAngle(Point rotatePoints, Point startPoints, Point endPoints) { 
		double startAngle = Math.toDegrees(Math.atan2(rotatePoints.x-startPoints.x, rotatePoints.y-startPoints.y));
		double endAngle = Math.toDegrees(Math.atan2(rotatePoints.x-endPoints.x, rotatePoints.y-endPoints.y));
		double rotateAngle = startAngle-endAngle;
		return rotateAngle;
	}

	@Override
	public void finalize(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
	}

}
