package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class DataDisplayerWindow {

    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    public DataDisplayerWindow(String dataType) {
    openWindow(dataType);
    }

    private void openWindow(String dataType){
        JFrame jFrame = new JFrame(dataType);
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
        JTable jTable = new JTable(getData(dataType), getColumnsTitle(dataType));
        jTable.getTableHeader().setFont(boldFont);
        jTable.setRowHeight(30);
        jTable.setFont(plainFont);
        JScrollPane scrollPane = new JScrollPane(jTable);
        jFrame.add(scrollPane);
        jFrame.setSize(950,310);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private String[] getColumnsTitle(String dataType){

        String[] title = null;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
           title = new String[4];
           title[0] = "Pontszám";
           title[1] = "Y_EOV";
           title[2] = "X_EOV";
           title[3] = "M_Balti";
        }


        return title;
    }
    private String[][] getData(String dataType){
        String[][] data = null;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){

            for (int i = 0; i < KMLWrapperController.INPUT_POINTS.size(); i++) {
                if( !KMLWrapperController.INPUT_POINTS.get(i).isWGS() ){
                    data = new String[getDataRowNumberValue()][4];
                    data[i][0] = KMLWrapperController.INPUT_POINTS.get(i).getPointId();
                    data[i][1] = KMLWrapperController.INPUT_POINTS.get(i).getY_EOV().toString();
                    data[i][2] = KMLWrapperController.INPUT_POINTS.get(i).getX_EOV().toString();
                    data[i][3] = KMLWrapperController.INPUT_POINTS.get(i).getM_EOV().toString();
                }
            }
        }

        return data;
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
