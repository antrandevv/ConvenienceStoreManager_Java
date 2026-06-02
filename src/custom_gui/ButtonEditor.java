package custom_gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ButtonEditor extends DefaultCellEditor {
    protected JButton button;
    private JTable table;
    private DeleteAction action;

    // Interface để gửi lệnh về UI xử lý hoàn kho/tính tiền
    public interface DeleteAction {
        void onRemove(int row);
    }

    public ButtonEditor(JCheckBox checkBox, JTable table, DeleteAction action) {
        super(checkBox);
        this.table = table;
        this.action = action;
        
        button = new JButton("Xóa");
        button.setOpaque(true);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lấy dòng đang edit trước khi dừng
                int row = table.getEditingRow();
                
                if (row != -1) {
                    // Ngừng edit để tránh lỗi logic Table
                    fireEditingStopped();
                    
                    // Thực hiện logic hoàn kho & tính tiền ở UI thông qua interface
                    if (action != null) {
                        action.onRemove(row);
                    }
                    
                    // Xóa dòng khỏi model
                    ((DefaultTableModel) table.getModel()).removeRow(row);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        return button;
    }
}