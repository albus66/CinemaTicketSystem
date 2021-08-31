//该类定义了一组常用的数据库命令执行及执行结果的处理方法
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLExec {

	private static Connection conn = null;

	// 该方法用于建立数据库连接
	public SQLExec() {
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String username = "root"; // 用户应根据本地数据库设置修改用户名密码
				String password = "123456";
				String url = "jdbc:mysql://localhost:3306/cinema_tickets?"
						+ "useUnicode=true&characterEncoding=UTF-8&useSSL=true";

				conn = DriverManager.getConnection(url, username, password);
			} catch (Exception e) {
				System.out.println("连接错误，请查看数据库是否运行正常");
				e.printStackTrace();
			}
		}
	}

	// 该方法用于从数据库中提取记录
	public ResultSet select(String query) throws SQLException {
		ResultSet res = null;
		if (!query.equals("")) {
			PreparedStatement statement = conn.prepareStatement(query);
			res = statement.executeQuery();
		}
		return res;
	}

	// 该方法用于更新数据库的记录
	public void update(String query) throws SQLException {
		if (!query.equals("")) {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.executeUpdate();
		}
	}

	// 该方法用于向数据库中插入记录
	public void insert(String query) throws SQLException {
			PreparedStatement statement = conn.prepareStatement(query);
			statement.executeUpdate();
	}

}
