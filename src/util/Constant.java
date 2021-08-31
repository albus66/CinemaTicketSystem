/**
 * 该类定义了该项目中的常量
 */
package util;

public class Constant {

	// 定义了一组管理员角色
	public static final int VISITOR_ROLE = 0;
	public static final int ONLY_VIEW_ROLE = 1;
	public static final int MOVIE_ADMIN_ROLE = 2;
	public static final int SHOW_ADMIN_ROLE = 3;
	public static final int ORDER_ADMIN_ROLE = 4;
	public static final int FULL_ADMIN_ROLE = 50;
	public static final int ROOT_ADMIN_ROLE = 99;

	// 定义了放映厅的大小
	public static final int HALL_ROW_NUM = 12;
	public static final int HALL_COLUMN_NUM = 9;

	// 定义了电影的标签和对应的数据库表格里的名称
	public static String[] movieLabels = { "电影编号", "电影名称", "电影类别", "导演",
			"来源国家", "发行公司", "上映日期" };
	public static String[] movieDBFields = { "mid", "name", "type", "director",
			"source", "publisher", "release_date" };

	// 定义了放映场次的标签和对应的数据库表格里的名称
	public static String[] showLabels = { "场次编号", "电影名称", "放映厅", "放映时间",
			"票价(元)" };
	public static String[] showDBFields = { "id", "mid", "hall", "time",
			"price" };

	// 定义了订单的标签和对应的数据库表格里的名称
	public static String[] orderLabels = { "订单编号", "姓名", "电话", "订单数据" };
	public static String[] orderDBFields = { "id", "name", "phone", "data" };

	// 定义了用户的标签和对应的数据库表格里的名称
	public static String[] staffLabels = { "用户编号", "用户名", "密码", "权限" };
	public static String[] staffDBFields = { "uid", "username", "password",
			"role" };

	// 定义了电影票的标签
	public static String[] ticketLabels = { "编号", "电影名称", "时间", "票价", "座位" };

	// 定义了管理员角色的标签和对应的角色编号
	public static String[] userRoleDescs = { "无管理权限", "只能管理电影", "只能管理场次",
			"只能管理订单", "完全管理权限", "根权限" };
	public static int[] userRoleIds = { ONLY_VIEW_ROLE, MOVIE_ADMIN_ROLE,
			SHOW_ADMIN_ROLE, ORDER_ADMIN_ROLE, FULL_ADMIN_ROLE, ROOT_ADMIN_ROLE };
	public static String[] timeHours = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12",
			"13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
	public static String[] timeMinutes = { "00", "05", "10", "15", "20", "25", "30", "35", "40", "45",
			"50", "55" };
}
