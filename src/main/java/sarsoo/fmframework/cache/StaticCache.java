package sarsoo.fmframework.cache;

import java.util.ArrayList;
import java.util.Optional;

import sarsoo.fmframework.cache.puller.Puller;
import sarsoo.fmframework.log.Log;
import sarsoo.fmframework.log.Logger;
import sarsoo.fmframework.log.entry.ErrorEntry;
import sarsoo.fmframework.log.entry.LogEntry;

public class StaticCache<T extends Cacheable, S> implements IStaticCache<T, S> {

	private ArrayList<CacheEntry<T>> pool;
	private Puller<T, S> puller;

	public StaticCache(Puller<T, S> puller) {
		pool = new ArrayList<CacheEntry<T>>();
		this.puller = puller;
	}

	public void setPuller(Puller<T, S> puller) {
		this.puller = puller;
	}

	@Override
	public T get(S input) {

		Optional<CacheEntry<T>> item = pool.stream().filter(i -> i.getSubject().matches(input)).findFirst();

		if (item.isPresent()) {
			Logger.getLog().log(new LogEntry("getCachedItem").addArg("found").addArg(input.toString()));
			return item.get().getSubject();
		} else {
			Logger.getLog().log(new LogEntry("getCachedItem").addArg("pulling").addArg(input.toString()));
			
			T pulled = puller.pull(input);

			if (pulled != null) {
				Logger.getLog().log(new LogEntry("getCachedItem").addArg("pulled").addArg(input.toString()));
				pool.add(new CacheEntry<T>(pulled));
				return pulled;
			} else {
				Logger.getLog()
						.logError(new ErrorEntry("getCachedItem").addArg("null item").addArg(input.toString()));
				return null;
			}
		}
	}

	@Override
	public T getNew(S input) {
		Optional<CacheEntry<T>> item = pool.stream().filter(i -> i.getSubject().matches(input)).findFirst();

		if (item.isPresent()) {
			Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("removed").addArg(input.toString()));
			pool.remove(item);
		}
		
		Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("pulling").addArg(input.toString()));
		
		T pulled = puller.pull(input);

		if (pulled != null) {
			Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("pulled").addArg(input.toString()));
			pool.add(new CacheEntry<T>(pulled));
			return pulled;
		} else {
			Logger.getLog().logError(new ErrorEntry("getNewCachedItem").addArg("null item").addArg(input.toString()));
			return null;
		}

	}

	@Override
	public void flush() {
		pool.clear();
	}

	@Override
	public void dumpToLog(Log log) {
		pool.stream().forEach(i -> log.log(new LogEntry("dumpCache").addArg(i.toString())));
	}

}
