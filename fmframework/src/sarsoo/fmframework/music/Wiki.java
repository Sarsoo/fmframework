package sarsoo.fmframework.music;

import java.io.Serializable;

import sarsoo.fmframework.jframe.WikiView;

public class Wiki implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String summary;
	private String content;
	
	public Wiki(String date, String summary, String content) {
		this.setDate(date);
		this.setSummary(summary);
		this.setContent(content);
	}
	
	public void view(String name) {
		WikiView view = new WikiView(this, name);
		view.setVisible(true);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
