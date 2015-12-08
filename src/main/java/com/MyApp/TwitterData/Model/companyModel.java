package com.MyApp.TwitterData.Model;

public class companyModel {
	private long id;
	private String companyName;
	private String screenName;
	private long twitter_id;
	private String location;
	private String description;
	private String url;
	private long followers_count;
	private long friends_count;
	private long listed_count;
	private String create_at;
	private long favourites_count;
	private boolean verified;
	private long statuses_count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public long getTwitter_id() {
		return twitter_id;
	}

	public void setTwitter_id(long twitter_id) {
		this.twitter_id = twitter_id;
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

	public long getListed_count() {
		return listed_count;
	}

	public void setListed_count(long listed_count) {
		this.listed_count = listed_count;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
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

	@Override
	public String toString() {
		String string = companyName + "\n" + screenName + "\n" + twitter_id
				+ "\n" + location + "\n" + description + "\n" + url + "\n"
				+ followers_count + "\n" + friends_count + "\n" + listed_count + "\n" + create_at
				+ "\n" + favourites_count + "\n" + verified + "\n"
				+ statuses_count;
		return string;
	}

}
