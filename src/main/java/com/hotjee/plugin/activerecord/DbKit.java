package com.hotjee.plugin.activerecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbKit {
	
	private static Map<String, Config> configNameToConfig = new HashMap<String, Config>();
	
	public static void addConfig(Config config) {
		if (config == null)
			throw new RuntimeException("DbKit addConfig error. message：参数不能为NULL");
		if (configNameToConfig.containsKey(config.getName()))
			throw new RuntimeException("DbKit addConfig error. message：" + config.getName() + "已存在");
		
		configNameToConfig.put(config.getName(), config);
	}
	
	public static Config getConfig(String configName) {
		return configNameToConfig.get(configName);
	}
	
	public static void removeConfig(String configName){
		if(configName == null)
			throw new RuntimeException("DbKit removeConfig error. message：数据源名称不能为NULL");
		if(!configNameToConfig.containsKey(configName))
			throw new RuntimeException("DbKit removeConfig error. message：没有找到" + configName);
		configNameToConfig.remove(configName);
	}
	
	public static void closeQuietly(ResultSet rs, Statement st) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException("DbKit closeQuietly error. message：" + e.getMessage());
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new RuntimeException("DbKit closeQuietly error. message：" + e.getMessage());
			}
		}
	}
}
