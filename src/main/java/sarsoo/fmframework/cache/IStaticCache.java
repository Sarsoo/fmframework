package sarsoo.fmframework.cache;

import sarsoo.fmframework.log.Log;

public interface IStaticCache<T, S> {
	
	public T get(S input);
	
	public T getNew(S input);
	
	public void add(T input);
	
	public void flush();
	
	public void dumpToLog(Log log);

}
