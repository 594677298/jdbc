package com.hotjee.plugin.c3p0;

import javax.sql.DataSource;

import com.hotjee.plugin.IDataSourceProvider;
import com.hotjee.plugin.IPlugin;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3p0Plugin implements IPlugin, IDataSourceProvider {
	
	private String url; // 数据库连接字符串
	private String driverClass; // 数据库驱动
	private String userName; // 用户名
	private String password; // 密码
	private int minPoolSize = 10; // 连接池中保留的最小连接数
	private int maxPoolSize = 20; // 连接池中保留的最大连接数
	private int maxIdleTime = 1800; // 最大空闲时间,1800秒内未使用则连接被丢弃,若为0则永不丢弃
	private int acquireIncrement = 3; // 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
	private int initialPoolSize = 10; // 初始化连接池大小
	private int idleConnectionTestPeriod = 60; // 每60秒检查所有连接池中的空闲连接
	private int acquireRetryAttempts = 5; // 定义在从数据库获取新连接失败后重复尝试的次数
	private ComboPooledDataSource dataSource = null;
	
	public C3p0Plugin(String url, String driverClass, String userName, String password){
		this.url = url;
		this.driverClass = driverClass;
		this.userName = userName;
		this.password = password;
	}

	/** 启动 */
	public boolean start() {
		dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl(url);
		dataSource.setUser(userName);
		dataSource.setPassword(password);
		try {
			dataSource.setDriverClass(driverClass);
		} catch (Exception e) {
			throw new RuntimeException("C3p0Plugin start error. message：" + e.getMessage());
		}
		dataSource.setMinPoolSize(minPoolSize);
		dataSource.setMaxPoolSize(maxPoolSize);
		dataSource.setMaxIdleTime(maxIdleTime);
		dataSource.setAcquireIncrement(acquireIncrement);
		dataSource.setInitialPoolSize(initialPoolSize);
		dataSource.setIdleConnectionTestPeriod(idleConnectionTestPeriod);
		dataSource.setAcquireRetryAttempts(acquireRetryAttempts);
		return true;
	}

	/** 关闭 */
	public boolean stop() {
		if(dataSource != null)
			dataSource.close();
		return true;
	}

	/** 获取数据源 */
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setMinPoolSize(int minPoolSize) {
		this.minPoolSize = minPoolSize;
	}

	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}

	public void setMaxIdleTime(int maxIdleTime) {
		this.maxIdleTime = maxIdleTime;
	}

	public void setAcquireIncrement(int acquireIncrement) {
		this.acquireIncrement = acquireIncrement;
	}

	public void setInitialPoolSize(int initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public void setIdleConnectionTestPeriod(int idleConnectionTestPeriod) {
		this.idleConnectionTestPeriod = idleConnectionTestPeriod;
	}

	public void setAcquireRetryAttempts(int acquireRetryAttempts) {
		this.acquireRetryAttempts = acquireRetryAttempts;
	}

}
