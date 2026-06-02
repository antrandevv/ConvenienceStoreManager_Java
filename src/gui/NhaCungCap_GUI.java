package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.NhaCungCap_dao;
import entity.NhaCungCap;

public class NhaCungCap_GUI extends JFrame implements ActionListener, MouseListener {

	private JTextField txtMaNCC;
	private JTextField txtTenNCC;
	private JTextField txtSoDienThoai;
	private JTextField txtEmail;
	private JTextField txtDiaChi;

	private JButton btnTaoMa;
	private JButton btnLuu;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JButton btnThoat;

	private DefaultTableModel model;
	private JTable table;
	private NhaCungCap_dao ncc_dao;

	public NhaCungCap_GUI() {
//		try {
//			ConnectDB.getInstance().connect();
//			System.out.println("Connected!!!");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		ncc_dao = new NhaCungCap_dao();

		setTitle("Quản lý nhà cung cấp");
		setSize(900, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setUndecorated(true);

		setLayout(new BorderLayout(10, 10));

		// nua tren
		JPanel pnlTopHalf = new JPanel();
		pnlTopHalf.setLayout(new BoxLayout(pnlTopHalf, BoxLayout.Y_AXIS));
		pnlTopHalf.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		// header
		JPanel pnlHeader = new JPanel(new BorderLayout());
		JLabel lblTitle = new JLabel("Nhà cung cấp", JLabel.CENTER);
		lblTitle.setFont(new Font("Arial", Font.BOLD, 30));
		lblTitle.setForeground(Color.BLUE);
		pnlHeader.add(lblTitle, BorderLayout.CENTER);

		pnlTopHalf.add(pnlHeader);
		pnlTopHalf.add(Box.createVerticalStrut(20));

		// form nhap lieu
		JPanel pnlFormFields = new JPanel();
		pnlFormFields.setLayout(new BoxLayout(pnlFormFields, BoxLayout.Y_AXIS));

		int labelWidth = 100;
		int labelHeight = 25;

		// row 1
		JPanel pnlRow1 = new JPanel();
		pnlRow1.setLayout(new BoxLayout(pnlRow1, BoxLayout.X_AXIS));

		// ma ncc
		JLabel lblMaNCC = new JLabel("Mã NCC:");
		lblMaNCC.setPreferredSize(new Dimension(labelWidth, labelHeight));
		lblMaNCC.setMinimumSize(new Dimension(labelWidth, labelHeight));
		lblMaNCC.setMaximumSize(new Dimension(labelWidth, labelHeight));
		txtMaNCC = new JTextField();
		txtMaNCC.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelHeight));

		txtMaNCC.setEditable(false);

		pnlRow1.add(lblMaNCC);
		pnlRow1.add(txtMaNCC);
		pnlRow1.add(Box.createHorizontalStrut(30));

