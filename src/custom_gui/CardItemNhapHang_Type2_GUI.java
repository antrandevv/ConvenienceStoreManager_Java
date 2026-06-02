package custom_gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.DecimalFormat;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.ui.FlatLineBorder;

import gui.NhapHang_GUI;

public class CardItemNhapHang_Type2_GUI extends JPanel implements ActionListener {

	private JLabel lblItem;
	private JLabel lblTenSP;
	private JLabel lblMaSP;
	private JLabel lblGiaNhap;
	private JTextField tfSoLuong;
	private JLabel lblThanhTien;
	private double thanhTien;
	private double giaNhap;
	private JButton btnDelete;
	private NhapHang_GUI nhapHang_GUI;
	private Font originalFont;
	private String link;

	public CardItemNhapHang_Type2_GUI(NhapHang_GUI nhapHang_UI, String maSP, String tenSP, int soLuong, double giaNhap,
			String url) {

		this.nhapHang_GUI = nhapHang_UI;
		this.giaNhap = giaNhap;
		this.link = url;
		thanhTien = tinhThanhTien(giaNhap, soLuong);
		Dimension editSize = new Dimension(350, 100);
		setPreferredSize(editSize);
		setMaximumSize(editSize);
		setBackground(new Color(238, 224, 229));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new FlatLineBorder(new Insets(15, 20, 20, 0), new Color(205, 92, 92), 1, 20));

