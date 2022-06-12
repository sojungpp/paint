package transformer;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import shapes.TShape;
import shapes.TAnchors.EAnchors;

public class Resizer extends Transformer {
	protected double xScale, yScale; //(몇 배인지)

	public Resizer(TShape selectedShape) {
		super(selectedShape);
	}

	@Override
	public void prepare(int x, int y) {
		this.px = x;
		this.py = y;
		Point2D resizeAnchorPoint = this.anchors.getResizeAnchorPoint(x, y);
		this.cx = resizeAnchorPoint.getX();
		this.cy = resizeAnchorPoint.getY();
	}

	@Override
	public void keepTransforming(int x, int y) {
		this.getResizeScale(x, y);
		this.affineTransform.translate(cx, cy); //계산 전 원점 이동
		this.affineTransform.scale(xScale, yScale);
		this.affineTransform.translate(-cx, -cy); //계산 후 원위치
		this.px = x; 
		this.py = y;
	}

	private void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.anchors.geteResizeAnchor();
		double w1 = px-cx;
		double w2 = x-cx;
		double h1 = py-cy;
		double h2 = y-cy;
		//배율
		
		switch (eResizeAnchor) {
		case eNW: xScale = w2/w1; yScale= h2/h1; break;
		case eWW: xScale = w2/w1; yScale=1.0; break;
		case eSW: xScale = w2/w1; yScale = h2/h1; break;
		case eSS: xScale = 1.0; yScale = h2/h1; break;
		case eSE: xScale = w2/w1; yScale = h2/h1; break;
		case eEE: xScale = w2/w1; yScale=1.0; break;
		case eNE: xScale = w2/w1; yScale = h2/h1; break;
		case eNN: xScale = 1.0; yScale = h2/h1; break;
		default: break;
		}
		
	}

	@Override
	public void finalize(int x, int y) {
		this.shape = this.affineTransform.createTransformedShape(this.shape); //누적시키지 않도록
//		this.affineTransform.setToIdentity(); //누적 데이터를 초기화시키기

	}

}
