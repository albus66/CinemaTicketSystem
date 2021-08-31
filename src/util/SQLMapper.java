//该类定义了一组将返回的数据库记录存储到对象的方法
package util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.Movie;
import com.Order;
import com.Show;
import com.Staff;

public class SQLMapper{
	// 存储到电影对象
	public static void mapResToMovie(ResultSet res, Movie movie)
			throws SQLException {
		movie.setMid(res.getInt("mid"));
		movie.setName(res.getString("name"));
		movie.setType(res.getString("type"));
		movie.setDirector(res.getString("director"));
		movie.setSource(res.getString("source"));
		movie.setPublisher(res.getString("publisher"));
		movie.setReleaseDate(res.getString("release_date"));
	}

	// 存储到订单对象
	public static void mapResToOrder(ResultSet res, Order order)
			throws SQLException {
		order.setId(res.getInt("id"));
		order.setPhone(res.getString("phone"));
		order.setName(res.getString("name"));
		order.setData(res.getString("data"));	
	}
	
	// 存储到放映场次对象
	public static void mapResToShow(ResultSet res, Show show)
			throws SQLException {
		show.setId(res.getInt("id"));
		show.setMid(res.getInt("mid"));
		show.setHall(res.getInt("hall"));
		show.setTime(res.getString("time"));
		show.setPrice(res.getDouble("price"));
		show.setSeatsUsed(res.getString("seats_used"));	
	}
	
	// 存储到管理员对象
	public static void mapResToUser(ResultSet res, Staff user)
			throws SQLException {
		user.setUid(res.getInt("uid"));
		user.setUsername(res.getString("username"));
		user.setPassword(res.getString("password"));
		user.setRole(res.getInt("role"));
	}

}
