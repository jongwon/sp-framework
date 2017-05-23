package com.sp.web.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Component
public class ModuleRegistry implements InitializingBean {

	@Autowired(required = false)
	private List<SpWebModule> moduleList;

	private Map<String, SpWebModule> moduleMap;

	private static ModuleRegistry singleTon;

	public static ModuleRegistry getInstance(){
		return singleTon;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		singleTon = this;
		moduleMap = new Hashtable<String, SpWebModule>();
		if(moduleList == null || moduleList.isEmpty()) return;

		for(SpWebModule module:moduleList){
			moduleMap.put(module.getModuleName(), module);
		}
	}

	public boolean hasModule(String moduleId){
		return moduleMap.containsKey(moduleId);
	}


	public List<SpWebModule> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<SpWebModule> moduleList) {
		this.moduleList = moduleList;
	}


}
