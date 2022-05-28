package shapes;

import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import shapes.TAnchors.EAnchors;

public class TAnchors {
	
	//동그라미 사이즈 고정
	private final static int WIDTH = 15;
	private final static int HEIGHT = 15;
	
	public enum EAnchors {
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eRR(new Cursor(Cursor.HAND_CURSOR)),
		eMove(new Cursor(Cursor.MOVE_CURSOR));
		
		private Cursor cursor;

		EAnchors(Cursor cursor) {
			this.setCursor(cursor);
		}

		public Cursor getCursor() {
			return cursor;
		}
		public void setCursor(Cursor cursor) {
			this.cursor = cursor;
		}
	}
	
	private Ellipse2D anchors[];
	private EAnchors eSelectedAnchor, eResizeAnchor; //resizeAnchor = selectedAnchor의 대각선 앵커
	
	public EAnchors geteSelectedAnchor() {
		return eSelectedAnchor;
	}

	public void seteSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	
	public EAnchors geteResizeAnchor() {
		return eResizeAnchor;
	}
	
	public TAnchors() {
		this.anchors = new Ellipse2D.Double[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) { //eMove는 빼주기
			this.anchors[i] = new Ellipse2D.Double(); // array에 enum 순으로 각 점들을 넣어줌
		}
	}
	
	public boolean contains(int x, int y) {
		for (int i=0; i<EAnchors.values().length-1; i++) {
			if (this.anchors[i].contains(x,y)) {
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		return false;
	}
	
	public void draw(Graphics2D graphics2D, Rectangle BoundingRectangle) {
		for (int i=0; i<EAnchors.values().length-1; i++) {
			
			int x = BoundingRectangle.x - WIDTH/2;
			int y = BoundingRectangle.y - HEIGHT/2;
			int w = BoundingRectangle.width;
			int h = BoundingRectangle.height;
			
			EAnchors eAnchor = EAnchors.values()[i];
			switch (eAnchor) {
			case eNW:
				break;
			case eWW:
				y = y+h/2;
				break;
			case eSW:
				y = y+h;
				break;
			case eSS:
				x = x+w/2;
				y = y+h;
				break;
			case eSE:
				x = x+w;
				y = y+h;
				break;
			case eEE:
				x = x+w;
				y = y+h/2;
				break;
			case eNE:
				x = x+w;
				break;
			case eNN:
				x = x+w/2;
				break;
			case eRR:
				x = x+w/2;
				y = y-50;
				break;
			default:
				break;
			}
			this.anchors[eAnchor.ordinal()].setFrame(x, y, WIDTH, HEIGHT); //좌표세팅
			graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
	}

	public Point2D getResizeAnchorPoint(int x, int y) {
		switch (this.eSelectedAnchor) {
		case eNW:
			this.eResizeAnchor = EAnchors.eSE;
			break;
		case eWW:
			this.eResizeAnchor = EAnchors.eEE;
			break;
		case eSW:
			this.eResizeAnchor = EAnchors.eNE;
			break;
		case eSS:
			this.eResizeAnchor = EAnchors.eNN;
			break;
		case eSE:
			this.eResizeAnchor = EAnchors.eNW;
			break;
		case eEE:
			this.eResizeAnchor = EAnchors.eWW;
			break;
		case eNE:
			this.eResizeAnchor = EAnchors.eSW;
			break;
		case eNN:
			this.eResizeAnchor = EAnchors.eSS;
			break;
		default:
			break;
		}
		//resize원점
		Point2D point = new Point2D.Double(
				anchors[eResizeAnchor.ordinal()].getCenterX(), anchors[eResizeAnchor.ordinal()].getCenterY());
		return point;
	}	

}
