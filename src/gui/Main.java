package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.Shape;

import com.formdev.flatlaf.ui.FlatLineBorder;

import common.DesignImg;
import connectDB.ConnectDB;
import dao.HoaDon_dao;
import entity.NhanVien;
import entity.ShareData;
import entity.TaiKhoan;
import entity.ViTri;

public class Main extends JFrame implements ActionListener{
	
	private JButton btnBanHang;
	private JPanel pnlRight;
	private JLabel lblTenNV;
	private JButton btnKhachHang;
	private JButton btnNhanVien;
	private JButton btnSanPham;
	private JButton btnThongKe;
	private BanHang_GUI banHang_UI;
	private KhachHang_GUI khachHang_UI;
	private NhanVien_GUI nhanVien_UI;
	private SanPham_GUI sanPham_UI;
	private ThongKeTaiChinh_GUI thongKe_UI;
	private NhapHang_GUI nhapHang_UI;

	private JButton btnSignOut;
	private JButton btnSelected = null;
	private CardLayout cardLayout;
	private final String PANEL_BAN_HANG = "BanHang";
	private final String PANEL_KHACH_HANG = "KhachHang";
	private final String PANEL_NHAN_VIEN = "NhanVien";
	private final String PANEL_SAN_PHAM = "SanPham";
	private final String PANEL_HOA_DON = "HoaDon";
	private final String PANEL_THONG_KE = "ThongKe";
	private final String PANEL_NHAP_HANG = "NhapHang";
	private final String PANEL_DS_PHIEU_NHAP = "DsPhieuNhap";
	private JButton btnNhapHang;
	private JButton btnHoaDon;
	private QuanLyHoaDon_GUI quanLyHD_UI;
	private TaiKhoan tk;
	private JButton btnAnhNV;
	private ThongTinNhanVien_GUI thongTinNhanVien_UI;
	private JButton btnNCC;
	private HoaDon_dao hd_dao;
	private JButton btnDSPN;
	private DanhSachPhieuNhap_GUI dspn_UI;
	private JPanel pnlTab;
	private JSeparator thanhPhaCach;

