package com.pixel.model;

public class MenuBean {

	private int menuId;
	private String menuDesc;
	private String menuDescUrl;
	private String parentUrl;
	
	private String parent;
	private String is_visible;
	private String target;
	
	
	public String getMenuDescUrl() {
		return menuDescUrl;
	}
	public void setMenuDescUrl(String menuDescUrl) {
		this.menuDescUrl = menuDescUrl;
	}
	public String getParentUrl() {
		return parentUrl;
	}
	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public String getMenuDesc() {
		return menuDesc;
	}
	public void setMenuDesc(String menuDesc) {
		this.menuDescUrl = menuDesc.replaceAll(" ", "-").replaceAll("/", "-").replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-");
		this.menuDesc = menuDesc;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parentUrl = parent.replaceAll(" ", "-").replaceAll("/", "-").replaceAll("\\?", "-").replaceAll("\\.", "-").replaceAll("\\,", "-");
		this.parent = parent;
	}
	public String getIs_visible() {
		return is_visible;
	}
	public void setIs_visible(String is_visible) {
		this.is_visible = is_visible;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	@Override
	public String toString() {
		return "MenuBean [menuId=" + menuId + ", menuDesc=" + menuDesc + ", parent=" + parent + ", is_visible="
				+ is_visible + ", target=" + target + "]";
	}
	
	
}
