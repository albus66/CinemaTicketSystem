/**
 * 该类用于与订单相关的数据库操作
 */
package database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import util.QueryCreate;
import util.SQLExec;
import util.SQLMapper;

import com.Order;

public class OrderDao {
	// 该方法用于获取多个满足特定条件的订单对象
	public static List<Order> getOrders(String field, String value) {
		List<Order> orders = new ArrayList<Order>();
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的订单数据
			String query= "";
			if(field.length()==0)
				query = QueryCreate.queryForResults("orders");  //提取所有订单信息
			else if(field.equals("id"))  //根据编号提取订单信息，用于查看和删除
				query = QueryCreate.queryForResults("orders", field, Integer.parseInt(value));
			else
				query = QueryCreate.queryForResults("orders", field, value); //订单信息的一般检索，用于查看

			ResultSet results = sqlExec.select(query);
			Order order = null;
			while (results.next()) {
				order = new Order();
				SQLMapper.mapResToOrder(results, order);
				orders.add(order);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}

	// 该方法用于获取单个满足特定条件的订单对象
	public static Order getOrder(int orderId) {
		List<Order> orders = getOrders("id", orderId+"");
		Order order = null;
		if(orders.size()>0) order = orders.get(0);
		return order;
	}

	// 该方法用于添加新的订单到数据库
	public static boolean addOrder(Order order) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 添加订单到数据库
			String query = QueryCreate.queryForAdd(order);
			sqlExec.insert(query);
			success = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}
	
	// 该方法用于更新数据库里已有订单数据
	public static boolean updateOrder(Order order) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 更新数据库里已有的订单数据
			String query = QueryCreate.queryForUpdate(order);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	// 该方法用于删除数据库里已有的订单数据
	public static boolean deleteOrder(int orderId) {
		boolean success = false;
		try {
			SQLExec sqlExec = new SQLExec();
			// 删除数据库里已有的订单数据
			String query = QueryCreate.queryForDelete("orders", "id", orderId);
			sqlExec.update(query);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

}
