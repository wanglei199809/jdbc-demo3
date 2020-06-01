package cn.net.trimmer.jdbc.test;

import java.sql.Connection;

import cn.net.trimmer.jdbc.util.JdbcUtils;

public class TestUtil {

	public static void main(String[] args) {
		Connection conn = JdbcUtils.getConn();
		System.out.println(conn);
	}

}
