/**
 * 该类用于更新当前用户的密码
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.GlobalVars;
import database.StaffDao;

public class UpdatePassword {

	private JPanel contentPane;
	private JPanel updatePane;
	private JLabel pwdLabel1;
	private JPasswordField pwdField1;
	private JLabel pwdLabel2;
	private JPasswordField pwdField2;
	private JPanel buttonBar;
	private JButton submitBtn;

	public UpdatePassword(Container mainContent) {
		initUI(mainContent); //传入主页面的内容区
	}

	// 初始化用户操作界面
	private void initUI(Container mainContent) {
		mainContent.removeAll();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(150, 200, 300, 300));
		contentPane.setLayout(new BorderLayout());

		updatePane = new JPanel();
		pwdLabel1 = new JLabel();
		pwdField1 = new JPasswordField();
		pwdLabel2 = new JLabel();
		pwdField2 = new JPasswordField();
		buttonBar = new JPanel();
		submitBtn = new JButton();

		updatePane.setLayout(new GridLayout(2, 2));

		pwdLabel1.setText("请输入新密码：");
		pwdLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		updatePane.add(pwdLabel1);
		updatePane.add(pwdField1);

		pwdLabel2.setText("请再次输入新密码：");
		pwdLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		updatePane.add(pwdLabel2);
		updatePane.add(pwdField2);

		contentPane.add(updatePane, BorderLayout.CENTER);

		buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
		buttonBar.setLayout(new GridBagLayout());
		((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0,
				85, 80 };
		((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] {
				1.0, 0.0, 0.0 };

		submitBtn.setText("提交修改");
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitButtonActionPerformed(e);
			}
		});
		buttonBar.add(submitBtn, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
						0, 0, 0, 5), 0, 0));

		contentPane.add(buttonBar, BorderLayout.SOUTH);

		//显示当前内容区（包含展示区和功能区），并添加到传入的主页面内容container
		contentPane.setVisible(true);
		mainContent.add(contentPane, BorderLayout.CENTER);
	}

	private void submitButtonActionPerformed(ActionEvent e) {
		String pwdVal1 = String.valueOf(pwdField1.getPassword()); // 输入密码的值
		String pwdVal2 = String.valueOf(pwdField2.getPassword());
		; // 再次输入密码的值

		if (!pwdVal1.equals(pwdVal2)) {
			JOptionPane.showMessageDialog(null, "密码输入不一致，请重新输入！");
			pwdField1.setText("");
			pwdField2.setText("");
			return;
		}

		if (pwdVal1.length() < 6) {
			JOptionPane.showMessageDialog(null, "密码太短，请重新输入，最小长度为6！");
			return;
		}
		boolean success = StaffDao.updateUserPass(GlobalVars.userId, pwdVal1);
		if (success) {
			JOptionPane.showMessageDialog(this.contentPane, "密码修改成功！");
		}
	}
}
