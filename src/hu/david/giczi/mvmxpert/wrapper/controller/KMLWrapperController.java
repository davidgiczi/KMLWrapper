package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import hu.david.giczi.mvmxpert.wrapper.service.Validation;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.prefs.InvalidPreferencesFormatException;


public class KMLWrapperController {

    public static InputDataFileWindow INPUT_DATA_FILE_WINDOW;
    public static ManuallyInputDataWindow MANUALLY_INPUT_DATA_WINDOW;
    public FileProcess fileProcess;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;


    public KMLWrapperController() {
        this.fileProcess = new FileProcess();
        INPUT_DATA_FILE_WINDOW = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        FileProcess.getReferencePoints();
    }

    public void openInputDataFile(){
        if(INPUT_DATA_FILE_WINDOW.EOV_DATA_TYPE[0].equals(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem())){
            MessagePane.getInfoMessage("A fájl nem nyitható meg",
                    "Formátum választása szükséges.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        fileProcess.openInputDataFile();
        if( FileProcess.FILE_NAME == null ){
            return;
        }
        if( !INPUT_POINTS.isEmpty() ) {
            if (MessagePane.getYesNoOptionMessage("Beolvasott pontok lista nem üres",
                    "Törli a korábban beolvasott pontokat?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                INPUT_POINTS.clear();
            }
        }
        validationInputData();
        openAutoCadListDataFile();
        setWindowTitle();
    }

    private void validationInputData(){
        String selectedFormat = Objects.requireNonNull(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem()).toString();
       if( selectedFormat.startsWith("AutoCad") ){
           return;
       }
        try {
            new Validation(selectedFormat);
        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    private void openAutoCadListDataFile(){
        String selectedFormat = Objects.requireNonNull(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem()).toString();
        if( !selectedFormat.startsWith("AutoCad") ){
            return;
        }
        List<String> autoCadInputDataList = fileProcess.getAutoCadInputData();
        if( autoCadInputDataList.isEmpty() ){
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban",
                    "AutoCad lista adatok nem olvashatók.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        for (String autoCadData : autoCadInputDataList) {
            String[] autoCadInputData = autoCadData.split(",");
            try {
                Validation.isValidInputDataForEOV(null, autoCadInputData[0], autoCadInputData[1], autoCadInputData[2]);
            }
            catch (InvalidPreferencesFormatException e){
                MessagePane.getInfoMessage("Hibás EOV koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
                break;
            }
        }
    }

    public void validationManuallyInputDataForEOV(){
        try{

            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForEOV.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_EOV_field.getText();
            Validation.isValidInputDataForEOV(pointId.isEmpty() ? null : pointId,
                    MANUALLY_INPUT_DATA_WINDOW.y_EOV_field.getText(),
                    MANUALLY_INPUT_DATA_WINDOW.x_EOV_field.getText(), h.isEmpty() ? "0.0" : h);

        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás EOV koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84DecimalFormat(){
        try{
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84DecimalFormat.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84DecimalFormat(pointId.isEmpty() ? null : pointId,
                    MANUALLY_INPUT_DATA_WINDOW.fi_WGS84_field.getText(),
                    MANUALLY_INPUT_DATA_WINDOW.lambda_WGS84_field.getText(), h.isEmpty() ? "0.0" : h);

        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84AngleMinSecFormat(){
        try{

            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84AngleMinSecFormat.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_angle_min_sec_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84AngleMinSecFormat(pointId.isEmpty() ? null : pointId,
                        MANUALLY_INPUT_DATA_WINDOW.fiAngleField.getText(),
                        MANUALLY_INPUT_DATA_WINDOW.fiMinField.getText(), MANUALLY_INPUT_DATA_WINDOW.fiSecField.getText(),
                        MANUALLY_INPUT_DATA_WINDOW.lambdaAngleField.getText(), MANUALLY_INPUT_DATA_WINDOW.lambdaMinField.getText(),
                        MANUALLY_INPUT_DATA_WINDOW.lambdaSecField.getText(), h.isEmpty() ? "0.0" : h);
        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84XYZFormat(){
        try{
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84XYZFormat.getText();
            Validation.isValidManuallyInputDataForWGS84XYZFormat(pointId.isEmpty() ? null : pointId,
                        MANUALLY_INPUT_DATA_WINDOW.x_WGS84_field.getText(),
                        MANUALLY_INPUT_DATA_WINDOW.y_WGS84_field.getText(),
                        MANUALLY_INPUT_DATA_WINDOW.z_WGS84_field.getText());
        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 térbeli koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public static String convertAngleMinSecFormat(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) (100000 * ((data - angle) * 3600 - min * 60))) / 100000.0;
        return angle + "°" + (9 < min ? min : "0" + min) + "'" + (9 < sec ? sec : "0" + sec) + "\"";
    }

    public static void setWindowTitle(){
        if( MANUALLY_INPUT_DATA_WINDOW != null && !INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty() ){
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle("Kézi adatbevitel");
        }
        else if( INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty() ){
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle("Adatok fájlból beolvasása");
        }
        else if( MANUALLY_INPUT_DATA_WINDOW != null && MANUALLY_INPUT_DATA_WINDOW.jFrame.isVisible()
                && !INPUT_POINTS.isEmpty() ){
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db");
        }
        else if( INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && !INPUT_POINTS.isEmpty() ){
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db");
        }
    }
    public static void addValidInputPoint(Point validPoint){
        if( KMLWrapperController.INPUT_POINTS.contains(validPoint) ){
            if(MessagePane.getYesNoOptionMessage("Hozzáadott pont: "
                            + (validPoint.getPointId() == null ? "-" : validPoint.getPointId()) ,
                    "Korábban már beolvasott pont, biztosan újra hozzáadja?", null ) == 0 ){
                KMLWrapperController.INPUT_POINTS.add(validPoint);
                setWindowTitle();
            }
        }
        else {
            KMLWrapperController.INPUT_POINTS.add(validPoint);
            setWindowTitle();
        }
    }
    public void setIdForValidPointList(){
        String pointId = INPUT_DATA_FILE_WINDOW.pointIdField.getText();
        int pointIdValue;
        if( pointId.isEmpty() ){
            pointIdValue = 1;
        }
        else{
            try{
                pointIdValue = Integer.parseInt(pointId);
            }catch (NumberFormatException e){
                MessagePane.getInfoMessage("Hibás pontszám érték",
                        "A pontszám érétke csak pozitív egész szám lehet.", INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        }
        String prefix = INPUT_DATA_FILE_WINDOW.pointPreIdField.getText();
        String postfix = INPUT_DATA_FILE_WINDOW.pointPostIdField.getText();
        for (Point inputPoint : INPUT_POINTS) {
            if( inputPoint.getPointId() == null ){
                inputPoint.setPointId((prefix.isEmpty() ? "" : prefix) +
                        (pointIdValue++) +
                        (postfix.isEmpty() ? "" : postfix));
            }
            else{
                String id = (prefix.isEmpty() ? "" : prefix) + (pointIdValue) + (postfix.isEmpty() ? "" : postfix);
               if( MessagePane.getYesNoOptionMessage("A pont száma: " + inputPoint.getPointId(),
                     "Cseréli a pont számát? Az új pontszám: " +  id, INPUT_DATA_FILE_WINDOW.jFrame) == 0){
                   inputPoint.setPointId((prefix.isEmpty() ? "" : prefix) +
                           (pointIdValue++) +
                           (postfix.isEmpty() ? "" : postfix));
               }
            }
        }
    }



}
