package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import hu.david.giczi.mvmxpert.wrapper.service.Validation;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;

import java.util.ArrayList;
import java.util.List;


public class KMLWrapperController {

    public InputDataFileWindow inputDataFileWindow;
    public ManuallyInputDataWindow manuallyInputDataWindow;
    public Validation validation;
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
        validationInputData();
        inputDataFileWindow.setInputDataFileWindowTitle(FileProcess.FILE_NAME +
                " - " + "A beolvasott pontok száma: " + INPUT_POINTS.size() + " db");
    }

    private void validationInputData(){

    }

    public static String convertAngleMinSecFormat(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) (100000 * ((data - angle) * 3600 - min * 60))) / 100000.0;
        return angle + "°" + (9 < min ? min : "0" + min) + "'" + (9 < sec ? sec : "0" + sec) + "\"";
    }

}
