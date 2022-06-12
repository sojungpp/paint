package frames;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	//attribute
	private static final long serialVersionUID = 1L;
	
	//components
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;
	
	public MainFrame(){
		//attribute
		this.setSize(700,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //���α׷� ����
		
		//components
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar); // menubar�� Ư���ؼ� set���, add���
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		
		//association �ڽİ� �ڽĸ� ���� ���� ����! �ٸ� ���� �ȵ�.
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
	}
}
