package com.hotjee.plugin;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.hotjee.plugin.activerecord.ActiveRecordPlugin;
import com.hotjee.plugin.activerecord.Db;
import com.hotjee.plugin.c3p0.C3p0Plugin;

public class TestPool{
   
	private C3p0Plugin c3p0 = null;
	
	@Before
	public void before(){
		String url = "jdbc:mysql://yqcloudout.mysql.rds.aliyuncs.com:3306/huiju_test?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
		String driveClass = "com.mysql.jdbc.Driver";
		String userName = "yunqi";
		String passowrd = "gotoapp2014";
		
		c3p0 = new C3p0Plugin(url, driveClass, userName, passowrd);
		/** 初始化连接池（不设置就是用默认设置） */
//		c3p0.setMaxPoolSize(100);
		c3p0.start(); // 获取数据源
		
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0);
		arp.start(); // 数据源配置信息放入 MAP 中方便程序获取
	}
	
	@Test
	public void one() throws SQLException{
		List<Object> list = Db.query("select name from t_company where id=?", new Object[]{1});
		for (Object obj : list) {
			System.out.println(obj);
			break;
		}
	}
	
	@After
	public void after(){
		c3p0.stop();
	}
	
}
