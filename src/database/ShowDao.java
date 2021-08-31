/**
 * 该类用于与放映相关的数据库操作
 */
package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.QueryCreate;
import util.SQLExec;
import util.SQLMapper;

import com.Show;

public class ShowDao {
	// 该方法用于获取多个满足特定条件的放映对象
	public static List<Show> getShows(String field, String value) {
		List<Show> shows = new ArrayList<Show>();
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的放映数据
			String query= "";
			if(field.length()==0)
				query = QueryCreate.queryForResults("shows");//提取所有订单信息
			else if(field.equals("id") || field.equals("mid") || field.equals("hall")) //查询的变量为整数时的查询方法
				query = QueryCreate.queryForResults("shows", field, Integer.parseInt(value)); 
			else if(field.equals("price")) //查询的变量为小数时的查询方法
				query = QueryCreate.queryForResults("shows", field, Double.parseDouble(value));
			else	//查询的变量为string时的查询方法
				query = QueryCreate.queryForResults("shows", field, value);

			ResultSet results = sqlExec.select(query);
			Show show = null;
			while (results.next()) {
				show = new Show();
				SQLMapper.mapResToShow(results, show);
				shows.add(show);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return shows;
	}

	// 该方法用于获取单个满足特定条件的放映场次对象
	public static Show getShow(int showId) {
		List<Show> shows = getShows("id", showId+"");
		Show show = null;
		if(shows.size()>0) show = shows.get(0);
		return show;
	}

	// 该方法用于添加新的放映场次到数据库
	public static boolean addShow(Show show) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 添加订单到数据库
			String query = QueryCreate.queryForAdd(show);
			sqlExec.insert(query);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// 该方法用于更新数据库里已有的放映场次数据
	public static boolean updateShow(Show show) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的放映数据
			String query = QueryCreate.queryForUpdate(show);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// 该方法用于删除数据库里已有的放映场次数据
	public static boolean deleteShow(int showId) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的放映数据
			String query = QueryCreate.queryForDelete("shows", "id", showId);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public static void removeUsedSeats(List<String> usedSeatList){
		for(String usedSeat : usedSeatList){
			System.out.println(usedSeat);
			String[] seatMeta = usedSeat.split(" ");
			int showId = Integer.parseInt(seatMeta[0]);
			String seat = seatMeta[1];
			// 提取数据库里放映场次的座位信息
			Show show = ShowDao.getShow(showId);
			String usedSeats = show.getSeatsUsed();
			if(usedSeats.length()>0){
				usedSeats = (" "+usedSeats+" ").replace(" "+seat+" ", "").trim(); //从占用列表中删除该座位
				System.out.println("usedSeats:" + usedSeats);
				show.setSeatsUsed(usedSeats);
				ShowDao.updateShow(show); //更新数据库
			}
		}
	}
}
