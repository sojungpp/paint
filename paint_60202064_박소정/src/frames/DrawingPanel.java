package frames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JPanel;

import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import transformer.Drawer;
import transformer.Mover;
import transformer.Resizer;
import transformer.Rotator;
import transformer.Transformer;
import shapes.TAnchors.EAnchors;
import shapes.TSelection;
import shapes.TShape;

public class DrawingPanel extends JPanel {
	//attribute
	
	private static final long serialVersionUID = 1L;
	//components
	
	private boolean updated;
	
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing,
		eMoving
	} 
	
	private EDrawingState eDrawingState;
	private ETools selectedTool;
	private TShape currentShape;
	private Vector<TShape> shapes;
	private TShape temp;
	private TShape selectedShape;
	private Transformer transformer;

	public DrawingPanel() {
		//attribute
		this.setBackground(Color.WHITE); //여기서 white로 세팅해줌(default값은 회색)
		this.eDrawingState = EDrawingState.eIdle;
		this.updated = false;

		//components
		this.setShapesAll(new Vector<TShape>());
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler); 
		this.addMouseMotionListener(mouseHandler); 
		this.addMouseWheelListener(mouseHandler); 
		
	}
	
	public boolean isUpdated() {
		return this.updated;
	}
	
	public void setUpdated(boolean updated) {
		this.updated = updated;
	}
	
	//constructor
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		this.setShapesAll((Vector<TShape>) shapes);
		this.repaint(); // paint함수는 호출하면 안 됨. repaint사용해서 윈도우가 알아서 할 수 있도록.
	}

	public Object getShapes() {
		return this.getShapesAll();
	}

	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;	
	}
	
	public Vector<TShape> getShapesAll() {
		return shapes;
	}

	public void setShapesAll(Vector<TShape> shapes) {
		this.shapes = shapes;
	}
	
	public void setTemp(TShape temp) {
		this.temp = temp;
	}
	
	public TShape getTemp() {
		return temp;
	}
	
	public void paint(Graphics graphics) { 
		super.paint(graphics); //드로잉패널 자체를 그리라는 뜻, 저장하라는 것 필요 -> finishdrawing에 저장
		for(TShape shape: this.getShapesAll()) {
			shape.draw((Graphics2D)graphics);
		}	
	}
	
	private void prepareTransforming(int x, int y) {
		if (selectedTool == ETools.eSelection) {
			currentShape = onShape(x,y);
			if(currentShape != null) {
				EAnchors eAnchor = currentShape.getSelectedAnchor();
				if(eAnchor == EAnchors.eMove) {
					this.transformer = new Mover(this.currentShape);
				} else if(eAnchor == EAnchors.eRR) {
					this.transformer = new Rotator(this.currentShape);
				} else {
					this.transformer = new Resizer(this.currentShape);
				}
			} else {
				//onshape 도형이 없는 경우
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(this.currentShape);
			}
		} else {
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(this.currentShape);
		}
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.prepare(x,y,graphics2D);
	}
	
	private void keepTransforming(int x, int y) {
		//erase
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.currentShape.draw(graphics2D);
		
		//draw
		this.transformer.keepTransforming(x, y, graphics2D);
		this.currentShape.draw(graphics2D);
	}
	
	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x, y);
	}

	private void finishTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
		graphics2D.setXORMode(this.getBackground());
		this.transformer.finalize(x,y,graphics2D);
		
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		if(!(this.currentShape instanceof TSelection)) { 
			this.shapes.add(this.currentShape);
			this.selectedShape = this.currentShape; 
			this.selectedShape.setSelected(true); 
//			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
		this.repaint(); 
	}
	
	
	private TShape onShape(int x, int y) {
		for (TShape shape: this.getShapesAll()) {
			if (shape.contains(x, y)) { 
				return shape;
			}
		} return null;
	}
	
	private void changeSelection(int x, int y) { 
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(false);
		}
		//erase
		this.repaint(); // 모든걸 다 지우고 새로 그리는것
		//draw
		this.selectedShape = this.onShape(x, y);
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(true); //어차피 selected되니까 아래 코드의 draw됨
			this.selectedShape.draw((Graphics2D) this.getGraphics());
		}
	}

	private void changeCursor(int x, int y) { 
		Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		if(this.selectedTool == ETools.eSelection) {
			cursor = new Cursor(Cursor.DEFAULT_CURSOR);
			this.currentShape = this.onShape(x, y);
			if (currentShape != null) {
				cursor = new Cursor(Cursor.MOVE_CURSOR);
				cursor = this.currentShape.getSelectedAnchor().getCursor();
			} 
		}
		this.setCursor(cursor);
	}

	private class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					lBUttonClick(e);
				} else if (e.getClickCount() == 2) {
					lBUttonDoubliClick(e);
				}
			}
		}
		private void lBUttonClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(), e.getY()); //현재 앵커가 그려진 도형
				if (selectedTool.geteTransformationStyle() == ETransformationStyle.eNPTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointDrawing; 
				}
			} else if (eDrawingState == EDrawingState.eNPointDrawing) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		private void lBUttonDoubliClick(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		

		
		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				if(selectedTool.geteTransformationStyle() == ETransformationStyle.e2PTransformation) {
					prepareTransforming(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointDrawing;
				}	
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				finishTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {	
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointDrawing) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {	
		}
	}
}
