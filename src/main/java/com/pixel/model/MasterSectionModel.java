package com.pixel.model;

import java.io.Serializable;

public class MasterSectionModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String masterid;
	 private String masterdesc;
	 private String orderid;
	 private String imagelink;
	 private String creationdate;
	 
	public String getMasterid() {
		return masterid;
	}
	public void setMasterid(String masterid) {
		this.masterid = masterid;
	}
	public String getMasterdesc() {
		return masterdesc;
	}
	public void setMasterdesc(String masterdesc) {
		this.masterdesc = masterdesc;
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
