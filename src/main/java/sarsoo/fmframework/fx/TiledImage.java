package sarsoo.fmframework.fx;

import javafx.scene.image.Image;

public class TiledImage extends Image {
	
	private int index;
	
	public TiledImage(String url, int index) {
		super(url);
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
}
