/**
 * 该类用于生成后台管理界面
 */
package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import util.Constant;
import util.GlobalVars;

public class AdminWindow extends JFrame {
	//定义一个容器
	public Container contentPane;
	//定义一个菜单条
	private JMenuBar menuBar;
	
	//定义电影管理菜单
	private JMenu movieMenu;
	//定义增加电影选项
	private JMenuItem addMovie;
	//定义修改电影选项
	private JMenuItem updateMovie;
	//定义删除电影选项
	private JMenuItem deleteMovie;
	//定义查询电影选项
	private JMenuItem queryMovie;

	//定义放映场次管理菜单项
	private JMenu showMenu;
	//定义增加放映场次选项
	private JMenuItem addShow;
	private JMenuItem updateShow;
	private JMenuItem deleteShow;
	private JMenuItem queryShow;

	//定义订单管理菜单项
	private JMenu orderMenu;
	private JMenuItem deleteOrder;
	private JMenuItem queryOrder;

	//定义用户管理菜单项
	private JMenu userMenu;
	private JMenuItem queryUser;
	private JMenuItem addUser;
	private JMenuItem updateUser;
	private JMenuItem deleteUser;

	//定义账号管理菜单项
	private JMenu accountMenu;
	private JMenuItem updatePass;
	private JMenuItem exitAccount;

	public AdminWindow() {
		initUI();
	}

	private void initUI() {
		menuBar = new JMenuBar();

		movieMenu = new JMenu(); // 电影管理菜单
		addMovie = new JMenuItem(); // 添加电影菜单项
		updateMovie = new JMenuItem(); // 修改电影菜单项
		deleteMovie = new JMenuItem(); // 删除电影菜单项
		queryMovie = new JMenuItem(); // 查询电影菜单项

		showMenu = new JMenu(); // 放映场次管理菜单
		addShow = new JMenuItem(); // 添加放映场次菜单项
		updateShow = new JMenuItem(); // 修改放映场次菜单项
		deleteShow = new JMenuItem(); // 删除放映场次菜单项
		queryShow = new JMenuItem(); // 查询放映场次菜单项

		orderMenu = new JMenu(); // 订单管理菜单
		deleteOrder = new JMenuItem(); // 删除订单菜单项
		queryOrder = new JMenuItem(); // 查询订单菜单项

		userMenu = new JMenu(); // 用户管理菜单
		queryUser = new JMenuItem(); // 查询订单菜单项
		addUser = new JMenuItem(); // 修改用户菜单项
		updateUser = new JMenuItem(); // 修改用户菜单项
		deleteUser = new JMenuItem(); // 删除用户菜单项

		accountMenu = new JMenu(); // 系统管理菜单项
		updatePass = new JMenuItem(); // 修改密码菜单项
		exitAccount = new JMenuItem(); // 退出系统菜单项

		setTitle("在线订票系统管理后台");
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// 主菜单
		{
			// 电影菜单，添加删除修改电影
			{
				movieMenu.setText("电影管理");

				queryMovie.setText("查询");
				queryMovie.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						queryMovieActionPerformed(e);
					}
				});
				movieMenu.add(queryMovie);

