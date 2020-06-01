package cn.net.trimmer.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * jdbc 工具类简单抽取
 * 
 * @author wl
 *
 */
public class JdbcUtils {
	private static final Properties prop = new Properties();
	private static final ThreadLocal<Connection> tol = new ThreadLocal<>();
	// 类加载时读取配置文件
	static {
		InputStream in = null;
		try {
			// 1. 从类路径加载配置文件
			in = JdbcUtils.class.getResourceAsStream("/cn/net/trimmer/jdbc/conf/db.properties");
			// 2. 解析配置文件
			prop.load(in);
			System.out.println("数据库配置文件加载完成...");
		} catch (IOException e) {
			e.printStackTrace();
			if (in != null) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取数据库连接对象
	 * 
	 * @return conn 数据库连接对象
	 */
	public static Connection getConn() {
		// 从线程局部变量中取连接对象
		Connection conn = tol.get();
		try {
			// 连接对象不存在则创建
			if (conn == null) {
				String driver = prop.getProperty("jdbc.driver");
				String url = prop.getProperty("jdbc.url");
				String username = prop.getProperty("jdbc.username");
				String password = prop.getProperty("jdbc.password");
				Class.forName(driver);
				conn = DriverManager.getConnection(url, username, password);
				// 存入连接对象到线程局部变量中
				tol.set(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return conn;
	}

	/**
	 * 释放资源
	 * 
	 * @param rs   执行结果集
	 * @param stm  sql操作对象
	 * @param conn 数据库连接
	 */
	public static void release(ResultSet rs, Statement stm, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		if (conn != null) {
			try {
				// 关闭连接
				conn.close();
				// 清除线程局部变量
				tol.remove();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
