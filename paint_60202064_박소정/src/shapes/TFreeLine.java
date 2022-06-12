package shapes;

import java.awt.geom.Path2D;

public class TFreeLine extends TShape {
	private static final long serialVersionUID = 1L;

	public TFreeLine() {
		this.shape = new Path2D.Double();
	}
	
	public TShape clone() {
		return new TFreeLine();
	}

	public void prepareDrawing(int x, int y){
		((Path2D) this.shape).moveTo(x, y);
	}
	
	public void keepDrawing(int x, int y) {
		((Path2D) this.shape).lineTo(x, y);
	}

}
