package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.music.Track;

public class GetLastTrackService extends Service<Track> {

	@Override
	protected Task<Track> createTask() {
		return new Task<Track>() {

			@Override
			protected Track call() throws Exception {

				Config config = FmFramework.getSessionConfig();

				FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

				Track lastTrack = net.getLastTrack();
				
				FmFramework.getTrackPool().add(lastTrack);
				FmFramework.getArtistPool().add(lastTrack.getArtist());
				
				if(lastTrack.getAlbum() != null) {
					FmFramework.getAlbumPool().add(lastTrack.getAlbum());
				}
				
				return lastTrack;
			}

			@Override
			protected void failed() {
				super.failed();

				Logger.getLog().logError(new ErrorEntry("failed to get last track"));
				
			}

		};
	}

}
