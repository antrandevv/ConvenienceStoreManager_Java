package custom_gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.ui.FlatLineBorder;

public class HoaDonCard extends JPanel {

    private JButton btnXem;
	private JButton btnIn;

	public HoaDonCard(String maHD, String tongTien, String thoiGian, String thuNgan, String khachHang, int soLuong) {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
     
        setBorder(new FlatLineBorder(new Insets(15, 20, 15, 20), new Color(230, 230, 230), 1, 15));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));


        JPanel pnlRow1 = new JPanel(new BorderLayout());
        pnlRow1.setOpaque(false);
        
        JLabel lblMaHD = new JLabel(maHD);
        lblMaHD.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblTongTien = new JLabel(tongTien);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));

        pnlRow1.add(lblMaHD, BorderLayout.WEST);
        pnlRow1.add(lblTongTien, BorderLayout.EAST);
        
      
        JPanel pnlDetails = new JPanel(new GridLayout(2, 2, 10, 5));
        pnlDetails.setOpaque(false);
        
        pnlDetails.add(new JLabel("📅 " + thoiGian));
        pnlDetails.add(new JLabel("Thu ngân: " + thuNgan));
        pnlDetails.add(new JLabel(khachHang != null ? "Khách hàng: " + khachHang : "Khách vãng lai"));
        pnlDetails.add(new JLabel("Số lượng: " + soLuong + " món"));
        
        
        for (Component c : pnlDetails.getComponents()) {
            if (c instanceof JLabel) c.setForeground(Color.GRAY);
        }

       
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        pnlButtons.setOpaque(false);
        
        btnXem = new JButton("👁 Xem");
        btnIn = new JButton("🖨 In");
        btnXem.putClientProperty("JButton.buttonType", "roundRect");
        btnIn.putClientProperty("JButton.buttonType", "roundRect");
        btnXem.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        pnlButtons.add(btnXem);
        pnlButtons.add(btnIn);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setOpaque(false);
        pnlBottom.add(pnlDetails, BorderLayout.CENTER);
        pnlBottom.add(pnlButtons, BorderLayout.EAST);

        add(pnlRow1, BorderLayout.NORTH);
        add(pnlBottom, BorderLayout.CENTER);
    }
    public void addXemActionListener(ActionListener listener) {
        btnXem.addActionListener(listener);
    }

    public void addInActionListener(ActionListener listener) {
        btnIn.addActionListener(listener);
    }
}