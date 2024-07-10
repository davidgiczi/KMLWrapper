package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataDisplayerWindow {

    private Object[][] data;
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    public DataDisplayerWindow(String dataType) {
    openWindow(dataType);
    }

    public Object[][] getData() {
        return data;
    }

    private void openWindow(String dataType){
        JFrame jFrame = new JFrame(dataType + " " + getDataRowNumberValue() + " db pont");
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
        getData(dataType);
        JTable jTable = new JTable(data, getColumnsTitle(dataType));
        jTable.getTableHeader().setFont(boldFont);
        jTable.setRowHeight(30);
        jTable.setFont(plainFont);
        jTable.setPreferredScrollableViewportSize(jTable.getPreferredSize());
        setTableAlignment(jTable, dataType);
        JScrollPane scrollPane = new JScrollPane(jTable);
        jFrame.setSize(950,  data.length < 10 ? 80 + jTable.getRowHeight() * data.length :
                80 + jTable.getRowHeight() * 10);
        jFrame.add(scrollPane);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private String[] getColumnsTitle(String dataType){

        String[] title = null;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
           title = new String[5];
           title[0] = "Pontszám";
           title[1] = "Y_EOV";
           title[2] = "X_EOV";
           title[3] = "M_Balti";
           title[4] = "Kihagy";
        }


        return title;
    }
    private void getData(String dataType){
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            data = new Object[getDataRowNumberValue()][5];
            for (int i = 0; i < KMLWrapperController.INPUT_POINTS.size(); i++) {
                if( !KMLWrapperController.INPUT_POINTS.get(i).isWGS() ){
                    data[i][0] = KMLWrapperController.INPUT_POINTS.get(i).getPointId();
                    data[i][1] = KMLWrapperController.INPUT_POINTS.get(i).getFormattedYForEOV();
                    data[i][2] = KMLWrapperController.INPUT_POINTS.get(i).getFormattedXForEOV();
                    data[i][3] = KMLWrapperController.INPUT_POINTS.get(i).getFormattedMForEOV();
                }
            }
        }
    }

    private void setTableAlignment(JTable table, String dataType){
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(JLabel.CENTER);
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1])){
            for (int i = 0; i < 5; i++) {
                table.getColumnModel().getColumn(i).setCellRenderer(tableCellRenderer);
            }
        }
    }

    private int getDataRowNumberValue(){
        int rowNumberValue = 0;
        for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
            if (!inputPoint.isWGS()) {
                rowNumberValue++;
            }
        }
        return rowNumberValue;
    }
}
