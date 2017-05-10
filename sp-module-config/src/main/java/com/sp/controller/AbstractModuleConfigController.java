package com.sp.controller;



import com.sp.web.config.ModuleRegistry;
import com.sp.web.config.SpWebModule;
import com.sp.web.menu.AbstractMenuManager;
import com.sp.web.menu.Menu;
import com.sp.web.menu.ShortcutMenu;
import com.sp.web.menu.SubMenuGroup;
import org.apache.commons.configuration2.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static java.lang.String.format;

/**
 *
 */
public class AbstractModuleConfigController {

	private Logger logger = LoggerFactory.getLogger(AbstractModuleConfigController.class);
	
	@Autowired
	protected ModuleRegistry moduleRegistry;
	

	protected void initMenus(AbstractMenuManager manager){

		logger.info("========== 모듈 리스트 세팅 ============");

		if(moduleRegistry == null || moduleRegistry.getModuleList() == null) return;

		XMLConfiguration prop = null;
		for(SpWebModule module:moduleRegistry.getModuleList()){
			
			logger.info(format(">>> %s 모듈", module.getModuleName()));
			if(module.getProperties() == null) continue;
			
			prop = module.getProperties();
			if(prop == null){
				logger.info(format(">>> %s 모듈에서 Properties 를 불러올 수 없음....", module.getModuleName()));
				continue;
			}
			
			// read top menu
			String id = (String)prop.getProperty("menugroup[@id]");
			if(id == null) continue;
			
			String menuTitle = (String)prop.getProperty("menugroup.name");
			String iconClass = (String)prop.getProperty("menugroup.iconClass");
			
			
			int order = getMenuOrder(prop, "menugroup[@order]");
			List<String> roles = SpWebModule.getListProperty(prop.getProperty("menugroup.auth.role"));
			
			if(menuTitle == null){
				logger.info(format(">>> %s 모듈에는 메뉴가 없음.", module.getModuleName()));
				continue;
			}
			logger.info(format(">>> %s 모듈에서 [%s] 메뉴가 발견됨.", module.getModuleName(), menuTitle));
			
			System.out.println(format("%s => %d, %s, %s %s", id, order, menuTitle, roles, iconClass));
			Menu menu = new Menu(id, order, menuTitle, null, roles);
			menu.setIconClass(iconClass);
			
			// read submenu list
			readSubMenuList(prop, menu, manager);
			
			readShortcutMenuList(prop, manager);
		}
		
//		Menu systemMenu = new Menu("system", Menu.SYSTEM_MENU_ORDER, "시스템", null, Arrays.asList("ADMIN"));
//		systemMenu.addSubMenu(new Menu("", Menu.SYSTEM_MENU_ORDER, "모듈관리", "/module/list", Arrays.asList("ADMIN")));
//		manager.addMenu(systemMenu);
		
		logger.info("========== 모듈 메뉴 검출 끝...  ============");
	}

	protected void readShortcutMenuList(XMLConfiguration prop, AbstractMenuManager manager) {
		List<String> shortcutMenus = SpWebModule.getListProperty(prop.getProperty("shortcutmenu.menus.menulist.menu"));
		for(String sub:shortcutMenus){
			sub = sub.trim();
			ShortcutMenu shortcutMenu = new ShortcutMenu();
			int order = getMenuOrder(prop, format("shortcutmenu.menus.%s[@order]", sub));
			String name = (String)prop.getString(format("shortcutmenu.menus.%s.name", sub));
			String href = (String)prop.getString(format("shortcutmenu.menus.%s.url", sub));
			String iconClass = (String)prop.getProperty(format("shortcutmenu.menus.%s.iconClass", sub));
			boolean popup = "true".equalsIgnoreCase((String)prop.getProperty(format("shortcutmenu.menus.%s.popup", sub)));
			
			List<String> roleList = SpWebModule.getListProperty(prop.getProperty(format("shortcutmenu.menus.%s.auth.role", sub)));
			name = name == null ? "": name.trim();
			href = href == null ? "" : href.trim();
			
			shortcutMenu.setHref(href);
			shortcutMenu.setName(name);
			shortcutMenu.setOrder(order);
			shortcutMenu.setRoleList(roleList);
			shortcutMenu.setIconClass(iconClass);
			shortcutMenu.setPopup(popup);
			
			logger.info(format("shortcut menu [%s] => %s (%s)", name, href, popup?"popup":"inpage"));
			manager.addShortcutMenu(shortcutMenu);
		}
	}

	protected void readSubMenuList(XMLConfiguration prop, Menu menu, AbstractMenuManager manager) {
		List<String> subMenus = SpWebModule.getListProperty(prop.getProperty("menugroup.menus.menulist.menu"));
		SubMenuGroup menugroup = new SubMenuGroup();
		menugroup.setOrder(getMenuOrder(prop, "menugroup.menus.menugroup[@order]"));
		List<String> groupRoleList = SpWebModule.getListProperty(prop.getProperty("menugroup.menus.menugroup.auth.role"));
		menugroup.setRoleList(groupRoleList);
		menu.addSubMenu(menugroup);
		
		for(String sub:subMenus){
			sub = sub.trim();
			
			String name = prop.getString(format("menugroup.menus.menugroup.%s.name", sub));
			String href = prop.getString(format("menugroup.menus.menugroup.%s.url", sub));
			
			List<String> roleList = SpWebModule.getListProperty(prop.getProperty(format("menugroup.menus.menugroup.%s.auth.role", sub)));
			name = name == null ? "": name.trim();
			href = href == null ? "" : href.trim();
			
			logger.info(format("%s => %s", name, href));
			menugroup.addSubMenu(new Menu(sub, name, href, roleList));
		}
		manager.addMenu(menu);
	}

	protected int getMenuOrder(XMLConfiguration prop, String path) {
		int order = Menu.DEFAULT_ORDER;
		try{
			order = Integer.parseInt((String)prop.getProperty(path));
		}catch(NumberFormatException ex){
			logger.error(format("sp.web.menu.order value shoud be number but %s", prop.getProperty("sp.web.menu.order")));
		}
		return order;
	}
	
}