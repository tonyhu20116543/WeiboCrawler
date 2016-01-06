package pku.java.bean;


public class Weibo {
	
	private String content;
	private String postDate;
	private String device;
	private Integer shareNum;
	private Integer commentNum;
	private Integer like;
	private boolean isForward;
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getPostDate() {
		return postDate;
	}
	
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	
	public String getDevice() {
		return device;
	}
	
	public void setDevice(String device) {
		this.device = device;
	}
	
	public Integer getShareNum() {
		return shareNum;
	}
	
	public void setShareNum(Integer shareNum) {
		this.shareNum = shareNum;
	}
	
	public Integer getCommentNum() {
		return commentNum;
	}
	
	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}
	
	public Integer getLike() {
		return like;
	}
	
	public void setLike(Integer like) {
		this.like = like;
	}
	
	public boolean isForward() {
		return isForward;
	}
	
	public void setForward(boolean isForward) {
		this.isForward = isForward;
	}
	
}
