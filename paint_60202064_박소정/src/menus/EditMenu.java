package menus;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import frames.DrawingPanel;
import global.Constants.EEditMenu;
import shapes.TShape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	//associate
	private DrawingPanel drawingPanel;
	private TShape tempShape;
	protected TShape selectedShape;

	public EditMenu(String title) {
		super(title);
		
		ActionHandler actionHandler = new ActionHandler();
		for(EEditMenu eMenuItem: EEditMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.selectedShape = drawingPanel.getSelectedShape();
	}

	public void undo() {
		drawingPanel.setTemp((drawingPanel.getShapesAll().get(drawingPanel.getShapesAll().size()-1)));
		drawingPanel.getShapesAll().remove(drawingPanel.getShapesAll().size()-1);
		drawingPanel.setShapes(drawingPanel.getShapesAll());
	}
	
	public void redo() {
		drawingPanel.getShapesAll().add(drawingPanel.getTemp());
		drawingPanel.setShapes(drawingPanel.getShapesAll());
	}
	
	public void cut() {
		if (this.selectedShape != null) {
			this.tempShape = this.selectedShape;
			this.delete();
		}
	}
	
	public void delete() {
		if (this.selectedShape != null) {
			this.drawingPanel.getShapesAll().remove(this.selectedShape);
			this.selectedShape = null;
			this.repaint();
		}
	}
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				undo();
			} else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				redo();
			} else if (e.getActionCommand().equals(EEditMenu.eCut.name())) {
				cut();
			} else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
			} else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
//				paste();
			} else if (e.getActionCommand().equals(EEditMenu.eGroup.name())) {
			} else if (e.getActionCommand().equals(EEditMenu.eUnGroup.name())) {
			}
		}	
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}