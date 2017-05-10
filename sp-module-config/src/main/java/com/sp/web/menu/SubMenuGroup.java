package com.sp.web.menu;

import java.util.ArrayList;
import java.util.List;

public class SubMenuGroup implements OrderedMenu, RoledMenu {

	private int order;
	private List<Menu> subMenus;
	
	private List<String> roleList;
	
	public SubMenuGroup(){
		
	}
	
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public List<Menu> getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public void addSubMenu(Menu menu){
		if(this.subMenus == null) this.subMenus = new ArrayList<Menu>();
		this.subMenus.add(menu);
	}
	
	public List<String> getRoleList() {
		return roleList;
	}


	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}
	
	
}
