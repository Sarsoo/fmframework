package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;

public class GetScrobbleCountService extends Service<ScrobbleCount> {

	@Override
	protected Task<ScrobbleCount> createTask() {
		return new Task<ScrobbleCount>() {

			@Override
			protected ScrobbleCount call() throws Exception {

				Config config = FmFramework.getSessionConfig();

				FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));

				return new ScrobbleCount(net.getScrobblesToday(), net.getUserScrobbleCount());
			}
			
			@Override
			protected void failed() {
				super.failed();
				
				Logger.getLog().logError(new ErrorEntry("failed to get scrobble count"));
				
			}

		};
	}

}
