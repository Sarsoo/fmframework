package sarsoo.fmframework.music;

import java.io.Serializable;

public class Tag implements Comparable<Tag>, Serializable{
	
	private String name;
	private String url;
	private int count;
	private int reach;
	private int taggings;
	private Boolean streamable;
	private String summary;
	private String content;
	
	public Tag(String name, String url) {
		this.setName(name);
		this.setUrl(url);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString() {
		return "Tag: " + name;
	}

	@Override
	public int compareTo(Tag arg0) {
		return name.compareTo(arg0.getName());
	}
}
