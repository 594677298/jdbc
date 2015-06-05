package com.hotjee.plugin.activerecord;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class Config {

	private boolean showSql; // 是否显示sql语句
	private String name; // 数据源名称（多个数据源时，每个数据源名称必须不相同）
	private DataSource dataSource; // 数据源
	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	
	public Config(String name, DataSource dataSource) {
		this(name, dataSource, false);
	}
	
	public Config(String name, DataSource dataSource, boolean showSql) {
		this.name = name;
		this.dataSource = dataSource;
		this.showSql = showSql;
	}

	public final Connection getConnection() throws SQLException {
		Connection conn = threadLocal.get();
		if (conn != null)
			return conn;
		return showSql ? dataSource.getConnection() : dataSource.getConnection();
	}

	public final void setThreadLocalConnection(Connection connection) {
		threadLocal.set(connection);
	}

	public final void removeThreadLocalConnection() {
		threadLocal.remove();
	}

	public final void close(Connection conn) {
		if (threadLocal.get() == null){
			if (conn != null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String getName() {
		return name;
	}
	
}
