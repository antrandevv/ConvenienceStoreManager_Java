package custom_gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.formdev.flatlaf.ui.FlatLineBorder;

import gui.NhapHang_GUI;

public class CardItemNhapHang_Type1_GUI extends JPanel{
	
	private JLabel lblMaSP;
	private JLabel lblTenSP;
	private JLabel lblGiaSP;
	NhapHang_GUI nhapHang_GUI;
	private Font defaultFont;
	
	public CardItemNhapHang_Type1_GUI(NhapHang_GUI nhapHang_GUI, String maSP, String tenSP, double donGia, String url) {
		
		this.nhapHang_GUI = nhapHang_GUI;
		Dimension sizePnl = new Dimension(150, 200);
		Color BgChange = new Color(225, 240, 255);
		Color BorderChange = new Color(100, 180, 255);
		setPreferredSize(sizePnl);
		setMaximumSize(sizePnl);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(224, 238, 238));
		setRoundBorder(this, new Color(232, 232, 232));
		
		//Anh san pham
		ImageIcon icon = new ImageIcon(url);
		JLabel lblAnhSP = new JLabel(setImg(icon));
		lblAnhSP.setAlignmentX(CENTER_ALIGNMENT);
		lblMaSP = new JLabel(maSP);
		lblMaSP.setFont(new Font("Segoe UI", Font.BOLD, 17));
		lblMaSP.setAlignmentX(CENTER_ALIGNMENT);
		

        Box boxTenVsGia = Box.createVerticalBox();
        boxTenVsGia.setAlignmentX(CENTER_ALIGNMENT); 

        lblTenSP = new JLabel(tenSP);
        lblTenSP.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTenSP.setHorizontalAlignment(SwingConstants.CENTER);
        lblTenSP.setPreferredSize(new Dimension(110, 20));
        lblTenSP.setMaximumSize(new Dimension(110, 20)); 
        defaultFont();
        
        lblGiaSP = new JLabel(formatVND(donGia));
        lblGiaSP.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblGiaSP.setForeground(Color.RED);

      
        lblGiaSP.setMaximumSize(new Dimension(110, 20)); 
        
  
        lblGiaSP.setHorizontalAlignment(SwingConstants.RIGHT); 
       
      
        boxTenVsGia.add(lblTenSP);
        boxTenVsGia.add(Box.createVerticalStrut(5));
        boxTenVsGia.add(lblGiaSP);
        
		
		add(lblAnhSP);
		add(Box.createVerticalStrut(10));
		add(lblMaSP);
		add(boxTenVsGia);
		autoReSize(lblTenSP, 5, defaultFont, 110);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(BgChange);
				setRoundBorder(CardItemNhapHang_Type1_GUI.this, BorderChange);
				setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(224, 238, 238));
				setRoundBorder(CardItemNhapHang_Type1_GUI.this, new Color(207, 207, 207));
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				nhapHang_GUI.themSanPhamVaoChiTiet(maSP, tenSP, donGia, url);
			}
		});
	}
	
	public ImageIcon setImg(ImageIcon icon) {
		
		Image img = icon.getImage();
		Image editImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon IconEdited = new ImageIcon(editImg);
		return IconEdited;
	}
	
	public void setRoundBorder(JPanel pnl, Color clr) {

		pnl.setBorder(new FlatLineBorder(new Insets(20, 20, 20, 20), clr, 1, 15));
	}
	
	public String formatVND(double tien) {

		DecimalFormat fm = new DecimalFormat("#,### đ");
		return fm.format(tien);
	}

	public void defaultFont() {
		
		defaultFont = lblTenSP.getFont();
	}
	
	public void autoReSize(JLabel lbl, int minSize, Font originalFont, int maxWidth) {
	    Font fnt = originalFont;
	    int size = fnt.getSize();
	    FontMetrics fm = lbl.getFontMetrics(fnt);

	    while (size > minSize && fm.stringWidth(lbl.getText()) > maxWidth) {
	        size--;
	        fnt = new Font(fnt.getName(), fnt.getStyle(), size);
	        fm = lbl.getFontMetrics(fnt);
	    }
	    lbl.setFont(fnt);
	}
}
