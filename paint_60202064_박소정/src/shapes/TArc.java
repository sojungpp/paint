package shapes;
import java.awt.geom.Arc2D;

public class TArc extends TShape{
	private static final long serialVersionUID = 1L;
	public TArc() {
		this.shape = new Arc2D.Double();
	}
	
	public TShape clone() {
		return new TArc();
	}
	
	public void prepareDrawing(int x, int y) {
		Arc2D arc = (Arc2D) this.shape;
		arc.setArc(x,y,0,0,0,0,0);
	}
	@Override
	public void keepDrawing(int x, int y) {
		Arc2D arc = (Arc2D) this.shape;
		arc.setArc(arc.getX(), arc.getY(), x-arc.getX(), y-arc.getY(),0,180,arc.CHORD);
	}
}