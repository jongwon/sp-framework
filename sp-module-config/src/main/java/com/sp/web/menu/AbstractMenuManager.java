package com.sp.web.menu;


import java.util.HashMap;
import java.util.Map;

/**
 * 메뉴를 관리하는 메뉴 매니저
 * 
 * 
 * @author jongwon
 *
 */
public class AbstractMenuManager {

	protected Map<String, Menu> menuMap;
	protected SortedMenuList<Menu> menuList;
	protected SortedMenuList<ShortcutMenu> shortcutMenuList;
	
	
	public SortedMenuList<Menu> getMenus(){
		return menuList;
	}

	public void addMenu(Menu menu) {
		if(menuMap == null){
			menuMap = new HashMap<String, Menu>();
		}
		if(menuList == null){
			menuList = new SortedMenuList<Menu>();
		}
		Menu top = menuMap.get(menu.getId());
		
		if(top == null){
			top = menu;
			menuMap.put(top.getId(), top);
			menuList.add(top);
			return;
		}
		top.addSubMenu(menu.getSubMenus().get(0));
	}

	public void addShortcutMenu(ShortcutMenu menu){
		if(this.shortcutMenuList == null) this.shortcutMenuList = new SortedMenuList<ShortcutMenu>();
		this.shortcutMenuList.add(menu);
	}
	
	public SortedMenuList<ShortcutMenu> getShortcutMenuList() {
		return shortcutMenuList;
	}

	public void setShortcutMenuList(SortedMenuList<ShortcutMenu> shortcutMenuList) {
		this.shortcutMenuList = shortcutMenuList;
	}

	public SortedMenuList<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(SortedMenuList<Menu> menuList) {
		this.menuList = menuList;
	}
}

