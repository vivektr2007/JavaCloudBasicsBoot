package com.pixel.model;

import java.util.List;

public class AddFormModel {

	private Integer menuId;
	private String subMenuId;
	private String titleId;
	private String shortDescId;
	private int prevId;
	private int nextId;
	private String tags;
	private int contentId;
	private String creationDate;
	private String parent;
	private String menuDesc;
	private String likeCount;
	private String titleIdUrl;
	
	
	
	public String getTitleIdUrl() {
		return titleIdUrl;
	}

	public void setTitleIdUrl(String titleIdUrl) {
		this.titleIdUrl = titleIdUrl;
	}

	private List<AddFormDetailModel> addFormDetailModel;

	public int getPrevId() {
		return prevId;
	}

	public void setPrevId(int prevId) {
		this.prevId = prevId;
	}

	public int getNextId() {
		return nextId;
	}

	public void setNextId(int nextId) {
		this.nextId = nextId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleIdUrl = titleId.replaceAll(" ", "-").replaceAll("/", "-").replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-");
		this.titleId = titleId;
	}

	public String getShortDescId() {
		return shortDescId;
	}

	public void setShortDescId(String shortDescId) {
		this.shortDescId = shortDescId;
	}

	public List<AddFormDetailModel> getAddFormDetailModel() {
		return addFormDetailModel;
	}

	public void setAddFormDetailModel(List<AddFormDetailModel> addFormDetailModel) {
		this.addFormDetailModel = addFormDetailModel;
	}

	public int getContentId() {
		return contentId;
	}

	public void setContentId(int contentId) {
		this.contentId = contentId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(String likeCount) {
		this.likeCount = likeCount;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
