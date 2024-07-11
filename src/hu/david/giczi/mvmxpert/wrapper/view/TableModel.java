package hu.david.giczi.mvmxpert.wrapper.view;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends DefaultTableModel {

    private final String dataType;
    public List<Point> displayedData;


    public TableModel(String dataType) {
       this.dataType = dataType;
       setColumNames();
       addInputData();
    }

    public void setSaveInputPoint(){
        for(int row = 0; row < getTableRowsNumber(); row++){
           boolean isSave = (boolean) getValueAt(row, getLastIndexOfRow());
            displayedData.get(row).setSave(isSave);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
       int index = getLastIndexOfRow();
       if( index > 1 && columnIndex == index ) {
           return Boolean.class;
       }
        return String.class;
    }

    private void addInputData(){
        displayedData = new ArrayList<>();
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                if( inputPoint.isWGS() ){
                    continue;
                }
                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedYForEOV(),
                        inputPoint.getFormattedXForEOV(),
                        inputPoint.getFormattedMForEOV(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                if( !inputPoint.isWGS() ){
                    continue;
                }
                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedDecimalFiForWGS84(),
                        inputPoint.getFormattedDecimalLambdaForWGS84(),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getFi_WGS84()),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getLambda_WGS84()),
                        inputPoint.getFormattedHForWGS84(), true};
                addRow(row);
            }
        }
        else  if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                if( !inputPoint.isWGS() ){
                    continue;
                }
                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedYForEOV(),
                        inputPoint.getFormattedXForEOV(),
                        inputPoint.getFormattedMForEOV(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                if( inputPoint.isWGS() ){
                    continue;
                }
                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedDecimalFiForWGS84(),
                        inputPoint.getFormattedDecimalLambdaForWGS84(),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getFi_WGS84()),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getLambda_WGS84()),
                        inputPoint.getFormattedHForWGS84(), true};
                addRow(row);
            }
        }

    }

    private void setColumNames(){
        String[] columNames = null;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            columNames = new String[]{"Pontszám", "Y_EOV", "X_EOV", "M_EOV", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            columNames = new String[]{"Pontszám", "Y_EOV", "X_EOV", "M_EOV", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }

        if( columNames == null ){
            return;
        }
        setColumnIdentifiers(columNames);
    }

    public int getTableRowsNumber(){
        int pcs = 0;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( !inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( inputPoint.isWGS() )
                    pcs++;
            }
        }

        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( !inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toEOV.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toEOV.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toEOV.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toWGS.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toWGS.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toEOV.PARAM_FOR_EOV.length;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toWGS.PARAM_FOR_WGS.length;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toWGS.referencePoints.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            pcs = KMLWrapperController.TRANSFORMATION.toWGS.referencePoints.size();
        }

        return pcs;
    }

    private int getLastIndexOfRow(){
        int lengthOfRow = 0;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            lengthOfRow = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            lengthOfRow = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            lengthOfRow = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            lengthOfRow = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            lengthOfRow = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            lengthOfRow = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            lengthOfRow = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            lengthOfRow = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){
            lengthOfRow = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            lengthOfRow = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            lengthOfRow = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            lengthOfRow = 3;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            lengthOfRow = 3;
        }

        return lengthOfRow;
    }

}
