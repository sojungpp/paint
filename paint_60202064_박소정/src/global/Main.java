package global;

import frames.MainFrame;

public class Main {
	
	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame(); //구조를 먼저 만듬
		mainFrame.setVisible(true); //size 등 계산 (트리따라서)
		mainFrame.initialize();
	}
}
