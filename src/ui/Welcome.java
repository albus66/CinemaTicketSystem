/**
 * 该类用于生成最初的欢迎界面，它包含了该项目的main程序
 */
package ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.Staff;

import util.GlobalVars;
import database.StaffDao;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;

public class Welcome extends JFrame {
	//创建用户名这个标签
	private JLabel userLabel;
	//创建输入用户名的编辑框
	private JTextField userField;
	//创建密码这个标签
	private JLabel passLabel;
	//创建输入密码的编辑框
	private JPasswordField passField;
	//创建开始订票这个按钮
	private JButton orderButton;
	//创建管理员登录按钮
	private JButton submitButton;

	public Welcome() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Program and code\\javafile\\CinemaTicketSystem\\res\\mainBackground.jpg"));
		//调用私有的初始化方法
		initUI();
	}
	private void initUI() {
		//设置窗体的标题
		setTitle("电影订票系统");
		//获取当前窗体对象
		Container welcomePane = getContentPane();
//		//创建具有分层的JLayeredPane
//		JLayeredPane layeredPane = new JLayeredPane();
//		layeredPane.setBounds(0,-5,300,200);
//		//frame.getContentPane().add(layeredPane);
//		//创建图片对象
//		ImageIcon
		//创建顶端面板
		JPanel topPane = new JPanel(); 	
		topPane.setOpaque(false);
		topPane.setBounds(0, 1, 786, 187);
		////设置面板的Insets，它有四个值：顶，左，底，右。设置面板与当前窗体的顶端距离是60
		topPane.setBorder(new EmptyBorder(60, 0, 0, 0));
		//创建标签，并指定显示信息
		JLabel welcomeLabel = new JLabel("欢迎光临影院在线订票系统");
		//设置标签的字体信息
		welcomeLabel.setFont(new Font("Serif", Font.PLAIN, 30));
		//将标签添加到面板上
		topPane.add(welcomeLabel);
		//创建中间面板和底端面板
		JPanel midPane = new JPanel(); 
		midPane.setOpaque(false);
		midPane.setBounds(0, 188, 786, 187);
		JPanel btmPane = new JPanel(); 
		btmPane.setBackground(new Color(255, 255, 0));
		btmPane.setOpaque(false);
		btmPane.setBounds(0, 375, 786, 187);
		//设置中间面板的布局是1行2列的网格布局
		midPane.setLayout(new GridLayout(1, 2));
		//设置面板的距离左边100，右边100
		midPane.setBorder(new EmptyBorder(0, 100, 0, 100));
		//创建用户订票面板custPane
		JPanel custPane = new JPanel();
		custPane.setOpaque(false);
		//设置custPane面板的insets值
		custPane.setBorder(new EmptyBorder(40, 0, 40, 50));
		//这个面板是2行1列的网格布局
		custPane.setLayout(new GridLayout(2, 1));
		
		JLabel helloLabel = new JLabel("        顾客，您好！");
		helloLabel.setFont(new Font("宋体", Font.PLAIN, 17));
		custPane.add(helloLabel); //标签添加到面板上
		orderButton = new JButton("开始订票");
		//按钮的单击事件
		orderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//用户单击【开始订票】按钮的处理方程
				orderButtonActionPerformed(e);
			}
		});
		custPane.add(orderButton);//按钮添加到面板custPane
		midPane.add(custPane);//custPane面板添加到中间面板
		
		JPanel adminPane = new JPanel(); //管理员登录面板
		adminPane.setOpaque(false);
		adminPane.setBorder(new EmptyBorder(40, 0, 40, 0));
		adminPane.setLayout(new GridLayout(3, 2));

		// 添加用户名标签
		userLabel = new JLabel("用户名：");
		//设置沿x轴，右对齐
		userLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		adminPane.add(userLabel);//将标签添加到管理员面板

		// 添加用户名输入框
		userField = new JTextField();
		adminPane.add(userField);

		// 添加密码标签
		passLabel = new JLabel("密  码：");
		passLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		adminPane.add(passLabel);

		// 添加密码输入框
		passField = new JPasswordField();
		adminPane.add(passField);
		
		adminPane.add(new JLabel());

		// 添加提交按钮
		submitButton = new JButton("管理员登录");
		//单击【管理员登录】按钮的事件
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//管理员登录处理方法
				submitButtonActionPerformed(e);
			}
		});
		//按钮添加到面板
		adminPane.add(submitButton);
		midPane.add(adminPane);//管理员面板添加到中间面板
		getContentPane().setLayout(null);
		
		//将顶端、中间、底端面板添加到主面板中
		welcomePane.add(topPane);
		welcomePane.add(midPane);
		welcomePane.add(btmPane);
		
		//设置窗体大小
		setSize(800, 600);
		//getOwner()返回此窗体的所有者，即设置该窗体相对于它自己的位置
		setLocationRelativeTo(getOwner());
		//添加窗口监听
		this.addWindowListener(new WindowAdapter() {
	        @Override
			public void windowClosing(WindowEvent e) {
	        	dispose();//关闭窗体时，不显示当前窗体
	        }

	    });
	}

	//"登录提交"按钮单击响应事件
	private void submitButtonActionPerformed(ActionEvent e) {

		String username = userField.getText(); // 获得用户名
		String password = String.valueOf(passField.getPassword()); // 获得密码
		//在没有输入用户名时，提示用户名为空
		if (username.equals("")) { 
			JOptionPane.showMessageDialog(this, "用户名不允许为空！");
			return;
		}

		try {
			//根据用户名，在数据库中查询用户是否存在
			Staff user = StaffDao.getUserByCredential(username, password);
			if (user == null) { //是null，说明用户不存在
				//信息提示框，提示用户名或密码不正确！
				JOptionPane.showMessageDialog(this, "用户名或密码不正确!");
				return;
			}
			//将登录的管理员id、管理员名和密码，存入GlobalVars类中
			GlobalVars.userId = user.getUid(); // 记录当前用户id
			GlobalVars.userName = user.getUsername(); // 记录当前用户名
			GlobalVars.userRole = user.getRole(); // 记录当前用户角色

			//进入管理员后台管理界面
			AdminWindow main = new AdminWindow();
			//根据管理员的权限，设置当前登录管理员可以进行的操作
			main.setViewVisable(user.getRole());
			this.dispose();//当前窗体不显示
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}	
	//"开始订票"按钮点击响应事件
	private void orderButtonActionPerformed(ActionEvent e) {
		try {
			// 进入订票主界面
			OrderWindow orderWindow = new OrderWindow();
			//设置用户订票界面可见
			orderWindow.setVisible(true);
			this.dispose();//当前窗体不可见
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public static void main(String args[]) {
		Welcome jFrame = new Welcome();//创建类的对象
		jFrame.setSize(800,600);//设置窗口大小
        jFrame.setLocationRelativeTo(null);//窗口居中
        jFrame.getContentPane().setLayout(null);//无布局，记得下面添加控件时要设置它们位置和大小
        JPanel imPanel=(JPanel) jFrame.getContentPane();//注意内容面板必须强转为JPanel才可以实现下面的设置透明
        imPanel.setOpaque(false);//将内容面板设为透明
        ImageIcon icon=new ImageIcon("F:\\Program and code\\javafile\\CinemaTicketSystem\\res\\mainBackground.jpg");//背景图
        JLabel label = new JLabel(icon);//往一个标签中加入图片
        label.setBounds(0, 0, jFrame.getWidth(), jFrame.getHeight());//设置标签位置大小，记得大小要和窗口一样大
        icon.setImage(icon.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));//图片自适应标签大小
        jFrame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));//标签添加到层面板

		jFrame.setVisible(true); //设置窗体可见
	}
}