				addMovie.setText("添加");
				addMovie.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addMovieActionPerformed(e);
					}
				});
				movieMenu.add(addMovie);

				updateMovie.setText("修改");
				updateMovie.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						updateMovieActionPerformed(e);
					}
				});
				movieMenu.add(updateMovie);

				deleteMovie.setText("删除");
				deleteMovie.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deleteMovieActionPerformed(e);
					}
				});
				movieMenu.add(deleteMovie);

			}
			menuBar.add(movieMenu);

			// 放映菜单，添加删除修改放映场次
			{
				showMenu.setText("放映场次管理");

				queryShow.setText("查询");
				queryShow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						queryShowActionPerformed(e);
					}
				});
				showMenu.add(queryShow);

				addShow.setText("添加");
				addShow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addShowActionPerformed(e);
					}
				});
				showMenu.add(addShow);

				updateShow.setText("修改");
				updateShow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						updateShowActionPerformed(e);
					}
				});
				showMenu.add(updateShow);

				deleteShow.setText("删除");
				deleteShow.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deleteShowActionPerformed(e);
					}
				});
				showMenu.add(deleteShow);

			}
			menuBar.add(showMenu);

			// 订单菜单，添加和删除订单项
			{
				orderMenu.setText("订单管理");

				queryOrder.setText("查询");
				queryOrder.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						queryOrderActionPerformed(e);
					}
				});
				orderMenu.add(queryOrder);

				deleteOrder.setText("删除");
				deleteOrder.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deleteOrderActionPerformed(e);
					}
				});
				orderMenu.add(deleteOrder);
			}
			menuBar.add(orderMenu);

			// 用户菜单，添加删除修改用户
			{
				userMenu.setText("用户管理");

				queryUser.setText("查询");
				queryUser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						queryUserActionPerformed(e);
					}
				});
				userMenu.add(queryUser);

				addUser.setText("添加");
				addUser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						addUserActionPerformed(e);
					}
				});
				userMenu.add(addUser);

				updateUser.setText("修改");
				updateUser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						updateUserActionPerformed(e);
					}
				});
				userMenu.add(updateUser);

				deleteUser.setText("删除");
				deleteUser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						deleteUserActionPerformed(e);
					}
				});
				userMenu.add(deleteUser);
			}
			menuBar.add(userMenu);

			// 账号管理菜单，密码修改和退出系统
			{
				accountMenu.setText("账号管理");

				updatePass.setText("密码更改");
				updatePass.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						updatePassActionPerformed(e);
					}
				});
				accountMenu.add(updatePass);

				exitAccount.setText("退出系统");
				exitAccount.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						exitAccountActionPerformed(e);
					}
				});
				accountMenu.add(exitAccount);
			}
			menuBar.add(accountMenu);
		}
		setJMenuBar(menuBar); // 添加主菜单到页面顶部
		setSize(800, 600);
		setResizable(false);
		setLocationRelativeTo(getOwner());

		// 设置用户登录后主页面所显示的信息
		if (GlobalVars.userRole == Constant.MOVIE_ADMIN_ROLE) //电影管理权限显示电影列表
			new RecordQuery(this.contentPane, "电影", this); 
		if (GlobalVars.userRole == Constant.SHOW_ADMIN_ROLE) //放映管理权限显示放映场次列表
			new RecordQuery(this.contentPane, "场次", this);
		if (GlobalVars.userRole == Constant.ORDER_ADMIN_ROLE) //订单管理权限显示订单列表
			new RecordQuery(this.contentPane, "订单", this);
		if (GlobalVars.userRole == Constant.FULL_ADMIN_ROLE // 所有管理权限显示电影列表
				|| GlobalVars.userRole == Constant.ROOT_ADMIN_ROLE) 
			new RecordQuery(this.contentPane, "电影", this);
		setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}

		});
	}
	//该方法在主页面显示“查询电影”功能的界面
	private void queryMovieActionPerformed(ActionEvent e) {
		new RecordQuery(this.contentPane, "电影", this);
		setVisible(true);
	}
	
	//该方法在主页面显示“添加电影”功能的界面
	private void addMovieActionPerformed(ActionEvent e) {
		new RecordAdd(this.contentPane, "电影", this);
		setVisible(true);
	}
	//该方法在主页面显示“更新电影”功能的界面
	private void updateMovieActionPerformed(ActionEvent e) {
		new RecordUpdate(this.contentPane, "电影", this);
		setVisible(true);
	}
	//该方法在主页面显示“删除电影”功能的界面
	private void deleteMovieActionPerformed(ActionEvent e) {
		new RecordDelete(this.contentPane, "电影", this);
		setVisible(true);
	}

	//该方法在主页面显示“查询放映场次”功能的界面
	private void queryShowActionPerformed(ActionEvent e) {
		new RecordQuery(this.contentPane, "场次", this);
		setVisible(true);
	}
	
	//该方法在主页面显示“添加放映场次”功能的界面
	private void addShowActionPerformed(ActionEvent e) {
		new RecordAdd(this.contentPane, "场次", this);
		setVisible(true);
	}
	//该方法在主页面显示“更新放映场次”功能的界面
	private void updateShowActionPerformed(ActionEvent e) {
		new RecordUpdate(this.contentPane, "场次", this);
		setVisible(true);
	}
	//该方法在主页面显示“删除放映场次”功能的界面
	private void deleteShowActionPerformed(ActionEvent e) {
		new RecordDelete(this.contentPane, "场次", this);
		setVisible(true);
	}

	//该方法在主页面显示“查询订单”功能的界面
	private void queryOrderActionPerformed(ActionEvent e) {
		new RecordQuery(this.contentPane, "订单", this);
		setVisible(true);
	}
	//该方法在主页面显示“删除订单”功能的界面
	private void deleteOrderActionPerformed(ActionEvent e) {
		new RecordDelete(this.contentPane, "订单", this);
		setVisible(true);
	}

	//该方法在主页面显示“查询管理员”功能的界面
	private void queryUserActionPerformed(ActionEvent e) {
		new RecordQuery(this.contentPane, "用户", this);
		setVisible(true);
	}
	
	//该方法在主页面显示“添加管理员”功能的界面
	private void addUserActionPerformed(ActionEvent e) {
		new RecordAdd(this.contentPane, "用户", this);
		setVisible(true);
	}
	
	//该方法在主页面显示“更新管理员”功能的界面
	private void updateUserActionPerformed(ActionEvent e) {
		new RecordUpdate(this.contentPane, "用户", this);
		setVisible(true);
	}
	
	//该方法在主页面显示“删除管理员”功能的界面
	private void deleteUserActionPerformed(ActionEvent e) {
		new RecordDelete(this.contentPane, "用户", this);
		setVisible(true);
	}

	//该方法在主页面显示“更新当前用户密码”功能的界面
	private void updatePassActionPerformed(ActionEvent e) {
		new UpdatePassword(this.contentPane);
		setVisible(true);
	}
	//该方法在主页面显示“退出系统”功能的界面
	private void exitAccountActionPerformed(ActionEvent e) {
		dispose();
	}
	
	//该方法根据用户权限设置菜单项是否可用
	public void setViewVisable(int role) { 
		if (role == Constant.VISITOR_ROLE) {
			menuBar.setEnabled(false);
			return;
		}
		if (role != Constant.MOVIE_ADMIN_ROLE
				&& role < Constant.FULL_ADMIN_ROLE) {
			movieMenu.setEnabled(false);
		}
		if (role != Constant.SHOW_ADMIN_ROLE && role < Constant.FULL_ADMIN_ROLE) {
			showMenu.setEnabled(false);
		}
		if (role != Constant.ORDER_ADMIN_ROLE
				&& role < Constant.FULL_ADMIN_ROLE) {
			orderMenu.setEnabled(false);
		}
		if (role < Constant.ROOT_ADMIN_ROLE) {
			userMenu.setEnabled(false);
		}

	}
}
