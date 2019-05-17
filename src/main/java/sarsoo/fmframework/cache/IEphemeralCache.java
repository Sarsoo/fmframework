package sarsoo.fmframework.cache;

import sarsoo.fmframework.cache.puller.Puller;
import sarsoo.fmframework.log.Log;

public interface IEphemeralCache<T> {
	
	public <S> T get(Puller<T, S> input);
	
	public <S> T getNew(Puller<T, S> input);
	
	public void flush();
	
	public void dumpToLog(Log log);

}
