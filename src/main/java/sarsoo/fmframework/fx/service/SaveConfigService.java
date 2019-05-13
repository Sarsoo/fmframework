package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.config.ConfigPersister;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class SaveConfigService extends Service<Integer> {
	
	protected Config config;
	protected String path;
	
	public SaveConfigService(String path, Config config) {
		this.path = path;
		this.config = config;
	}

	@Override
	protected Task<Integer> createTask() {
		return new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {

				ConfigPersister persister = new ConfigPersister();
				
				while(!this.isCancelled()) {
					persister.saveConfig(path, config);
					Logger.getLog().log(new LogEntry("save config").addArg("config saved"));
					Thread.sleep(60000);
				}

				return 0;
			}
			
			@Override
			protected void failed() {
				super.failed();
				
				Logger.getLog().logError(new ErrorEntry("failed to save config"));
				
			}

		};
	}

}
