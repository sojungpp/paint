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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //프로그램 종료
		
		//components
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar); // menubar는 특수해서 set사용, add대신
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel, BorderLayout.CENTER);
		
		//association 자식과 자식만 관계 설정 가능! 다른 것은 안됨.
		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
	}

	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
		
	}
}
