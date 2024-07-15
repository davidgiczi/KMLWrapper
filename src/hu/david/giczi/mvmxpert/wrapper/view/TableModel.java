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
                        inputPoint.getFormattedXForWGS84(),
                        inputPoint.getFormattedYForWGS84(),
                        inputPoint.getFormattedZForWGS84(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {

                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedXForIUGG67(),
                        inputPoint.getFormattedYForIUGG67(),
                        inputPoint.getFormattedZForIUGG67(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {

                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedDecimalFiForIUGG67(),
                        inputPoint.getFormattedDecimalLambdaForIUGG67(),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getFi_IUGG67()),
                        inputPoint.convertAngleMinSecFormat(inputPoint.getLambda_IUGG67()),
                        inputPoint.getFormattedHForIUGG67(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ) {
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if (!inputPoint.isWGS()) {
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
            else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
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
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                if( inputPoint.isWGS() ){
                    continue;
                }
                displayedData.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedXForWGS84(),
                        inputPoint.getFormattedYForWGS84(),
                        inputPoint.getFormattedZForWGS84(), true};
                addRow(row);
            }
        }

    }

    private void setColumNames(){
        String[] columNames = null;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
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
                pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( !inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
                if( !inputPoint.isWGS() )
                    pcs++;
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){

        }

        return pcs;
    }

    private int getLastIndexOfRow(){
        int lastIndex = 0;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            lastIndex = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            lastIndex = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            lastIndex = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[8]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){

        }

        return lastIndex;
    }

}