	public Main(TaiKhoan tk) {
		
		try {
			ConnectDB.getInstance().connect();
			System.out.println("Connected!!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.tk = tk;
		
		setSize(1400,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Border lineBorder = BorderFactory.createLineBorder(new Color(220, 220, 220), 1);
		thanhPhaCach = new JSeparator(SwingConstants.HORIZONTAL);
		thanhPhaCach.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
		thanhPhaCach.setForeground(new Color(220, 220, 220));
		
		UIManager.put("Component.arc", 15);
		UIManager.put("TextComponent.arc", 15);
		UIManager.put("Button.arc", 15);
		//pnlTop
		JPanel pnlTop = new JPanel(new BorderLayout());
		pnlTop.setBackground(Color.white);
		pnlTop.setBorder(lineBorder);
		
		Box boxTop = Box.createHorizontalBox();
		boxTop.setOpaque(false);
		
		//Logo và tên Cửa hàng
		ImageIcon logo = new ImageIcon("img/icon/cart.png");
		JLabel lblLogoCuaHang = new JLabel(setImg(logo, 100, 70));
		
		Box boxTitle = Box.createVerticalBox();
		
		String formattedText = "<html>Swift<font color='#adff2f'>Pick</font></html>";
		JLabel lblBrandName = new JLabel(formattedText);
		lblBrandName.setFont(new Font("Arial",Font.ITALIC,30));
		
		//Câu slogan
		JLabel lblSlogan = new JLabel("Nhanh Chóng - Tiện Lợi - Tiết Kiệm");
//		lblSlogan.setForeground(Color.white);
		lblSlogan.setFont(new Font("Arial",Font.ITALIC,13));
		
		boxTitle.add(lblBrandName);
		boxTitle.add(Box.createVerticalStrut(5));
		boxTitle.add(lblSlogan);
		
		boxTop.add(lblLogoCuaHang);
		boxTop.add(Box.createHorizontalStrut(5));
		boxTop.add(boxTitle);
		boxTop.add(Box.createHorizontalGlue()); 
		boxTop.add(createUserInfoPanel());  
		
		pnlTop.add(boxTop);
		
		JPanel pnlLeft = new JPanel();
		pnlLeft.setBackground(Color.WHITE);
		pnlLeft.setPreferredSize(new Dimension(150,0));
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
		pnlLeft.setBorder(lineBorder);
		
		pnlTab = new JPanel(new GridLayout(15,0,5,5));
		pnlTab.setOpaque(false);
		
		DesignImg di = new DesignImg();
		
		ImageIcon cartIcon = new ImageIcon("img/icon/cart-shopping.png");
		ImageIcon customerIcon = new ImageIcon("img/icon/customers.png");
		ImageIcon staffIcon = new ImageIcon("img/icon/staff.png");
		ImageIcon orderIcon = new ImageIcon("img/icon/order.png");
		ImageIcon productIcon = new ImageIcon("img/icon/products.png");
		ImageIcon tkIcon = new ImageIcon("img/icon/thongke.png");
		ImageIcon nccIcon = new ImageIcon("img/icon/ncc.png");
		ImageIcon nhapHangIcon = new ImageIcon("img/icon/nhapHang.png");
		ImageIcon dsPNIcon = new ImageIcon("img/icon/dspn.png");
	
		
		
		btnBanHang = new JButton("Bán hàng");
		btnBanHang.setIcon(di.setImg(cartIcon, 20, 20));
		btnBanHang.setBorderPainted(false);
		btnBanHang.setFocusPainted(false);
		btnBanHang.setOpaque(true);
		btnBanHang.setBackground(new Color(173, 255, 47));
		btnBanHang.setHorizontalAlignment(SwingConstants.LEFT); 
		btnBanHang.setIconTextGap(15); 
		btnBanHang.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnSanPham = new JButton("Sản phẩm");
		btnSanPham.setIcon(di.setImg(productIcon, 20, 20));
		btnSanPham.setBorderPainted(false);
		btnSanPham.setOpaque(false);
		btnSanPham.setFocusPainted(false);
		btnSanPham.setHorizontalAlignment(SwingConstants.LEFT); 
		btnSanPham.setIconTextGap(15); 
		btnSanPham.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnHoaDon = new JButton("Hóa đơn");
		btnHoaDon.setIcon(di.setImg(orderIcon, 20, 20));
		btnHoaDon.setBorderPainted(false);
		btnHoaDon.setOpaque(false);
		btnHoaDon.setFocusPainted(false);
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT); 
		btnHoaDon.setIconTextGap(15); 
		btnHoaDon.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnNCC = new JButton("Nhà cung cấp");
		btnNCC.setIcon(di.setImg(nccIcon, 20, 20));
		btnNCC.setBorderPainted(false);
		btnNCC.setOpaque(false);
		btnNCC.setFocusPainted(false);
		btnNCC.setHorizontalAlignment(SwingConstants.LEFT); 
		btnNCC.setIconTextGap(15); 
		btnNCC.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnThongKe = new JButton("Thống kê");
		btnThongKe.setIcon(di.setImg(tkIcon, 20, 20));
		btnThongKe.setBorderPainted(false);
		btnThongKe.setOpaque(false);
		btnThongKe.setFocusPainted(false);
		btnThongKe.setHorizontalAlignment(SwingConstants.LEFT); 
		btnThongKe.setIconTextGap(15); 
		btnThongKe.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnNhapHang = new JButton("Nhập hàng");
		btnNhapHang.setIcon(di.setImg(nhapHangIcon, 20, 20));
		btnNhapHang.setBorderPainted(false);
		btnNhapHang.setOpaque(false);
		btnNhapHang.setFocusPainted(false);
		btnNhapHang.setHorizontalAlignment(SwingConstants.LEFT); 
		btnNhapHang.setIconTextGap(15); 
		btnNhapHang.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		
		btnKhachHang = new JButton("Khách hàng");
		btnKhachHang.setIcon(di.setImg(customerIcon, 20, 20));
		btnKhachHang.setBorderPainted(false);
		btnKhachHang.setOpaque(false);
		btnKhachHang.setFocusPainted(false);
		btnKhachHang.setHorizontalAlignment(SwingConstants.LEFT); 
		btnKhachHang.setIconTextGap(15); 
		btnKhachHang.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnNhanVien = new JButton("Nhân viên");
		btnNhanVien.setIcon(di.setImg(staffIcon, 20, 20));
		btnNhanVien.setBorderPainted(false);
		btnNhanVien.setOpaque(false);
		btnNhanVien.setFocusPainted(false);
		btnNhanVien.setHorizontalAlignment(SwingConstants.LEFT); 
		btnNhanVien.setIconTextGap(15); 
		btnNhanVien.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		btnDSPN = new JButton("DS phiếu nhập");
		btnDSPN.setIcon(di.setImg(dsPNIcon, 20, 20));
		btnDSPN.setBorderPainted(false);
		btnDSPN.setOpaque(false);
		btnDSPN.setFocusPainted(false);
		btnDSPN.setHorizontalAlignment(SwingConstants.LEFT); 
		btnDSPN.setIconTextGap(15); 
		btnDSPN.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		//Exit
			
		ImageIcon iconExit = new ImageIcon("img/icon/exit.png");
		btnSignOut = new JButton("Đăng xuất");
		btnSignOut.setIcon(setImg(iconExit, 15, 15));
		btnSignOut.setBorderPainted(false);
		btnSignOut.setOpaque(false);
		btnSignOut.setFocusPainted(false);
		btnSignOut.setForeground(Color.red);
		btnSignOut.setHorizontalAlignment(SwingConstants.LEFT); 
		btnSignOut.setIconTextGap(15); 
		btnSignOut.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 5));
		
		pnlTab.add(btnBanHang);
		pnlTab.add(btnSanPham);
		pnlTab.add(btnHoaDon);
		pnlTab.add(btnThongKe);
		pnlTab.add(btnNhapHang);
		pnlTab.add(btnDSPN);
		pnlTab.add(btnKhachHang);
		pnlTab.add(btnNhanVien);
		pnlTab.add(Box.createVerticalGlue());
		pnlTab.add(Box.createVerticalGlue());
		pnlTab.add(Box.createVerticalGlue());
		pnlTab.add(thanhPhaCach);
		pnlTab.add(btnSignOut);
		
		pnlLeft.add(pnlTab);
		
		pnlRight = new JPanel();
		pnlRight.setBorder(lineBorder);
		cardLayout = new CardLayout(); 
	    pnlRight.setLayout(cardLayout);
	    pnlRight.setBackground(Color.white);
	    
	    dspn_UI = new DanhSachPhieuNhap_GUI(this);
	    banHang_UI = new BanHang_GUI();
	    khachHang_UI = new KhachHang_GUI();
	    nhanVien_UI = new NhanVien_GUI();
	    sanPham_UI = new SanPham_GUI(this);
	    quanLyHD_UI = new QuanLyHoaDon_GUI();
	    thongKe_UI = new ThongKeTaiChinh_GUI();
	    nhapHang_UI = new NhapHang_GUI();
	    thongTinNhanVien_UI = new ThongTinNhanVien_GUI(this, true);
	    hd_dao = new HoaDon_dao();
	    
	    pnlRight.add(banHang_UI, PANEL_BAN_HANG);
	    pnlRight.add(khachHang_UI, PANEL_KHACH_HANG);
	    pnlRight.add(nhanVien_UI, PANEL_NHAN_VIEN);
	    pnlRight.add(sanPham_UI, PANEL_SAN_PHAM);
	    pnlRight.add(quanLyHD_UI,PANEL_HOA_DON);
	    pnlRight.add(thongKe_UI, PANEL_THONG_KE);
	    pnlRight.add(nhapHang_UI,PANEL_NHAP_HANG);
	    pnlRight.add(dspn_UI,PANEL_DS_PHIEU_NHAP);

		
		add(pnlTop, BorderLayout.NORTH);
		add(pnlLeft,BorderLayout.WEST);
		add(pnlRight, BorderLayout.CENTER);
		
		phanQuyen();
		
		//Phím tắt & toolTip
		btnBanHang.setMnemonic(KeyEvent.VK_B);
		btnSanPham.setMnemonic(KeyEvent.VK_S);
		btnThongKe.setMnemonic(KeyEvent.VK_T);
		btnKhachHang.setMnemonic(KeyEvent.VK_K);
		btnNhanVien.setMnemonic(KeyEvent.VK_N);
		btnNhapHang.setMnemonic(KeyEvent.VK_I);
		btnDSPN.setMnemonic(KeyEvent.VK_D);
		btnHoaDon.setMnemonic(KeyEvent.VK_H);
		btnSignOut.setMnemonic(KeyEvent.VK_X);
		

		btnBanHang.setToolTipText("Phím tắt: Alt + B");
		btnSanPham.setToolTipText("Phím tắt: Alt + S");
		btnThongKe.setToolTipText("Phím tắt: Alt + T");		
		btnKhachHang.setToolTipText("Phím tắt: Alt + K");		
		btnNhanVien.setToolTipText("Phím tắt: Alt + N");		
		btnNhapHang.setToolTipText("Phím tắt: Alt + I");		
		btnDSPN.setToolTipText("Phím tắt: Alt + D");		
		btnHoaDon.setToolTipText("Phím tắt: Alt + H");
		btnSignOut.setToolTipText("Phím tắt: Alt + X");		
	
		
		btnBanHang.addActionListener(this);
		btnSanPham.addActionListener(this);
		btnThongKe.addActionListener(this);
		btnKhachHang.addActionListener(this);
		btnNhanVien.addActionListener(this);
		btnNhapHang.addActionListener(this);
		btnDSPN.addActionListener(this);
		btnHoaDon.addActionListener(this);
		btnSignOut.addActionListener(this);
		btnAnhNV.addActionListener(this);
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
		if (o instanceof JButton && o != btnSignOut) {
			btnBanHang.setOpaque(false);
			btnBanHang.setBackground(Color.white);
	        updateButtonColor((JButton) o);
	    }
		
		if(o == btnBanHang) {
			banHang_UI.loadAllProductList();
			setBanHang();
		}
		else if(o == btnKhachHang) {
			setKhachHang();
		}
		else if(o == btnNhanVien) {
			setNhanVien();
		}
		else if (o == btnSanPham) {
			sanPham_UI.DocDuLieuVaoTable();
			setSanPham();
		}
		else if(o == btnThongKe) {
			setThongKe();
		} else if (o == btnNhapHang){
			nhapHang_UI.loadCardItemNhapHang_Type1();
			nhapHang_UI.loadNCC();
			setNhapHang();
		} else if (o == btnHoaDon) {
			quanLyHD_UI.loadDataToUI(hd_dao.getAllHoaDon());
			setHoaDon();
		}
		else if(o == btnSignOut) {
			dangXuat();
		}
		else if(o.equals(btnAnhNV)) {
			setThongTinNV();
		} else if (o == btnDSPN) {
			dspn_UI.loadDuLieu();
			setDSPN();
		}
		
	}




