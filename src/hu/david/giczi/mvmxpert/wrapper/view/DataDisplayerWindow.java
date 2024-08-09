package hu.david.giczi.mvmxpert.wrapper.view;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.CalcData;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class DataDisplayerWindow {

    public JFrame jFrame;
    private TableModel tableModel;
    private final Font plainFont = new Font("Roboto", Font.PLAIN, 16);
    private final Font boldFont = new Font("Roboto", Font.BOLD, 17);
    public List<Point> usedForCalcPointList;
    public DataDisplayerWindow(String dataType) {
    displayData(dataType);
    }


    private void displayData(String dataType){
        if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            getCalcDataMessagePane();
        }
        tableModel = new TableModel(dataType);
         if( tableModel.displayedPointList.isEmpty() &&
                 tableModel.toEOVParams == null && tableModel.toWGSParams == null &&
                    tableModel.deviationListForEOV ==  null && tableModel.deviationListForWGS == null){
            throw new IllegalArgumentException("Nem található adat");
        }
        jFrame = new JFrame(dataType + " " + tableModel.getTableRowsNumber() + " db pont");
        jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        jFrame.setIconImage(Toolkit.getDefaultToolkit()
                .getImage(getClass().getResource("/logo/MVM.jpg")));
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                tableModel.setSaveInputPoint();
                KMLWrapperController.INPUT_DATA_FILE_WINDOW.saveBtn.setEnabled(true);
            }
        });
        JTable table = new JTable(tableModel);
        table.getTableHeader().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int col = table.columnAtPoint(e.getPoint());
                String headerName = table.getColumnName(col);
                if( e.getClickCount() == 2 && "Használ".equals(headerName)){
                    for (int row = 0; row < tableModel.getTableRowsNumber(); row++) {
                        tableModel.setValueAt(false, row, tableModel.getTableColsNumber());
                    }
                    usedForCalcPointList.clear();
                }
                else if( e.getClickCount() == 2 && "Ment".equals(headerName)){
                    for (int row = 0; row < tableModel.getTableRowsNumber(); row++) {
                        boolean saveValue = (boolean) tableModel.getValueAt(row, tableModel.getTableColsNumber());
                        tableModel.setValueAt( !saveValue, row, tableModel.getTableColsNumber() );
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                int col = table.columnAtPoint(e.getPoint());
                if( col == tableModel.getTableColsNumber() ){
                    table.getTableHeader().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else {
                    table.getTableHeader().setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6])){
                    int row = table.getSelectedRow();
                    int col = table.getSelectedColumn();
                    if( col == tableModel.getTableColsNumber() ){
                        String pointId = tableModel.getValueAt(row, 0).toString();
                        if( (boolean) tableModel.getValueAt(row, col) ){
                            addPointIntoCalcPointList(pointId);
                        }
                        else {
                            removePointFromCalcPointList(pointId);
                        }
                        getCalcDataMessagePane();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                int col = table.columnAtPoint(e.getPoint());
                if( col == tableModel.getTableColsNumber() ){
                    table.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                else{
                    table.setCursor(Cursor.getDefaultCursor());
                }
            }
        });
        table.setRowHeight(30);
        table.setFont(plainFont);
        table.getTableHeader().setFont(boldFont);
       DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, tableCellRenderer);
        jFrame.add(new JScrollPane(table));
        jFrame.setSize(950,  tableModel.getTableRowsNumber() < 10 ?
                70 + tableModel.getTableRowsNumber() * table.getRowHeight() :
                70 + 10 * table.getRowHeight());
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
    public TableModel getTableModel() {
        return tableModel;
    }

    public void getCalcDataMessagePane(){
        if( usedForCalcPointList == null ){
            usedForCalcPointList = new ArrayList<>(KMLWrapperController.INPUT_POINTS);
        }
        CalcData calc = new CalcData(usedForCalcPointList);
        MessagePane.getInfoMessage("Terület és távolság adatok",
                "Felhasznált pontok:<br>" + calc.getUsedPointId() + "<br><br>" +
                        "Távolság: " + calc.calcDistance() + " m<br>" +
                        "Terület: " + calc.calcArea() + " m2<br>" +
                        "Kerület: " + calc.calcPerimeter() + " m<br>" +
                        "Magasságkülönbség: " + calc.calcElevation() + " m",
                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
    }
    private void removePointFromCalcPointList(String pointId){
        usedForCalcPointList.removeIf(point -> point.getPointId().equals(pointId));
    }
    private void addPointIntoCalcPointList(String pointId){
        for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
            if( inputPoint.getPointId().equals(pointId) ){
                usedForCalcPointList.add(inputPoint);
            }
        }
    }
}
