package sarsoo.fmframework.drive;

import sarsoo.fmframework.gui.AlbumView;
import sarsoo.fmframework.music.Album;

public class Driver {
	
	public static void main(String[] args) {
		
		System.out.println("Hello World");
		AlbumView view = new AlbumView(Album.getAlbum("Recovery", "Eminem", "sarsoo"));
		view.setVisible(true);
	}

}
