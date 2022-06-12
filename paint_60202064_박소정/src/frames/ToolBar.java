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
		ButtonGroup buttonGroup = new ButtonGroup(); //����� �� �� �ϳ��� �������� �ϴ� ��
		ActionHandler actionHandler = new ActionHandler();
		ActionHandler2 actionHandler2 = new ActionHandler2();
		
		for(ETools eTool: ETools.values()) {
			JRadioButton toolButton = new JRadioButton(eTool.getLabel(), new ImageIcon(eTool.getImage()));
			toolButton.setActionCommand(eTool.name()); //string�̸��� �ִ� ��
			toolButton.addActionListener(actionHandler);
			toolButton.setToolTipText(eTool.getToolTip());
			this.add(toolButton);
			buttonGroup.add(toolButton);
		}
	
	JLabel label = new JLabel("�β�");
	this.add(label);
	Integer[] thickness = {1,2,3,4,5,6,7,8,9,10};
	comboBox = new JComboBox<Integer>(thickness);
	comboBox.addActionListener(actionHandler2);
	comboBox.setToolTipText("�β��� �����մϴ�.");
	this.add(comboBox);
	
	button = new JButton("��ü�����");
	button.addActionListener(actionHandler2);
	button.setToolTipText("�׸� ��ü�� ����ϴ�.");
	this.add(button);
}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton defaultButton = (JRadioButton) this.getComponent(ETools.eSelection.ordinal());
		defaultButton.doClick(); //component�� doclick�Լ��� ��� �ٽ� JRadiobutton���� �ٲ㼭 doclick����
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
