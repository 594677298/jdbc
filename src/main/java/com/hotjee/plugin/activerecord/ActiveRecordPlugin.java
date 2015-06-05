package com.hotjee.plugin.activerecord;

import javax.sql.DataSource;

import com.hotjee.plugin.IDataSourceProvider;
import com.hotjee.plugin.IPlugin;

public class ActiveRecordPlugin implements IPlugin {
	
	private String configName;
	private IDataSourceProvider dataSourceProvider;
	
	public ActiveRecordPlugin(IDataSourceProvider dataSourceProvider){
		this(ConstParams.DATASOURCE_NAME, dataSourceProvider);
	}
	
	public ActiveRecordPlugin(String configName, IDataSourceProvider dataSourceProvider) {
		this.configName = configName.trim();
		this.dataSourceProvider = dataSourceProvider;
	}

	/** 启动 */
	public boolean start() {
		DataSource tdataSource = null;
		Config config = null;
		
		if(dataSourceProvider == null){
			throw new RuntimeException("ActiveRecordPlugin start error. message：数据源为空");
		}
		if (dataSourceProvider != null)
			tdataSource = dataSourceProvider.getDataSource();
		if(config == null)
			config = new Config(configName, tdataSource, false);
		DbKit.addConfig(config);
		Db.init();
		
		return true;
	}

	/** 关闭 */
	@Deprecated
	public boolean stop() {
		return true;
	}

}
