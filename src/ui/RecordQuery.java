/**
 * 该类用于查询已有的电影，放映场次，订单或者管理员
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import util.CheckHandler;
import util.Constant;

import com.Movie;
import com.Order;
import com.Show;
import com.Staff;

import database.MovieDao;
import database.OrderDao;
import database.ShowDao;
import database.StaffDao;

public class RecordQuery {
	//以表格形式显示查询结果
	private JTable recordTable;
	//定义一个面板
	private JPanel contentPane;
	//定义一个下拉框
	private JComboBox<String> queryBox;
	//定义一个输入查询方式的编辑框
	private JTextField queryValue;
	//定义一个查询条件变量
	private String recordType;

	public RecordQuery(Container mainContent, String type, JFrame frame) {
		recordType = type;
		initUI(mainContent);  //传入主页面的内容区
	}

	// 初始化用户操作界面
	public void initUI(Container mainContent) {
		//清空其查询他条件
		mainContent.removeAll();
		//创建一个新的面板
		contentPane = new JPanel();
		//为创建的面板规定大小格式
		contentPane.setBorder(new EmptyBorder(50, 50, 50, 50));

		// 添加查询功能模块区域
		final JPanel queryPane = new JPanel();
		queryPane.setLayout(new GridLayout(1, 3));
		//定义一个查询类型变量
		String[] fields = null;
		if (recordType.equals("电影"))
			fields = Constant.movieLabels;
		if (recordType.equals("场次"))
			fields = Constant.showLabels;
		if (recordType.equals("订单"))
			fields = Constant.orderLabels;
		if (recordType.equals("用户"))
			fields = Constant.staffLabels;

		// 添加查询功能模块区域
		queryBox = new JComboBox<String>(fields);
		queryValue = new JTextField();
		JButton queryBtn = new JButton("查询"); // 添加查询按钮
		queryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryActionPerformed(e);
			}
		});		
		JButton resetBtn = new JButton("重置"); // 添加查询重置按钮
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnResetActionPerformed(e);
			}
		});
		//为查询面板添加按钮、查询条件等器件
		queryPane.add(queryBox);
		queryPane.add(queryValue);
		queryPane.add(queryBtn);
		queryPane.add(resetBtn);
		contentPane.add(queryPane);

		// 添加查询结果显示表格
		final JPanel resultPane = new JPanel();
		resultPane.setPreferredSize(new Dimension(600, 400));
		recordTable = new JTable();
		final BorderLayout bdLayout = new BorderLayout();
		//设置一个行高
		bdLayout.setVgap(5);
		resultPane.setLayout(bdLayout);
		contentPane.add(resultPane);

		//添加一个滚动条
		final JScrollPane scrollPane = new JScrollPane();
		resultPane.add(scrollPane);
		paintTable("", "");
		scrollPane.setViewportView(recordTable);
		
		// 点击表格内的记录，将弹出编辑框
		recordTable.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = recordTable.rowAtPoint(evt.getPoint());
		        int col = recordTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {	
		        	int itemId = (int) recordTable.getValueAt(row, 0);
		        	new RecordEditDialog(recordType, itemId, recordTable, row);
		        }
		    }
		});
		

		 
		//显示当前内容区（包含展示区和功能区），并添加到传入的主页面内容container
		contentPane.setVisible(true);
		mainContent.add(contentPane, BorderLayout.CENTER);
	}

	/**
	 * 该方法处理在数据库表格查询不同类型的数据
	 */
	private void btnQueryActionPerformed(ActionEvent e) {
		String field = "";
		if (recordType.equals("电影"))
			field = Constant.movieDBFields[queryBox.getSelectedIndex()];
		if (recordType.equals("场次"))
			field = Constant.showDBFields[queryBox.getSelectedIndex()];
		if (recordType.equals("订单"))
			field = Constant.orderDBFields[queryBox.getSelectedIndex()];
		if (recordType.equals("用户"))
			field = Constant.staffDBFields[queryBox.getSelectedIndex()];

		String value = queryValue.getText();
		if (value.length() == 0) {
			JOptionPane.showMessageDialog(this.contentPane, "请输入关键词后再检索!");
			return;
		}
		paintTable(field, value);
	}

	/**
	 * 该方法重新加载数据库表格所有记录
	 */
	private void btnResetActionPerformed(ActionEvent e) {
		paintTable("", "");
	}

	/**
	 * 该方法获取数据库里符合特定要求的所有记录，并显示在表格里
	 */
	private void paintTable(String field, String value) {
		// 设置数据加载方式
		//创建一个默认的表控模型
		DefaultTableModel model = new DefaultTableModel();
		recordTable.setModel(model);
		recordTable.setEnabled(false); 
		
		// 执行查询操作，将查询结果显示出来
		Object[][] tbData = null; 
		int i = 0;
		if (recordType.equals("电影")) {
			List<Movie> movies = MovieDao.getMovies(field, value);
			tbData = new Object[movies.size()][Constant.movieLabels.length];
			for (Movie movie : movies) {
				tbData[i][0] = movie.getMid();
				tbData[i][1] = movie.getName();
				tbData[i][2] = movie.getType();
				tbData[i][3] = movie.getDirector();
				tbData[i][4] = movie.getSource();
				tbData[i][5] = movie.getPublisher();
				tbData[i][6] = movie.getReleaseDate();
				i++;
			}
			model.setDataVector(tbData, Constant.movieLabels);
		}

		if (recordType.equals("场次")) {
			List<Show> shows = ShowDao.getShows(field, value);
			tbData = new Object[shows.size()][Constant.showLabels.length];
			for (Show show : shows) {
				tbData[i][0] = show.getId();
				tbData[i][1] = MovieDao.getMovie(show.getMid()).getName();
				tbData[i][2] = show.getHall();				
				tbData[i][3] = show.getTime();
				tbData[i][4] = show.getPrice();
				i++;
			}
			model.setDataVector(tbData, Constant.showLabels);
		}

		if (recordType.equals("订单")) {
			List<Order> orders = OrderDao.getOrders(field, value);
			tbData = new Object[orders.size()][Constant.movieLabels.length];
			for (Order order : orders) {
				tbData[i][0] = order.getId();
				tbData[i][1] = order.getName();
				tbData[i][2] = order.getPhone();
				tbData[i][3] = order.getData();
				tbData[i][4] = order.getDatetime();
				i++;
			}
			model.setDataVector(tbData, Constant.orderLabels);
		}

		if (recordType.equals("用户")) {
			List<Staff> users = StaffDao.getUsers(field, value);
			tbData = new Object[users.size()][Constant.movieLabels.length];
			for (Staff user : users) {
				tbData[i][0] = user.getUid();
				tbData[i][1] = user.getUsername();
				tbData[i][2] = user.getPassword();
				int index = CheckHandler.geSelectIndexById(user.getRole()); //检查数据库里的权限数据是否有效
				if(index>=0)
				tbData[i][3] = Constant.userRoleDescs[index];
				else tbData[i][3] = "";
				i++;
			}
			model.setDataVector(tbData, Constant.staffLabels);
		}

	}
}
