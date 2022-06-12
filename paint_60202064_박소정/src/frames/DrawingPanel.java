package frames;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
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
	private boolean fillCheck;
	
	private enum EDrawingState {
		eIdle,
		e2PointDrawing,
		eNPointDrawing,
		eMoving
	} 
	
	private Vector<TShape> shapes;
	private BufferedImage bufferedImage;
	private Graphics2D graphics2DBufferedImage;
	private ArrayList<TShape> shapeList;
	
	private EDrawingState eDrawingState;
	private ETools selectedTool;
	private TShape currentShape;
	private TShape temp;
	private TShape selectedShape;
	private Color lineColor;
	private Color fillColor;
	private Transformer transformer;
	private int thickness;

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
		this.lineColor = Color.black;
		//여기에 bufferedImage 넣으면 아직 사이즈 계산도 되지 않았는데 만들라고하니까 에러뜸, 그래서 구조 다 만들고 난 후인 initialize에 넣기
			
	}
	
	public void initialize() {
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(), this.getHeight());
		this.graphics2DBufferedImage = (Graphics2D) this.bufferedImage.getGraphics();
//		this.graphics2DBufferedImage.setXORMode(this.getBackground());
	}	
	
	public TShape getSelectedShape() {
		return this.selectedShape;
	}

	public void setSelectedShape(TShape selectedShape) {
		this.selectedShape = selectedShape;
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
		this.selectedShape=null;
		this.temp=null;
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
	
	public void cut() {
			this.temp = null;
			setTemp(this.selectedShape);
			this.delete();
	}
	
	public void delete() {
		if (this.selectedShape != null) {
				for (int i = 0; i <= shapes.size(); i++) {
					if(shapes.get(i)==this.selectedShape)
						this.shapes.remove(this.selectedShape);
					}
			this.selectedShape = null;
			this.repaint();
		}
	}
	
	public void paste() {
		if (this.temp != null) {
			this.shapes.add(this.temp);
			this.repaint();
		}
	}

	
	public void setThickness(int thickness) {
		if(selectedShape!=null) {
			selectedShape.setThickness(thickness);
			repaint();
		} else {
			this.thickness = thickness;
		}
	}
	
	public void setLineColor(Color lineColor) {
		if(selectedShape!=null) {
			selectedShape.setLineColor(lineColor);
			repaint();
		} else {
			this.lineColor = lineColor;
		}
		
	}

	public void setFillColor(Color fillColor) {
		if(selectedShape!=null) {
			selectedShape.setFillColor(fillColor);
			repaint();
		} else {
			this.fillColor = fillColor;
		}
	}
	
	public void paint(Graphics graphics) { 
		super.paint(graphics); //드로잉패널 자체를 그리라는 뜻, 저장하라는 것 필요 -> finishdrawing에 저장
		
		this.graphics2DBufferedImage.clearRect(0,0,this.bufferedImage.getWidth(), this.bufferedImage.getHeight());
		for(TShape shape: this.getShapesAll()) {
			shape.draw(this.graphics2DBufferedImage);
		}	
		graphics.drawImage(this.bufferedImage, 0, 0, this);
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
//		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
//		graphics2D.setXORMode(this.getBackground());
		this.transformer.prepare(x,y);
		this.graphics2DBufferedImage.setXORMode(this.getBackground());
	}
	
	private void keepTransforming(int x, int y) {
		//erase
//		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
//		this.graphics2DBufferedImage.setXORMode(this.getBackground());
		this.currentShape.draw(this.graphics2DBufferedImage);
		this.getGraphics().drawImage(this.bufferedImage,0,0,this);
		//transform
		this.transformer.keepTransforming(x, y);
		//draw
		this.currentShape.draw(this.graphics2DBufferedImage);
//		this.graphics2DBufferedImage.setPaintMode();
		this.getGraphics().drawImage(this.bufferedImage, 0, 0, this);
//		this.repaint(); 
	}
	
	private void continueTransforming(int x, int y) {
		this.currentShape.addPoint(x, y);
	}

	private void finishTransforming(int x, int y) {
//		Graphics2D graphics2D = (Graphics2D) this.getGraphics();
//		graphics2D.setXORMode(this.getBackground());
		this.graphics2DBufferedImage.setPaintMode();
		this.transformer.finalize(x,y);
//		this.currentShape.drawAnchors(this.graphics2DBufferedImage);
		
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
		this.selectedShape.getAffineTransform().setToIdentity();
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
		//draw anchors
		this.selectedShape = this.onShape(x, y);
		if(this.selectedShape != null) {
			this.selectedShape.setSelected(true); //어차피 selected되니까 아래 코드의 draw됨
			this.selectedShape.draw(this.graphics2DBufferedImage);
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
