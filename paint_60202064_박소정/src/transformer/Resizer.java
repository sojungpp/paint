package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Resizer extends Transformer {

	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.prepareResizing(x, y);

	}

	@Override
	public void keepTransforming(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.keepResizing(x, y);

	}

	@Override
	public void finalize(int x, int y, Graphics2D graphics2d) {
		this.selectedShape.finalizeResizing(x, y);

	}

}
