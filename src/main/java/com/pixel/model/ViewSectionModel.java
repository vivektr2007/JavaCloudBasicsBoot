package com.pixel.model;

import java.io.Serializable;

public class ViewSectionModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String submasterdesc;
	private String contentid; 
	private String longdesc;
	private String contenttype;
	private String orderid;
	private String creationdate;
	private String subsectionid;
	private String title;
	private String tags;
	private String prev;
	private String next;
	
	public String getSubmasterdesc() {
		return submasterdesc;
	}
	public void setSubmasterdesc(String submasterdesc) {
		this.submasterdesc = submasterdesc;
	}
	public String getContentid() {
		return contentid;
	}
	public void setContentid(String contentid) {
		this.contentid = contentid;
	}
	public String getLongdesc() {
		return longdesc;
	}
	public void setLongdesc(String longdesc) {
		this.longdesc = longdesc;
	}
	public String getContenttype() {
		return contenttype;
	}
	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}
	public String getSubsectionid() {
		return subsectionid;
	}
	public void setSubsectionid(String subsectionid) {
		this.subsectionid = subsectionid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getPrev() {
		return prev;
	}
	public void setPrev(String prev) {
		this.prev = prev;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
