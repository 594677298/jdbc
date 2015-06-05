package com.hotjee.plugin.activerecord;

import java.util.List;

public class Db {
	
	private static DbPro dbPro = null;
	
	static void init() {
		dbPro = DbPro.use();
	}

	public static <T> List<T> query(String sql, Object... paras) {
		return dbPro.query(sql, paras);
	}
}
