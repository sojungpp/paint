package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

public class ToolBar extends JToolBar {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components

	
	//association
	private DrawingPanel drawingPanel;

	public ToolBar() {
		//components
		ButtonGroup buttonGroup = new ButtonGroup(); //등록한 것 중 하나만 눌려지게 하는 것
		ActionHandler actionHandler = new ActionHandler();
		
		for(ETools eTool: ETools.values()) {
			JRadioButton toolButton = new JRadioButton(eTool.getLabel(), new ImageIcon(eTool.getImage()));
			toolButton.setActionCommand(eTool.name()); //string이름을 주는 것
			toolButton.addActionListener(actionHandler);
			this.add(toolButton);
			buttonGroup.add(toolButton);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick(); //component에 doclick함수가 없어서 다시 JRadiobutton으로 바꿔서 doclick진행
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
				drawingPanel.setSelectedTool(ETools.valueOf(e.getActionCommand()));
		}	
	}


	
}
