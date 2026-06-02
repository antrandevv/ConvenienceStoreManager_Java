package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import entity.NhanVien;
import entity.ShareData;
import common.DesignImg;

public class ThongTinNhanVien_GUI extends JDialog implements ActionListener{
	
	private JLabel lblAnh;
	private JTextField tfMaNV;
	private JTextField tfTenNV;
	private JTextField tfGioiTinh;
	private JTextField tfTuoi;
	private JTextField tfSDT;
	private JTextField tfEmail;
	private JTextField tfCaLamViec;
	private JTextField tfKhuVuc;
	private JButton btnThoat;
	private JButton btnDangXuat;
	private Main main;
	private JTextField tfHeSo;
	private JTextField tfLuongCB;
	private JTextField tfTongLuong;
	

	public ThongTinNhanVien_GUI(Main main, boolean modal) {
		super(main, modal);
		
		NhanVien nv = ShareData.taiKhoanDangNhap;
		DesignImg dsignImg = new DesignImg();
		this.main = main;
		
		//Thiet lap cho dialog
		setTitle("Thông Tin Nhân Viên");
		setSize(700, 550);
		setLocationRelativeTo(main);
		setResizable(false);
		this.requestFocusInWindow();
		
		//Tieu de dialog
		JPanel pnlNorth = new JPanel();
		JLabel lblTitle = new JLabel("THÔNG TIN NHÂN VIÊN");
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
		pnlNorth.add(lblTitle);
		
		//Anh nhan vien
		Dimension sizeAnh = new Dimension(200, 250);
		Border borderAnh = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.WHITE, Color.GRAY);
		JPanel pnlAnh = new JPanel();
		ImageIcon imgNV = new ImageIcon(nv.getHinhAnh());
		ImageIcon newImg = dsignImg.setImg(imgNV, 200, 250);
		lblAnh = new JLabel(newImg);
		lblAnh.setPreferredSize(sizeAnh);
		lblAnh.setMaximumSize(sizeAnh);
		lblAnh.setBorder(borderAnh);
		pnlAnh.add(lblAnh);
		
		//Thong tin nhan vien
		Dimension sizeLbl = new Dimension(150, 30);
		Box boxInfo = Box.createVerticalBox();
		Box boxMargin_X = Box.createHorizontalBox();
		
		Box boxMaNV = Box.createHorizontalBox();
		JLabel lblMaNV = new JLabel("Mã Nhân Viên: ");
		lblMaNV.setPreferredSize(sizeLbl);
		tfMaNV = new JTextField(nv.getMaNV());
		tfMaNV.setEditable(false);
		boxMaNV.add(lblMaNV);
		boxMaNV.add(tfMaNV);
		
		Box boxTenNV = Box.createHorizontalBox();
		JLabel lblTenNV = new JLabel("Tên Nhân Viên: ");
		lblTenNV.setPreferredSize(sizeLbl);
		tfTenNV = new JTextField(nv.getTenNV());
		tfTenNV.setEditable(false);
		boxTenNV.add(lblTenNV);
		boxTenNV.add(tfTenNV);
		
		Box boxGioiTinh = Box.createHorizontalBox();
		JLabel lblGioiTinh = new JLabel("Giới Tính");
		lblGioiTinh.setPreferredSize(sizeLbl);
		String gioiTinh = nv.getGioiTinh() ? "Nữ" : "Nam";
		tfGioiTinh = new JTextField(gioiTinh);
		tfGioiTinh.setEditable(false);
		boxGioiTinh.add(lblGioiTinh);
		boxGioiTinh.add(tfGioiTinh);
		
		Box boxTuoi = Box.createHorizontalBox();
        JLabel lblTuoi = new JLabel("Tuổi: ");
        lblTuoi.setPreferredSize(sizeLbl);
        tfTuoi = new JTextField(String.valueOf(nv.getTuoi()));
        tfTuoi.setEditable(false);
        boxTuoi.add(lblTuoi);
        boxTuoi.add(tfTuoi);

        Box boxSDT = Box.createHorizontalBox();
        JLabel lblSDT = new JLabel("Số Điện Thoại: ");
        lblSDT.setPreferredSize(sizeLbl);
        tfSDT = new JTextField(nv.getSoDienThoai());
        tfSDT.setEditable(false);
        boxSDT.add(lblSDT);
        boxSDT.add(tfSDT);

        Box boxEmail = Box.createHorizontalBox();
        JLabel lblEmail = new JLabel("Email: ");
        lblEmail.setPreferredSize(sizeLbl);
        tfEmail = new JTextField(nv.getEmail());
        tfEmail.setEditable(false);
        boxEmail.add(lblEmail);
        boxEmail.add(tfEmail);

