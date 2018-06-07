package sarsoo.fmframework.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Persister<T> {
	
	public void saveToFile(File file, T obj) {
		ObjectOutputStream out = null;
		try {
			
			out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(file)));
			
			out.writeObject(obj);
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public T readFromFile(File file) {
		
		ObjectInputStream in = null;
		T obj = null;
		try {
			
			in = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			obj = (T)in.readObject();
			
			in.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}

}
