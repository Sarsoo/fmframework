package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.net.Key;
import sarsoo.fmframework.util.Reference;

public class GetScrobbleCountService extends Service<ScrobbleCount>{

	@Override
	protected Task<ScrobbleCount> createTask() {
		return new Task<ScrobbleCount>() {

			@Override
			protected ScrobbleCount call() throws Exception {
				
				FmUserNetwork net = new FmUserNetwork(Key.getKey(), Reference.getUserName());

				return new ScrobbleCount(net.getScrobblesToday(), net.getUserScrobbleCount());
			}
			
		};
	}

}
