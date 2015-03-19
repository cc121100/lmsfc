package com.cc.lmsfc.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertyUtil /*extends PropertyPlaceholderConfigurer */{
	/*private static Map<String, Object> ctxPropertiesMap;

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public static String getContextProperty(String name) {
		return (String) ctxPropertiesMap.get(name);
	}*/
	
	private static Logger logger = Logger.getLogger(PropertyUtil.class);
	
	private static Configuration config;
	
	public static Configuration loadProperties(String propertyPath){
		
		try {
			config = new PropertiesConfiguration(propertyPath);
		} catch (ConfigurationException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}   
		return config;
	}
	
	public static String getValue(String key){
		return config.getString(key);
	}
	
}
