//该类定义了一组生成查询命令的方法，用于从数据库中提取或者更新数据
package util;

import com.Movie;
import com.Order;
import com.Show;
import com.Staff;

public class QueryCreate {

	// 该方法用于生成查询命令，获得表格里所有记录
	public static String queryForResults(String tableName) {

		String str = "SELECT * FROM " + tableName;
		return str;
	}

	// 该方法用于生成基于整数项的查询命令
	public static String queryForResults(String tableName, String fieldName,
			int fieldVal) {

		String str = "SELECT * FROM " + tableName + " WHERE " + fieldName
				+ " = " + fieldVal;
		return str;
	}
	
	// 该方法用于生成基于小数项的查询命令
	public static String queryForResults(String tableName, String fieldName,
			double fieldVal) {

		String str = "SELECT * FROM " + tableName + " WHERE " + fieldName
				+ " = " + fieldVal;
		return str;
	}

	// 该方法用于生成基于string项的查询命令, 用like的方法
	public static String queryForResults(String tableName, String fieldName,
			String fieldVal) {

		String str = "SELECT * FROM " + tableName + " WHERE " + fieldName
				+ " LIKE '%" + fieldVal + "%'";
		return str;
	}

	// 该方法用于生成用户身份的查询命令
	public static String queryByCredential(String username, String password) {
		String str = "SELECT * FROM staff WHERE username = '" + username
				+ "' AND password = '" + password + "'";
		return str;
	}

	// 该方法用于生成更新电影表格记录的命令
	public static String queryForUpdate(Movie movie) {
		String query = "UPDATE movies SET name = '" + movie.getName()
				+ "', type = '" + movie.getType() + "', director = '"
				+ movie.getDirector() + "', source = '" + movie.getSource()
				+ "', publisher = '" + movie.getPublisher()
				+ "', release_date = '" + movie.getReleaseDate()
				+ "' WHERE mid=" + movie.getMid();
		;
		return query;
	}
	
	// 该方法用于生成更新播放场次表格记录的命令
	public static String queryForUpdate(Show show) {
		String query = "UPDATE shows SET  mid = "
				+ show.getMid() + ", hall = " + show.getHall() + ", price = "
				+ show.getPrice() + ", time = '" +show.getTime()+"'";
		if (show.getSeatsUsed() != null)
			query += ", seats_used = '" + show.getSeatsUsed() + "'";
		query += " WHERE id = " + show.getId();
		return query;
	}

	// 该方法用于生成更新订单表格记录的命令
	public static String queryForUpdate(Order order) {
		String query = "UPDATE orders SET name = '" + order.getName()
				+ "', phone = '" + order.getPhone() + "'";
		if (order.getData() != null)
			query += ", data='" + order.getData() + "'";
		query += " WHERE id =" + order.getId();
		return query;
	}

	// 该方法用于生成更新管理员表格记录的命令
	public static String queryForUpdate(Staff user) {
		String query = "UPDATE staff SET username = '" + user.getUsername()
				+ "', password = '" + user.getPassword() + "', role = "
				+ user.getRole() + " WHERE uid=" + user.getUid();
		return query;
	}

	// 该方法用于生成更新当前用户密码的命令
	public static String queryForUpdatePass(int userId, String password) {
		String query = "UPDATE staff SET password = '" + password
				+ "' WHERE uid = " + userId;

		return query;
	}

	// 该方法用于生成在电影表格里添加记录的命令
	public static String queryForAdd(Movie movie) {
		String query = "INSERT INTO movies"
				+ " (name, type, director, source, publisher, release_date) VALUES ("
				+ "'" + movie.getName() + "','" + movie.getType() + "','"
				+ movie.getDirector() + "','" + movie.getSource() + "','"
				+ movie.getPublisher() + "','" + movie.getReleaseDate() + "')";

		return query;
	}

	// 该方法用于生成在放映场次表格里添加记录的命令
	public static String queryForAdd(Show show) {
		String query = "INSERT INTO shows (mid, hall, time, price, seats_used) "
				+ " VALUES ("
				+ show.getMid()
				+ ", "
				+ show.getHall()
				+ ",'"
				+ show.getTime()
				+ "',"
				+ show.getPrice()
				+ ",'"
				+ show.getSeatsUsed() + "')";

		return query;
	}
	
	// 该方法用于生成在订单表格里添加记录的命令
	public static String queryForAdd(Order order) {
		String query = "INSERT INTO orders (name, phone, data) "
				+ " VALUES ('"
				+ order.getName()
				+ "', '"
				+ order.getPhone()
				+ "','"
				+ order.getData()
				+ "')";

		return query;
	}

	// 该方法用于生成在管理员表格里添加记录的命令
	public static String queryForAdd(Staff user) {
		String query = "INSERT INTO staff (username, password, role) "
				+ " VALUES ('" + user.getUsername() + "','"
				+ user.getPassword() + "'," + user.getRole() + ")";

		return query;
	}

	// 该方法用于生成根据编号删除记录的命令
	public static String queryForDelete(String tableName, String idField, int id) {
		String query = "DELETE FROM " + tableName + " WHERE " + idField + "="
				+ id;
		return query;
	}

}