/**
 * 该类用于添加新的电影，放映场次或者管理员
 */
package ui;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.impl.JDatePickerImpl;

import util.CheckHandler;
import util.Constant;
import util.DateHandler;

import com.Movie;
import com.Show;
import com.Staff;

import database.MovieDao;
import database.ShowDao;
import database.StaffDao;

public class RecordAdd {

	//定义一个要添加内容的变量
	private String recordType;
	private JPanel contentPane;
	private JPanel recordPane;
	//定义一些需要下拉框的 内容
	private JComboBox<String> roleBox;
	private JComboBox<String> hourBox;
	private JComboBox<String> minBox;
	private JComboBox<String> movieBox;
	private JDatePickerImpl datePicker;
	private JPanel combinedPane;
	private JPanel btnBar;
	//定义一个保存添加信息的按钮
	private JButton btnSave;
	private JFrame mainFrame;

	private List<JTextField> textFields;
	private List<Integer> movieIds;

	public RecordAdd(Container mainContent, String type, JFrame frame) {
		recordType = type;
		textFields = new ArrayList<JTextField>();
		movieIds = new ArrayList<Integer>();
		mainFrame = frame;
		initUI(mainContent); // 传入主页面的内容区
	}

	// 初始化用户操作界面
	private void initUI(Container mainContent) {
		mainContent.removeAll(); // 清空主页面的内容区
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());

		recordPane = new JPanel();
		btnBar = new JPanel();
		btnSave = new JButton();

		// 根据数据类型的不同，在界面上设置不同数目的网格和边框大小
		String[] currLabels = new String[0];
		if (recordType.equals("电影")) {
			currLabels = Constant.movieLabels;
			recordPane.setLayout(new GridLayout(6, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 150, 300));
		}
		if (recordType.equals("场次")) {
			currLabels = Constant.showLabels;
			recordPane.setLayout(new GridLayout(5, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 200, 250));
		}
		if (recordType.equals("用户")) {
			currLabels = Constant.staffLabels;
			recordPane.setLayout(new GridLayout(3, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(150, 150, 250, 300));
		}

		for (int i = 1; i < currLabels.length; i++) {
			JLabel entryLabel = new JLabel();
			JTextField entryField = new JTextField();
			entryLabel.setText(currLabels[i] + "：");
			entryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

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
				datePicker.setPreferredSize(new Dimension(160, 30));
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
			} else {
				recordPane.add(entryField);
				textFields.add(entryField);
			}

		}
		contentPane.add(recordPane, BorderLayout.CENTER);

		// 添加底部按钮行
		btnBar.setBorder(new EmptyBorder(12, 0, 0, 0));
		btnBar.setLayout(new GridBagLayout());
		((GridBagLayout) btnBar.getLayout()).columnWidths = new int[] { 0, 85,
				80 };
		((GridBagLayout) btnBar.getLayout()).columnWeights = new double[] {
				1.0, 0.0, 0.0 };

		// 添加保存按钮
		btnSave.setText("添加" + recordType);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
			}
		});
		btnBar.add(btnSave, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));
		contentPane.add(btnBar, BorderLayout.SOUTH);

		// 显示当前内容区（包含展示区和功能区），并添加到传入的主页面内容container
		contentPane.setVisible(true);
		mainContent.add(contentPane, BorderLayout.CENTER); // 将添加表格放入主页面的内容区
	}

	/**
	 * 该方法处理添加不同类别的数据到对应的数据库表格
	 */
	private void btnSaveActionPerformed(ActionEvent e) {
		if (CheckHandler.checkEmptyField(textFields)) {
			JOptionPane.showMessageDialog(this.contentPane, "有些项为空，请填入内容!");
			return;
		}
		boolean success = false;
		if (recordType.equals("电影")) {
			Movie movie = new Movie();
			movie.setName(textFields.get(0).getText());
			movie.setType(textFields.get(1).getText());
			movie.setDirector(textFields.get(2).getText());
			movie.setSource(textFields.get(3).getText());
			movie.setPublisher(textFields.get(4).getText());
			String totalContent = textFields.get(1).getText() + textFields.get(2).getText()
					+ textFields.get(3).getText() + textFields.get(4).getText();
			if (CheckHandler.containsDigit(totalContent)) {
				JOptionPane.showMessageDialog(this.contentPane,
						"你的输入中包含了数字，请只输入文字内容！");
				return;
			}
			movie.setReleaseDate(datePicker.getJFormattedTextField().getText());
			success = MovieDao.addMovie(movie);
		}
		if (recordType.equals("场次")) {

			Show show = new Show();
			show.setMid(movieIds.get(movieBox.getSelectedIndex()));

			if (!CheckHandler.isNumeric(textFields.get(0).getText())) {
				JOptionPane.showMessageDialog(this.contentPane,
						"输入的放映厅号码必须为整数！");
				return;
			} else
				show.setHall(Integer.parseInt(textFields.get(0).getText()));

			String time = datePicker.getJFormattedTextField().getText();
			String hour = hourBox.getSelectedItem().toString();
			String minute = minBox.getSelectedItem().toString();
			time += " " + hour + ":" + minute;
			show.setTime(time);

			if (!CheckHandler.isNumeric(textFields.get(1).getText())) {
				JOptionPane.showMessageDialog(this.contentPane, "输入的价格必须为数字！");
				return;
			} else
				show.setPrice(Double.parseDouble(textFields.get(1).getText()));
			show.setSeatsUsed("");
			success = ShowDao.addShow(show);
		}
		if (recordType.equals("用户")) {

			Staff user = new Staff();
			user.setUsername(textFields.get(0).getText());
			user.setPassword(textFields.get(1).getText());
			user.setRole(Constant.userRoleIds[roleBox.getSelectedIndex()]);
			success = StaffDao.addUser(user);

		}
		if (success) { // 未检测到数据库返回的错误，则添加成功
			JOptionPane.showMessageDialog(this.contentPane, "添加成功");
			new RecordQuery(contentPane, recordType, mainFrame);
			contentPane.setBorder(new EmptyBorder(0, 50, 100, 50));
			mainFrame.setVisible(true);
		}
	}

}
