package com.sp.web.menu;

import java.util.List;


/**
 * 상단의 shortcut 메뉴 객체 
 * 
 * 
 * @author skyisblue
 *
 */
public class ShortcutMenu implements OrderedMenu, RoledMenu {

	private int order;
	private String name;
	private String href;
	private String iconClass;
	private List<String> roleList;
	private boolean popup;
	
	public ShortcutMenu(){
		
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public boolean isPopup() {
		return popup;
	}

	public void setPopup(boolean popup) {
		this.popup = popup;
	}
	
}
