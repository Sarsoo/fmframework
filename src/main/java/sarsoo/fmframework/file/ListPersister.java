package sarsoo.fmframework.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sarsoo.fmframework.util.FMObjList;

public class ListPersister {
	
	public void saveListToFile(File file, FMObjList list) {
		ObjectOutputStream out = null;
		try {
			
			out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream(file)));
			
			out.writeObject(list);
			out.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FMObjList readListFromFile(File file) {
		
		ObjectInputStream in = null;
		FMObjList list = null;
		try {
			
			in = new ObjectInputStream(new BufferedInputStream(
					new FileInputStream(file)));
			list = (FMObjList)in.readObject();
			
			in.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
}
