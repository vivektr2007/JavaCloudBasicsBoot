package com.pixel.model;

public class BlogDescripitionModel {

	private Integer blogid;
	private String blogdesc;
	private String creationdate;
	private String category;
	private String linkid;
	private String blogtitle;

	public Integer getBlogid() {
		return blogid;
	}

	public void setBlogid(Integer blogid) {
		this.blogid = blogid;
	}

	public String getBlogdesc() {
		return blogdesc;
	}

	public void setBlogdesc(String blogdesc) {
		this.blogdesc = blogdesc;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getLinkid() {
		return linkid;
	}

	public void setLinkid(String linkid) {
		this.linkid = linkid;
	}

	public String getBlogtitle() {
		return blogtitle;
	}

	public void setBlogtitle(String blogtitle) {
		this.blogtitle = blogtitle;
	}

	@Override
	public String toString() {
		return "BlogDescripitionModel [blogid=" + blogid + ", blogdesc=" + blogdesc + ", creationdate=" + creationdate
				+ ", category=" + category + ", linkid=" + linkid + ", blogtitle=" + blogtitle + "]";
	}

	
	
}
