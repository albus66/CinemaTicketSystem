/**
 * 该类用于更新已有的电影，放映场次或者管理员
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

public class RecordUpdate {

	private String recordType; // movies, show, order, user
	private JLabel idLabel;
	private JTextField idField;
	private JPanel contentPane;
	private JPanel queryPane;
	private JComboBox<String> roleBox;
	private JComboBox<String> hourBox;
	private JComboBox<String> minBox;
	private JComboBox<String> movieBox;
	private JDatePickerImpl datePicker;
	private JPanel combinedPane;
	private JPanel recordPane;
	private JPanel buttonBar;
	private JButton btnSave;

	private List<JTextField> textFields;
	private List<Integer> movieIds;
	
	public JFrame mainFrame;

	public RecordUpdate(Container mainContent, String type,  JFrame frame) {
		recordType = type;
		textFields = new ArrayList<JTextField>();
		movieIds = new  ArrayList<Integer>();
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
		buttonBar = new JPanel();
		btnSave = new JButton();
		queryPane.setLayout(new GridLayout(1, 3));
		idLabel = new JLabel(recordType + "编号：");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idField = new JTextField();
		JButton queryBtn = new JButton("查询");
		queryBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnQueryActionPerformed(e);
			}
		});
		queryPane.add(idLabel);
		queryPane.add(idField);
		queryPane.add(queryBtn);
		
		// 添加查询结果展示区，根据数据类型的不同，在界面上设置不同数目的网格和边框大小
		String[] currLabels = new String[0];
		if (recordType.equals("电影")) {
			currLabels = Constant.movieLabels;
			recordPane.setLayout(new GridLayout(7, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(50, 150, 100, 300));
		}
		if (recordType.equals("场次")) {
			currLabels = Constant.showLabels;
			recordPane.setLayout(new GridLayout(6, 2, 6, 6));
			contentPane.setBorder(new EmptyBorder(100, 150, 150, 250));
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
			if (i == 0){
				entryField.setEnabled(false);
				entryField.setBackground(new Color(230, 230, 230));
				}
			recordPane.add(entryLabel);
			// 采用输入框展示数据的项目
			if (recordType.equals("场次") && currLabels[i].equals("电影名称")) {
				List<Movie> movies = MovieDao.getMovies("", "");
				String[] movieNames = new String[movies.size()];
				for(int m=0; m<movies.size(); m++){
					movieNames[m] = movies.get(m).getName();
					movieIds.add(movies.get(m).getMid());
				}
				movieBox = new JComboBox<String>(movieNames);
				recordPane.add(movieBox);
			}else if (recordType.equals("电影") && currLabels[i].equals("上映日期")) {		
				combinedPane = new JPanel();
				datePicker = DateHandler.getDatePicker();
				datePicker.setPreferredSize(new Dimension(160, 30));
				combinedPane.add(datePicker);
				recordPane.add(combinedPane);
			}else if (recordType.equals("场次") && currLabels[i].equals("放映时间")) {	
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
			}else if (recordType.equals("用户") && currLabels[i].equals("权限")) { 
				roleBox = new JComboBox<String>(Constant.userRoleDescs);
				recordPane.add(roleBox);
			}else{
				recordPane.add(entryField);
				textFields.add(entryField);	
			}
		}
		contentPane.add(queryPane, BorderLayout.NORTH);
		contentPane.add(recordPane, BorderLayout.CENTER);

		//添加底部按钮行
		buttonBar.setBorder(new EmptyBorder(15, 5, 5, 5));
		buttonBar.setLayout(new GridBagLayout());
		((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {
				1.0, 0.0, 0.0 };
		((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0, 80,
				75 };

		btnSave.setText("保存修改");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSaveActionPerformed(e);
			}
		});
		buttonBar.add(btnSave, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));
		contentPane.add(buttonBar, BorderLayout.SOUTH);
		
		//显示当前内容区（包含展示区和功能区），并添加到传入的主页面内容container
		contentPane.setVisible(true);
		mainContent.add(contentPane, BorderLayout.CENTER);
	}

	/**
	 * 该方法处理更新不同类别的数据到对应的数据库表格
	 */
	private void btnSaveActionPerformed(ActionEvent e) {
		if(textFields.get(0).getText().length()==0){
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
				JOptionPane.showMessageDialog(this.contentPane,
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
			if(!CheckHandler.isNumeric(textFields.get(1).getText())){
				JOptionPane.showMessageDialog(this.contentPane, "输入的放映厅号码必须为整数！");
				return;
			}
			else
				show.setHall(Integer.parseInt(textFields.get(1).getText()));
			
			String time = datePicker.getJFormattedTextField().getText();
			String hour = hourBox.getSelectedItem().toString();
			String minute = minBox.getSelectedItem().toString();
			time += " "+ hour+":"+ minute;
			show.setTime(time);
			
			if(!CheckHandler.isNumeric(textFields.get(2).getText())){
				JOptionPane.showMessageDialog(this.contentPane, "输入的价格必须为数字！");
				return;
			}else				
				show.setPrice(Double.parseDouble(textFields.get(2).getText()));			
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
			JOptionPane.showMessageDialog(this.contentPane, "修改成功");
			new RecordQuery(contentPane, recordType, mainFrame);
			
			contentPane.setBorder(new EmptyBorder(0, 50, 100, 50));
			mainFrame.setVisible(true);
		}
	}

	/**
	 * 该方法处理在数据库表格查询不同类型的数据
	 */
	private void btnQueryActionPerformed(ActionEvent e) {
		int itemId = -1;
		boolean queryFail = false;
		try {
			String idVal = idField.getText();
			if (idVal.length() == 0) {
				JOptionPane.showMessageDialog(this.contentPane, "请输入编号后再查询!");
				return;
			}
			itemId = Integer.parseInt(idVal);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this.contentPane, "输入格式不正确，需要是整数!");
			return;
		}
		itemId = Integer.parseInt(idField.getText());

		if (recordType.equals("电影")) {
			Movie movie = MovieDao.getMovie(itemId);
			if (movie != null) {
				textFields.get(0).setText(movie.getMid() + "");
				textFields.get(1).setText(movie.getName());
				textFields.get(2).setText(movie.getType());
				textFields.get(3).setText(movie.getDirector());
				textFields.get(4).setText(movie.getSource());
				textFields.get(5).setText(movie.getPublisher());
				datePicker.getJFormattedTextField().setText(movie.getReleaseDate());
			}else queryFail = true;
		}

		if (recordType.equals("场次")) {
			Show show = ShowDao.getShow(itemId);
			if (show != null) {
				textFields.get(0).setText(show.getId() + "");
				textFields.get(1).setText(show.getHall() + "");
				textFields.get(2).setText(show.getPrice() + "");
				String[] timeMeta = show.getTime().split(" ");
				movieBox.setSelectedItem(MovieDao.getMovie(show.getMid()).getName());
				datePicker.getJFormattedTextField().setText(timeMeta[0]);
				String[] timeMeta1 = timeMeta[1].split(":");
				hourBox.setSelectedItem(timeMeta1[0]);
				minBox.setSelectedItem(timeMeta1[1]);
			}else queryFail = true;
		}

		if (recordType.equals("用户")) {
			Staff user = StaffDao.getUser(itemId);
			if (user != null) {
				textFields.get(0).setText(user.getUid() + "");
				textFields.get(1).setText(user.getUsername());
				textFields.get(2).setText(user.getPassword());
				int index = CheckHandler.geSelectIndexById(user.getRole());
				if(index>=0) roleBox.setSelectedIndex(index);
				else roleBox.setSelectedIndex(0);
			}else queryFail = true;
		}
		textFields.get(0).setEnabled(false);
		if(queryFail){
			JOptionPane.showMessageDialog(this.contentPane, "未检索到数据，请调整编号！");
			return;
		}
	}

}
