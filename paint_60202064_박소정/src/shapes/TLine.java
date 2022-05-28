package shapes;

import java.awt.geom.Line2D;

public class TLine extends TShape{
	private static final long serialVersionUID = 1L;

	public TLine() {
		this.shape = new Line2D.Double();
	}
	
	public TShape clone() {
		return new TLine();
	}
	
	public void prepareDrawing(int x, int y) {
		Line2D line2D = (Line2D) this.shape;
		line2D.setLine(x,y,x,y);
	}

	public void keepDrawing(int x, int y) {
		Line2D line2D = (Line2D) this.shape;
		line2D.setLine(line2D.getX1(),line2D.getY1(),x,y);
	}
}