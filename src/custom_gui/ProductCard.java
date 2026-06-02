package custom_gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.ui.FlatLineBorder;

import entity.SanPham;

public class ProductCard extends JPanel {
	private JLabel lblAmount;
	private SanPham sp;
	
	// Khai báo màu sắc giống Type1
	private final Color colorDefault = new Color(224, 238, 238);
	private final Color colorHover = new Color(225, 240, 255);
	private final Color borderDefault = new Color(232, 232, 232);
	private final Color borderHover = new Color(100, 180, 255);

	public ProductCard(SanPham sp, CardActionListener listener) {
		this.sp = sp;

		// 1. Cấu hình Layout và Kích thước giống Type1
		Dimension size = new Dimension(150, 230); // Tăng chiều cao một chút để chứa nút bấm
		setPreferredSize(size);
		setMaximumSize(size);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(colorDefault);
		setRoundBorder(this, borderDefault);

		// 2. Xử lý Ảnh
		String path = sp.getHinhAnh();
		JLabel lblImage = new JLabel();
		try {
			ImageIcon icon = new ImageIcon(path);
			Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			lblImage.setIcon(new ImageIcon(img));
		} catch (Exception e) {
			lblImage.setText("No Image");
		}
		lblImage.setAlignmentX(CENTER_ALIGNMENT);

		// 3. Thông tin Sản phẩm (Tên)
		JLabel lblName = new JLabel(sp.getTenSP());
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		lblName.setAlignmentX(CENTER_ALIGNMENT);
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setMaximumSize(new Dimension(130, 20));

		// 4. Giá và Số lượng
		JLabel lblPrice = new JLabel(String.format("%,.0f đ", sp.getGiaBan()));
		lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 13));
		lblPrice.setForeground(Color.RED);
		lblPrice.setAlignmentX(CENTER_ALIGNMENT);

		lblAmount = new JLabel("Còn: " + sp.getSoLuongTon());
		lblAmount.setFont(new Font("Segoe UI", Font.ITALIC, 11));
		lblAmount.setAlignmentX(CENTER_ALIGNMENT);

		// 5. Nút bấm (Tùy chỉnh để không phá layout)
		JButton btnAdd = new JButton("Thêm");
		btnAdd.setAlignmentX(CENTER_ALIGNMENT);
		btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnAdd.addActionListener(e -> {
			if (sp.getSoLuongTon() > 0) {
				sp.setSoLuongTon(sp.getSoLuongTon() - 1);
				lblAmount.setText("Còn: " + sp.getSoLuongTon());
				if (listener != null) {
					listener.onAddButtonListener(sp);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Sản phẩm đã hết hàng!");
			}
		});

		// 6. Thêm các thành phần vào Card với khoảng cách (Strut)
		add(Box.createVerticalStrut(10));
		add(lblImage);
		add(Box.createVerticalStrut(5));
		add(lblName);
		add(Box.createVerticalStrut(5));
		add(lblPrice);
		add(lblAmount);
		add(Box.createVerticalStrut(10));
		add(btnAdd);
		add(Box.createVerticalStrut(10));

		// 7. Hiệu ứng Hover giống Type1
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(colorHover);
				setRoundBorder(ProductCard.this, borderHover);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(colorDefault);
				setRoundBorder(ProductCard.this, borderDefault);
			}
		});
	}

	// Hàm hỗ trợ bo góc từ file Type1
	private void setRoundBorder(JPanel pnl, Color clr) {
		pnl.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), clr, 1, 15));
	}

	public void capNhatSoLuongTon(int soLuongMoi) {
		this.sp.setSoLuongTon(soLuongMoi);
		lblAmount.setText("Còn: " + soLuongMoi);
	}

	public int getSoLuongTon() {
		return this.sp.getSoLuongTon();
	}
	public SanPham getSanPham() {
		return this.sp;
	}
}