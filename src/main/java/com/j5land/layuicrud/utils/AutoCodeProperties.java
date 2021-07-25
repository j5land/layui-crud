package com.j5land.layuicrud.utils;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class AutoCodeProperties {

    /**
     * 获取配置信息
     */
    public static  Configuration getConfig() {
        try {
        	final PropertiesConfiguration configuration = new PropertiesConfiguration();
        	configuration.setEncoding("UTF-8");
        	configuration.load("auto-config.properties");
        	return configuration;
        } catch (ConfigurationException e) {
            System.out.println("获取配置文件失败");
        	e.printStackTrace();
        	return null;
        }
    }

    /**
     * 获取配置文件value
     *
     * @param key
     * @return
     */
    public  String getConfigkey(String key) {
    	return getConfig().getString(key);
    }

}
