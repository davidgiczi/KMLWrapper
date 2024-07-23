package hu.david.giczi.mvmxpert.wrapper.view;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DataDisplayerWindow {

    private TableModel tableModel;
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    public DataDisplayerWindow(String dataType) {
    displayData(dataType);
    }


    private void displayData(String dataType){
        tableModel = new TableModel(dataType);
        if( tableModel.displayedPointList.isEmpty() ){
            throw new IllegalArgumentException("Nem található adat");
        }
        JFrame jFrame = new JFrame(dataType + " " + tableModel.getTableRowsNumber() + " db pont");
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                tableModel.setSaveInputPoint();
            }
        });
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setFont(plainFont);
        table.getTableHeader().setFont(boldFont);
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, tableCellRenderer);
        jFrame.add(new JScrollPane(table));
        jFrame.setSize(950,  tableModel.getTableRowsNumber() < 10 ?
                80 + tableModel.getTableRowsNumber() * table.getRowHeight() :
                80 + 10 * table.getRowHeight());
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
    public TableModel getTableModel() {
        return tableModel;
    }
}
