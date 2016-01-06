package pku.java.bean;

import java.util.List;

public class User {

	private String userid;
	private String username;
	private String intro;
	private Integer followers;
	private Integer following;
	private Integer weiboNum;
	private String location;
	private String profile;
	private String domain;
	private String blog;
	private String tag;
	
	private List<Weibo> weibos;
	
	public String getUserid() {
		return userid;
	}
	
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	public Integer getFollowers() {
		return followers;
	}
	
	public void setFollowers(Integer followers) {
		this.followers = followers;
	}
	
	public Integer getFollowing() {
		return following;
	}
	
	public void setFollowing(Integer following) {
		this.following = following;
	}
	
	public Integer getWeiboNum() {
		return weiboNum;
	}

	public void setWeiboNum(Integer weiboNum) {
		this.weiboNum = weiboNum;
	}
	
	public String getLocation() {
		return location;
	}
	
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getProfile() {
		return profile;
	}
	
	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getBlog() {
		return blog;
	}
	
	public void setBlog(String blog) {
		this.blog = blog;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public List<Weibo> getWeibos() {
		return weibos;
	}
	
	public void setWeibos(List<Weibo> weibos) {
		this.weibos = weibos;
	}
}