	private void updateButtonColor(JButton clickedButton) {
	    //Reset màu cho nút đã chọn trước đó 
		
	    if (btnSelected != null) {
	        btnSelected.setOpaque(false);
	        btnSelected.setBackground(Color.white);
	    }

	    //Thiết lập màu cho nút vừa click
	    clickedButton.setOpaque(true); 
	    clickedButton.setBackground(new Color(173, 255, 47)); 
	    
	    // Cập nhật nút hiện tại vào biến tạm
	    btnSelected = clickedButton;
	    
	    // Vẽ lại nút đang chọn
	    clickedButton.repaint();
	}
	
	public void dangXuat() {
		new Login().setVisible(true);
		this.dispose();
	}
	
	private void setBanHang() {
	    cardLayout.show(pnlRight, PANEL_BAN_HANG);
	}

	private void setKhachHang() {
	    cardLayout.show(pnlRight, PANEL_KHACH_HANG);
	}

	private void setNhanVien() {
	    cardLayout.show(pnlRight, PANEL_NHAN_VIEN);
	}

	private void setSanPham() {
	    cardLayout.show(pnlRight, PANEL_SAN_PHAM);
	}

	private void setHoaDon() {
		cardLayout.show(pnlRight, PANEL_HOA_DON);
	}
	
