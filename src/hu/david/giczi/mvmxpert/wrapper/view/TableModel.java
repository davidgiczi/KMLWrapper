package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Deviation;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.domain.TransformationParam;
import hu.david.giczi.mvmxpert.wrapper.service.ToEOV;
import hu.david.giczi.mvmxpert.wrapper.service.ToWGS;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TableModel extends DefaultTableModel {

    private final String dataType;
    public List<Point> displayedPointList;
    public TransformationParam toEOVParams;
    public TransformationParam toWGSParams;
    public List<Deviation> commonPointsDeviationList;


    public TableModel(String dataType) {
       this.dataType = dataType;
       setColumNames();
       addInputData();
    }

    public void setSaveInputPoint(){
        if( !displayedPointList.isEmpty() ) {
            for (int row = 0; row < getTableRowsNumber(); row++) {
                boolean isSave = (boolean) getValueAt(row, getLastIndexOfRow());
                displayedPointList.get(row).setSave(isSave);
            }
        }
        else if( toWGSParams != null ){
            toWGSParams.setSave((boolean) getValueAt(0, getLastIndexOfRow()));
        }
        else if( toEOVParams != null ){
            toEOVParams.setSave((boolean) getValueAt(0, getLastIndexOfRow()));
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
        displayedPointList = new ArrayList<>();
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedYForEOV(),
                        inputPoint.getFormattedXForEOV(),
                        inputPoint.getFormattedMForEOV(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
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
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedXForWGS84(),
                        inputPoint.getFormattedYForWGS84(),
                        inputPoint.getFormattedZForWGS84(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            for (Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedXForIUGG67(),
                        inputPoint.getFormattedYForIUGG67(),
                        inputPoint.getFormattedZForIUGG67(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
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
                displayedPointList.add(inputPoint);
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
                    displayedPointList.add(inputPoint);
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
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedXForWGS84(),
                        inputPoint.getFormattedYForWGS84(),
                        inputPoint.getFormattedZForWGS84(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){

            setCommonPointsDisplayedData();

            for (Point displayedPoint : displayedPointList) {
                        Object[] row = new Object[]{displayedPoint.getPointId(),
                        displayedPoint.getFormattedYForEOV(),
                        displayedPoint.getFormattedXForEOV(),
                        displayedPoint.getFormattedMForEOV(), true};
                addRow(row);
            }

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){

            setCommonPointsDisplayedData();

            for (Point displayedPoint : displayedPointList) {
                Object[] row = new Object[]{displayedPoint.getPointId(),
                        displayedPoint.getFormattedXForWGS84(),
                        displayedPoint.getFormattedYForWGS84(),
                        displayedPoint.getFormattedZForWGS84(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){

            setCommonPointsDisplayedData();

            for (Point displayedPoint : displayedPointList) {
                Object[] row = new Object[]{displayedPoint.getPointId(),
                        displayedPoint.getFormattedDecimalFiForWGS84(),
                        displayedPoint.getFormattedDecimalLambdaForWGS84(),
                        displayedPoint.convertAngleMinSecFormat(displayedPoint.getFi_WGS84()),
                        displayedPoint.convertAngleMinSecFormat(displayedPoint.getLambda_WGS84()),
                        displayedPoint.getFormattedHForWGS84(), true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){

                    toWGSParams =
                            new TransformationParam(KMLWrapperController.TRANSFORMATION.toWGS.PARAM_FOR_WGS);
                    Object[] row = new Object[]{
                            toWGSParams.getDeltaXParam(),
                            toWGSParams.getDeltaYParam(),
                            toWGSParams.getDeltaZParam(),
                            toWGSParams.getScaleParam(),
                            toWGSParams.getRotationXParam(),
                            toWGSParams.getRotationYParam(),
                            toWGSParams.getRotationZParam(), true};
                    addRow(row);
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){

                toEOVParams =
                        new TransformationParam(KMLWrapperController.TRANSFORMATION.toEOV.PARAM_FOR_EOV);
                Object[] row = new Object[]{
                        toEOVParams.getDeltaXParam(),
                        toEOVParams.getDeltaYParam(),
                        toEOVParams.getDeltaZParam(),
                        toEOVParams.getScaleParam(),
                        toEOVParams.getRotationXParam(),
                        toEOVParams.getRotationYParam(),
                        toEOVParams.getRotationZParam(), true};
                addRow(row);

        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            commonPointsDeviationList = new ArrayList<>();
                setCommonPointsDisplayedData();
            for (Point commonPoint : displayedPointList) {
                if( !KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS.contains(commonPoint) ){
                    continue;
                }
               new ToEOV(commonPoint.getX_WGS84(),
                        commonPoint.getY_WGS84(),
                        commonPoint.getZ_WGS84(),
                        KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS);
                Deviation deviation = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getY_EOV(),
                        commonPoint.getX_EOV(),
                        commonPoint.getM_EOV(),
                        ToEOV.Y_EOV,
                        ToEOV.X_EOV,
                        ToEOV.M_EOV);
                commonPointsDeviationList.add(deviation);
                Object[] row = new Object[]{
                        deviation.getPointId(),
                        deviation.getXDeviation(),
                        deviation.getYDeviation(),
                        deviation.getZDeviation(),
                        true};
                addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            commonPointsDeviationList = new ArrayList<>();
            setCommonPointsDisplayedData();
            for (Point commonPoint : displayedPointList) {
               if( !KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS.contains(commonPoint) ){
                   continue;
               }
               new ToWGS(commonPoint.getX_IUGG67(),
                         commonPoint.getY_IUGG67(),
                         commonPoint.getZ_IUGG67(),
                         KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS);
                Deviation deviation = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getX_WGS84(),
                        commonPoint.getY_WGS84(),
                        commonPoint.getZ_WGS84(),
                        ToWGS.X_WGS84,
                        ToWGS.Y_WGS84,
                        ToWGS.Z_WGS84);
                commonPointsDeviationList.add(deviation);
                Object[] row = new Object[]{
                        deviation.getPointId(),
                        deviation.getXDeviation(),
                        deviation.getYDeviation(),
                        deviation.getZDeviation(),
                        true};
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
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[° ' \"]" , "[° ' \"]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            columNames = new String[]{"X elt. [m]", "Y elt. [m]", "Z elt. [m]",
                    "Méretarány" , "X forgatás", "Y forgatás", "Z forgatás", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            columNames = new String[]{"X elt. [m]", "Y elt. [m]", "Z elt. [m]",
                    "Méretarány" , "X forgatás", "Y forgatás", "Z forgatás", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            columNames = new String[]{"Pontszám", "dX", "dY", "dZ", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            columNames = new String[]{"Pontszám", "dX", "dY", "dZ", "Ment"};
        }

        if( columNames == null ){
            return;
        }
        setColumnIdentifiers(columNames);
    }

    private int setCommonPointsDisplayedData(){

        if( !displayedPointList.isEmpty() ){
            return displayedPointList.size();
        }

        for (Point eovToWgsReferencePoint : KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS) {
            if( eovToWgsReferencePoint == null ){
                continue;
            }
           displayedPointList.add(eovToWgsReferencePoint);
        }

        for (Point wgsToEovReferencePoint : KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS) {
            if( wgsToEovReferencePoint == null || displayedPointList.contains(wgsToEovReferencePoint)){
                continue;
            }
            displayedPointList.add(wgsToEovReferencePoint);
        }

        return displayedPointList.size();
    }

    public int getTableRowsNumber(){
        int pcs = 0;
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[1]) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[2]) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }

        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
           pcs = KMLWrapperController.INPUT_POINTS.size();
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
            pcs = setCommonPointsDisplayedData();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            pcs = setCommonPointsDisplayedData();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            pcs = setCommonPointsDisplayedData();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            pcs = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            pcs = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            pcs = 8;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            pcs = 8;
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
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            lastIndex = 6;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            lastIndex = 7;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            lastIndex = 7;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            lastIndex = 4;
        }

        return lastIndex;
    }

}
