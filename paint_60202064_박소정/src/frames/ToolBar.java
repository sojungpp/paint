package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JToolBar;

import global.Constants.ETools;

public class ToolBar extends JToolBar {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private JButton button;
	private JComboBox<Integer> comboBox;
	
	//association
	private DrawingPanel drawingPanel;

	public ToolBar() {
		//components
		ButtonGroup buttonGroup = new ButtonGroup(); //등록한 것 중 하나만 눌려지게 하는 것
		ActionHandler actionHandler = new ActionHandler();
		ActionHandler2 actionHandler2 = new ActionHandler2();
		
		for(ETools eTool: ETools.values()) {
			JRadioButton toolButton = new JRadioButton(eTool.getLabel(), new ImageIcon(eTool.getImage()));
			toolButton.setActionCommand(eTool.name()); //string이름을 주는 것
			toolButton.addActionListener(actionHandler);
			toolButton.setToolTipText(eTool.getToolTip());
			this.add(toolButton);
			buttonGroup.add(toolButton);
		}
	
	JLabel label = new JLabel("두께");
	this.add(label);
	Integer[] thickness = {1,2,3,4,5,6,7,8,9,10};
	comboBox = new JComboBox<Integer>(thickness);
	comboBox.addActionListener(actionHandler2);
	comboBox.setToolTipText("두께를 변경합니다.");
	this.add(comboBox);
	
	button = new JButton("전체지우기");
	button.addActionListener(actionHandler2);
	button.setToolTipText("그림 전체를 지웁니다.");
	this.add(button);
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
	
	private class ActionHandler2 implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					drawingPanel.getShapesAll().clear();
					drawingPanel.repaint();
				} else if (e.getSource() == comboBox) {
					drawingPanel.setThickness(comboBox.getSelectedIndex()+1);
				}
		}	
	}


	
}
