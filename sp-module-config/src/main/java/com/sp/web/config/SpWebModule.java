package com.sp.web.config;


import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sp web 모듈을 만듦
 * 각 모듈에서 해당 모듈 이름으로 된 properties 파일을 읽어들인다.
 * 
 * @author jongwon
 *
 */
public abstract class SpWebModule implements InitializingBean, ServletContextAware {

	public static final String TRUE = "true";
	
	protected static final String PLUGIN_FILE = "/plugins/%s.xml";
	
	private Pattern VAR = Pattern.compile("\\$\\{([a-z\\.A-Z]+\\w*)\\}");
	
	private static Logger logger = LoggerFactory.getLogger(SpWebModule.class);
	
	private XMLConfiguration props;

	private XMLConfiguration xmlConfig = null;
	
	public void afterPropertiesSet(){
		ClassPathResource cpr = new ClassPathResource(String.format("/plugins/%s.xml", getModuleName()));
		try {
			InputStream inputStream = cpr.getInputStream();
			this.props = readXMLProperties(inputStream);
		} catch (IOException ioex){
			//
			ioex.printStackTrace();
		}
		
		if(xmlConfig != null){
			setProperties(xmlConfig);
		}
		xmlConfig = null;
	}
	
	public void reloadPropertiesSet(String pluginsDir){
		File propertiesFile = new File(pluginsDir+"/"+getModuleName()+".xml");
		if (!propertiesFile.exists()) {
			return;
		}
		try {
			this.props = readXMLProperties(new FileInputStream(propertiesFile));
		} catch (IOException ioex){
			ioex.printStackTrace();
		}
	}
	
	public static XMLConfiguration readXMLProperties(InputStream ins){
		try {
			XMLConfiguration prop = new XMLConfiguration();
			prop.read(ins);
			return prop;
		} catch (ConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public abstract String getModuleName();
	
	
	
	/**
	 * 이전 설정 값을 덮어쓴다.
	 * 
	 * @param new_properties
	 */
	public void setProperties(XMLConfiguration new_properties){
		Iterator<String> keys = new_properties.getKeys();
		String key; 
		Object value;
		while(keys.hasNext()){
			key = keys.next();
			value = new_properties.getProperty(key);
			props.setProperty(key, value);
			logger.info(String.format("%s -> %s 으로 설정...", key, value.toString()));
		}
	}

	public String getProperty(String name) {
        return props.getProperty(name).toString();
    }
	
	public String getProperty(String name, String defaultValue) {
        Object obj = props.getProperty(name);
        if(obj == null) return defaultValue;
        return obj.toString();
    }
	
	public XMLConfiguration getProperties(){
		return props;
	}
	
	public Map<String, String> getPropertiesMap(){
		Map<String, String> keyMap = new HashMap<String, String>();
		Iterator<String> keys = props.getKeys();
		String key = null;
		while(keys.hasNext()){
			key = keys.next();
			keyMap.put(key, props.getProperty(key).toString());
		}
		return keyMap;
	}
	
	public String getString(String key){
		return (String)props.getProperty(key);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getStringValues(String key){
		return (List<String>) props.getProperty(key);
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getIntegerValues(String key){
		return (List<Integer>) props.getProperty(key);
	}
	
	public Integer getInt(String key){
		return Integer.parseInt(getString(key));
	}
	
	public Boolean getBoolean(String key){
		return Boolean.parseBoolean(getString(key));
	}

	@SuppressWarnings("unchecked")
	public static List<String> getListProperty(Object property) {
		List<String> listProperty = null;
		if(property instanceof String){
			listProperty = new ArrayList<String>();
			listProperty.add((String)property);
		}else if(property instanceof List){
			listProperty = (List<String>) property;
		}else{
			if(property != null){
				logger.error(String.format("Unknown Object type [%s]", property.getClass()));
			}
			listProperty = new ArrayList<String>();
		}
		return listProperty;
	}
	
	/**
	 * ${} 로 된 system property를 치환해 준다.
	 * 
	 * @param value
	 * @return
	 */
	public String checkSystemProperties(String value){
		Matcher finds = VAR.matcher(value);
		while(finds.find()){
			String variable = finds.group(1);
			String systemValue = System.getProperty(variable);
			if(systemValue != null){
				value = value.replace("${"+variable+"}", systemValue);
			}
		}
		return value;
	}

	/**
	 * setServletContext 가 afterPropertiesSet 보다 먼저 호출이 된다
	 *
	 * @param servletContext
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		String plugins_dir = servletContext.getRealPath("/WEB-INF/plugins");
		File pluginsDir = new File(plugins_dir);
		if(pluginsDir.exists()){
			File propertiesFile = new File(plugins_dir+"/"+getModuleName()+".xml");
			if(propertiesFile.exists()){
				if(propertiesFile.exists()){
					try {
						xmlConfig = SpWebModule.readXMLProperties(new FileInputStream(propertiesFile));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}else{
				try {
					FileWriter fw = new FileWriter(propertiesFile);
					fw.write("<?xml version=\"1.0\"?>\n<properties>\n\t<!--##"+getModuleName()+" module's properties file made auth-->\t\n</properties>");
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
