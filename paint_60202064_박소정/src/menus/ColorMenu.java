package menus;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frames.DrawingPanel;
import global.Constants.EColorMenu;
import global.Constants.EEditMenu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

public class ColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	
	//association
	private DrawingPanel drawingPanel;

	public ColorMenu(String title) {
		super(title);
		
		ActionHandler actionHandler = new ActionHandler();
		for(EColorMenu eColorMenu: EColorMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eColorMenu.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eColorMenu.name());
			menuItem.setAccelerator(KeyStroke.getKeyStroke(eColorMenu.getKeyvalue(), InputEvent.CTRL_MASK));
			this.add(menuItem);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void setLineColor() {
		Color lineColor = JColorChooser.showDialog(null, "Line Color", Color.black);
		drawingPanel.setLineColor(lineColor);
	}
	
	public void setFillColor() {
		Color lineColor = JColorChooser.showDialog(null, "Fill Color", null);
		drawingPanel.setFillColor(lineColor);
	}

	
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EColorMenu.eLineColor.name())) {
				setLineColor();
			} else if (e.getActionCommand().equals(EColorMenu.eFillColor.name())) {
				setFillColor();
			}
		}	
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
}