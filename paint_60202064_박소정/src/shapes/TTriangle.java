package shapes;

import java.awt.Polygon;

public class TTriangle extends TShape{
	private static final long serialVersionUID = -4771349890581389274L;


	public TTriangle() {
		this.shape = new Polygon();
	}
	
	public TShape clone() {
		return new TTriangle();
	}

	@Override
	public void prepareDrawing(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.npoints=3;
		polygon.xpoints[1] = x;
		polygon.ypoints[0] = y;
		
	}

	@Override
	public void keepDrawing(int x1, int y1) {
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[0]=polygon.xpoints[1]+(x1-polygon.xpoints[1])/2;
		polygon.ypoints[1]=y1;
		polygon.xpoints[2]=x1;
		polygon.ypoints[2]=y1;
		
	}

}