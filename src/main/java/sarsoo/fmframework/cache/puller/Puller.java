package sarsoo.fmframework.cache.puller;

public interface Puller<T, S> {
	
	public T pull(S input);
	
}
