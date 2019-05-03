package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.music.Track;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Reference;

public class GetLastTrackService extends Service<Track>{

	@Override
	protected Task<Track> createTask() {
		return new Task<Track>() {

			@Override
			protected Track call() throws Exception {
				
				FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());

				return net.getLastTrack();
			}
			
		};
	}

}
