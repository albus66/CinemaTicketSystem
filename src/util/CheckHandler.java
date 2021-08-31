/**
 * 该类定义常用的check方法
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTextField;

public class CheckHandler {

	// 该方法检查用户角色的下拉菜单项
	public static int geSelectIndexById(int roleId) {
		int i = 0;
		while (i < Constant.userRoleIds.length) {
			if (Constant.userRoleIds[i] == roleId)
				break;
			i++;
		}
		if (i == Constant.userRoleIds.length)
			i = -1;
		return i;
	}

	// 该方法用提取下拉菜单中的编号
	public static int geCbIndex(String value, String[] arr) {
		int i = 0;
		while (i < arr.length) {
			if (arr[i].equals(value))
				break;
			i++;
		}
		if (i == arr.length)
			i = -1;
		return i;
	}

	// 该方法检查是否有输入框为空
	public static boolean checkEmptyField(List<JTextField> textFields) {
		for (int i = 0; i < textFields.size(); i++) {
			if (textFields.get(i).getText().trim().length() == 0)
				return true;
		}
		return false;
	}

	// 该方法获得订单种的座位信息
	public static List<String> getSeats(String orderData) {
		List<String> usedSeats = new ArrayList<String>();
		System.out.println(orderData);
		if (orderData.length() > 0) {
			System.out.println();
			String[] tickets = orderData.split(";");
			for (String ticket : tickets) {
				String[] ticketMeta = ticket.split("\\|");
				usedSeats.add(ticketMeta[0]);
			}
		}
		return usedSeats;
	}

	// 该方法验证输入是否为数字
	public static boolean isNumeric(String str) {
		return str != null && str.matches("[-+]?\\d*\\.?\\d+");
	}

	// 该方法判断输入是否含有数字
	public static boolean containsDigit(String str) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(".*\\d+.*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches())
			flag = true;
		return flag;
	}

	// 判断是否包含字母  
	 public static boolean containsChar(String str) {  
	        String regex=".*[a-zA-Z]+.*";  
	        Matcher m=Pattern.compile(regex).matcher(str);  
	        return m.matches();  
	    }  
	 
	// 该方法验证输入是否为整数
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	// 该方法验证是否为手机号
	public static boolean isValidMobile(String str) {
		boolean isValid = false;
		Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		Matcher matcher = pattern.matcher(str);
		isValid = matcher.matches();
		return isValid;
	}

	public static String showOrder(String orderData) {
		if (orderData.length() == 0)
			return "";

		String outHtml = "<tr><th>放映场次</th><th>电影名称</th><th>时间</th><th>座位</th></tr>";
		String[] tickets = orderData.split(";");
		for (String ticket : tickets) {
			String[] ticketMeta = ticket.split("\\|");
			String[] seat = ticketMeta[0].split(" ");
			String[] seatMeta = seat[1].split(",");
			outHtml += "<tr><td>" + seat[0] + "</td><td>" + ticketMeta[1]
					+ "</td>";
			outHtml += "<td>" + ticketMeta[2] + "</td><td>" + seatMeta[0] + "行"
					+ seatMeta[1] + "列</td></tr>";
		}
		outHtml = "<html><table border=1>" + outHtml + "</table></html>";
		return outHtml;

	}

}
