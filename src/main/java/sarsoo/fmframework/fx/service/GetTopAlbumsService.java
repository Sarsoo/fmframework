package sarsoo.fmframework.fx.service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import sarsoo.fmframework.fm.FmUserNetwork;
import sarsoo.fmframework.fm.TimePeriod;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.util.FMObjList;

public class GetTopAlbumsService extends Service<FMObjList> {

	private int limit;
	private TimePeriod period;
	private FmUserNetwork net;
	
	public GetTopAlbumsService(TimePeriod period, int limit, FmUserNetwork net) {
		super();
		this.limit = limit;
		this.period = period;
		this.net = net;
	}

	@Override
	protected Task<FMObjList> createTask() {
		return new Task<FMObjList>() {

			@Override
			protected FMObjList call() throws Exception {
				
				return net.getTopAlbums(period, limit);
			}

		};
	}

	@Override
	protected void failed() {
		super.failed();
		
		Logger.getLog().logError(new ErrorEntry("failed to get top albums"));
		
	}

}
