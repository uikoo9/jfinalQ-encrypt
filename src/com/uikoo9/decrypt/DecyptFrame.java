package com.uikoo9.decrypt;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

@SuppressWarnings("serial")
public class DecyptFrame extends JFrame {
	private JButton choicePathButton;
	private JButton encryptButton;
	private JFileChooser jFileChooser1;
	private JLabel jLabel1;
	private JTextField sourcePathTF;
	private String pDir;
	private String sDir;

	public DecyptFrame() {
		initComponents();

		setLocationRelativeTo(null);
	}

	private void initComponents() {
		this.jFileChooser1 = new JFileChooser();
		this.jLabel1 = new JLabel();
		this.sourcePathTF = new JTextField();
		this.choicePathButton = new JButton();
		this.encryptButton = new JButton();

		setDefaultCloseOperation(3);
		setTitle("class解密程序");

		this.jFileChooser1.setFileSelectionMode(1);

		this.jLabel1.setText("目标文件夹");

		this.sourcePathTF.setEditable(false);

		this.choicePathButton.setText("选择");
		this.choicePathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DecyptFrame.this.choicePathButtonActionPerformed(evt);
			}
		});
		this.encryptButton.setText("开始解密");
		this.encryptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				DecyptFrame.this.encryptButtonActionPerformed(evt);
			}
		});
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(this.jLabel1)
						.addGap(18, 18, 18)
						.addComponent(this.sourcePathTF, -2, 160, -2)
						.addPreferredGap(
								LayoutStyle.ComponentPlacement.RELATED, 22,
								32767).addComponent(this.choicePathButton)
						.addGap(18, 18, 18).addComponent(this.encryptButton)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(
										GroupLayout.Alignment.BASELINE)
										.addComponent(this.jLabel1)
										.addComponent(this.sourcePathTF, -2,
												-1, -2)
										.addComponent(this.encryptButton)
										.addComponent(this.choicePathButton))
						.addContainerGap(-1, 32767)));

		pack();
	}

	private void choicePathButtonActionPerformed(ActionEvent evt) {
		this.jFileChooser1.showDialog(this, "选择文件夹");

		this.pDir = this.jFileChooser1.getCurrentDirectory().toString();
		this.sDir = this.jFileChooser1.getSelectedFile().toString();
		this.sourcePathTF.setText(this.sDir);
	}

	private void encryptButtonActionPerformed(ActionEvent evt) {
		String path = this.sourcePathTF.getText();
		if (path == null || path.trim().equals("")){
			JOptionPane.showMessageDialog(this, "请选择目标文件夹！");
		} else {
			this.choicePathButton.setEnabled(false);
			this.encryptButton.setText("解密中。。。");
			this.encryptButton.setEnabled(false);
			String res = DecryptClass.decrypt(this.sDir, this.pDir);
			JOptionPane.showMessageDialog(this, res);
			this.choicePathButton.setEnabled(true);
			this.encryptButton.setText("开始解密");
			this.encryptButton.setEnabled(true);
		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new DecyptFrame().setVisible(true);
			}
		});
	}
}