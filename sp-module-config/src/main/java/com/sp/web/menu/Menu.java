package com.sp.web.menu;

import java.util.List;

/**
 * 접근 권한에 대한 정보가 필요함.
 * 
 * 메뉴 id 가 같으면 하위 메뉴를 sidebar로 구분해 같은 메뉴로 넣는다.
 * 메뉴 order 값이 작은 순서대로 앞으로 배열한다. 
 * 
 * - sp.web.menu.order 값으로 메뉴의 순서를 결정한다.
 * - 메뉴 순서를 주지 않으면, 기본 값은 100 이다.
 * - 시스템 메뉴는 10000 이다. 가장 뒤에 나올 것을 권장한다.
 * - 앞에 나올 메뉴들은 10, 1, 5, 80, 150... 이런 식으로 주면 된다. 마이너스 값도 허용한다.
 * 
 * @author jongwon
 *
 */
public class Menu implements OrderedMenu, RoledMenu {
	
	public static final int DEFAULT_ORDER = 100;
	public static final int SYSTEM_MENU_ORDER = 10000;
	
	private String id;
	private int order;
	private String name;
	private String href;
	private String iconClass;
	private List<String> roleList;
	private SortedMenuList<SubMenuGroup> subMenus;
	private boolean leaf;
	
	public Menu(String id, String name, String href, List<String> roles){
		this(id, -1, name, href, true, roles);
	}
	
	public Menu(String id, int order, String name, String href, List<String> roles){
		this(id, order, name, href, true, roles);
	}
	
	public Menu(String id, int order, String name, String href, boolean leaf, List<String> roles){
		this.id = id;
		this.order = order;
		this.name = name;
		this.href = href;
		this.leaf = leaf;
		this.roleList = roles;
	}
	
	public List<String> getRoleList(){
		return this.roleList;
	}
	
	public String getId(){
		return this.id;
	}
	public  int getOrder(){
		return this.order;
	}
	
	public String getName() {
		return name;
	}
	
	public String getHref() {
		return href;
	}
	
	public SortedMenuList<SubMenuGroup> getSubMenus() {
		return subMenus;
	}
	
	public void addSubMenu(SubMenuGroup menugroup){
		if(this.subMenus == null) this.subMenus = new SortedMenuList<SubMenuGroup>();
		this.subMenus.add(menugroup);
	}
	
	public void setSubMenus(SortedMenuList<SubMenuGroup> subMenus) {
		this.subMenus = subMenus;
	}
	public boolean isLeaf() {
		return leaf;
	}
	
	public String getIconClass() {
		return iconClass;
	}
	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}
	
}
