package com.pixel.model;

import java.util.List;

public class BlogCommentBean {

	private int seq;
	private int topicId;
	private String commentDesc;
	private String commentBy;
	private String commentDate;
	
	private List<BlogCommentReplyBean> replyBeanList;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getTopicId() {
		return topicId;
	}
	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}
	public String getCommentDesc() {
		return commentDesc;
	}
	public void setCommentDesc(String commentDesc) {
		this.commentDesc = commentDesc;
	}
	public String getCommentBy() {
		return commentBy;
	}
	public void setCommentBy(String commentBy) {
		this.commentBy = commentBy;
	}
	public String getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}
	public List<BlogCommentReplyBean> getReplyBeanList() {
		return replyBeanList;
	}
	public void setReplyBeanList(List<BlogCommentReplyBean> replyBeanList) {
		this.replyBeanList = replyBeanList;
	}
	
	
}
