package hu.david.giczi.mvmxpert.wrapper.view;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Deviation;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.domain.TransformationParam;
import hu.david.giczi.mvmxpert.wrapper.service.ToEOV;
import hu.david.giczi.mvmxpert.wrapper.service.ToWGS;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableModel extends DefaultTableModel {

    private final String dataType;
    public List<Point> displayedPointList;
    public TransformationParam toEOVParams;
    public TransformationParam toWGSParams;
    public List<Deviation> deviationListForEOV;
    public List<Deviation> deviationListForWGS;
    private boolean isAddedSave;


    public TableModel(String dataType) {
       this.dataType = dataType;
       setColumNames();
       addInputData();
    }

    public void setSaveInputPoint() {
        if (isAddedSave) {
            return;
        }
        if ( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ) {
            for (int row = 0; row < getTableRowsNumber(); row++) {
                boolean isSaved = (boolean) getValueAt(row, getTableColsNumber());
                deviationListForWGS.get(row).setSave(isSaved);
            }
        }
        else if ( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ) {
            for (int row = 0; row < getTableRowsNumber(); row++) {
                boolean isSaved = (boolean) getValueAt(row, getTableColsNumber());
                deviationListForEOV.get(row).setSave(isSaved);
            }
        }
        else if ( !displayedPointList.isEmpty()) {
                for (int row = 0; row < getTableRowsNumber(); row++) {
                    boolean isSaved = (boolean) getValueAt(row, getTableColsNumber());
                    displayedPointList.get(row).setLeftOut(isSaved);
                }
        }
        else if (toWGSParams != null) {
                toWGSParams.setSave((boolean) getValueAt(0, getTableColsNumber()));
        }
        else if (toEOVParams != null) {
                toEOVParams.setSave((boolean) getValueAt(0, getTableColsNumber()));
            }

            isAddedSave = true;
        }

    public int getHowManyInputPointSaved() {
        int pcs = 0;

        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            for (Deviation deviation : deviationListForWGS) {
                if( deviation.isSave() ){
                    pcs++;
                }
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            for (Deviation deviation : deviationListForEOV) {
                if( deviation.isSave() ){
                    pcs++;
                }
            }
        }
        else if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6])){
            pcs = KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.usedForCalcPointList.size();
        }
       else if( !displayedPointList.isEmpty() ) {
            for (Point displayedPoint : displayedPointList) {
                if (displayedPoint.isLeftOut()) {
                    pcs++;
                }
            }
        }
        else if( toWGSParams != null ){
            pcs = toWGSParams.isSave() ? 1 : 0;
        }
        else if( toEOVParams != null ){
           pcs = toEOVParams.isSave() ? 1 : 0;
        }

        return pcs;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
       int index = getTableColsNumber();
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
            if( displayedPointList.isEmpty() ){
             throw new IllegalArgumentException("Hozzáadott WGS84 pont nem található");
         }

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
            if( displayedPointList.isEmpty() ){
                throw new IllegalArgumentException("Hozzáadott EOV pont nem található");
            }

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
            if(  displayedPointList.isEmpty() ){
                throw new IllegalArgumentException("Hozzáadott EOV pont nem található");
            }

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

            if( KMLWrapperController.TRANSFORMATION.toWGS == null ){
                throw new IllegalArgumentException("Hozzáadott EOV pont nem található");
            }
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

            if( KMLWrapperController.TRANSFORMATION.toEOV == null ){
                throw new IllegalArgumentException("Hozzáadott WGS84 pont nem található");
            }

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
            if( KMLWrapperController.TRANSFORMATION.toWGS == null ){
                throw new IllegalArgumentException("Hozzáadott EOV pont nem található");
            }
            setCommonPointsDisplayedData();
            deviationListForWGS = new ArrayList<>();
            for (Point commonPoint : displayedPointList) {
               new ToWGS(commonPoint.getX_IUGG67(),
                        commonPoint.getY_IUGG67(),
                        commonPoint.getZ_IUGG67(),
                        KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS);
                        Deviation deviationForWGS = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getX_WGS84(),
                        commonPoint.getY_WGS84(),
                        commonPoint.getZ_WGS84(),
                        ToWGS.X_WGS84,
                        ToWGS.Y_WGS84,
                        ToWGS.Z_WGS84);
                deviationListForWGS.add(deviationForWGS);
                Object[] row = new Object[]{
                        deviationForWGS.getPointId(),
                        deviationForWGS.getXDeviation(),
                        deviationForWGS.getYDeviation(),
                        deviationForWGS.getZDeviation(),
                        !commonPoint.isLeftOut()};
                    addRow(row);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            if( KMLWrapperController.TRANSFORMATION.toEOV == null ){
                throw new IllegalArgumentException("Hozzáadott WGS84 pont nem található");
            }
            setCommonPointsDisplayedData();
            deviationListForEOV = new ArrayList<>();
            for (Point commonPoint : displayedPointList) {

               new ToEOV(commonPoint.getX_WGS84(),
                         commonPoint.getY_WGS84(),
                         commonPoint.getZ_WGS84(),
                         KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS);
                        Deviation  deviationForEOV = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getY_EOV(),
                        commonPoint.getX_EOV(),
                        commonPoint.getM_EOV(),
                        ToEOV.Y_EOV,
                        ToEOV.X_EOV,
                        ToEOV.M_EOV);
                Object[] row = new Object[]{
                        deviationForEOV.getPointId(),
                        deviationForEOV.getXDeviation(),
                        deviationForEOV.getYDeviation(),
                        deviationForEOV.getZDeviation(),
                        !commonPoint.isLeftOut()};
                deviationListForEOV.add(deviationForEOV);
                addRow(row);
            }
        }
        else if(  Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) > 0  &&
                6 > Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) ){


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
        } else if ( dataType.startsWith("AutoCad")) {
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedYForEOV(),
                        inputPoint.getFormattedXForEOV(),
                        inputPoint.getFormattedMForEOV(), true};
                addRow(row);
            }
        }
        else if ( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ) {
            for ( Point inputPoint : KMLWrapperController.INPUT_POINTS ) {
                displayedPointList.add(inputPoint);
                Object[] row = new Object[]{inputPoint.getPointId(),
                        inputPoint.getFormattedYForEOV(),
                        inputPoint.getFormattedXForEOV(),
                        inputPoint.getFormattedMForEOV(), true};
                addRow(row);
            }
        }

    }

    public void setCommonPointsDeviationData(){
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            if( KMLWrapperController.TRANSFORMATION.toWGS == null ){
                throw new IllegalArgumentException("Hozzáadott EOV pont nem található");
            }
            setCommonPointsDisplayedData();
            deviationListForWGS = new ArrayList<>();
            for (Point commonPoint : displayedPointList) {
                new ToWGS(commonPoint.getX_IUGG67(),
                        commonPoint.getY_IUGG67(),
                        commonPoint.getZ_IUGG67(),
                        KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS);
                Deviation deviationForWGS = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getX_WGS84(),
                        commonPoint.getY_WGS84(),
                        commonPoint.getZ_WGS84(),
                        ToWGS.X_WGS84,
                        ToWGS.Y_WGS84,
                        ToWGS.Z_WGS84);
                deviationListForWGS.add(deviationForWGS);
                Object[] row = new Object[]{
                        deviationForWGS.getPointId(),
                        deviationForWGS.getXDeviation(),
                        deviationForWGS.getYDeviation(),
                        deviationForWGS.getZDeviation(),
                        !commonPoint.isLeftOut()};
                for (int i = 0; i < row.length; i++) {
                    setValueAt(row[i], displayedPointList.indexOf(commonPoint), i);
                }
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ) {
            if (KMLWrapperController.TRANSFORMATION.toEOV == null) {
                throw new IllegalArgumentException("Hozzáadott WGS84 pont nem található");
            }
            setCommonPointsDisplayedData();
            deviationListForEOV = new ArrayList<>();
            for (Point commonPoint : displayedPointList) {

                new ToEOV(commonPoint.getX_WGS84(),
                        commonPoint.getY_WGS84(),
                        commonPoint.getZ_WGS84(),
                        KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS);
                Deviation deviationForEOV = new Deviation(
                        commonPoint.getPointId(),
                        commonPoint.getY_EOV(),
                        commonPoint.getX_EOV(),
                        commonPoint.getM_EOV(),
                        ToEOV.Y_EOV,
                        ToEOV.X_EOV,
                        ToEOV.M_EOV);
                Object[] row = new Object[]{
                        deviationForEOV.getPointId(),
                        deviationForEOV.getXDeviation(),
                        deviationForEOV.getYDeviation(),
                        deviationForEOV.getZDeviation(),
                        !commonPoint.isLeftOut()};
                deviationListForEOV.add(deviationForEOV);
                for (int i = 0; i < row.length; i++) {
                    setValueAt(row[i], displayedPointList.indexOf(commonPoint), i);
                }
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
                    "[fok perc mperc]" , "[fok perc mperc]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[3]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[4]) ){
            columNames = new String[]{"Pontszám", "X", "Y", "Z", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[5]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[fok perc mperc]" , "[fok perc mperc]", "h", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[6]) ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[7]) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[fok perc mperc]" , "[fok perc mperc]", "h", "Ment"};
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
                    "[fok perc mperc]" , "[fok perc mperc]", "h", "Ment"};
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
            columNames = new String[]{"Pontszám", "dX", "dY", "dZ", "Számol és ment"};
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            columNames = new String[]{"Pontszám", "dY", "dX", "dM", "Számol és ment"};
        }
        else if( Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) > 0  &&
                6 > Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) ){
            columNames = new String[]{"Pontszám", "Szélesség", "Hosszúság",
                    "[fok perc mperc]" , "[fok perc mperc]", "h", "Ment"};
        }
        else if( dataType.startsWith("AutoCad") ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Ment"};
        }
        else if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            columNames = new String[]{"Pontszám", "Y", "X", "M", "Használ"};
        }

        if( columNames == null ){
            return;
        }
        setColumnIdentifiers(columNames);
    }

    private void setCommonPointsDisplayedData(){
        displayedPointList.clear();
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[9]) ||
                dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){

            for (Point wgsToEovReferencePoint : KMLWrapperController.TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS) {
                if( wgsToEovReferencePoint == null ){
                    continue;
                }
                displayedPointList.add(wgsToEovReferencePoint);
            }
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ||
                dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ||
                dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            for (Point eovToWgsReferencePoint : KMLWrapperController.TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS) {
                if( eovToWgsReferencePoint == null ){
                    continue;
                }
                displayedPointList.add(eovToWgsReferencePoint);
            }
        }

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
            setCommonPointsDisplayedData();
            pcs = displayedPointList.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[10]) ){
            setCommonPointsDisplayedData();
            pcs = displayedPointList.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[11]) ){
            setCommonPointsDisplayedData();
            pcs = displayedPointList.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[12]) ){
            pcs = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[13]) ){
            pcs = 1;
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14]) ){
            setCommonPointsDisplayedData();
            pcs = displayedPointList.size();
        }
        else if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15]) ){
            setCommonPointsDisplayedData();
            pcs = displayedPointList.size();
        }
        else if( Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) > 0  &&
                6 > Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        else if( dataType.startsWith("AutoCad") ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        else if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            pcs = KMLWrapperController.INPUT_POINTS.size();
        }
        return pcs;
    }

    public int getTableColsNumber(){
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
        else if( Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) > 0  &&
                6 > Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(dataType) ){
            lastIndex = 6;
        }
        else if( dataType.startsWith("AutoCad") ){
            lastIndex = 4;
        }
        else if( dataType.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            lastIndex = 4;
        }


        return lastIndex;
    }

}
