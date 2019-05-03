package sarsoo.fmframework.fx.service;

import java.util.ArrayList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.music.Tag;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Reference;

public class GetTagsService extends Service<ArrayList<Tag>>{

	@Override
	protected Task<ArrayList<Tag>> createTask() {
		return new Task<ArrayList<Tag>>() {

			@Override
			protected ArrayList<Tag> call() throws Exception {
				
				FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());
				return net.getTags();
				
			}
			
		};
	}

}
