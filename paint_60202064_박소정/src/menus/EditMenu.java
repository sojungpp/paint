package menus;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EEditMenu;
import shapes.TShape;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

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
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eMenuItem.getKeyvalue(), InputEvent.CTRL_MASK));
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
	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EEditMenu.eUndo.name())) {
				undo();
			} else if (e.getActionCommand().equals(EEditMenu.eRedo.name())) {
				redo();
			} else if (e.getActionCommand().equals(EEditMenu.eCut.name())) {
				drawingPanel.cut();
			} else if (e.getActionCommand().equals(EEditMenu.eCopy.name())) {
				drawingPanel.copy();
			} else if (e.getActionCommand().equals(EEditMenu.ePaste.name())) {
				drawingPanel.paste();
			} else if (e.getActionCommand().equals(EEditMenu.eGroup.name())) {
			} else if (e.getActionCommand().equals(EEditMenu.eUnGroup.name())) {
			}
		}	
	}

	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}