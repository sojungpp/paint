package frames;
import javax.swing.JMenuBar;

import menus.EditMenu;
import menus.FileMenu;
import menus.ColorMenu;

public class MenuBar extends JMenuBar {
	//attributes
	private static final long serialVersionUID = 1L;
	
	//components
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private ColorMenu colorMenu;
	
	private DrawingPanel drawingPanel;
	
	public MenuBar() {
		//components
		this.fileMenu = new FileMenu("파일");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("편집");
		this.add(this.editMenu);
		
		this.colorMenu = new ColorMenu("색깔");
		this.add(this.colorMenu);
		
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.colorMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		this.fileMenu.initialize();
		this.editMenu.initialize();
		this.colorMenu.initialize();
	}

}
