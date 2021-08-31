/**
 * 该类用于生成前台订票界面
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import util.CheckHandler;
import util.Constant;

import com.Movie;
import com.Order;
import com.Show;
import com.Ticket;

import database.MovieDao;
import database.OrderDao;
import database.ShowDao;

public class OrderWindow extends JFrame {
	//以表格形式对数据进行显示
	private JTable showTable;
	//座位标签定义一个对象
	private JLabel seatMatrix;
	//订单标签定义一个对象
	private JLabel ticketTable;
	//定义一个存放电影票的容器
	private List<Ticket> ticketList;
	//定义一个容器
	private Container contentPane;
	//定义一个上部面板
	private JPanel mainPane;
	//定义一个下部面板
	private JPanel bottomPane;
	//座位行数
	private JTextField seatRowVal;
	//座位列数
	private JTextField seatColVal; 
	//订票人姓名编辑框
	private JTextField userNameVal;
	//订票人手机号编辑框
	private JTextField userPhoneVal;
	//定义一个电影票类的对象
	private Ticket ticketTmp;

	//类的构造方法
	public OrderWindow() {
		//创建存放电影票的List容器
		ticketList = new ArrayList<Ticket>();
		ticketTmp = new Ticket(); //创建电影票类的对象
		initUI(); 
	}

	//该方法用于生成用户界面
	public void initUI() {
		setTitle("在线订票系统前台");
		//获取当前窗体对象
		contentPane = getContentPane();
		//设置布局格式为边框布局（将容器分为东南西北中5个区域）
		contentPane.setLayout(new BorderLayout());

		//1、设置主要的展示区域（页面上半部分）
		mainPane = new JPanel();
		//设置面板与当前窗体的上下左右距离均为10
		mainPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		//(1) 展示放映场次表格，用户选择后显示座位信息
		final JPanel showPane = new JPanel();
		//设置showPane面板的首选大小
		showPane.setPreferredSize(new Dimension(300, 350));
		final BorderLayout bdLayout = new BorderLayout();
		 //设置边框布局管理器的组件之间的垂直间距是5
		bdLayout.setVgap(5); 
		showPane.setLayout(bdLayout); 
		//创建一个滚动条
		final JScrollPane scrollPane = new JScrollPane();
		//将滚动条添加到showPane面板上
		showPane.add(scrollPane);
		//创建显示数据的表格
		showTable = new JTable();
		//创建一个文本编辑区域
		JTextField tf = new JTextField();
		//设置文本域不可编辑
		tf.setEditable(false);
		//创建一个文本字段的编辑器editor
		DefaultCellEditor editor = new DefaultCellEditor( tf );
		// 设置表格无法编辑
		showTable.setDefaultEditor(Object.class, editor); 
		// 设置表格项可以选择
		showTable.setRowSelectionAllowed(true);
		//将表格showTable添加到滚动条面板上
		scrollPane.setViewportView(showTable);
		//调用用户显示当前场次列表的方法
		paintShowTable("", "");
		showTable.getSelectionModel().addListSelectionListener( // 更新座位信息
				new ListSelectionListener() {
					@Override
					public void valueChanged(ListSelectionEvent event) {
						//显示放映厅座位图的方法
						paintSeatMatrix((int) showTable.getValueAt(
								showTable.getSelectedRow(), 0), "");
					}
				});
		//将显示面板，添加到主面板的west
		mainPane.add(showPane, BorderLayout.WEST);

		//(2) 展示该场次的座位信息，用户选择场次和座位后生成的电影票会添加到订单中
		//座位标签创建一个对象
		seatMatrix = new JLabel();
		//设置一个最好的大小，会根据具体界面适当调整
		seatMatrix.setPreferredSize(new Dimension(200, 350));
		//设置这个标签垂直方向顶端对齐 水平方向中间对齐 
		seatMatrix.setVerticalAlignment(SwingConstants.TOP);
		//放映厅座位图
		paintSeatMatrix(0, "");
		//座位图放到mainPane面板的center
		mainPane.add(seatMatrix, BorderLayout.CENTER);
		//绘制边界内所有像素
		seatMatrix.setOpaque(true); 
		//设置标签背景色是白色
		seatMatrix.setBackground(Color.WHITE);

		//(3）展示订单信息
		//为订单创建一个对象
		ticketTable = new JLabel();
		//设置一个最好的大小，会根据具体界面适当调整
		ticketTable.setPreferredSize(new Dimension(350, 350));
		//设置标签大小
		ticketTable.setBorder(new EmptyBorder(0, 20, 0, 0));;
		//设置订单信息沿y轴的对齐方式，顶部对齐
		ticketTable.setVerticalAlignment(SwingConstants.TOP);
		//显示订单信息的方法
		paintTicketTable();
		//订单信息放到主面板的east
		mainPane.add(ticketTable, BorderLayout.EAST);

		//2、 设置主要的功能区域（页面下半部分）
		bottomPane = new JPanel();
		bottomPane.setLayout(new BorderLayout());
		bottomPane.setBorder(new EmptyBorder(10, 10, 10, 10));

		//（1）选择座位功能区域
		//创建一个选择座位的面板
		JPanel selectPane = new JPanel();
		//设置布局格式为边框布局（将容器分为东南西北中5个区域）
		selectPane.setLayout(new BorderLayout());
		//设置一个最好的大小，会根据具体界面适当调整
		selectPane.setPreferredSize(new Dimension(300, 100));
		JLabel selectDesc = new JLabel("请输入所选座位的行和列：");
		//将提示标签selectDesc添加到selectPane面板的NORTH
		selectPane.add(selectDesc, BorderLayout.NORTH); 
		//为输入选择座位创建一个面板
		JPanel inputPane = new JPanel();
		//设置布局格式为一行4列
		inputPane.setLayout(new GridLayout(1, 4));
		//设置两个标签
		JLabel seatRowName = new JLabel("行数");
		JLabel seatColName = new JLabel("列数");
		//设置提示列和行标签沿X轴的对齐方式是右对齐
		seatRowName.setHorizontalAlignment(SwingConstants.RIGHT);
		seatColName.setHorizontalAlignment(SwingConstants.RIGHT);
		seatRowVal = new JTextField(); //座位行：输入文本框
		seatColVal = new JTextField(); //座位列：输入文本框
		//将行和列的标签以及文本框添加到inputPane
		inputPane.add(seatRowName);
		inputPane.add(seatRowVal);
		inputPane.add(seatColName);
		inputPane.add(seatColVal);
		//将输入部分的面板inputPane添加到selectPane
		selectPane.add(inputPane, BorderLayout.CENTER); // 添加选择输入框
		JButton selectBtn = new JButton("选定座位"); //选定座位的按钮
		selectBtn.setPreferredSize(new Dimension(300, 50));
		selectBtn.addActionListener(new ActionListener() { //座位选定后提交
			@Override
			public void actionPerformed(ActionEvent e) {
				//选定提交后的座位信息处理
				btnSelectSeatActionPerformed(e);
			}
		});		
		//选定作为提交按钮添加到面板的south
		selectPane.add(selectBtn, BorderLayout.SOUTH); //添加选择提交按钮		
		//selectPane面板添加到bottomPane的west
		bottomPane.add(selectPane, BorderLayout.WEST);

		//（2） 订单提交功能区域
		JPanel orderPane = new JPanel();
		orderPane.setLayout(new BorderLayout());
		orderPane.setPreferredSize(new Dimension(300, 100));
		JLabel orderDesc = new JLabel("请输入个人信息：");
		orderPane.add(orderDesc, BorderLayout.NORTH); 
		JPanel inputPane1 = new JPanel();
		//输入用户信息的面板
		inputPane1.setLayout(new GridLayout(1, 4));
		JLabel userName = new JLabel("姓名");
		JLabel userPhone = new JLabel("手机号");
		//姓名和手机号沿X轴对齐方式是右对齐
		userName.setHorizontalAlignment(SwingConstants.RIGHT);
		userPhone.setHorizontalAlignment(SwingConstants.RIGHT);
		userNameVal = new JTextField();
		userPhoneVal = new JTextField();
		//将输入信息的标签和文本框添加到面板上
		inputPane1.add(userName);
		inputPane1.add(userNameVal);
		inputPane1.add(userPhone);
		inputPane1.add(userPhoneVal);
		//将inputPane1添加到orderPane的中间
		orderPane.add(inputPane1, BorderLayout.CENTER);
		JButton placeBtn = new JButton("提交订单");
		placeBtn.setPreferredSize(new Dimension(300, 50));
		placeBtn.addActionListener(new ActionListener() { // 开始订单提交
			@Override
			public void actionPerformed(ActionEvent e) {
				//提交订单的处理方法
				btnPlaceOrderActionPerformed(e);
			}
		});
		//将提交订单按钮添加到orderPane的south
		orderPane.add(placeBtn, BorderLayout.SOUTH); 
		//将orderPane添加到bottomPane的east
		bottomPane.add(orderPane, BorderLayout.EAST);

		//将展示区mainPane和功能区bottomPane添加到主页面contentPane
		contentPane.add(mainPane, BorderLayout.NORTH);
		contentPane.add(bottomPane, BorderLayout.SOUTH);
		setSize(900, 600); 
		setResizable(false);
		//getOwner()返回此窗体的所有者
		setLocationRelativeTo(getOwner());
		setVisible(true);
		
		//设置关闭主页面时中止主程序
		this.addWindowListener(new WindowAdapter() {
	        @Override
			public void windowClosing(WindowEvent e) {
	        	dispose(); //当前窗体不可见
	        }

	    });
	}

	//该方法用于所选座位提交后的处理
	private void btnSelectSeatActionPerformed(ActionEvent e) {
		if(ticketTmp.getShow()==0) { // 尚未选择放映场次
			JOptionPane.showMessageDialog(this, "请先选择电影!");
			return;
		}
		Ticket ticket = new Ticket();		
		//所选座位格式错误
		try{
		ticket.setMovie(ticketTmp.getMovie());
		ticket.setShow(ticketTmp.getShow());
		ticket.setTime(ticketTmp.getTime());
		ticket.setPrice(ticketTmp.getPrice());
		ticket.setSeatRow(Integer.parseInt(seatRowVal.getText()));
		ticket.setSeatColumn(Integer.parseInt(seatColVal.getText()));
		}catch (NumberFormatException ex) {
			//错误信息提示框
			JOptionPane.showMessageDialog(this, "输入为空或格式不正确，请重新输入!"); 
			return;
		}		
		//所选座位超出放映厅的范围
		if(ticket.getSeatRow()<1 || ticket.getSeatRow()>Constant.HALL_ROW_NUM 
				|| ticket.getSeatColumn() <1 || ticket.getSeatColumn()>Constant.HALL_COLUMN_NUM){
			JOptionPane.showMessageDialog(this, "输入的座位位置有误，请重新输入!");
			return;
		}
		//检查该场次所选座位是否已经被别人预订（或者已经在自己订单中），没被预定返回true，预定返回false
		boolean paintSuccess = paintSeatMatrix(ticket.getShow(), ticket.getSeatRow()+","+ticket.getSeatColumn());
		if(!paintSuccess){
			JOptionPane.showMessageDialog(this, "该座位无法预订，请重新选择!");
			return;
		}
		ticketList.add(ticket);
		paintTicketTable(); // 更新该场次放映厅座位图
	}
	
	// 该方法用于订单提交后的处理
	private void btnPlaceOrderActionPerformed(ActionEvent e) {	
		String userName = userNameVal.getText().trim();
		String userPhone = userPhoneVal.getText().trim();
		if(ticketList.size()==0){
			JOptionPane.showMessageDialog(this, "你的当前订单为空，无法提交!");
			return;
		}		
		if(userName.length()==0 || userPhone.length()==0 ){
			JOptionPane.showMessageDialog(this, "姓名或手机为空，请重新输入!");
			return;
		}else if(CheckHandler.containsDigit(userName)|| CheckHandler.containsChar(userName)){
			JOptionPane.showMessageDialog(this, "输入的姓名包含非中文信息，请修改!");
			return;
		}else if(!CheckHandler.isValidMobile(userPhone)){
			JOptionPane.showMessageDialog(this, "输入的手机号格式不正确（应为11位整数，第1位为1，第2位为3，4，5，7，8中一个）!");
			return;
		}	
		//创建用户订单类对象，并保存用户信息
		Order order = new Order();
		order.setName(userName);
		order.setPhone(userPhone);
		String data = "";
		String seat = "";
		//记录用户订单数据。为了简单演示，此处每张票的内部数据采用竖线隔开，不同票的数据采用分号隔开。该数据也可采用json方式来存储。
		for(Ticket ticket : ticketList){
			seat = ticket.getSeatRow()+","+ticket.getSeatColumn();
			if(data.length()>0) data += ";";
			data += ticket.getShow()+" "+seat+"|"+ticket.getMovie()+"|"+ticket.getTime();
			Show show = ShowDao.getShow(ticket.getShow());
			String seatsUsed = show.getSeatsUsed()+" "+seat;
			show.setSeatsUsed(seatsUsed.trim());
			ShowDao.updateShow(show);
		}
		order.setData(data);
		boolean addSuccess = OrderDao.addOrder(order);
		JOptionPane.showMessageDialog(this, addSuccess?"恭喜，订票成功。为了方便你继续订购，订单区将被清空!":"对不起，下单失败!");
		if(addSuccess){ // 完成提交后，清理订单界面数据，方便用户继续订票
			ticketList = new ArrayList<Ticket>();			
			paintTicketTable();
			seatRowVal.setText("");
			seatColVal.setText("");
			userNameVal.setText("");
			userPhoneVal.setText("");
		}		
	}

	//该方法用户显示当前场次列表
	private void paintShowTable(String field, String value) {
		DefaultTableModel model = new DefaultTableModel();
		showTable.setModel(model);
		// 执行查询操作，将查询结果显示到界面
		Object[][] tbData = null;
		int i = 0;
		String[] labels = { "放映场次", "电影名称", "放映时间", "票价(元)" };
		//查询数据库中所有的电影放映信息
		List<Show> shows = ShowDao.getShows(field, value);
		tbData = new Object[shows.size()][labels.length];
		for (Show show : shows) {
			//根据放映mid查询电影信息
			Movie movie = MovieDao.getMovie(show.getMid());
			if (movie == null) //如果无法查到对应电影信息，则不显示该场次，以防管理员输入错误
				continue;
			tbData[i][0] = show.getId();
			tbData[i][1] = movie.getName();
			tbData[i][2] = show.getTime();
			tbData[i][3] = show.getPrice();
			i++;
		}
		model.setDataVector(tbData, labels);
	}

	//该方法用于显示放映厅座位图，checkSeat为用户所选座位
	private boolean paintSeatMatrix(int showId, String checkSeat) {
		String usedSeats = "";
		String seatHtml = "";
		Show show = ShowDao.getShow(showId);
		if (show != null) { //已选放映场次
			usedSeats = show.getSeatsUsed();
			ticketTmp.setMovie(MovieDao.getMovie(show.getMid()).getName());
			ticketTmp.setPrice(show.getPrice());
			ticketTmp.setShow(show.getId());
			ticketTmp.setTime(show.getTime());
			seatHtml += "<p>该场安排在<font color=red>" + show.getHall()
					+ "</font>号放映厅，座位情况如下（X为已选，O为未选）：</p>";
			for (Ticket ticket : ticketList) {
				if (ticket.getShow() == showId)
					usedSeats += " " + ticket.getSeatRow() + ","
							+ ticket.getSeatColumn();
			}
		}else{ //尚未选放映场次
			seatHtml += "<p>请选择电影，座位情况如下（X为已选，O为未选）：</p>";			
		}
		usedSeats = " " + usedSeats.trim() + " ";
		if(checkSeat.length()>0 && usedSeats.indexOf(" "+checkSeat+" ")>=0) { //所选座位被占用，无法完成当前座位的提交
			return false; 
		}else if(checkSeat.length()>0){ //所选座位未被占用，完成当前座位的提交，并将当前座位信息添加到已选定座位列表信息中
			usedSeats += checkSeat + " "; 
		}else;
		
		//打印出所有列的标记
		seatHtml += "<table><tr><th></th>";
		for (int j = 0; j < Constant.HALL_COLUMN_NUM; j++) {
			seatHtml += "<th>" + (j + 1) + "</th>";
		}
		seatHtml += "</tr>";
		//执行循环打印出座位图
		String curSeat;
		for (int i = 0; i < Constant.HALL_ROW_NUM; i++) {
			for (int j = 0; j < Constant.HALL_COLUMN_NUM; j++) {
				if (j == 0)
					seatHtml += "<tr><th>" + (i + 1) + "</th>"; // 打印出当前行的标记
				curSeat = " " + (i + 1) + "," + (j + 1) + " ";
				if (usedSeats.indexOf(curSeat) >= 0)  // 判断该座位是否已被预定
					seatHtml += "<td><font color=red>X</font></td>";
				else
					seatHtml += "<td>O</td>";
				if (j == Constant.HALL_COLUMN_NUM - 1)
					seatHtml += "</tr>";
			}
		}
		seatHtml += "</table>";
		seatHtml = "<html>" + seatHtml + "</html>";

		seatMatrix.setText(seatHtml);
		return true;

	}

	//该方法用于显示订单中的电影票列表
	private void paintTicketTable() {
		String ticketHtml = "";
		double priceTotal = 0;
		ticketHtml += "<table width=320 border=1><tr>";
		for (String label : Constant.ticketLabels) {
			ticketHtml += "<th>" + label + "</th>";
		}
		int i=0;
		for (Ticket ticket : ticketList) {
			ticketHtml += "<tr><td>" + (i+1) + "</td>";
			ticketHtml += "<td>" + ticket.getMovie() + "</td>";
			ticketHtml += "<td>" + ticket.getTime();
			ticketHtml += "<td>" + ticket.getPrice() + "</td>";
			ticketHtml += "<td>" + ticket.getSeatRow() + "行"
					+ ticket.getSeatColumn() + "列</td></tr>";			
			priceTotal += ticket.getPrice();
			i++;
		}

		ticketHtml += "<tr><td colspan=5>总计： " + priceTotal + "元</td></tr></table>";
		String title = "<p>你的当前订单 ("+(i>0?("包含"+i+"张电影票"):"订单为空")+")：</p>";
		ticketHtml = "<html>" + title + ticketHtml + "</html>";
		ticketTable.setText(ticketHtml);
	}

}
