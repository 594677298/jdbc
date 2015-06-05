package com.hotjee.plugin;

import javax.sql.DataSource;

public interface IDataSourceProvider {

	/** 获取数据源 */
	DataSource getDataSource();
}
