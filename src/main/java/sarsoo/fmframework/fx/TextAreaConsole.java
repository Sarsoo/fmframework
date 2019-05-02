package sarsoo.fmframework.fx;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import sarsoo.fmframework.log.console.Console;

public class TextAreaConsole implements Console{
	
	private static TextAreaConsole instance;
	
	private TextArea output;
	
	private TextAreaConsole() {
		output = new TextArea();
	}
	
	public static TextAreaConsole getInstance(){
	    if(instance == null){
	        synchronized (TextAreaConsole.class) {
	            if(instance == null){
	                instance = new TextAreaConsole();
	            }
	        }
	    }
	    return instance;
	}
	
	public void write(final String string){
	    Platform.runLater(new Runnable() {
	        public void run() {
	            output.appendText(string + "\n");
	        }
	    });
	}
	
	public TextArea getTextArea() {
		return output;
	}
	
}
