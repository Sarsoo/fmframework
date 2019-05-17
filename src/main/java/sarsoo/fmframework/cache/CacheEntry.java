package sarsoo.fmframework.cache;

import java.time.LocalDateTime;

public class CacheEntry<T> {
	
	private LocalDateTime date;
	private T subject;
	
	public CacheEntry(T input) {
		date = LocalDateTime.now();
		subject = input;
	}
	
	public LocalDateTime getTime() {
		return date;
	}
	
	public T getSubject() {
		return subject;
	}
	
	@Override
	public String toString() {
		return date.toString() + ' ' + subject.toString();
	}

}
