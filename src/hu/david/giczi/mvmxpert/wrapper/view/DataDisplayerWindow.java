package hu.david.giczi.mvmxpert.wrapper.view;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import javax.swing.*;
import java.awt.*;

public class DataDisplayerWindow {
    private JFrame jFrame;
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    public DataDisplayerWindow(String dataType) {
    displayData(dataType);
    }


    private void displayData(String dataType){
        jFrame = new JFrame(dataType + " " + " db pont");
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
        jFrame.setLayout(new GridLayout(getRowPcs(dataType), 1));
        jFrame.setSize(950, 60 + getRowPcs(dataType) * 60);
        createRowTitleForEOV(dataType);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    private void createRowTitleForEOV(String dataType){
        JPanel dataPanel = new JPanel();
        dataPanel.setSize(950, 60);
        dataPanel.setBackground(Color.RED);
        jFrame.add(dataPanel);
    }

    private int getRowPcs(String dataType){
        int pcs = 0;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( !inputPoint.isWGS() )
                    pcs++;
            }
        }
        return pcs;
    }
    private int getRowLength(String dataType){
       if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
           return 5;
       }
       return 0;
    }

}
