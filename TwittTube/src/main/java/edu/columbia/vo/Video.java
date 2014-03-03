package edu.columbia.vo;

public class Video {
	
	private String videoId;
	private String videoLoc;
	private String userId;
	private String replyVideoId;
	
	public Video(String videoId, String userId,String videoLoc, String replyId) {
		this.videoId = videoId;
		this.videoLoc = videoLoc;
		this.userId = userId;
		this.replyVideoId = replyId;
	}
	
	public String getVideoId() {
		return videoId;
	}
	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}
	public String getVideoLoc() {
		return videoLoc;
	}
	public void setVideoLoc(String videoLoc) {
		this.videoLoc = videoLoc;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReplyVideoId() {
		return replyVideoId;
	}
	public void setReplyVideoId(String replyVideoId) {
		this.replyVideoId = replyVideoId;
	}

}