		// ten ncc
		JLabel lblTenNCC = new JLabel("Tên NCC:");
		lblTenNCC.setPreferredSize(new Dimension(labelWidth, labelHeight));
		lblTenNCC.setMinimumSize(new Dimension(labelWidth, labelHeight));
		lblTenNCC.setMaximumSize(new Dimension(labelWidth, labelHeight));
		txtTenNCC = new JTextField();
		txtTenNCC.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelHeight));

		pnlRow1.add(lblTenNCC);
		pnlRow1.add(txtTenNCC);

		pnlFormFields.add(pnlRow1);
		pnlFormFields.add(Box.createVerticalStrut(10));

		// row 2
		JPanel pnlRow2 = new JPanel();
		pnlRow2.setLayout(new BoxLayout(pnlRow2, BoxLayout.X_AXIS));

		// sdt
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setPreferredSize(new Dimension(labelWidth, labelHeight));
		lblSDT.setMinimumSize(new Dimension(labelWidth, labelHeight));
		lblSDT.setMaximumSize(new Dimension(labelWidth, labelHeight));
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelHeight));

		pnlRow2.add(lblSDT);
		pnlRow2.add(txtSoDienThoai);
		pnlRow2.add(Box.createHorizontalStrut(30));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setPreferredSize(new Dimension(labelWidth, labelHeight));
		lblEmail.setMinimumSize(new Dimension(labelWidth, labelHeight));
		lblEmail.setMaximumSize(new Dimension(labelWidth, labelHeight));
		txtEmail = new JTextField();
		txtEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelHeight));

		pnlRow2.add(lblEmail);
		pnlRow2.add(txtEmail);

		pnlFormFields.add(pnlRow2);
		pnlFormFields.add(Box.createVerticalStrut(10));

		// row3
		JPanel pnlRow3 = new JPanel();
		pnlRow3.setLayout(new BoxLayout(pnlRow3, BoxLayout.X_AXIS));

		// dia chi
		JLabel lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setPreferredSize(new Dimension(labelWidth, labelHeight));
		lblDiaChi.setMinimumSize(new Dimension(labelWidth, labelHeight));
		lblDiaChi.setMaximumSize(new Dimension(labelWidth, labelHeight));
		txtDiaChi = new JTextField();
		txtDiaChi.setMaximumSize(new Dimension(Integer.MAX_VALUE, labelHeight));

		pnlRow3.add(lblDiaChi);
		pnlRow3.add(txtDiaChi);

		pnlFormFields.add(pnlRow3);

		pnlTopHalf.add(pnlFormFields);
		add(pnlTopHalf, BorderLayout.NORTH);

		// table
		JPanel pnlTable = new JPanel();
		pnlTable.setLayout(new BorderLayout());
		String col[] = { "Mã nhà cung cấp", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ", "Email" };
		model = new DefaultTableModel(col, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		JScrollPane pane = new JScrollPane(table);
		pnlTable.add(pane);
		add(pnlTable, BorderLayout.CENTER);

		// do du lieu vao bang
		DocDuLieuVaoTable();

		// btn chuc nang
		JPanel pnlBottom = new JPanel();
		pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.X_AXIS));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

		pnlBottom.add(Box.createHorizontalGlue()); // can giua tat ca nut

		int btnWidth = 80;
		int btnHeight = 30;

		btnTaoMa = new JButton("Tạo mã");
		btnTaoMa.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnTaoMa);
		pnlBottom.add(Box.createHorizontalStrut(10));

		btnLuu = new JButton("Lưu");
		btnLuu.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnLuu);
		pnlBottom.add(Box.createHorizontalStrut(10));

		btnSua = new JButton("Sửa");
		btnSua.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnSua);
		pnlBottom.add(Box.createHorizontalStrut(10));

		btnXoa = new JButton("Xoá");
		btnXoa.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnXoa);
		pnlBottom.add(Box.createHorizontalStrut(10));

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnLamMoi);
		pnlBottom.add(Box.createHorizontalStrut(10));

		btnThoat = new JButton("Thoát");
		btnThoat.setPreferredSize(new Dimension(btnWidth, btnHeight));
		pnlBottom.add(btnThoat);

		pnlBottom.add(Box.createHorizontalGlue()); // can giua tat ca nut

		add(pnlBottom, BorderLayout.SOUTH);

		table.addMouseListener(this);
		btnTaoMa.addActionListener(this);
		btnLuu.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnThoat.addActionListener(this);
	}

	// ham doc du lieu vao bang
	private void DocDuLieuVaoTable() {
		ArrayList<NhaCungCap> listNCC = ncc_dao.getTableNhaCungCap();
		for (NhaCungCap ncc : listNCC) {
			model.addRow(new Object[] { ncc.getMaNCC(), ncc.getTenNCC(), ncc.getSoDienThoai(), ncc.getDiaChi(),
					ncc.getEmail() });
		}
	}

	// phat sinh maNCC
	private String phatSinhMaNCC() {
		ArrayList<NhaCungCap> listNCC = ncc_dao.getTableNhaCungCap();
		if (listNCC.isEmpty()) {
			return "NCC1";
		}

		int max = 0;
		for (NhaCungCap ncc : listNCC) {
			String ma = ncc.getMaNCC();
			try {

				int so = Integer.parseInt(ma.substring(3));
				if (so > max) {
					max = so;
				}
			} catch (Exception e) {

			}
		}

		return String.format("NCC%d", max + 1);
	}

	// validate
	private boolean validData() {
		String ten = txtTenNCC.getText().trim();
		String sdt = txtSoDienThoai.getText().trim();
		String email = txtEmail.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String ma = txtMaNCC.getText().trim();

		if (ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Mã NCC không được để trống. Hãy ấn 'Tạo mã' để tự động phát sinh mã!");
			txtMaNCC.requestFocus();
			return false;
		}

		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Tên NCC không được để trống");
			txtTenNCC.requestFocus();
			return false;
		}

		if (!ten.matches("^\\p{Lu}.*$")) {

			JOptionPane.showMessageDialog(this, "Tên nhà cung cấp phải viết hoa chữ cái đầu");
			txtTenNCC.requestFocus();
			txtTenNCC.selectAll();
			return false;
		}

		if (sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống");
			txtSoDienThoai.requestFocus();
			return false;
		}

		if (!sdt.matches("^0\\d{9}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại gồm 10 số và bắt đầu bằng 0");
			txtSoDienThoai.requestFocus();
			txtSoDienThoai.selectAll();
			return false;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Email không được để trống");
			txtEmail.requestFocus();
			return false;
		}

		if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,4}$")) {
			JOptionPane.showMessageDialog(this, "Email không hợp lệ");
			txtEmail.requestFocus();
			txtEmail.selectAll();
			return false;
		}

		if (diaChi.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Địa chỉ không được để trống");
			txtDiaChi.requestFocus();
			return false;
		}

		return true;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnTaoMa)) {
			txtTenNCC.setText("");
			txtSoDienThoai.setText("");
			txtDiaChi.setText("");
			txtEmail.setText("");

			txtMaNCC.setText(phatSinhMaNCC());
			txtMaNCC.setEditable(false);

			txtTenNCC.requestFocus();
		} else if (o.equals(btnLuu)) {
			if (validData()) {

				String ma = txtMaNCC.getText();
				String ten = txtTenNCC.getText();
				String sdt = txtSoDienThoai.getText();
				String email = txtEmail.getText();
				String dc = txtDiaChi.getText();

				NhaCungCap ncc = new NhaCungCap(ma, ten, sdt, dc, email);
				try {
					// luu vao db
					if (ncc_dao.themNhaCungCap(ncc)) {
						model.addRow(new Object[] { ma, ten, sdt, dc, email });
						JOptionPane.showMessageDialog(this, "Thêm nhà cung cấp thành công!");
					}
				} catch (Exception exx) {
					JOptionPane.showMessageDialog(this, exx.getMessage(), "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
					if (exx.getMessage().contains("đã tồn tại")) {
						txtMaNCC.requestFocus();
					}
				}

			}
		} else if (o.equals(btnXoa)) {

			int row = table.getSelectedRow();

			if (row != -1) {
				String maNCC = model.getValueAt(row, 0).toString();
				String tenNCC = model.getValueAt(row, 1).toString();
				int confirm = JOptionPane.showConfirmDialog(this,
						"Bạn có chắc chắn muốn xóa nhà cung cấp '" + tenNCC + " (" + maNCC + ")' không?",
						"Xác nhận xóa", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					try {
						if (ncc_dao.xoaNhaCungCap(maNCC)) {
							model.removeRow(row);
							btnLamMoi.doClick();
							JOptionPane.showMessageDialog(this, "Xóa thành công!");
						}
					} catch (SQLException ex) {
						// 547 la ma loi rang buoc khoa ngoai
						if (ex.getErrorCode() == 547) {
							JOptionPane.showMessageDialog(this,
									"Không thể xóa nhà cung cấp này vì họ đang có sản phẩm trong kho!",
									"Lỗi ràng buộc dữ liệu", JOptionPane.ERROR_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(this, "Lỗi xóa dữ liệu: " + ex.getMessage());
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng trên bảng để xóa!");
			}
		} else if (o.equals(btnSua)) {
			int row = table.getSelectedRow();

			if (row != -1) {
				if (validData()) {

					String ma = txtMaNCC.getText();
					String ten = txtTenNCC.getText();
					String sdt = txtSoDienThoai.getText();
					String dc = txtDiaChi.getText();
					String email = txtEmail.getText();

					NhaCungCap ncc = new NhaCungCap(ma, ten, sdt, dc, email);

					if (ncc_dao.suaNhaCungCap(ncc)) {
						model.setValueAt(ten, row, 1);
						model.setValueAt(sdt, row, 2);
						model.setValueAt(dc, row, 3);
						model.setValueAt(email, row, 4);

						JOptionPane.showMessageDialog(this, "Cập nhật thông tin thành công!");
					} else {
						JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
					}

				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa trên bảng!");
			}
		} else if (o.equals(btnLamMoi)) {
			txtMaNCC.setEditable(true);
			txtMaNCC.setText("");
			txtTenNCC.setText("");
			txtSoDienThoai.setText("");
			txtDiaChi.setText("");
			txtEmail.setText("");
			table.clearSelection();

			txtMaNCC.setEditable(false);
		} else if (o.equals(btnThoat)) {
			this.dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		int row = table.getSelectedRow();

		if (row >= 0) {
			// lay du lieu neu la null thi de trong
			txtMaNCC.setText(model.getValueAt(row, 0) != null ? model.getValueAt(row, 0).toString() : "");
			txtTenNCC.setText(model.getValueAt(row, 1) != null ? model.getValueAt(row, 1).toString() : "");
			txtSoDienThoai.setText(model.getValueAt(row, 2) != null ? model.getValueAt(row, 2).toString() : "");
			txtDiaChi.setText(model.getValueAt(row, 3) != null ? model.getValueAt(row, 3).toString() : "");
			txtEmail.setText(model.getValueAt(row, 4) != null ? model.getValueAt(row, 4).toString() : "");

			txtMaNCC.setEditable(false);
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