        Box boxCaLamViec = Box.createHorizontalBox();
        JLabel lblCaLamViec = new JLabel("Ca Làm Việc: ");
        lblCaLamViec.setPreferredSize(sizeLbl);
        tfCaLamViec = new JTextField(String.valueOf(nv.getCaLamViec()));
        tfCaLamViec.setEditable(false);
        boxCaLamViec.add(lblCaLamViec);
        boxCaLamViec.add(tfCaLamViec);

        Box boxKhuVuc = Box.createHorizontalBox();
        JLabel lblKhuVuc = new JLabel("Địa Chỉ: ");
        lblKhuVuc.setPreferredSize(sizeLbl);
        tfKhuVuc = new JTextField(nv.getDiaChi());
        tfKhuVuc.setEditable(false);
        boxKhuVuc.add(lblKhuVuc);
        boxKhuVuc.add(tfKhuVuc);
        
        //He so luong
        Box boxHeSo = Box.createHorizontalBox();
        JLabel lblHeSo = new JLabel("Hệ số lương:");
        lblHeSo.setPreferredSize(sizeLbl);
        tfHeSo = new JTextField(String.valueOf(nv.getHeSoLuong()));
        tfHeSo.setEditable(false);
        boxHeSo.add(lblHeSo);
        boxHeSo.add(tfHeSo);
        
      //Luong co ban
        DecimalFormat df = new DecimalFormat("#,###");
        Box boxLuongCB = Box.createHorizontalBox();
        JLabel lblLuongCB = new JLabel("Lương cơ bản:");
        lblLuongCB.setPreferredSize(sizeLbl);
        
        // Dinh dang double thanh dang tien te (5,000,000)
        String luongCBFormatted = df.format(nv.getLuongCoBan());
        tfLuongCB = new JTextField(luongCBFormatted);
        tfLuongCB.setEditable(false);
        boxLuongCB.add(lblLuongCB);
        boxLuongCB.add(tfLuongCB);
        
      //Tong luong
        Box boxTongLuong = Box.createHorizontalBox();
        JLabel lblTongLuong = new JLabel("Tổng lương:");
        lblTongLuong.setPreferredSize(sizeLbl);
        
        //Dinh dang (10,000,000)
        String tongLuongFormatted = df.format(nv.getLuong());
        tfTongLuong = new JTextField(tongLuongFormatted);
        tfTongLuong.setEditable(false);
        boxTongLuong.add(lblTongLuong);
        boxTongLuong.add(tfTongLuong);
		
        tfMaNV.setFocusable(false);
        tfTenNV.setFocusable(false);
        tfGioiTinh.setFocusable(false);
        tfTuoi.setFocusable(false);
        tfSDT.setFocusable(false);
        tfEmail.setFocusable(false);
        tfCaLamViec.setFocusable(false);
        tfKhuVuc.setFocusable(false);
        tfHeSo.setFocusable(false);
        tfLuongCB.setFocusable(false);
        tfTongLuong.setFocusable(false);
        
        boxInfo.add(boxMaNV);
        boxInfo.add(Box.createVerticalStrut(10)); 
        boxInfo.add(boxTenNV);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxGioiTinh);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxTuoi);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxSDT);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxEmail);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxCaLamViec);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxKhuVuc);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxHeSo);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxLuongCB);
        boxInfo.add(Box.createVerticalStrut(10));
        boxInfo.add(boxTongLuong);
        boxMargin_X.add(Box.createHorizontalStrut(20));
        boxMargin_X.add(boxInfo);
        boxMargin_X.add(Box.createHorizontalStrut(50));
        
        //Nut chuc nang
        Border sizeBtn = BorderFactory.createEmptyBorder(6, 25, 6, 25);
        Border paddingSouth = BorderFactory.createEmptyBorder(20, 0, 20, 0);
		Border border = BorderFactory.createLineBorder(new Color(96, 123, 139));
        Box boxSouth = Box.createHorizontalBox();
        btnThoat = new JButton("Thoát");
        btnThoat.setFocusPainted(false);
		btnThoat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnThoat.setBorder(BorderFactory.createCompoundBorder(border, sizeBtn));
        btnDangXuat = new JButton("Đăng Xuất");
        btnDangXuat.setFocusPainted(false);
		btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDangXuat.setBorder(BorderFactory.createCompoundBorder(border, sizeBtn));
        boxSouth.add(Box.createGlue());
        boxSouth.add(btnDangXuat);
        boxSouth.add(Box.createHorizontalStrut(42));
        boxSouth.add(btnThoat);
        boxSouth.add(Box.createGlue());
        boxSouth.setBorder(paddingSouth);
        
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlAnh, BorderLayout.WEST);
		add(boxMargin_X, BorderLayout.CENTER);
		add(boxSouth, BorderLayout.SOUTH);
		
		btnDangXuat.addActionListener(this);
		btnThoat.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object o = e.getSource();
		
		if(o.equals(btnDangXuat)) {
			
			main.dangXuat();
		}
		
		else if(o.equals(btnThoat)) {
			dispose();
		}
	}
}
