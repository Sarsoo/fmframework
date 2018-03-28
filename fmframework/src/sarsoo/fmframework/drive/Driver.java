package sarsoo.fmframework.drive;

import sarsoo.fmframework.gui.MainMenu;
import sarsoo.fmframework.util.Reference;

public class Driver {
	
	public static void main(String[] args) {
		Reference.setUserName("Sarsoo");
		
		MainMenu main = new MainMenu();
		main.setVisible(true);
	}

	
}
