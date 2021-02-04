package com.pixel.model;

import java.io.Serializable;

public class SubSectionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String masterid;
	private String submasterid;
	private String submasterdesc;
	private String orderid;
	private String imagelink;
	private String creationdate;
	
	public String getMasterid() {
		return masterid;
	}
	public void setMasterid(String masterid) {
		this.masterid = masterid;
	}
	public String getSubmasterid() {
		return submasterid;
	}
	public void setSubmasterid(String submasterid) {
		this.submasterid = submasterid;
	}
	public String getSubmasterdesc() {
		return submasterdesc;
	}
	public void setSubmasterdesc(String submasterdesc) {
		this.submasterdesc = submasterdesc;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getImagelink() {
		return imagelink;
	}
	public void setImagelink(String imagelink) {
		this.imagelink = imagelink;
	}
	public String getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}
	
}
