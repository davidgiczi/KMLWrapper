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

    public InputDataFileWindow inputDataFileWindow;
    public ManuallyInputDataWindow manuallyInputDataWindow;
    public FileProcess fileProcess;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;


    public KMLWrapperController() {
        this.fileProcess = new FileProcess();
        this.inputDataFileWindow = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        FileProcess.getReferencePoints();
    }

    public void openInputDataFile(){
        if(inputDataFileWindow.EOV_DATA_TYPE[0].equals(inputDataFileWindow.inputDataTypeComboBox.getSelectedItem())){
            MessagePane.getInfoMessage("A fájl nem nyitható meg",
                    "Formátum választása szükséges.", inputDataFileWindow.jFrame);
            return;
        }
        fileProcess.openInputDataFile();
        if( FileProcess.FILE_NAME == null ){
            return;
        }
        if( !INPUT_POINTS.isEmpty() ) {
            if (MessagePane.getYesNoOptionMessage("Beolvasott pontok lista nem üres",
                    "Hozzáadja az új pontokat a már beolvasott pontokhoz?", inputDataFileWindow.jFrame) == 1) {
                INPUT_POINTS.clear();
            }
        }
        validationInputData();
        inputDataFileWindow.setInputDataFileWindowTitle(getWindowTitle());
    }

    private void validationInputData(){
        String selectedFormat = Objects.requireNonNull(inputDataFileWindow.inputDataTypeComboBox.getSelectedItem()).toString();
        try {
            Validation validation = new Validation(selectedFormat);
        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban", e.getMessage(), inputDataFileWindow.jFrame);
        }
    }

    public void validationManuallyInputDataForEOV(){
        try{

            String pointId = manuallyInputDataWindow.pointIdFieldForEOV.getText();
            String h = manuallyInputDataWindow.h_EOV_field.getText();
            Validation.isValidManuallyInputDataForEOV(pointId.isEmpty() ? null : pointId,
                    manuallyInputDataWindow.y_EOV_field.getText(),
                    manuallyInputDataWindow.x_EOV_field.getText(), h.isEmpty() ? "0.0" : h);

        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás EOV koordináta", e.getMessage(), inputDataFileWindow.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84DecimalFormat(){
        try{
            String pointId = manuallyInputDataWindow.pointIdFieldForWGS84DecimalFormat.getText();
            String h = manuallyInputDataWindow.h_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84DecimalFormat(pointId.isEmpty() ? null : pointId,
                    manuallyInputDataWindow.fi_WGS84_field.getText(),
                    manuallyInputDataWindow.lambda_WGS84_field.getText(), h.isEmpty() ? "0.0" : h);

        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), inputDataFileWindow.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84AngleMinSecFormat(){
        try{

            String pointId = manuallyInputDataWindow.pointIdFieldForWGS84AngleMinSecFormat.getText();
            String h = manuallyInputDataWindow.h_angle_min_sec_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84AngleMinSecFormat(pointId.isEmpty() ? null : pointId,
                        manuallyInputDataWindow.fiAngleField.getText(),
                        manuallyInputDataWindow.fiMinField.getText(), manuallyInputDataWindow.fiSecField.getText(),
                        manuallyInputDataWindow.lambdaAngleField.getText(), manuallyInputDataWindow.lambdaMinField.getText(),
                        manuallyInputDataWindow.lambdaSecField.getText(), h.isEmpty() ? "0.0" : h);
        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), inputDataFileWindow.jFrame);
        }
    }
    public void validationManuallyInputDataForWGS84XYZFormat(){
        try{
            String pointId = manuallyInputDataWindow.pointIdFieldForWGS84XYZFormat.getText();
            Validation.isValidManuallyInputDataForWGS84XYZFormat(pointId.isEmpty() ? null : pointId,
                        manuallyInputDataWindow.x_WGS84_field.getText(),
                        manuallyInputDataWindow.y_WGS84_field.getText(),
                        manuallyInputDataWindow.z_WGS84_field.getText());
        } catch (InvalidPreferencesFormatException e){
            MessagePane.getInfoMessage("Hibás WGS84 térbeli koordináta", e.getMessage(), inputDataFileWindow.jFrame);
        }
    }

    public static String convertAngleMinSecFormat(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) (100000 * ((data - angle) * 3600 - min * 60))) / 100000.0;
        return angle + "°" + (9 < min ? min : "0" + min) + "'" + (9 < sec ? sec : "0" + sec) + "\"";
    }

    public String getWindowTitle(){
        if( manuallyInputDataWindow != null && !inputDataFileWindow.jFrame.isVisible() && INPUT_POINTS.isEmpty() ){
            return "Kézi adatbevitel";
        }
        else if( inputDataFileWindow.jFrame.isVisible() && INPUT_POINTS.isEmpty() ){
            return "Adatok fájlból beolvasása";
        }
        return FileProcess.FILE_NAME == null ? "A beolvasott pontok száma: " + INPUT_POINTS.size() + " db" :
               FileProcess.FILE_NAME + " - " + "A beolvasott pontok száma: " + INPUT_POINTS.size() + " db";
    }
}
