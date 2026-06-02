	package gui;
	
	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Component;
	import java.awt.Cursor;
	import java.awt.Dimension;
	import java.awt.FlowLayout;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.Image;
	import java.awt.Insets;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	
	import java.text.DecimalFormat;
	import java.time.LocalDate;
	import java.util.ArrayList;
	import java.util.HashMap;
	import java.util.Map;
	
	import javax.swing.BorderFactory;
	import javax.swing.Box;
	import javax.swing.BoxLayout;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JCheckBox;
	import javax.swing.JComponent;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JSeparator;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.SwingConstants;
	
	import javax.swing.border.Border;
	
	import javax.swing.border.TitledBorder;
	import javax.swing.event.DocumentEvent;
	import javax.swing.event.DocumentListener;
	import javax.swing.event.TableModelEvent;
	import javax.swing.event.TableModelListener;
	import javax.swing.table.DefaultTableModel;
	
	import com.formdev.flatlaf.ui.FlatLineBorder;
	
	import entity.ChiTietHoaDon;
	import entity.HoaDon;
	import entity.KhachHang;
	import entity.LoaiSanPham;
	import entity.NhanVien;
	import entity.SanPham;
	import entity.ShareData;
	import entity.TrangThaiHoaDon;
	import common.DesignImg;
	import custom_gui.ProductCard;
	import dao.ChiTietHoaDon_dao;
	import dao.HoaDon_dao;
	import dao.KhachHang_dao;
	import dao.SanPham_dao;
	
	public class BanHang_GUI extends JPanel implements TableModelListener, ActionListener, DocumentListener {
	
		private DefaultTableModel cartTableModel;
		private JTable cartTable;
		private JLabel lblTienTamTinh;
		private JLabel lblTienThue;
		private JButton btnThanhToan;
		private JTextField txtTienNhan;
		private JTextField txtTienThua;
		private JLabel lblTong;
		private JTextField txtPhoneNo;
		private JTextField txtId;
		private JTextField txtName;
		private JTextField txtScore;
		private JTextField txtType;
		private DecimalFormat df = new DecimalFormat("#,###");
		private Map<String, ProductCard> mapProductCards;
		private JCheckBox chkSuDungDiem;
		private JPanel pnlRank;
		private JPanel pnlInfo;
		private JTextField txtTimKiemSP;
		private JButton btnTatCa;
		private JButton btnDoUong;
		private JButton btnVPP;
		private JButton btnBanh_Keo;
		private JButton btnSua;
		private JButton btnMiGoi;
		private SanPham_dao sp_dao;
		private KhachHang_dao kh_dao;
		private JPanel pnlProductList;
		private ArrayList<SanPham> listProduct;
		private boolean isUpdatingTable = false;
		private JLabel lblGiamDiemText;
		private JLabel lblGiamDiemSoTien;
		private double soTienGiam = 0;
		private JLabel lblDiemTichLuyText;
		private JLabel lblDiemTichLuySo;
	
		public BanHang_GUI() {
	
			// pnl để chuyển qua main
	
			this.setLayout(new BorderLayout());
			this.setBackground(Color.white);
	
			sp_dao = new SanPham_dao();
			kh_dao = new KhachHang_dao();
	
			Cursor pointer = new Cursor(Cursor.HAND_CURSOR);
			ImageIcon iconTimKiem = new ImageIcon("img/icon/icon_timkiem.png");
			DesignImg di = new DesignImg();
			JSeparator thanhPhaCach = new JSeparator(SwingConstants.HORIZONTAL);
			thanhPhaCach.setMaximumSize(new Dimension(Integer.MAX_VALUE, 10));
			thanhPhaCach.setForeground(new Color(220, 220, 220));
	
			// Tìm kiếm sản phẩm và danh sách sản phẩm
			JPanel pnlProduct = new JPanel();
			pnlProduct.setLayout(new BorderLayout());
	
			// Khu vực tìm kiếm
			Box boxBoTimKiemSP = Box.createVerticalBox();
	
			JPanel pnlTimKiemSP = new JPanel();
			pnlTimKiemSP.setLayout(new BoxLayout(pnlTimKiemSP, BoxLayout.X_AXIS));
	
			// Thanh tìm kiếm
			Dimension sizeThanhTimKiemSP = new Dimension(750, 50);
			txtTimKiemSP = new JTextField();
			txtTimKiemSP.setPreferredSize(sizeThanhTimKiemSP);
			setRoundBorder(txtTimKiemSP, 50);
			txtTimKiemSP.putClientProperty("JTextField.leadingIcon", di.setImg(iconTimKiem, 25, 25));
			txtTimKiemSP.putClientProperty("JTextField.placeholderText", "Tìm kiếm sản phẩm...");
			pnlTimKiemSP.add(txtTimKiemSP);
	
			// Bộ lọc
			JPanel pnlBoLocSP = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
			pnlBoLocSP.setOpaque(false);
			setAllTransparentForButton(pnlBoLocSP);
	
			btnTatCa = new JButton("Tất cả");
			btnTatCa.setCursor(pointer);
	
			btnDoUong = new JButton("Đồ uống");
			btnVPP = new JButton("Văn phòng phẩm");
			btnBanh_Keo = new JButton("Bánh kẹo");
			btnSua = new JButton("Sữa");
			btnMiGoi = new JButton("Mì gói");
	
			btnTatCa.putClientProperty("JButton.buttonType", "roundRect");
			btnDoUong.putClientProperty("JButton.buttonType", "roundRect");
			btnVPP.putClientProperty("JButton.buttonType", "roundRect");
			btnBanh_Keo.putClientProperty("JButton.buttonType", "roundRect");
			btnSua.putClientProperty("JButton.buttonType", "roundRect");
			btnMiGoi.putClientProperty("JButton.buttonType", "roundRect");
	
			pnlBoLocSP.add(btnTatCa);
			pnlBoLocSP.add(btnDoUong);
			pnlBoLocSP.add(btnVPP);
			pnlBoLocSP.add(btnBanh_Keo);
			pnlBoLocSP.add(btnSua);
			pnlBoLocSP.add(btnMiGoi);
	
			boxBoTimKiemSP.add(Box.createVerticalStrut(20));
			boxBoTimKiemSP.add(pnlTimKiemSP);
			boxBoTimKiemSP.add(Box.createVerticalStrut(10));
			boxBoTimKiemSP.add(pnlBoLocSP);
			boxBoTimKiemSP.add(Box.createVerticalStrut(20));
			boxBoTimKiemSP.add(thanhPhaCach);
	
			// Lấy danh sách sp từ database
			listProduct = sp_dao.getTableSanPham();
	
			JPanel pnlContainer = new JPanel(new BorderLayout());
			pnlProductList = new JPanel();
			pnlProductList.setLayout(new GridLayout(0, 3, 20, 20));
			pnlContainer.add(pnlProductList, BorderLayout.NORTH);
	
			mapProductCards = new HashMap<>();
			for (SanPham sp : listProduct) {
	
				ProductCard productCard = new ProductCard(sp, sanPham -> {
					addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
				});
				mapProductCards.put(sp.getTenSP(), productCard);// lưu sản phẩm vào map
				pnlProductList.add(productCard);
			}
	
			JScrollPane scrllProduct = new JScrollPane(pnlContainer);
			scrllProduct.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrllProduct.getVerticalScrollBar().setUnitIncrement(16);
			scrllProduct.setBorder(null);
	
			pnlProduct.add(boxBoTimKiemSP, BorderLayout.NORTH);
			pnlProduct.add(scrllProduct, BorderLayout.CENTER);
	
			// Giỏ hàng và thanh toán
			// ================= BẮT ĐẦU: KHU VỰC GIỎ HÀNG VÀ THANH TOÁN =================
					JPanel pnlCart = new JPanel();
					pnlCart.setLayout(new BorderLayout(0, 10)); // Khoảng cách dọc 10px
					pnlCart.setPreferredSize(new Dimension(450, 0));
					pnlCart.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding ngoài
					pnlCart.setBackground(Color.WHITE);
	
					// --- 1. KHU VỰC TOP: TIÊU ĐỀ & THÔNG TIN KHÁCH HÀNG ---
					JPanel pnlCartTop = new JPanel();
					pnlCartTop.setLayout(new BoxLayout(pnlCartTop, BoxLayout.Y_AXIS));
					pnlCartTop.setOpaque(false);
	
					// Tiêu đề giỏ hàng
					JPanel pnlCartTitle = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
					pnlCartTitle.setOpaque(false);
					ImageIcon cartShoppingIcon = new ImageIcon("img/icon/cart-shopping.png");
					JLabel lblCartTitle = new JLabel("GIỎ HÀNG & THANH TOÁN");
					lblCartTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
					lblCartTitle.setForeground(new Color(41, 128, 185));
					try {
						lblCartTitle.setIcon(di.setImg(cartShoppingIcon, 22, 22));
					} catch (Exception e) {}
					pnlCartTitle.add(lblCartTitle);
	
					// Panel bọc Thông tin KH
					JPanel pnlKHWrapper = new JPanel();
					pnlKHWrapper.setLayout(new BoxLayout(pnlKHWrapper, BoxLayout.Y_AXIS));
					pnlKHWrapper.setBackground(new Color(248, 249, 250));
					pnlKHWrapper.setBorder(BorderFactory.createCompoundBorder(
							new FlatLineBorder(new Insets(10, 10, 10, 10), new Color(220, 220, 220), 1, 10),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)
					));
	
					// SĐT Khách hàng
					JPanel pnlPhone = new JPanel(new BorderLayout(10, 0));
					pnlPhone.setOpaque(false);
					JLabel lblPhone = new JLabel("SĐT Khách:");
					lblPhone.setFont(new Font("SansSerif", Font.BOLD, 13));
					pnlPhone.add(lblPhone, BorderLayout.WEST);
					
					txtPhoneNo = new JTextField();
					txtPhoneNo.putClientProperty("JTextField.placeholderText", "Nhập SĐT + Enter...");
					txtPhoneNo.putClientProperty("JComponent.roundRect", true);
					pnlPhone.add(txtPhoneNo, BorderLayout.CENTER);
	
					// Checkbox điểm
					JPanel pnlSuDungDiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
					pnlSuDungDiem.setOpaque(false);
					chkSuDungDiem = new JCheckBox("Sử dụng điểm tích lũy");
					chkSuDungDiem.setFont(new Font("SansSerif", Font.ITALIC, 12));
					chkSuDungDiem.setFocusable(false);
					chkSuDungDiem.setOpaque(false);
					pnlSuDungDiem.add(chkSuDungDiem);
	
					// Thông tin chi tiết KH & Hạng
					pnlInfo = new JPanel(new GridLayout(0,4, 5, 5)); 
					pnlInfo.setOpaque(false);
					
					Font readOnlyFont = new Font("SansSerif", Font.BOLD, 12);
					Color readOnlyBg = new Color(248, 249, 250);
					
					txtId = new JTextField(); txtId.setEditable(false); txtId.setBorder(null); txtId.setBackground(readOnlyBg); txtId.setFont(readOnlyFont);
					txtName = new JTextField(); txtName.setEditable(false); txtName.setBorder(null); txtName.setBackground(readOnlyBg); txtName.setFont(readOnlyFont);
					
					pnlInfo.add(new JLabel("Mã KH:")); pnlInfo.add(txtId);
					pnlInfo.add(new JLabel("Tên KH:")); pnlInfo.add(txtName);
	
					pnlRank = new JPanel(new GridLayout(0, 4, 5, 5));
					pnlRank.setOpaque(false);
					
					txtScore = new JTextField(); txtScore.setEditable(false); txtScore.setBorder(null); txtScore.setBackground(readOnlyBg); txtScore.setFont(readOnlyFont); txtScore.setForeground(new Color(0, 153, 51));
					txtType = new JTextField(); txtType.setEditable(false); txtType.setBorder(null); txtType.setBackground(readOnlyBg); txtType.setFont(readOnlyFont); txtType.setForeground(new Color(255, 140, 0));
					
					pnlRank.add(new JLabel("Điểm hiện có:")); pnlRank.add(txtScore);
					pnlRank.add(new JLabel("Hạng KH:")); pnlRank.add(txtType);
	
					pnlKHWrapper.add(pnlPhone);
					pnlKHWrapper.add(pnlSuDungDiem);
					pnlKHWrapper.add(pnlInfo);
					pnlKHWrapper.add(pnlRank);
					
					pnlInfo.setVisible(false);
					pnlRank.setVisible(false);
	
					pnlCartTop.add(pnlCartTitle);
					pnlCartTop.add(Box.createVerticalStrut(10));
					pnlCartTop.add(pnlKHWrapper);
	
					// KHU VỰC BẢNG GIỎ HÀNG
					String[] rowCart = { "Tên sản phẩm", "SL", "Đơn giá", "Thành Tiền", "" };
					cartTableModel = new DefaultTableModel(rowCart, 0) {
						@Override
						public boolean isCellEditable(int row, int column) {
							return column == 1 || column == 4; // Chỉ cho phép sửa số lượng và nút xóa
						}
					};
					cartTable = new JTable(cartTableModel);
					
					// Đảm bảo bảng luôn giữ được một không gian tối thiểu không bị ép co lại
					cartTable.setPreferredScrollableViewportSize(new Dimension(400, 150)); 
					cartTable.setRowHeight(30); 
					cartTable.setShowVerticalLines(false);
					cartTable.setSelectionBackground(new Color(228, 242, 255));
					cartTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
					cartTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
					cartTable.getTableHeader().setBackground(new Color(240, 240, 240));
	
					cartTable.getColumnModel().getColumn(4).setCellRenderer(new custom_gui.ButtonRenderer());
					cartTable.getColumnModel().getColumn(4).setCellEditor(new custom_gui.ButtonEditor(new JCheckBox(), cartTable, row -> {
						String tenSP = cartTable.getValueAt(row, 0).toString();
						int slTrongGio = Integer.parseInt(cartTable.getValueAt(row, 1).toString());
						ProductCard card = mapProductCards.get(tenSP);
						if (card != null) {
							card.capNhatSoLuongTon(card.getSoLuongTon() + slTrongGio);
						}
						javax.swing.SwingUtilities.invokeLater(() -> tinhTien());
					}));
					cartTable.getColumnModel().getColumn(4).setPreferredWidth(40);
	
					JScrollPane cartScrlPane = new JScrollPane(cartTable);
					cartScrlPane.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(200, 200, 200), 1, 10));
					
					JPanel pnlTable = new JPanel(new BorderLayout());
					pnlTable.setOpaque(false);
					pnlTable.add(cartScrlPane, BorderLayout.CENTER);
	
					//  KHU VỰC: TÍNH TIỀN & THANH TOÁN
					JPanel pnlService = new JPanel();
					pnlService.setLayout(new BoxLayout(pnlService, BoxLayout.Y_AXIS));
					pnlService.setBackground(new Color(250, 250, 250));
					pnlService.setBorder(BorderFactory.createCompoundBorder(
							new FlatLineBorder(new Insets(5, 5, 5, 5), new Color(220, 220, 220), 1, 10),
							BorderFactory.createEmptyBorder(10, 10, 10, 10)
					));
	
					Font fontLabel = new Font("SansSerif", Font.PLAIN, 14);
					Font fontValue = new Font("SansSerif", Font.BOLD, 14);
	
					// Box Tạm tính
					Box boxTamTinh = Box.createHorizontalBox();
					JLabel lblTamTinh = new JLabel("Tạm tính:");
					lblTamTinh.setFont(fontLabel);
					lblTamTinh.setForeground(Color.DARK_GRAY);
					lblTienTamTinh = new JLabel("0đ");
					lblTienTamTinh.setFont(fontValue);
					boxTamTinh.add(lblTamTinh);
					boxTamTinh.add(Box.createHorizontalGlue());
					boxTamTinh.add(lblTienTamTinh);
	
					// Box Thuế VAT
					Box boxThue = Box.createHorizontalBox();
					JLabel lblThue = new JLabel("VAT (10%):");
					lblThue.setFont(fontLabel);
					lblThue.setForeground(Color.DARK_GRAY);
					lblTienThue = new JLabel("0đ");
					lblTienThue.setFont(fontValue);
					boxThue.add(lblThue);
					boxThue.add(Box.createHorizontalGlue());
					boxThue.add(lblTienThue);
	
					// Box Giảm điểm
					Box boxGiamDiem = Box.createHorizontalBox();
					lblGiamDiemText = new JLabel("Giảm điểm (0 điểm):");
					lblGiamDiemText.setFont(fontLabel);
					lblGiamDiemText.setForeground(new Color(0, 153, 51));
					lblGiamDiemSoTien = new JLabel("0đ");
					lblGiamDiemSoTien.setFont(fontValue);
					lblGiamDiemSoTien.setForeground(new Color(0, 153, 51));
					boxGiamDiem.add(lblGiamDiemText);
					boxGiamDiem.add(Box.createHorizontalGlue());
					boxGiamDiem.add(lblGiamDiemSoTien);
					boxGiamDiem.setVisible(false);
	
					// Box Tổng tiền
					Box boxTongTien = Box.createHorizontalBox();
					JLabel lblTongTien = new JLabel("Tổng tiền:");
					lblTongTien.setFont(new Font("SansSerif", Font.BOLD, 18));
					lblTongTien.setForeground(Color.BLACK);
					lblTong = new JLabel("0đ");
					lblTong.setFont(new Font("SansSerif", Font.BOLD, 22));
					lblTong.setForeground(new Color(220, 53, 69));
					boxTongTien.add(lblTongTien);
					boxTongTien.add(Box.createHorizontalGlue());
					boxTongTien.add(lblTong);
	
					// Box Điểm tích lũy
					Box boxDiemTichLuy = Box.createHorizontalBox();
					lblDiemTichLuyText = new JLabel("Điểm tích lũy:");
					lblDiemTichLuyText.setFont(fontLabel);
					lblDiemTichLuyText.setForeground(new Color(0, 153, 51));
					lblDiemTichLuySo = new JLabel("+0 điểm");
					lblDiemTichLuySo.setFont(fontValue);
					lblDiemTichLuySo.setForeground(new Color(0, 153, 51));
					boxDiemTichLuy.add(lblDiemTichLuyText);
					boxDiemTichLuy.add(Box.createHorizontalGlue());
					boxDiemTichLuy.add(lblDiemTichLuySo);
					boxDiemTichLuy.setVisible(false);
	
					
					pnlService.add(boxTamTinh);
					pnlService.add(Box.createVerticalStrut(8));
					pnlService.add(boxThue);
					pnlService.add(Box.createVerticalStrut(8));
					pnlService.add(boxGiamDiem);
					pnlService.add(Box.createVerticalStrut(8));
					pnlService.add(new JSeparator()); 
					pnlService.add(Box.createVerticalStrut(8));
					pnlService.add(boxTongTien);
					pnlService.add(Box.createVerticalStrut(8));
					pnlService.add(boxDiemTichLuy);
					pnlService.add(Box.createVerticalStrut(10));
	
					// Tiền nhận & Tiền thừa
					JPanel pnlCash = new JPanel(new GridLayout(2, 2, 10, 8));
					pnlCash.setOpaque(false);
					
					JLabel lblTienNhan = new JLabel("Tiền nhận:"); lblTienNhan.setFont(fontLabel);
					JLabel lblTienThua = new JLabel("Tiền thừa:"); lblTienThua.setFont(fontLabel);
					
					txtTienNhan = new JTextField();
					txtTienNhan.putClientProperty("JTextField.placeholderText", "0");
					txtTienNhan.putClientProperty("JComponent.roundRect", true);
					txtTienNhan.setFont(fontValue);
					
					txtTienThua = new JTextField();
					txtTienThua.setEditable(false);
					txtTienThua.putClientProperty("JComponent.roundRect", true);
					txtTienThua.setFont(fontValue);
					txtTienThua.setForeground(Color.RED);
					
					pnlCash.add(lblTienNhan); pnlCash.add(txtTienNhan);
					pnlCash.add(lblTienThua); pnlCash.add(txtTienThua);
					
					pnlService.add(pnlCash);
					pnlService.add(Box.createVerticalStrut(15));
	
					// Nút Thanh Toán
					btnThanhToan = new JButton("THANH TOÁN");
					btnThanhToan.setFont(new Font("SansSerif", Font.BOLD, 18));
					btnThanhToan.setBackground(new Color(40, 167, 69)); 
					btnThanhToan.setForeground(Color.WHITE);
					btnThanhToan.setFocusPainted(false);
					btnThanhToan.setCursor(pointer);
					btnThanhToan.putClientProperty("JButton.buttonType", "roundRect"); 
					btnThanhToan.setPreferredSize(new Dimension(0, 45)); 
					
					JPanel pnlBtn = new JPanel(new BorderLayout());
					pnlBtn.setOpaque(false);
					pnlBtn.add(btnThanhToan, BorderLayout.CENTER);
					pnlService.add(pnlBtn);
	
				
					pnlCart.add(pnlCartTop, BorderLayout.NORTH);
					pnlCart.add(pnlTable, BorderLayout.CENTER);
					pnlCart.add(pnlService, BorderLayout.SOUTH);
	
					this.add(pnlProduct, BorderLayout.CENTER);
					this.add(pnlCart, BorderLayout.EAST);
					
	
			cartTableModel.addTableModelListener(this);
			txtTienNhan.addActionListener(this);
			chkSuDungDiem.addActionListener(this);
			btnTatCa.addActionListener(this);
			btnDoUong.addActionListener(this);
			btnVPP.addActionListener(this);
			btnBanh_Keo.addActionListener(this);
			btnSua.addActionListener(this);
			btnMiGoi.addActionListener(this);
			txtPhoneNo.addActionListener(this);
			txtTimKiemSP.getDocument().addDocumentListener(this);
			btnThanhToan.addActionListener(this);
	
			setAllTransparentForPanel(this);
		}
	
		// Thêm vào giỏ hàng
		private void addProductToCart(String tenSP, double donGia) {
			boolean flag = false;
			int rowTrung = -1;
	
			for (int i = 0; i < cartTableModel.getRowCount(); i++) {
				String tenSPTrongTable = cartTableModel.getValueAt(i, 0).toString();
				if (tenSPTrongTable.equalsIgnoreCase(tenSP)) {
					flag = true;
					rowTrung = i;
					break;
				}
			}
	
			if (flag) {
				int soLuongCu = Integer.parseInt(cartTableModel.getValueAt(rowTrung, 1).toString());
				int soLuongMoi = soLuongCu + 1;
				double thanhTien = donGia * soLuongMoi;
	
				cartTableModel.setValueAt(soLuongMoi, rowTrung, 1);
				cartTableModel.setValueAt(df.format(thanhTien), rowTrung, 3);
	
				ProductCard card = mapProductCards.get(tenSP);
				if (card != null) {
					int slTonMới = card.getSoLuongTon() - 1; // Trừ đi 1
					card.capNhatSoLuongTon(slTonMới);
				}
			} else {
				cartTableModel.addRow(new Object[] { tenSP, 1, df.format(donGia), df.format(donGia) });
			}
			tinhTien();
		}
	
		// Tính tiền tạm tính
		private void tinhTien() {
			double tamTinh = 0;
			double thue = 0;
			double tongTien = 0;
	
			for (int i = 0; i < cartTableModel.getRowCount(); i++) {
				String thanhTien = cartTableModel.getValueAt(i, 3).toString();
				thanhTien = thanhTien.replace(",", "").replace(".", "");
				tamTinh += Double.parseDouble(thanhTien);
			}
			lblTienTamTinh.setText(df.format(tamTinh) + "đ");
	
			thue = tamTinh * 0.1;
			lblTienThue.setText(df.format(thue) + "đ");
	
			tongTien = tamTinh + thue - soTienGiam;
			if (tongTien < 0)
				tongTien = 0;
	
			lblTong.setText(df.format(tongTien) + "đ");
			
			if (!txtId.getText().isEmpty()) {
		        int diemCong = (int) (tongTien / 100); // Mỗi 100đ = 1 điểm
		        lblDiemTichLuySo.setText("+" + diemCong + " điểm");
		        lblDiemTichLuySo.getParent().setVisible(true); // Hiển thị dòng điểm tích lũy
		    } else {
		        lblDiemTichLuySo.getParent().setVisible(false); // Ẩn đi nếu là khách vãng lai
		    }
	
		}
	
		// Tùy chỉnh bo góc các component
		public void setRoundBorder(JComponent cmp, int arc) {
			cmp.setBorder(new FlatLineBorder(new Insets(5, 25, 5, 25), new Color(200, 200, 200), 1, arc));
		}
	
		@Override
		public void tableChanged(TableModelEvent e) {
			if (e.getType() == TableModelEvent.UPDATE) {
				int row = e.getFirstRow();
				int col = e.getColumn();
	
				if (col == 1 && !isUpdatingTable) {
	//				String tenSP = cartTableModel.getValueAt(row, 0).toString();
	//				int soLuong = Integer.parseInt(cartTableModel.getValueAt(row, 1).toString());
	
					updateTongTien(row);
				}
			}
		}
	
		private void updateTongTien(int row) {
			isUpdatingTable = true;
			try {
				String input = cartTable.getValueAt(row, 1).toString();
				if (input.isEmpty()) {
					throw new NumberFormatException();
				}
				int sl = Integer.parseInt(input);
				String tenSP = cartTableModel.getValueAt(row, 0).toString();
	
				if (sl <= 0) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
					sl = 1;// set lai sl
					cartTableModel.setValueAt(sl, row, 1);
				}
	
				Double donGia = Double
						.parseDouble(cartTableModel.getValueAt(row, 2).toString().replace(",", "").replace(".", ""));
				Double thanhTienMoi = sl * donGia;
	
				cartTableModel.setValueAt(df.format(thanhTienMoi), row, 3);
	
				ProductCard card = mapProductCards.get(tenSP);
				if (card != null) {
					int tongSoLuongBanDau = sp_dao.getSoLuongTon(tenSP);
					card.capNhatSoLuongTon(tongSoLuongBanDau - sl);
				}
				tinhTien();
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ!");
	
				// set lai sl
				cartTableModel.setValueAt(1, row, 1);
				Double donGia = Double
						.parseDouble(cartTableModel.getValueAt(row, 2).toString().replace(",", "").replace(".", ""));
				cartTableModel.setValueAt(df.format(donGia), row, 3);
				tinhTien();
			} finally {
				isUpdatingTable = false;
			}
		}
	
		@Override
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			if (o == txtTienNhan) {
				double tongTien = Double.parseDouble(lblTong.getText().replace(",", "").replace(".", "").replace("đ", ""));
				double tienNhan = Double.parseDouble(txtTienNhan.getText());
				double tienThua = tienNhan - tongTien;
				if (tienThua < 0) {
					JOptionPane.showMessageDialog(null, "Tiền nhận chưa đủ");
					return;
				} else {
					txtTienThua.setText(df.format(tienThua));
					if (!txtId.getText().isEmpty()) {
						int diemCong = tinhDiemTichLuy(tongTien);
						lblDiemTichLuySo.setText("+" + diemCong + " điểm");
						lblDiemTichLuySo.getParent().setVisible(true);
					}
				}
			} else if (o == chkSuDungDiem) {
				if (chkSuDungDiem.isSelected()) {
					if (txtPhoneNo.getText().isEmpty() || txtScore.getText().isEmpty()) {
						JOptionPane.showMessageDialog(this, "Vui lòng nhập SĐT khách hàng trước!");
						chkSuDungDiem.setSelected(false);
						return;
					}
	
					int diemHienCo = Integer.parseInt(txtScore.getText());
					if (diemHienCo <= 0) {
						JOptionPane.showMessageDialog(this, "Khách hàng không có điểm tích lũy!");
						chkSuDungDiem.setSelected(false);
						return;
					}
	
					
					int diemSuDung = Math.min(diemHienCo, 100000);
					soTienGiam = diemSuDung * 1; //
	
					lblGiamDiemText.setText("Giảm điểm (-" + diemSuDung + " điểm):");
					lblGiamDiemSoTien.setText("-" + df.format(soTienGiam) + "đ");
	
					// Hiển thị dòng giảm giá và thông tin KH
					lblGiamDiemText.getParent().setVisible(true);
					pnlInfo.setVisible(true);
					pnlRank.setVisible(true);
				} else {
					// Hủy sử dụng điểm
					soTienGiam = 0;
					lblGiamDiemText.getParent().setVisible(false);
				}
				tinhTien();
			} else if (o == btnTatCa) {
				loadAllProductList();
			} else if (o == btnDoUong) {
				loadDrinkProductList();
			} else if (o == btnVPP) {
				loadVPPProductList();
			} else if (o == btnBanh_Keo) {
				loadSnackAndSweetProductList();
			} else if (o == btnSua) {
				loadMilkProductList();
			} else if (o == btnMiGoi) {
				loadNoodlesProductList();
			} else if (o == txtPhoneNo) {
				hienThiKhachHang();
			} else if (o == btnThanhToan) {
				thucHienThanhToan();
			}
		}
	
		// THANH TOAN
		private void thucHienThanhToan() {
			if (cartTableModel.getRowCount() == 0) {
				JOptionPane.showMessageDialog(this, "Giỏ hàng đang trống!");
				return;
			}
	
			if (txtTienNhan.getText().trim().isEmpty() || txtTienThua.getText().trim().isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa và nhấn Enter để tính tiền thừa!");
				return;
			}
	
			double tienThua = Double.parseDouble(txtTienThua.getText().replace(",", ""));
			if (tienThua < 0) {
				JOptionPane.showMessageDialog(this, "Số tiền khách đưa chưa đủ!");
				return;
			}
	
			// Kiểm tra an toàn: Đảm bảo đã có nhân viên đăng nhập trong ShareData
			if (ShareData.taiKhoanDangNhap == null) {
				JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy thông tin phiên đăng nhập của nhân viên!");
				return;
			}
	
			try {
				// Khởi tạo DAO và phát sinh mã tự động
				HoaDon_dao hdDao = new HoaDon_dao();
				String maHD = hdDao.phatSinhMaHD();
	
				LocalDate ngayLap = LocalDate.now();
	
				// LẤY NHÂN VIÊN TỪ SHARE DATA
				NhanVien nv = ShareData.taiKhoanDangNhap;
	
				double tongTien = Double.parseDouble(lblTong.getText().replace(",", "").replace(".", "").replace("đ", ""));
				
				KhachHang kh = null;
				if (!txtId.getText().isEmpty()) {
	
					kh = kh_dao.timKiemKhachHangTheoSDT(txtPhoneNo.getText());
					if (kh != null) {
				        //  Nếu khách có tick vào ô sử dụng điểm -> Trừ điểm trước
				        if (chkSuDungDiem.isSelected() && soTienGiam > 0) {
				            int diemSuDung = (int) soTienGiam; // Tỉ lệ quy đổi hiện tại của bạn: 1đ = 1 điểm
				            kh.truDiem(diemSuDung);
				        }
	
			
				        int diemCong = (int) (tongTien / 100);
				        kh.congDiem(diemCong);
	
				        // 3. Cập nhật xuống database
				        boolean isUpdated = kh_dao.suaKhachHang(kh);
				        
				        // Cảnh báo nếu câu lệnh Update bị lỗi (giúp dễ debug sau này)
				        if (!isUpdated) {
				            JOptionPane.showMessageDialog(this, "Lỗi: Không thể cập nhật điểm cho khách hàng vào CSDL!");
				        }
				    }
				} else {
					kh = new KhachHang();
					kh.setTenKH("Khách vãng lai");
					kh.setMaKH(null);
				}
	
				
	
				HoaDon hd = new HoaDon(maHD, ngayLap, nv, kh, TrangThaiHoaDon.DA_THANH_TOAN, tongTien);
				System.out.println("DEBUG HOA DON TRUOC KHI GUI DAO:");
				System.out.println("MaHD: " + hd.getMaHD());
				System.out.println("Ngay: " + hd.getNgayLap());
				System.out.println("MaNV: " + (hd.getNV() != null ? hd.getNV().getMaNV() : "NULL"));
				System.out.println("MaKH: " + (hd.getKH() != null ? hd.getKH().getMaKH() : "NULL"));
				System.out.println("Tong Tien: " + hd.getTongTien());
				// Lưu Hóa đơn vào DB
				if (hdDao.themHoaDon(hd)) {
	
					// Lưu Chi tiết hóa đơn và Cập nhật tồn kho
					ChiTietHoaDon_dao cthdDao = new ChiTietHoaDon_dao();
	
					for (int i = 0; i < cartTableModel.getRowCount(); i++) {
						String tenSP = cartTableModel.getValueAt(i, 0).toString();
						int soLuong = Integer.parseInt(cartTableModel.getValueAt(i, 1).toString());
						double thanhTien = Double
								.parseDouble(cartTableModel.getValueAt(i, 3).toString().replace(",", "").replace(".", "").replace("đ", ""));
	
						SanPham sp = sp_dao.getSanPhamTheoTen(tenSP);
						
						if (sp != null) {
							double thue = thanhTien * 0.1;
					        ChiTietHoaDon cthd = new ChiTietHoaDon(hd, sp, soLuong, thanhTien, thue);
					       
					        boolean kq = cthdDao.themChiTietHoaDon(cthd);
					        
					        if(kq) {
					            sp_dao.capNhatSoLuong(sp.getMaSP(), -soLuong);
					        } else {
					            System.out.println("Lỗi lưu chi tiết cho sản phẩm: " + tenSP);
					        }
					    }
	
					}
	
					JOptionPane.showMessageDialog(this, "Thanh toán thành công! Mã HĐ: " + maHD);
					// Reset giao diện
					cartTableModel.setRowCount(0);
					tinhTien();
					txtTienNhan.setText("");
					txtTienThua.setText("");
					txtPhoneNo.setText("");
					txtId.setText("");
					txtName.setText("");
					txtScore.setText("");
					txtType.setText("");
					chkSuDungDiem.setSelected(false);
					soTienGiam = 0;
					lblGiamDiemText.getParent().setVisible(false);
					lblDiemTichLuySo.getParent().setVisible(false);
					pnlInfo.setVisible(false);
					pnlRank.setVisible(false);
	
					listProduct = sp_dao.getTableSanPham();
					loadAllProductList();
				}
	
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Lỗi thanh toán: " + ex.getMessage());
			}
		}
	
		private void hienThiKhachHang() {
			KhachHang kh = kh_dao.timKiemKhachHangTheoSDT(txtPhoneNo.getText());
			if (kh == null) {
				JOptionPane.showMessageDialog(this, "Số điện thoại chưa đăng ký thành viên");
				return;
			} else {
				pnlInfo.setVisible(true);
				pnlRank.setVisible(true);
				txtId.setText(kh.getMaKH());
				txtName.setText(kh.getTenKH());
				txtScore.setText(kh.getDiemTichLuy() + "");
				txtType.setText(kh.getLoaiKhachHang().getMoTa());
			}
		}
	
		private void loadNoodlesProductList() {
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
				if (sp.getLoaiSanPham().equals(LoaiSanPham.MI_GOI)) {
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
					mapProductCards.put(sp.getTenSP(), productCard);
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
		}
	
		private void loadMilkProductList() {
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
				if (sp.getLoaiSanPham().equals(LoaiSanPham.SUA)) {
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
					mapProductCards.put(sp.getTenSP(), productCard);
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
	
		}
	
		private void loadSnackAndSweetProductList() {
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
				if (sp.getLoaiSanPham().equals(LoaiSanPham.BANH_KEO)) {
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
					mapProductCards.put(sp.getTenSP(), productCard);
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
	
		}
	
		private void loadVPPProductList() {
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
				if (sp.getLoaiSanPham().equals(LoaiSanPham.VAN_PHONG_PHAM)) {
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
					mapProductCards.put(sp.getTenSP(), productCard);
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
	
		}
	
		private void loadDrinkProductList() {
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
				if (sp.getLoaiSanPham().equals(LoaiSanPham.DO_UONG)) {
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
					mapProductCards.put(sp.getTenSP(), productCard);
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
		}
	
		public void loadAllProductList() {
			pnlProductList.removeAll();
			listProduct = sp_dao.getTableSanPham();
			for (SanPham sp : listProduct) {
				ProductCard productCard = new ProductCard(sp, sanPham -> {
					addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
				});
				mapProductCards.put(sp.getTenSP(), productCard);
				pnlProductList.add(productCard);
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
		}
	
		public void setRoundBorder(JPanel pnl, int arc) {
	
			pnl.setBorder(new FlatLineBorder(new Insets(20, 20, 20, 20), new Color(240, 240, 240), 1, arc));
		}
	
		// hàm hỗ trợ chỉnh các panel con ở trong parent
		private void setAllTransparentForPanel(JComponent parent) {
			for (Component child : parent.getComponents()) {
				if (child instanceof JPanel) {
					((JPanel) child).setOpaque(false);
					setAllTransparentForPanel((JPanel) child); // đệ quy cho pnl lồng nhau
				}
			}
		}
	
		// hàm hỗ trợ chỉnh các button con ở trong parent
		private void setAllTransparentForButton(JComponent parent) {
			for (Component child : parent.getComponents()) {
				if (child instanceof JButton) {
					((JButton) child).setOpaque(false);
				}
			}
		}
	
		private int tinhDiemTichLuy(double tongTien) {
			return (int) (tongTien / 100);
		}
	
		@Override
		public void insertUpdate(DocumentEvent e) {
			thucHienTimKiem();
		}
	
		@Override
		public void removeUpdate(DocumentEvent e) {
			thucHienTimKiem();
		}
	
		@Override
		public void changedUpdate(DocumentEvent e) {
			thucHienTimKiem();
		}
	
		private void thucHienTimKiem() {
			String tuKhoa = txtTimKiemSP.getText().toLowerCase().trim();
	
			pnlProductList.removeAll();
	
			for (SanPham sp : listProduct) {
	
				if (sp.getTenSP().toLowerCase().contains(tuKhoa)) {
	
					ProductCard productCard = new ProductCard(sp, sanPham -> {
						addProductToCart(sanPham.getTenSP(), sanPham.getGiaBan());
					});
	
					mapProductCards.put(sp.getTenSP(), productCard);
	
					pnlProductList.add(productCard);
				}
			}
	
			pnlProductList.revalidate();
			pnlProductList.repaint();
		}
	
	}