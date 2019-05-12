package sarsoo.fmframework.fx.service;

import java.util.ArrayList;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.config.Config;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fx.FmFramework;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.music.Tag;

public class GetTagsService extends Service<ArrayList<Tag>> {

	@Override
	protected Task<ArrayList<Tag>> createTask() {
		return new Task<ArrayList<Tag>>() {

			@Override
			protected ArrayList<Tag> call() throws Exception {

				Config config = FmFramework.getSessionConfig();

				FmUserNetwork net = new FmUserNetwork(config.getValue("api_key"), config.getValue("username"));
				return net.getTags();

			}

		};
	}

	@Override
	protected void failed() {
		super.failed();

		Logger.getLog().logError(new ErrorEntry("failed to get tags"));

	}

}
