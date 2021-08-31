/**
 * 该类用于与电影相关的数据库操作
 */
package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import util.QueryCreate;
import util.SQLExec;
import util.SQLMapper;

import com.Movie;
import  com.*;
public class MovieDao {
	// 该方法用于获取多个电影对象
	public static List<Movie> getMovies(String field, String value) {
		List<Movie> movies = new ArrayList<Movie>();
		try {
			SQLExec sqlExec = new SQLExec();
			// 提取数据库里所有电影数据
			String query = "";
			if(field.length()==0)
				query = QueryCreate.queryForResults("movies"); //提取所有电影信息
			else if(field.equals("mid")) 	//根据编号提取电影信息，用于查看，修改和删除
				query = QueryCreate.queryForResults("movies", field, Integer.parseInt(value));
			else
				query = QueryCreate.queryForResults("movies", field, value); //电影信息的一般检索，用于查看
			ResultSet results = sqlExec.select(query);
			Movie movie = null;
			while (results.next()) {
				movie = new Movie();
				SQLMapper.mapResToMovie(results, movie);
				movies.add(movie);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return movies;
	}

	// 该方法用于获取单个满足特定条件的电影对象
	public static Movie getMovie(int movieId) {
		List<Movie> movies = getMovies("mid", movieId+"");
		Movie movie = null;
		if(movies.size()>0) movie = movies.get(0);
		return movie;
	}

	// 该方法用于添加新的电影到数据库
	public static boolean addMovie(Movie movie) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 添加电影到数据库
			String query = QueryCreate.queryForAdd(movie);
			System.out.println(query); 
			sqlExec.insert(query);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// 该方法用于更新数据库里已有的电影数据
	public static boolean updateMovie(Movie movie) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 更新数据库里的电影记录
			String query = QueryCreate.queryForUpdate(movie);
			System.out.println(query);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// 该方法用于删除数据库里已有的电影数据
	public static boolean deleteMovie(int movieId) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里的电影
			String query = QueryCreate.queryForDelete("movies", "mid", movieId);
			System.out.println(query);
			sqlExec.update(query);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;
	}

}