		ImageIcon iconItem = new ImageIcon(url);
		lblItem = new JLabel(setImg(iconItem, 70, 70));
		lblItem.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(205, 92, 92), 1, 10));

		lblTenSP = new JLabel(tenSP);
		Dimension nameSize = new Dimension(100, 20);
		lblTenSP.setPreferredSize(nameSize);
		lblTenSP.setMaximumSize(nameSize);
		lblTenSP.setMinimumSize(nameSize);
		lblTenSP.setSize(nameSize);
		lblMaSP = new JLabel(maSP);
		lblMaSP.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblGiaNhap = new JLabel(formatVND(giaNhap) + "");
		lblGiaNhap.setForeground(Color.RED);
		tfSoLuong = new JTextField(soLuong + "");
		ImageIcon multiIcon = new ImageIcon("Img/multi.png");
		JLabel lblMultiIcon = new JLabel(setImg(multiIcon, 20, 20));
		Dimension editTFSoLuong = new Dimension(70, 30);
		tfSoLuong.setPreferredSize(editTFSoLuong);
		tfSoLuong.setMaximumSize(editTFSoLuong);
		tfSoLuong.setHorizontalAlignment(JTextField.CENTER);
		lblThanhTien = new JLabel(formatVND(thanhTien));
		lblThanhTien.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblThanhTien.setForeground(Color.RED);
		lblThanhTien.setAlignmentX(LEFT_ALIGNMENT);
		lblThanhTien.setPreferredSize(new Dimension(160, 30));
		lblThanhTien.setMaximumSize(new Dimension(160, 30));
		lblThanhTien.setSize(new Dimension(150, 30));
		lblThanhTien.setHorizontalAlignment(SwingConstants.RIGHT);

		Box bxSoLuong = Box.createHorizontalBox();
		bxSoLuong.setAlignmentX(LEFT_ALIGNMENT);
		bxSoLuong.add(lblMultiIcon);
		bxSoLuong.add(tfSoLuong);

		Box bxMa_Ten_Gia = Box.createVerticalBox();
		bxMa_Ten_Gia.add(lblMaSP);
		bxMa_Ten_Gia.add(Box.createVerticalStrut(5));
		bxMa_Ten_Gia.add(lblTenSP);
		bxMa_Ten_Gia.add(Box.createVerticalStrut(5));
		bxMa_Ten_Gia.add(lblGiaNhap);

		Box bxSoLuong_ThanhTien = Box.createVerticalBox();
		bxSoLuong_ThanhTien.setPreferredSize(new Dimension(160, 80));
		bxSoLuong_ThanhTien.setMaximumSize(new Dimension(160, 80));
		bxSoLuong_ThanhTien.add(bxSoLuong);
		bxSoLuong_ThanhTien.add(Box.createVerticalStrut(10));
		bxSoLuong_ThanhTien.add(lblThanhTien);

		JPanel sepLine = new JPanel();
		sepLine.setBackground(new Color(121, 205, 205));
		sepLine.setMaximumSize(new Dimension(3, Integer.MAX_VALUE));
		sepLine.setMinimumSize(new Dimension(3, 0));
		sepLine.setPreferredSize(new Dimension(3, 0));

		ImageIcon iconDelete = new ImageIcon("Img/delete.png");
		btnDelete = new JButton(setImg(iconDelete, 25, 25));
		btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDelete.setFocusPainted(false);
		btnDelete.setContentAreaFilled(false);


		Box bxInfoItem = Box.createHorizontalBox();

		bxInfoItem.add(lblItem);
		bxInfoItem.add(Box.createHorizontalStrut(5));
		bxInfoItem.add(bxMa_Ten_Gia);
		bxInfoItem.add(Box.createHorizontalGlue());
		bxInfoItem.add(bxSoLuong_ThanhTien);
		bxInfoItem.add(Box.createHorizontalStrut(5));
		bxInfoItem.add(sepLine);
		bxInfoItem.add(Box.createHorizontalStrut(5));
		bxInfoItem.add(btnDelete);

		setOriginalFont();
		add(bxInfoItem);

		btnDelete.addActionListener(this);
		tfSoLuong.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				if(e.getOppositeComponent() == btnDelete) {
					return;
				}
				nhapSoLuong();
			}
		});
	}

	public ImageIcon setImg(ImageIcon icon, int width, int height) {

		Image img = icon.getImage();
		Image editImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon IconEdited = new ImageIcon(editImg);
		return IconEdited;
	}

	public double tinhThanhTien(double giaNhap, int soLuong) {

		return giaNhap * soLuong;
	}

	public String formatVND(double tien) {

		DecimalFormat fm = new DecimalFormat("#,### đ");
		return fm.format(tien);
	}

	public String getMaSP() {
		return lblMaSP.getText();
	}
	
	public String getTenSP() {
		return lblTenSP.getText();
	}
	
	public int getSoLuong() {
		int soLuong = -1;
		
		try {
				soLuong = Integer.parseInt(tfSoLuong.getText());
			
		} catch (NumberFormatException e) {
			
		}
		return soLuong;
	}
	
	public double thanhTien() {
		
		double thanhTien = -1;
		
		try {
				thanhTien = Double.parseDouble(lblThanhTien.getText());
			
		} catch (NumberFormatException e) {
			
		}
		return thanhTien;
	}
	
	public String getAnh() {
		
		return link;
	}
	
	public double giaNhap() {
		
		return giaNhap;
	}
	
	public void setSoLuong() {

		try {

			int soLuongHienTai = Integer.parseInt(tfSoLuong.getText());
			int soLuongMoi = soLuongHienTai + 1;
			thanhTien = tinhThanhTien(giaNhap, soLuongMoi);
			tfSoLuong.setText(String.valueOf(soLuongMoi));
			lblThanhTien.setText(formatVND(thanhTien));
			lblThanhTien.revalidate();
			lblThanhTien.repaint();
			nhapHang_GUI.autoReSize(lblThanhTien, 5, originalFont);

		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
			tfSoLuong.requestFocus();

		}
	}

	public double getThanhTien() {
		return thanhTien;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object o = e.getSource();

		if (o.equals(btnDelete)) {
			nhapHang_GUI.xoaSanPhamKhoiChiTiet(lblMaSP.getText());
		}

	}

	public void nhapSoLuong() {

		try {

			int soLuong = Integer.parseInt(tfSoLuong.getText());
			if(soLuong <= 0) {
				JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Error", JOptionPane.ERROR_MESSAGE);
				tfSoLuong.requestFocus();
				return;
			}
			thanhTien = tinhThanhTien(giaNhap, soLuong);
			tfSoLuong.setText(String.valueOf(soLuong));
			lblThanhTien.setText(formatVND(thanhTien));
			lblThanhTien.revalidate();
			lblThanhTien.repaint();
			nhapHang_GUI.autoReSize(lblThanhTien, 5, originalFont);
			nhapHang_GUI.setLBLTongTien();

		} catch (NumberFormatException e) {

			JOptionPane.showMessageDialog(this, "Số lượng không hợp lệ!");
			tfSoLuong.requestFocus();
		}
	}

	public void setOriginalFont() {
		originalFont = lblThanhTien.getFont();
	}

}
