package sarsoo.fmframework.cache;

import java.time.LocalDateTime;
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
	private int expiryMinutes = 10;
	private boolean expires = false;

	public StaticCache(Puller<T, S> puller) {
		pool = new ArrayList<CacheEntry<T>>();
		this.puller = puller;
	}

	public StaticCache(Puller<T, S> puller, int expiry) {
		pool = new ArrayList<CacheEntry<T>>();
		this.puller = puller;
		this.expires = true;
		this.expiryMinutes = expiry;
	}

	public void setExpiryMinutes(int minutes) {
		this.expiryMinutes = minutes;
	}

	public void setExpires(boolean choice) {
		this.expires = choice;
	}

	public void setPuller(Puller<T, S> puller) {
		this.puller = puller;
	}

	@Override
	public T get(S input) {

		Optional<CacheEntry<T>> item = pool.stream().filter(i -> i.getSubject().matches(input)).findFirst();

		if (item.isPresent()) {
			Logger.getLog().log(new LogEntry("getCachedItem").addArg("found").addArg(input.toString()));

			if (expires) {
				if (item.get().getTime().isBefore(LocalDateTime.now().minusMinutes(expiryMinutes))) {
					pool.remove(item.get());
					return get(input);
				}
			}
			return item.get().getSubject();

		} else {
			Logger.getLog().log(new LogEntry("getCachedItem").addArg("pulling").addArg(input.toString()));

			T pulled = puller.pull(input);

			if (pulled != null) {
				Logger.getLog().log(new LogEntry("getCachedItem").addArg("pulled").addArg(input.toString()));
				pool.add(new CacheEntry<T>(pulled));
				propagateCache(pulled);
				return pulled;
			} else {
				Logger.getLog().logError(new ErrorEntry("getCachedItem").addArg("null item").addArg(input.toString()));
				return null;
			}
		}
	}

	@Override
	public T getNew(S input) {
		Optional<CacheEntry<T>> item = pool.stream().filter(i -> i.getSubject().matches(input)).findFirst();

		if (item.isPresent()) {
			Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("removed").addArg(input.toString()));
			pool.remove(item.get());
		}

		Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("pulling").addArg(input.toString()));

		T pulled = puller.pull(input);

		if (pulled != null) {
			Logger.getLog().log(new LogEntry("getNewCachedItem").addArg("pulled").addArg(input.toString()));
			pool.add(new CacheEntry<T>(pulled));
			propagateCache(pulled);
			return pulled;
		} else {
			Logger.getLog().logError(new ErrorEntry("getNewCachedItem").addArg("null item").addArg(input.toString()));
			return null;
		}

	}

	@Override
	public void add(T input) {
		Optional<CacheEntry<T>> item = pool.stream().filter(i -> i.getSubject().matches(input)).findFirst();

		Logger.getLog().log(new LogEntry("addCachedItem").addArg(input.toString()));

		if (item.isPresent()) {
			Logger.getLog().log(new LogEntry("addCachedItem").addArg("replaced").addArg(input.toString()));
			pool.remove(item.get());
		}

		pool.add(new CacheEntry<T>(input));

	}

	protected void propagateCache(T in) {
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
