package com.hotjee.plugin.activerecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbPro {

	private final Config config;
	private static final Map<String, DbPro> map = new HashMap<String, DbPro>();

	public DbPro(String configName) {
		config = DbKit.getConfig(configName);
		if (config == null)
			throw new RuntimeException("DbPro DbPro error. message: 没有找到" + configName);
	}

	public static DbPro use(String configName) {
		DbPro result = map.get(configName);
		if (result == null) {
			result = new DbPro(configName);
			map.put(configName, result);
		}
		return result;
	}

	public static DbPro use() {
		return use(ConstParams.DATASOURCE_NAME);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <T> List<T> query(Config config, Connection conn, String sql, Object... params) throws SQLException {
		List result = new ArrayList();
		// 预处理sql
		PreparedStatement pst = conn.prepareStatement(sql);
		// 添加查询条件
		if(params != null && params.length > 0){
			for (int i = 0; i < params.length; i++) {
				pst.setObject(i + 1, params[i]);
			}
		}
		// 返回结果集
		ResultSet rs = pst.executeQuery();
		// 获取查询多少列
		int colAmount = rs.getMetaData().getColumnCount();
		
		if (colAmount > 1) {
			while (rs.next()) {
				Object[] temp = new Object[colAmount];
				for (int i = 0; i < colAmount; i++) {
					temp[i] = rs.getObject(i + 1);
				}
				result.add(temp);
			}
		} else if (colAmount == 1) {
			while (rs.next()) {
				result.add(rs.getObject(1));
			}
		}
		DbKit.closeQuietly(rs, pst);
		return result;
	}

	public <T> List<T> query(String sql, Object... params) {
		Connection conn = null;
		try {
			conn = config.getConnection();
			return query(config, conn, sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			config.close(conn);
		}
		return null;
	}
}