	private void setThongKe() {
	    cardLayout.show(pnlRight, PANEL_THONG_KE);
	}
	
	public void setNhapHang() {
	    cardLayout.show(pnlRight, PANEL_NHAP_HANG);
	    
	    btnBanHang.setOpaque(true);
	    btnBanHang.setBackground(Color.WHITE);
	    
	    updateButtonColor(btnNhapHang);
	}
	
	private void setDSPN() {
		cardLayout.show(pnlRight,PANEL_DS_PHIEU_NHAP);
		
	}
	
	private void setThongTinNV(){
		thongTinNhanVien_UI.setVisible(true);
	}
	
	
	public ImageIcon setImg(ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image edit = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon editedImg = new ImageIcon(edit);
		return editedImg;
	}
	
	public boolean phanQuyen() {
	       
        if (tk.getViTri() != ViTri.QUAN_LY) { 
            pnlTab.removeAll();
            
        	pnlTab.add(btnBanHang);
    		pnlTab.add(btnSanPham);
    		pnlTab.add(btnHoaDon);
    		pnlTab.add(btnNhapHang);
    		pnlTab.add(btnDSPN);
    		pnlTab.add(btnKhachHang);
    		pnlTab.add(btnNhanVien);
    		pnlTab.add(btnThongKe);
    		pnlTab.add(Box.createVerticalGlue());
    		pnlTab.add(Box.createVerticalGlue());
    		pnlTab.add(Box.createVerticalGlue());
    		pnlTab.add(thanhPhaCach);
    		pnlTab.add(btnSignOut);
            
            btnNhanVien.setVisible(false);
            btnThongKe.setVisible(false);
            return false;
        } 
        return true;
    }
	private JPanel createUserInfoPanel() {
		JPanel pnlUser = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		pnlUser.setOpaque(false);
		
		// Lấy thông tin từ ShareData
		NhanVien nv = ShareData.taiKhoanDangNhap;
		String ten = (nv != null) ? nv.getTenNV() : "N/A";
		String pathAnh = (nv != null && nv.getHinhAnh() != null) ? nv.getHinhAnh() : "img/user.png";
		
		String chucVu = (nv != null && nv.getViTri() != null) ? nv.getViTri().toString() : "N/A";

		Box boxTextInfo = Box.createVerticalBox();
		
		lblTenNV = new JLabel("Tên: " + ten);
		lblTenNV.setFont(new Font("Arial", Font.BOLD, 14));
		lblTenNV.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
	
		JLabel lblViTri = new JLabel("Chức vụ: " +chucVu);
		lblViTri.setFont(new Font("Arial", Font.ITALIC, 12));
		lblViTri.setForeground(Color.GRAY);
		lblViTri.setAlignmentX(JLabel.RIGHT_ALIGNMENT);

		boxTextInfo.add(lblTenNV);
		boxTextInfo.add(Box.createVerticalStrut(2));
		boxTextInfo.add(lblViTri);

	
		try {
		    ImageIcon icon = new ImageIcon(pathAnh);
		    Image img = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		    
		    // Khởi tạo nút với paintComponent tùy chỉnh ngay từ đầu
		    btnAnhNV = new JButton(new ImageIcon(img)) {
		        @Override
		        protected void paintComponent(Graphics g) {
		            Graphics2D g2 = (Graphics2D) g.create();
		            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		            
		            // Tạo vùng cắt hình tròn
		            Shape clip = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
		            g2.setClip(clip);
		            
		            super.paintComponent(g2);
		            g2.dispose();
		        }
		    };
		    
		  
		    btnAnhNV.setPreferredSize(new Dimension(40, 40));
		    btnAnhNV.setBorder(null);
		    btnAnhNV.setFocusPainted(false);
		    btnAnhNV.setContentAreaFilled(false);
		    btnAnhNV.setBorderPainted(false); 
		    btnAnhNV.setCursor(new Cursor(Cursor.HAND_CURSOR));
		

		} catch (Exception e) {
		    btnAnhNV = new JButton("[Ảnh]");
		}

		
		pnlUser.add(boxTextInfo);
		pnlUser.add(btnAnhNV);

		return pnlUser;
	}
}
