package shapes;
import java.awt.geom.RoundRectangle2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class TRoundRectangle extends TShape{
	private static final long serialVersionUID = 1L;
	public TRoundRectangle() {
		this.shape = new RoundRectangle2D.Double();
	}
	
	public TShape clone() {
		return new TRoundRectangle();
	}
	
	public void prepareDrawing(int x, int y) {
		RoundRectangle2D rectangle = (RoundRectangle2D) this.shape;
		rectangle.setRoundRect(x, y, 0, 0, 50, 50);
	}
	@Override
	public void keepDrawing(int x, int y) {
		RoundRectangle2D rectangle = (RoundRectangle2D) this.shape;
		rectangle.setFrame(rectangle.getX(), rectangle.getY(), x-rectangle.getX(), y-rectangle.getY());
	}
}