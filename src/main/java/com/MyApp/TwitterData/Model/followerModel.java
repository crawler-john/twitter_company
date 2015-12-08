package com.MyApp.TwitterData.Model;

public class followerModel {
	private long userId;
	private String screen_name;
	private String location;
	private String description;
	private String url;
	private long followers_count;
	private long friends_count;
	private long favourites_count;
	private boolean verified;
	private long statuses_count;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getScreen_name() {
		return screen_name;
	}
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public long getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(long followers_count) {
		this.followers_count = followers_count;
	}
	public long getFriends_count() {
		return friends_count;
	}
	public void setFriends_count(long friends_count) {
		this.friends_count = friends_count;
	}
	public long getFavourites_count() {
		return favourites_count;
	}
	public void setFavourites_count(long favourites_count) {
		this.favourites_count = favourites_count;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public long getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(long statuses_count) {
		this.statuses_count = statuses_count;
	}

	
}
