/**
 * 该类用于编辑（修改或者删除）已有的电影，放映场次，订单或者管理员。点击电影（或其他）列表中的记录将打开该编辑页面
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePickerImpl;

import util.CheckHandler;
import util.Constant;
import util.DateHandler;

import com.Movie;
import com.Order;
import com.Show;
import com.Staff;

import database.MovieDao;
import database.OrderDao;
import database.ShowDao;
import database.StaffDao;

public class RecordEditDialog extends JDialog {

	private String recordType; // movies, show, order, user
	private JPanel dialogPane;
	private JComboBox<String> roleBox;
	private JComboBox<String> hourBox;
	private JComboBox<String> minBox;
	private JComboBox<String> movieBox;
	private JDatePickerImpl datePicker;
	private JPanel combinedPane;
	private JPanel recordPane;
	private JPanel buttonBar;
	private JButton btnSave;
	private JButton btnDelete;
	private JButton btnClose;
	private JTable srcTable;
	private int srcRowId;

	private List<JTextField> textFields;
	private List<Integer> movieIds;

	public RecordEditDialog(String type, int itemId, JTable dataTable, int rowId) {
		recordType = type; // 数据类型
		textFields = new ArrayList<JTextField>();
		movieIds = new ArrayList<Integer>();
		srcTable = dataTable;
		srcRowId = rowId;
		initUI(); // 初始化弹出框界面
		setInitialData(itemId); // 初始化弹出框里表格的数据
	}

	// 初始化用户操作界面
	private void initUI() {
		dialogPane = new JPanel();
		dialogPane.setLayout(new BorderLayout());
		recordPane = new JPanel();
		buttonBar = new JPanel();
		btnSave = new JButton();
		btnDelete = new JButton();
		btnClose = new JButton();

		setTitle(recordType.equals("订单") ? "删除" : "编辑/删除" + recordType);
		setResizable(false);
		Container contentPane = getContentPane();

		String[] currLabels = new String[0];
		if (recordType.equals("电影")) {
			currLabels = Constant.movieLabels;
			recordPane.setLayout(new GridLayout(7, 2, 6, 6));
			dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		}
		if (recordType.equals("场次")) {
			currLabels = Constant.showLabels;
			recordPane.setLayout(new GridLayout(6, 2, 6, 6));
			dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		}
		if (recordType.equals("订单")) {
			currLabels = Constant.orderLabels;
			recordPane.setLayout(new GridLayout(4, 2, 6, 6));
			dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		}

		if (recordType.equals("用户")) {
			currLabels = Constant.staffLabels;
			recordPane.setLayout(new GridLayout(4, 2, 6, 6));
			dialogPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		}

		for (int i = 0; i < currLabels.length; i++) {
			JLabel entryLabel = new JLabel();
			entryLabel.setText(currLabels[i] + "：");
			entryLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			JTextField entryField = new JTextField();
			if (recordType.equals("订单") || i == 0) {
				entryField.setEnabled(false);
				entryField.setBackground(new Color(230, 230, 230));
			}
			recordPane.add(entryLabel);
			// 采用输入框展示数据的项目
			if (recordType.equals("场次") && currLabels[i].equals("电影名称")) {
				List<Movie> movies = MovieDao.getMovies("", "");
				String[] movieNames = new String[movies.size()];
				for (int m = 0; m < movies.size(); m++) {
					movieNames[m] = movies.get(m).getName();
					movieIds.add(movies.get(m).getMid());
				}
				movieBox = new JComboBox<String>(movieNames);
				recordPane.add(movieBox);
			} else if (recordType.equals("电影") && currLabels[i].equals("上映日期")) {
				combinedPane = new JPanel();
				datePicker = DateHandler.getDatePicker();
				datePicker.setPreferredSize(new Dimension(160, 30));
				combinedPane.add(datePicker);
				recordPane.add(combinedPane);
			} else if (recordType.equals("场次") && currLabels[i].equals("放映时间")) {
				datePicker = DateHandler.getDatePicker();
				datePicker.setPreferredSize(new Dimension(140, 20));
				datePicker.setBorder(new EmptyBorder(10, 0, 0, 20));
				recordPane.add(datePicker);
				combinedPane = new JPanel();
				hourBox = new JComboBox<String>(Constant.timeHours);
				JLabel sepLabel1 = new JLabel("时");
				JLabel sepLabel2 = new JLabel("分");
				minBox = new JComboBox<String>(Constant.timeMinutes);
				combinedPane.add(hourBox);
				combinedPane.add(sepLabel1);
				combinedPane.add(minBox);
				combinedPane.add(sepLabel2);
				JLabel timeLabel = new JLabel("");
				recordPane.add(timeLabel);
				timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
				recordPane.add(combinedPane);
			} else if (recordType.equals("用户") && currLabels[i].equals("权限")) {
				roleBox = new JComboBox<String>(Constant.userRoleDescs);
				recordPane.add(roleBox);
			} else if(recordType.equals("订单") && currLabels[i].equals("订单数据")){
				textFields.add(entryField);
			}else{
				recordPane.add(entryField);
				textFields.add(entryField);
			}
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

		dialogPane.add(recordPane, BorderLayout.CENTER);

		buttonBar.setBorder(new EmptyBorder(15, 5, 5, 5));
		buttonBar.setLayout(new GridBagLayout());
		((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {
				1.0, 0.0, 0.0 };
		((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0,
				80, 75 };

		if (!recordType.equals("订单")) {
			btnSave.setText("保存修改");
			btnSave.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					btnSaveActionPerformed(e);
				}
			});
			buttonBar.add(btnSave, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));
		}

		btnDelete.setText("删除" + recordType);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDeleteActionPerformed(e);
			}
		});
		buttonBar.add(btnDelete, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));

		btnClose.setText("关闭");
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCloseActionPerformed(e);
			}
		});
		buttonBar.add(btnClose, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));

		dialogPane.add(buttonBar, BorderLayout.SOUTH);
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(500, 400);
		setLocationRelativeTo(getOwner());
		setVisible(true);

	}

	/**
	 * 该方法处理更新不同类别的数据到对应的数据库表格
	 */
	private void btnSaveActionPerformed(ActionEvent e) {
		if (textFields.get(0).getText().length() == 0) {
			return;
		}
		int itemId = Integer.parseInt(textFields.get(0).getText());
		boolean success = false;
		if (recordType.equals("电影")) {
			Movie movie = new Movie();
			movie.setMid(itemId);
			movie.setName(textFields.get(1).getText());
			movie.setType(textFields.get(2).getText());
			movie.setDirector(textFields.get(3).getText());
			movie.setSource(textFields.get(4).getText());
			movie.setPublisher(textFields.get(5).getText());
			String totalContent = textFields.get(1).getText() + textFields.get(2).getText()
					+ textFields.get(3).getText() + textFields.get(4).getText();
			if (CheckHandler.containsDigit(totalContent)) {
				JOptionPane.showMessageDialog(this,
						"你的输入中包含了数字，请只输入文字内容！");
				return;
			}
			movie.setReleaseDate(datePicker.getJFormattedTextField().getText());
			success = MovieDao.updateMovie(movie);
		}
		if (recordType.equals("场次")) {

			Show show = new Show();
			show.setId(itemId);

			show.setMid(movieIds.get(movieBox.getSelectedIndex()));
			if (!CheckHandler.isNumeric(textFields.get(1).getText())) {
				JOptionPane.showMessageDialog(this, "输入的放映厅号码必须为整数！");
				return;
			} else
				show.setHall(Integer.parseInt(textFields.get(1).getText()));

			String time = datePicker.getJFormattedTextField().getText();
			String hour = hourBox.getSelectedItem().toString();
			String minute = minBox.getSelectedItem().toString();
			time += " " + hour + ":" + minute;
			show.setTime(time);

			if (!CheckHandler.isNumeric(textFields.get(2).getText())) {
				JOptionPane.showMessageDialog(this, "输入的价格必须为数字！");
				return;
			} else
				show.setPrice(Double.parseDouble(textFields.get(2).getText()));
			System.out.println(show.getHall() + " " + show.getPrice());
			success = ShowDao.updateShow(show);

		}
		if (recordType.equals("用户")) {

			Staff user = new Staff();
			user.setUid(itemId);
			user.setUsername(textFields.get(1).getText());
			user.setPassword(textFields.get(2).getText());
			user.setRole(Constant.userRoleIds[roleBox.getSelectedIndex()]);
			success = StaffDao.updateUser(user);

		}

		if (success) {
			updateSrcTable(); // 同步更新查询页面的数据行
			JOptionPane.showMessageDialog(this.dialogPane, "修改保存成功");
			dispose();
		}
	}

	private void btnDeleteActionPerformed(ActionEvent e) {
		if (textFields.get(0).getText().length() == 0) {
			return;
		}
		boolean success = false;
		int itemId = Integer.parseInt(textFields.get(0).getText());
		;
		if (recordType.equals("电影")) {
			success = MovieDao.deleteMovie(itemId);

		}
		if (recordType.equals("场次")) {

			success = ShowDao.deleteShow(itemId);

		}
		if (recordType.equals("订单")) {
			String orderData = textFields.get(3).getText();
			List<String> usedSeats = CheckHandler.getSeats(orderData);
			ShowDao.removeUsedSeats(usedSeats); // 将该订单中所涉及的座位重新释放到放映场次中
			success = OrderDao.deleteOrder(itemId);

		}

		if (recordType.equals("用户")) {

			success = StaffDao.deleteUser(itemId);
		}

		if (success) {
			((DefaultTableModel) srcTable.getModel()).removeRow(srcRowId); // 同步删除查询页面的数据行
			JOptionPane.showMessageDialog(this.dialogPane, recordType + "删除成功");
			dispose();
		}
	}

	private void btnCloseActionPerformed(ActionEvent e) {
		dispose();
	}

	private void setInitialData(int itemId) {
		boolean queryFail = false;
		if (recordType.equals("电影")) {
			Movie movie = MovieDao.getMovie(itemId);
			if (movie != null) {
				textFields.get(0).setText(movie.getMid() + "");
				textFields.get(1).setText(movie.getName());
				textFields.get(2).setText(movie.getType());
				textFields.get(3).setText(movie.getDirector());
				textFields.get(4).setText(movie.getSource());
				textFields.get(5).setText(movie.getPublisher());
				
				datePicker.getJFormattedTextField().setText(
						movie.getReleaseDate());
			} else
				queryFail = true;
		}

		if (recordType.equals("场次")) {
			Show show = ShowDao.getShow(itemId);
			if (show != null) {
				textFields.get(0).setText(show.getId() + "");
				textFields.get(1).setText(show.getHall() + "");
				textFields.get(2).setText(show.getPrice() + "");
				String[] timeMeta = show.getTime().split(" ");
				movieBox.setSelectedItem(MovieDao.getMovie(show.getMid())
						.getName());
				datePicker.getJFormattedTextField().setText(timeMeta[0]);
				String[] timeMeta1 = timeMeta[1].split(":");
				hourBox.setSelectedItem(timeMeta1[0]);
				minBox.setSelectedItem(timeMeta1[1]);
			} else
				queryFail = true;
		}

		if (recordType.equals("订单")) {
			Order order = OrderDao.getOrder(itemId);
			if (order != null) {
				textFields.get(0).setText(order.getId() + "");
				textFields.get(1).setText(order.getName());
				textFields.get(2).setText(order.getPhone());
				textFields.get(3).setText(order.getData());
			} else
				queryFail = true;
		}

		if (recordType.equals("用户")) {
			Staff user = StaffDao.getUser(itemId);
			if (user != null) {
				textFields.get(0).setText(user.getUid() + "");
				textFields.get(1).setText(user.getUsername());
				textFields.get(2).setText(user.getPassword());
				int index = CheckHandler.geSelectIndexById(user.getRole());
				if (index >= 0)
					roleBox.setSelectedIndex(index);
			} else
				queryFail = true;
		}
		textFields.get(0).setEnabled(false);
		if (queryFail) {
			JOptionPane.showMessageDialog(this.dialogPane, "未检索到数据，请调整编号！");
			return;
		}
	}

	// 同步更新查询页面的数据表格
	private void updateSrcTable() {
		
		if (!recordType.equals("场次")) {
			int i = 1;
			while (i < textFields.size()) { // 更新表格中的数据项
				srcTable.setValueAt(textFields.get(i).getText(), srcRowId, i);
				i++;
			}
			if (recordType.equals("用户")) { // 如果时用户数据，单独更新权限项
				srcTable.setValueAt(roleBox.getSelectedItem(), srcRowId, 3);
			}
			if (recordType.equals("电影")) { // 如果是电影数据，单独更新权限项
				srcTable.setValueAt(datePicker.getJFormattedTextField()
						.getText(), srcRowId, 6);
			}
		} else {
			srcTable.setValueAt(movieBox.getSelectedItem(), srcRowId, 1);
			srcTable.setValueAt(textFields.get(1).getText(), srcRowId, 2);
			String time = datePicker.getJFormattedTextField().getText();
			String hour = hourBox.getSelectedItem().toString();
			String minute = minBox.getSelectedItem().toString();
			time += " " + hour + ":" + minute;
			srcTable.setValueAt(time, srcRowId, 3);
			srcTable.setValueAt(textFields.get(2).getText(), srcRowId, 4);
		}

	}
}
