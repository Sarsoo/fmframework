package sarsoo.fmframework.fm;

public class User {
	
	private String userName;
	private String realName;
	
	private String url;
	private String country;
	
	private int age;
	private char gender;
	
	private boolean isSubscriber;
	
	private int playCount;
	
	public User(String userName, String realName, String url, String country, int age, char gender, int playCount) {
		this.userName = userName;
		this.realName = realName;
		
		this.url = url;
		this.country = country;
		
		this.age = age;
		this.gender = gender;
		
//		this.isSubscriber = isSubscriber;
		
		this.playCount = playCount;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getCountry() {
		return country;
	}
	
	public int getAge() {
		return age;
	}
	
	public char getGender() {
		return gender;
	}
	
	public boolean isSubscriber() {
		return isSubscriber;
	}
	
	public int getScrobbleCount() {
		return playCount;
	}
}
