/**
 * 该类用于与后台管理相关的数据库操作
 */
package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.QueryCreate;
import util.SQLExec;
import util.SQLMapper;

import com.Staff;

public class StaffDao {
	// 该方法用于获取多个满足特定条件的管理员对象
	public static List<Staff> getUsers(String field, String value) {
		List<Staff> users = new ArrayList<Staff>();
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的用户数据
			String query= "";
			if(field.length()==0) //提取所有管理员信息
				query = QueryCreate.queryForResults("staff");
			else if(field.equals("uid") || field.equals("role")) //查询的变量为整数时的查询方法
				query = QueryCreate.queryForResults("staff", field, Integer.parseInt(value));
			else
				query = QueryCreate.queryForResults("staff", field, value);


			ResultSet results = sqlExec.select(query);
			Staff user = null;
			while (results.next()) {
				user = new Staff();
				SQLMapper.mapResToUser(results, user);
				users.add(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}

	// 该方法用于获取单个满足特定条件的管理员对象
	public static Staff getUser(int userId) {
		List<Staff> users = getUsers("uid", userId+"");
		Staff user = null;
		if(users.size()>0) user = users.get(0);
		return user;
	}
	
	// 该方法用于管理员登录验证
	public static Staff getUserByCredential(String username, String password) {
		Staff user = null;
		try {
			SQLExec sqlExec = new SQLExec();
			
			String query = QueryCreate
					.queryByCredential(username, password);

			ResultSet results = sqlExec.select(query);
			if (results.next()) {
				user = new Staff();
				SQLMapper.mapResToUser(results, user);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// 该方法用于添加新的管理员到数据库
	public static boolean addUser(Staff user) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();		
			String query = QueryCreate.queryForAdd(user);
			sqlExec.insert(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// 该方法用于更新数据库里已有的管理员数据
	public static boolean updateUser(Staff user) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();			
			String query = QueryCreate.queryForUpdate(user);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// 该方法用于更新当前管理员的密码
	public static boolean updateUserPass(int userId, String password) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			String query = QueryCreate.queryForUpdatePass(userId, password);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// 该方法用于删除数据库里已有的管理员数据
	public static boolean deleteUser(int userId) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			
			String query = QueryCreate.queryForDelete("staff", "uid", userId);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
