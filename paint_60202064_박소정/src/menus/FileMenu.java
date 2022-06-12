package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import frames.DrawingPanel;
import global.Constants.EFileMenu;

public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private File file;
	private DrawingPanel drawingPanel;

	public FileMenu(String title) {
		super(title); 
		
		this.file = null;
		
		ActionHandler actionHandler = new ActionHandler();
		for(EFileMenu eMenuItem: EFileMenu.values()) {
			JMenuItem menuItem = new JMenuItem(eMenuItem.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setActionCommand(eMenuItem.name());
			this.add(menuItem);
		}
	}
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	public void initialize() {
		// TODO Auto-generated method stub
		
	}
	
	private void store(File file) {
		try {
//			FileOutputStream fileOutputStream = new FileOutputStream(file);
//			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(file)));
			objectOutputStream.writeObject(this.drawingPanel.getShapes());
			 JOptionPane.showMessageDialog(null,file.getName()+ "로 저장되었습니다.");
			objectOutputStream.close();
			this.drawingPanel.setUpdated(false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load(File file) {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	public void newPanel() {
		int result = this.askSave();
		if(result != JOptionPane.CANCEL_OPTION) {
			if(result == JOptionPane.YES_OPTION) {
				this.save();
			}
			file = null;
			drawingPanel.getShapesAll().clear();
			drawingPanel.repaint();
		}
	}
	
	public void open() {
		int result = this.askSave();
		if(result == JOptionPane.YES_OPTION) {
			this.save();
		}
		JFileChooser fileChooser = new JFileChooser();
		int returnVal = fileChooser.showOpenDialog(this.drawingPanel); //얘 없어지면 다이얼로그도 없애기 (부모를 지정해준 것)
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.load(this.file);
		}
	}
	
	private int askSave() {
		if (this.drawingPanel.isUpdated()) {
			int result = JOptionPane.showConfirmDialog(this.drawingPanel, "변경된 사항을 저장하시겠습니까?");
			return result;
		} return -1;
	}
	
	public void save() {
		if(this.drawingPanel.isUpdated()) {
			if(this.file==null) this.saveAs();
			else this.store(this.file);
		}
	}
	
	public void saveAs() {
		JFileChooser fileChooser = new JFileChooser(this.file);
		int returnVal = fileChooser.showSaveDialog(this.drawingPanel); //얘 없어지면 다이얼로그도 없애기 (부모를 지정해준 것)
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = fileChooser.getSelectedFile();
			this.store(this.file);
			
		}
	}
	
	public void print() {
		this.save();
		JEditorPane editorPane;
		try {
			editorPane = new JEditorPane("file:///"+this.file.getPath());
			editorPane.print(null, null, true, null, null, false); 
			} catch (PrinterException e) {
				e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void quit() {
		this.askSave();
		System.exit(0);
	}
	
	class ActionHandler implements ActionListener {
		@Override
		//이런 형태 코드 바꾸기
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EFileMenu.eNew.name())) {
				newPanel();
			} else if (e.getActionCommand().equals(EFileMenu.eOpen.name())) {
				open();
			} else if (e.getActionCommand().equals(EFileMenu.eSave.name())) {
				save();
			} else if (e.getActionCommand().equals(EFileMenu.eSaveAs.name())) {
				saveAs();
			} else if (e.getActionCommand().equals(EFileMenu.ePrint.name())) {
				print();
			} else if (e.getActionCommand().equals(EFileMenu.eQuit.name())) {
				quit();
			}
		}	
	}


}
