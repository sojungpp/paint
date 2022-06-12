package transformer;

import java.awt.Graphics2D;

import shapes.TShape;

public class Mover extends Transformer {

	public Mover(TShape selectedShape) {
		super(selectedShape);
	}
	
	@Override
	public void prepare(int x, int y) {
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.affineTransform.translate(x-this.px, y-this.py);
		this.px = x; //다시 세팅해야 move가 또 일어날 때 거리가 새롭게 계산됨
		this.py = y;
	}

	@Override
	public void finalize(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape);
		
//		this.affineTransform.setToIdentity();
//		this.anchors.draw(graphics2D, transformedShape.getBounds()); //transformedShape 하면서 앵커 그리도록
		
	}

}
