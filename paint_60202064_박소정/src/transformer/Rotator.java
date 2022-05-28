package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Rotator extends Transformer {

	public Rotator(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.prepareRotating(x, y);
	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.keepRotating(x, y);
	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
		// TODO Auto-generated method stub

	}

}
