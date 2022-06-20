package shapes;

import java.awt.geom.Path2D;

public class TCurve extends TShape {
	private static final long serialVersionUID = 1L;

	public TCurve() {
		this.shape = new Path2D.Double();
	}
	
	public TShape clone() {
		return new TCurve();
	}

	public void prepareDrawing(int x, int y){
		((Path2D) this.shape).moveTo(x, y);
	}
	
	public void keepDrawing(int x, int y) {
		((Path2D) this.shape).lineTo(x, y);
	}

}
