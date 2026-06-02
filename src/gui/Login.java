package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import connectDB.ConnectDB;
import custom_gui.BackgroundPanel;
import dao.TaiKhoan_dao;
import entity.ShareData;
import entity.TaiKhoan;



public class Login extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JButton btnLogin;
	private JButton btnExit;

	public Login() {
		
		setSize(500,300);
		setUndecorated(true);
		setLocationRelativeTo(null);
		UIManager.put("Component.arc", 15);
		UIManager.put("TextComponent.arc", 15);
		UIManager.put("Button.arc", 15);

		BackgroundPanel pnlMain = new BackgroundPanel("img/bg1.png");
	    pnlMain.setLayout(new BorderLayout());
	    setContentPane(pnlMain);
		
		JPanel pnlTitle = new JPanel();
		ImageIcon cartIcon = new ImageIcon("img/icon/cart.png");
		String formattedText = "<html>Swift<font color='#adff2f'>Pick</font></html>";
		JLabel lblTitle = new JLabel(formattedText);
		lblTitle.setIcon(setImg(cartIcon, 70, 50));
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Arial",Font.ITALIC,30));
		pnlTitle.add(lblTitle);
		
		pnlMain.add(pnlTitle, BorderLayout.NORTH);
		
		JPanel pnlForm = new JPanel();
		pnlForm.setBackground(new Color(0, 0, 0, 100)); 
		pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); 
		pnlForm.setLayout(new BorderLayout());
		Box boxInput = Box.createVerticalBox();
		Dimension size = new Dimension(200,20);
		//Username
		Box boxUsername = Box.createHorizontalBox();
		ImageIcon userIcon = new ImageIcon("img/icon/user_login.png");
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setIcon(setImg(userIcon, 15, 15));
		lblUsername.setForeground(Color.white);
		boxUsername.add(lblUsername);
		boxUsername.add(Box.createHorizontalStrut(10));
		txtUserName = new JTextField();
		txtUserName.setPreferredSize(size);
		txtUserName.setMaximumSize(size);
		boxUsername.add(txtUserName);
		
		//Passwordnv
		Box boxPassword = Box.createHorizontalBox();
		ImageIcon pwdIcon = new ImageIcon("img/icon/key.png");
		JLabel lblPwd = new JLabel("Password:");
		lblPwd.setIcon(setImg(pwdIcon, 15, 15));
		lblPwd.setForeground(Color.white);
		boxPassword.add(lblPwd);
		boxPassword.add(Box.createHorizontalStrut(10));
		txtPassword = new JPasswordField();
		txtPassword.setPreferredSize(size);
		txtPassword.setMaximumSize(size);
		boxPassword.add(txtPassword);
		
		boxInput.add(Box.createVerticalGlue());
		boxInput.add(boxUsername);
		boxInput.add(boxPassword);
		boxInput.add(Box.createVerticalGlue());
		pnlForm.add(boxInput);
		
		pnlMain.add(pnlForm);
		//Button
		JPanel pnlBtn = new JPanel();
		btnLogin = new JButton("Đăng Nhập");
		btnLogin.setOpaque(false);
		
		btnExit = new JButton("Thoát");
		btnExit.setOpaque(false);
		
		
		btnLogin.setFocusPainted(false);
		btnExit.setFocusPainted(false);
		
		
		pnlBtn.add(btnLogin);
		pnlBtn.add(btnExit);
		pnlMain.add(pnlBtn, BorderLayout.SOUTH);
		
		pnlBtn.setOpaque(false);
		pnlTitle.setOpaque(false);
		
		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);
		txtUserName.addActionListener(this);
		txtUserName.setText("admin");
		txtPassword.setText("admin123");
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if(o == btnExit) {
			System.exit(0);
		}else {
			
			try {
				ConnectDB.getInstance().connect();
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(this, "Kết nối hệ thống không thành công");
				e1.printStackTrace();
			}
			String userName = txtUserName.getText().trim();
			String pass = new String(txtPassword.getPassword()).trim();
			
			TaiKhoan_dao tk_DAO = new TaiKhoan_dao();
			TaiKhoan tk = tk_DAO.checkLogin(userName, pass);
			
			if(tk != null) {
				
				ShareData.taiKhoanDangNhap = tk.getMaNV();
				
				 try {
			            FlatLightLaf.setup();
			        } catch (Exception e1) {
			            e1.printStackTrace();
			        }
				 SwingUtilities.invokeLater(() -> {
			        	
			            new Main(tk).setVisible(true);// sua cai nay la class cua ong
			            this.dispose();
			        });

				
			}else {
				
	            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác!");
	        }
		}	
		
		}		
	public ImageIcon setImg(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image edit = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon editedImg = new ImageIcon(edit);
		return editedImg;
	}
}
