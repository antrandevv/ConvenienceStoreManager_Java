package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import dao.ChiTietPhieuNhap_DAO;
import entity.PhieuNhap;
import entity.ChiTietPhieuNhap;

public class ChiTietPhieuNhap_GUI extends JDialog {
    
    private PhieuNhap pN;
    private ArrayList<ChiTietPhieuNhap> dsCTPN;
    private JPanel pnlTable;
    private JLabel lblTotalVal;
    private final int CONTENT_WIDTH = 450;

    public ChiTietPhieuNhap_GUI(PhieuNhap phieuNhap) {
        this.pN = phieuNhap;
        
        setModal(true);
        setTitle("Chi tiết phiếu nhập - " + pN.getMaPN());
        setSize(550, 750);
        setLocationRelativeTo(null);
        
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.setBackground(Color.WHITE);
        pnlMain.setBorder(new EmptyBorder(20, 40, 20, 40));

        JLabel lblStoreName = new JLabel("CỬA HÀNG SWIFTPICK");
        lblStoreName.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblStoreName.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblTitle = new JLabel("PHIẾU NHẬP HÀNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlMain.add(lblStoreName);
        pnlMain.add(lblTitle);
        pnlMain.add(Box.createVerticalStrut(15));
        pnlMain.add(createSeparator());

        JPanel pnlInfo = new JPanel(new GridLayout(4, 2, 0, 8));
        pnlInfo.setBackground(Color.WHITE);
        pnlInfo.setMaximumSize(new Dimension(CONTENT_WIDTH, 120));
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        addInfoRow(pnlInfo, "Mã phiếu nhập:", pN.getMaPN());
        addInfoRow(pnlInfo, "Ngày nhập:", pN.getThoiGianNhap().format(dtf));
        addInfoRow(pnlInfo, "Nhân viên:", pN.getnV().getTenNV());
        addInfoRow(pnlInfo, "Nhà cung cấp:", pN.getnCC().getTenNCC());
        
        pnlMain.add(pnlInfo);
        pnlMain.add(Box.createVerticalStrut(15));
        pnlMain.add(createSeparator());

        JPanel pnlTableHeader = new JPanel(new GridBagLayout());
        pnlTableHeader.setBackground(new Color(245, 245, 245));
        pnlTableHeader.setPreferredSize(new Dimension(CONTENT_WIDTH, 35));
        pnlTableHeader.setMaximumSize(new Dimension(CONTENT_WIDTH, 35));
        pnlTableHeader.setBorder(new EmptyBorder(0, 10, 0, 10));
        
        addGridBagLabel(pnlTableHeader, "Sản phẩm", 0, 0.5, SwingConstants.LEFT, Font.BOLD);
        addGridBagLabel(pnlTableHeader, "Số Lượng", 1, 0.1, SwingConstants.CENTER, Font.BOLD);
        addGridBagLabel(pnlTableHeader, "Đơn giá", 2, 0.2, SwingConstants.RIGHT, Font.BOLD);
        addGridBagLabel(pnlTableHeader, "T.Tiền", 3, 0.2, SwingConstants.RIGHT, Font.BOLD);
        
        pnlMain.add(pnlTableHeader);

        pnlTable = new JPanel();
        pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.Y_AXIS));
        pnlTable.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(pnlTable);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setMaximumSize(new Dimension(CONTENT_WIDTH, 300));
        scroll.setPreferredSize(new Dimension(CONTENT_WIDTH, 300));
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        pnlMain.add(scroll);

        pnlMain.add(Box.createVerticalStrut(10));
        pnlMain.add(createSeparator());
        
        JPanel pnlFooter = new JPanel(new BorderLayout());
        pnlFooter.setBackground(Color.WHITE);
        pnlFooter.setMaximumSize(new Dimension(CONTENT_WIDTH, 50));
        
        JLabel lblTotalTxt = new JLabel("TỔNG CỘNG:");
        lblTotalTxt.setFont(new Font("Segoe UI", Font.BOLD, 18));
        
        lblTotalVal = new JLabel(); 
        lblTotalVal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotalVal.setForeground(Color.RED);
        
        pnlFooter.add(lblTotalTxt, BorderLayout.WEST);
        pnlFooter.add(lblTotalVal, BorderLayout.EAST);
        
        pnlMain.add(pnlFooter);
        pnlMain.add(Box.createVerticalStrut(20));
        
        JLabel lblThanks = new JLabel("Cảm ơn Quý khách!");
        lblThanks.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblThanks.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlMain.add(lblThanks);

        add(pnlMain);
        loadCTPN();
    }

    private void addGridBagLabel(JPanel pnl, String txt, int gridx, double weightx, int align, int style) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.weightx = weightx;
        gbc.fill = GridBagConstraints.BOTH; 
        gbc.insets = new Insets(0, 0, 0, 0);

      
        int width;
        switch (gridx) {
            case 0: width = 180; break; 
            case 1: width = 40;  break; 
            case 2: width = 100; break;
            case 3: width = 100; break; 
            default: width = 100;
        }

     
        String formattedTxt = "<html><div style='width: " + width + "px;'>" + txt + "</div></html>";
        
        JLabel lbl = new JLabel(formattedTxt, align);
        lbl.setFont(new Font("Segoe UI", style, 12));
        
      
        lbl.setPreferredSize(new Dimension(width, lbl.getPreferredSize().height));
        lbl.setMinimumSize(new Dimension(width, 20));

        pnl.add(lbl, gbc);
    }

    private void addInfoRow(JPanel pnl, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setHorizontalAlignment(SwingConstants.RIGHT);
        pnl.add(lbl); pnl.add(val);
    }

    private JComponent createSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(CONTENT_WIDTH, 10));
        sep.setForeground(new Color(200, 200, 200));
        return sep;
    }
    
    public void loadCTPN() {
        ChiTietPhieuNhap_DAO dao = new ChiTietPhieuNhap_DAO();
        dsCTPN = dao.getChiTietTheoMa(pN.getMaPN());
        for (ChiTietPhieuNhap ct : dsCTPN) {
            pnlTable.add(createRow(ct));
        }
     
        lblTotalVal.setText(String.format("%,.0f đ", pN.getTongTien()));
        pnlTable.revalidate();
        pnlTable.repaint();
    }
    
    private JPanel createRow(ChiTietPhieuNhap ct) {
        JPanel pnlRow = new JPanel(new GridBagLayout());
        pnlRow.setBackground(Color.WHITE);
        pnlRow.setBorder(new EmptyBorder(10, 10, 10, 10));
        pnlRow.setPreferredSize(new Dimension(CONTENT_WIDTH, 40));
        pnlRow.setMaximumSize(new Dimension(CONTENT_WIDTH, 40));

        addGridBagLabel(pnlRow, ct.getsP().getTenSP(), 0, 0.5, SwingConstants.LEFT, Font.PLAIN);
        addGridBagLabel(pnlRow, String.valueOf(ct.getSoLuong()), 1, 0.1, SwingConstants.CENTER, Font.PLAIN);
        addGridBagLabel(pnlRow, String.format("%,.0f đ", ct.getsP().getGiaNhap()), 2, 0.2, SwingConstants.RIGHT, Font.PLAIN);
        addGridBagLabel(pnlRow, String.format("%,.0f đ", ct.getThanhTien()), 3, 0.2, SwingConstants.RIGHT, Font.BOLD);

        return pnlRow;
    }
}