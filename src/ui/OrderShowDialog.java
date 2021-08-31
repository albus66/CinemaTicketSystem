package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import util.CheckHandler;

public class OrderShowDialog extends JDialog {
	public OrderShowDialog(String orderData) {
		initUI(orderData); // 初始化弹出框界面
	}

	// 初始化用户操作界面
	private void initUI(String orderData) {
		setTitle("查看订单详情");
		setResizable(false);
		Container contentPane = getContentPane();
		JPanel dialogPane = new JPanel();
		dialogPane.setLayout(new BorderLayout());
		dialogPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		JLabel records =  new JLabel();
		records.setPreferredSize(new Dimension(350, 350));
		records.setVerticalAlignment(SwingConstants.TOP);
		records.setText(CheckHandler.showOrder(orderData));
		dialogPane.add(records);
		contentPane.add(dialogPane, BorderLayout.CENTER);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(getOwner());		
		setVisible(true);
		}
}
