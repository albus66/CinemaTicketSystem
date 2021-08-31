/**
 * 该类用于删除已有的电影，放映场次，订单或者管理员
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

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

public class RecordDelete {
	private String recordType;
	private JPanel contentPane;
	private JPanel queryPane;
	private JLabel idLabel;
	private JTextField idField;
	private JPanel recordPane;
	private JButton btnQuery;
	private JPanel buttonBar;
	private JButton btnDel;
	private JFrame mainFrame;

	private List<JTextField> textFields;

	public RecordDelete(Container mainContent, String type, JFrame frame) {
		recordType = type;
		textFields = new ArrayList<JTextField>();
		mainFrame = frame;
		initUI(mainContent); //传入主页面的内容区
	}

	// 初始化用户操作界面
	private void initUI(Container mainContent) {
		mainContent.removeAll();
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		
		// 添加查询功能模块区域
		queryPane = new JPanel();
		recordPane = new JPanel();
		idLabel = new JLabel();
		idField = new JTextField();
		btnQuery = new JButton();
		btnDel = new JButton();
		queryPane.setLayout(new GridLayout(1, 3));
		idLabel.setText("输入" + recordType + "编号：");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		queryPane.add(idLabel);
		queryPane.add(idField);

		btnQuery.setText("查询");
		btnQuery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryActionPerformed(e);
			}
		});
		queryPane.add(btnQuery);
		contentPane.add(queryPane, BorderLayout.NORTH);

		// 添加查询结果展示区，根据数据类型的不同，在界面上设置不同数目的网格和边框大小
		String[] currLabels = new String[0];
		if (recordType.equals("电影")) {
			currLabels = Constant.movieLabels;
			recordPane.setLayout(new GridLayout(7, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(50, 150, 100, 300));
		}
		if (recordType.equals("场次")) {
			currLabels = Constant.showLabels;
			recordPane.setLayout(new GridLayout(5, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 150, 300));
		}
		if (recordType.equals("订单")) {
			currLabels = Constant.orderLabels;
			recordPane.setLayout(new GridLayout(4, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 200, 300));
		}
		if (recordType.equals("用户")) {
			currLabels = Constant.staffLabels;
			recordPane.setLayout(new GridLayout(4, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 200, 300));
		}

		for (int i = 0; i < currLabels.length; i++) {
			JLabel entryLabel = new JLabel();
			entryLabel.setText(currLabels[i] + "：");
			entryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			JTextField entryField = new JTextField();
			entryField.setEditable(false);
			entryField.setBackground(new Color(230, 230, 230));			
			recordPane.add(entryLabel);
			if(!(recordType.equals("订单") && currLabels[i].equals("订单数据"))){
				recordPane.add(entryField);
				}
			textFields.add(entryField);			
		}
		
		if (recordType.equals("订单")) {
			JButton viewButton = new JButton("点击查看");			
			viewButton.addMouseListener(new java.awt.event.MouseAdapter() {
			    @Override
			    public void mouseClicked(java.awt.event.MouseEvent evt) {
			    	String orderData = textFields.get(3).getText();
			    	if(orderData.length()>0)
					  new OrderShowDialog(orderData);
				  } 
				} );
			recordPane.add(viewButton);
		}
		
	
		contentPane.add(recordPane, BorderLayout.CENTER);

		//添加底部按钮行
		buttonBar = new JPanel();
		buttonBar.setBorder(new EmptyBorder(15, 5, 5, 5));
		buttonBar.setLayout(new GridBagLayout());
		((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {
				1.0, 0.0, 0.0 };
		((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0, 80,
				75 };

		btnDel.setText("删除"+ recordType);
		btnDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDelActionPerformed(e);
			}
		});
		buttonBar.add(btnDel, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));
		contentPane.add(buttonBar, BorderLayout.SOUTH);
		
		//显示当前内容区（包含展示区和功能区），并添加到传入的主页面内容container
		contentPane.setVisible(true);
		mainContent.add(contentPane, BorderLayout.CENTER);
	}

	private void btnQueryActionPerformed(ActionEvent e) {
		int itemId = -1;
		boolean queryFail = false;
		try {
			String idVal = idField.getText();
			if (idVal.length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入编号后再查询!");
				return;
			}
			itemId = Integer.parseInt(idVal);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "输入格式不正确，需要是整数!");
			return;
		}
		if (recordType.equals("电影")) {
			Movie movie = MovieDao.getMovie(itemId);
			if (movie != null) {
				textFields.get(0).setText(movie.getMid()+"");
				textFields.get(1).setText(movie.getName());
				textFields.get(2).setText(movie.getType());
				textFields.get(3).setText(movie.getDirector());
				textFields.get(4).setText(movie.getSource());
				textFields.get(5).setText(movie.getPublisher());
				textFields.get(6).setText(movie.getReleaseDate() + "");
			}else queryFail = true;
		}

		if (recordType.equals("场次")) {
			Show show = ShowDao.getShow(itemId);
			if (show != null) {
				textFields.get(0).setText(show.getId() + "");
				textFields.get(1).setText(MovieDao.getMovie(show.getMid()).getName() + "");
				textFields.get(2).setText(show.getHall() + "");
				textFields.get(3).setText(show.getTime() + "");
				textFields.get(4).setText(show.getPrice() + "");
				textFields.get(4).setHorizontalAlignment(SwingConstants.LEFT);
			}else queryFail = true;
		}

		if (recordType.equals("订单")) {
			Order order = OrderDao.getOrder(itemId);
			if (order != null) {
				textFields.get(0).setText(order.getId()+"");
				textFields.get(1).setText(order.getName());
				textFields.get(2).setText(order.getPhone());
				textFields.get(3).setText(order.getData());
				textFields.get(3).setVisible(false);
			}else queryFail = true;
		}

		if (recordType.equals("用户")) {
			Staff user = StaffDao.getUser(itemId);
			if (user != null) {
				textFields.get(0).setText(user.getUid()+"");
				textFields.get(1).setText(user.getUsername());
				textFields.get(2).setText(user.getPassword());				
				int index = CheckHandler.geSelectIndexById(user.getRole());
				if(index>=0) textFields.get(3).setText(Constant.userRoleDescs[index]);
			}else queryFail = true;
		}
		textFields.get(0).setEnabled(false);
		
		if(queryFail){
			JOptionPane.showMessageDialog(this.contentPane, "未检索到数据，请调整编号！");
			return;
		}
	}

	/**
	 * 该方法处理在数据库表格中删除不同类别的数据
	 */
	private void btnDelActionPerformed(ActionEvent e) {
		if(textFields.get(0).getText().length()==0) return;
		int itemId = Integer.parseInt(textFields.get(0).getText()); // 获取上次查询所用的编号
		boolean success = false;
		if (recordType.equals("电影"))
			success = MovieDao.deleteMovie(itemId);
		if (recordType.equals("场次"))
			success = ShowDao.deleteShow(itemId);
		if (recordType.equals("订单")){			
			String orderData = textFields.get(3).getText();
			List<String> usedSeats = CheckHandler.getSeats(orderData);
			ShowDao.removeUsedSeats(usedSeats); // 将该订单中所涉及的座位重新释放到放映场次中
			success = OrderDao.deleteOrder(itemId); //删除订单记录
			}			
		if (recordType.equals("用户"))
			success = StaffDao.deleteUser(itemId);
		if (success) {
			JOptionPane.showMessageDialog(null, "删除成功");
			new RecordQuery(contentPane, recordType, mainFrame);
			contentPane.setBorder(new EmptyBorder(0, 50, 100, 50));
			mainFrame.setVisible(true);
		}

	}
}
