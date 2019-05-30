package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import sarsoo.fmframework.fx.TiledImage;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;

public class GetFXImageService extends Service<TiledImage> {

	private String url;
	private int index;
	
	public GetFXImageService(String url, int index) {
		super();
		this.url = url;
		this.index = index;
	}

	@Override
	protected Task<TiledImage> createTask() {
		return new Task<TiledImage>() {

			@Override
			protected TiledImage call() throws Exception {
				
				return new TiledImage(url, index);
			}

		};
	}

	@Override
	protected void failed() {
		super.failed();
		
		Logger.getLog().logError(new ErrorEntry("failed to get image " + url));
		
	}

}
