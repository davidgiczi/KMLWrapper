package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import hu.david.giczi.mvmxpert.wrapper.service.Transformation;
import hu.david.giczi.mvmxpert.wrapper.service.Validation;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.prefs.InvalidPreferencesFormatException;


public class KMLWrapperController {

    public static InputDataFileWindow INPUT_DATA_FILE_WINDOW;
    public static ManuallyInputDataWindow MANUALLY_INPUT_DATA_WINDOW;
    public static Transformation TRANSFORMATION;
    public FileProcess fileProcess;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;


    public KMLWrapperController() {
        this.fileProcess = new FileProcess();
        INPUT_DATA_FILE_WINDOW = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        FileProcess.getReferencePoints();
    }

    public void transformationInputPointData() {
        TRANSFORMATION = new Transformation();
    }

    public void openInputDataFile() {
        if (InputDataFileWindow.EOV_DATA_TYPE[0].equals(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem())) {
            MessagePane.getInfoMessage("A f�jl nem nyithat� meg",
                    "Form�tum v�laszt�sa sz�ks�ges.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        fileProcess.openInputDataFile();
        if (FileProcess.FILE_NAME == null) {
            INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.setSelectedItem(InputDataFileWindow.EOV_DATA_TYPE[0]);
            return;
        }
        if (!INPUT_POINTS.isEmpty()) {
            if (MessagePane.getYesNoOptionMessage("Beolvasott pontok lista nem �res",
                    "T�rli a kor�bban beolvasott pontokat?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                INPUT_POINTS.clear();
            }
        }
        validationInputData();
        setWindowTitle();
    }

    private void validationInputData() {
        String selectedFormat = Objects.requireNonNull(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem()).toString();
        if (selectedFormat.startsWith("AutoCad") || selectedFormat.startsWith("kml")) {
            return;
        }
        try {
            new Validation(selectedFormat);
        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hiba a beolvasott f�jlban", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public void openKMLDataFile() {
        List<String> dataFromKML = fileProcess.getInputDataFromKML();
        if( dataFromKML.isEmpty() ){
            MessagePane.getInfoMessage("Hiba a beolvasott f�jlban",
                    "KML f�jl adatok nem olvashat�k.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        for (String kmlData : dataFromKML) {
            String[] kmlInputData = kmlData.split(",");
            try{
                Validation.isValidManuallyInputDataForWGS84DecimalFormat
                        (null, kmlInputData[1], kmlInputData[0], kmlInputData[2]);
            }
            catch (InvalidPreferencesFormatException e){
                MessagePane.getInfoMessage("Hib�s WGS84 f�ldrajzi koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
            }
        }
    }

    public void openAutoCadListDataFile() {
        List<String> autoCadInputDataList = fileProcess.getAutoCadInputData();
        if (autoCadInputDataList.isEmpty()) {
            MessagePane.getInfoMessage("Hiba a beolvasott f�jlban",
                    "AutoCad lista adatok nem olvashat�k.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        for (String autoCadData : autoCadInputDataList) {
            String[] autoCadInputData = autoCadData.split(",");
            try {
                Validation.isValidInputDataForEOV(null, autoCadInputData[0], autoCadInputData[1], autoCadInputData[2]);
            } catch (InvalidPreferencesFormatException e) {
                MessagePane.getInfoMessage("Hib�s EOV koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
                break;
            }
        }
    }

    public void validationManuallyInputDataForEOV() {
        try {
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForEOV.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_EOV_field.getText();
            Validation.isValidInputDataForEOV(pointId.isEmpty() ? null : pointId.trim(),
                    MANUALLY_INPUT_DATA_WINDOW.y_EOV_field.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.x_EOV_field.getText().trim(), h.isEmpty() ? "0.0" : h.trim());

        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hib�s EOV koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public void validationManuallyInputDataForWGS84DecimalFormat() {
        try {
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84DecimalFormat.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84DecimalFormat(pointId.isEmpty() ? null : pointId.trim(),
                    MANUALLY_INPUT_DATA_WINDOW.fi_WGS84_field.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.lambda_WGS84_field.getText().trim(), h.isEmpty() ? "0.0" : h.trim());

        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hib�s WGS84 f�ldrajzi koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public void validationManuallyInputDataForWGS84AngleMinSecFormat() {
        try {
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84AngleMinSecFormat.getText();
            String h = MANUALLY_INPUT_DATA_WINDOW.h_angle_min_sec_WGS84_field.getText();
            Validation.isValidManuallyInputDataForWGS84AngleMinSecFormat(pointId.isEmpty() ? null : pointId.trim(),
                    MANUALLY_INPUT_DATA_WINDOW.fiAngleField.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.fiMinField.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.fiSecField.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.lambdaAngleField.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.lambdaMinField.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.lambdaSecField.getText().trim(), h.isEmpty() ? "0.0" : h.trim());
        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hib�s WGS84 f�ldrajzi koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public void validationManuallyInputDataForWGS84XYZFormat() {
        try {
            String pointId = MANUALLY_INPUT_DATA_WINDOW.pointIdFieldForWGS84XYZFormat.getText();
            Validation.isValidManuallyInputDataForWGS84XYZFormat(pointId.isEmpty() ? null : pointId.trim(),
                    MANUALLY_INPUT_DATA_WINDOW.x_WGS84_field.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.y_WGS84_field.getText().trim(),
                    MANUALLY_INPUT_DATA_WINDOW.z_WGS84_field.getText().trim());
        } catch (InvalidPreferencesFormatException e) {
            MessagePane.getInfoMessage("Hib�s WGS84 t�rbeli koordin�ta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public static void setWindowTitle() {
        if (MANUALLY_INPUT_DATA_WINDOW != null && !INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty()) {
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle("K�zi adatbevitel");
        } else if (INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty()) {
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle("Adatok f�jlb�l beolvas�sa");
        } else if (MANUALLY_INPUT_DATA_WINDOW != null && MANUALLY_INPUT_DATA_WINDOW.jFrame.isVisible()
                && !INPUT_POINTS.isEmpty()) {
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok sz�ma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok sz�ma: " + INPUT_POINTS.size() + " db");
        } else if (INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && !INPUT_POINTS.isEmpty()) {
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok sz�ma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok sz�ma: " + INPUT_POINTS.size() + " db");
        }
    }

    public static void addValidInputPoint(Point validPoint) {
        if (KMLWrapperController.INPUT_POINTS.isEmpty()) {
            KMLWrapperController.INPUT_POINTS.add(validPoint);
            return;
        }
        Point addedPoint = isAddedPoint(validPoint);
        if (addedPoint == null) {
            validPoint.setPointId(validPoint.getPointId() == null ?
                    String.valueOf(INPUT_POINTS.size() + 1) : validPoint.getPointId());
            KMLWrapperController.INPUT_POINTS.add(validPoint);
        } else {
            if (MessagePane.getYesNoOptionMessage("Hozz�adott pont: " + addedPoint.getPointId(),
                    "Kor�bban m�r beolvasott pont, biztosan �jra hozz�adja?",
                    INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                validPoint.setPointId(addedPoint.getPointId());
                KMLWrapperController.INPUT_POINTS.add(validPoint);
            }
        }
        setWindowTitle();
    }

    private static Point isAddedPoint(Point validPoint) {
        for (Point inputPoint : INPUT_POINTS) {
            if (!validPoint.isWGS() && !inputPoint.isWGS() &&
                    Objects.equals(validPoint.getY_EOV(), inputPoint.getY_EOV()) &&
                    Objects.equals(validPoint.getX_EOV(), inputPoint.getX_EOV()) &&
                    Objects.equals(validPoint.getM_EOV(), inputPoint.getM_EOV())) {
                return inputPoint;
            } else if (validPoint.isWGS() && inputPoint.isWGS() &&
                    !validPoint.isXYZ() && !inputPoint.isXYZ() &&
                    Objects.equals(validPoint.getFi_WGS84(), inputPoint.getFi_WGS84()) &&
                    Objects.equals(validPoint.getLambda_WGS84(), inputPoint.getLambda_WGS84()) &&
                    Objects.equals(validPoint.getH_WGS84(), inputPoint.getH_WGS84())) {
                return inputPoint;
            } else if (validPoint.isWGS() && inputPoint.isWGS() &&
                    validPoint.isXYZ() && inputPoint.isXYZ() &&
                    Objects.equals(validPoint.getX_WGS84(), inputPoint.getX_WGS84()) &&
                    Objects.equals(validPoint.getY_WGS84(), inputPoint.getY_WGS84()) &&
                    Objects.equals(validPoint.getZ_WGS84(), inputPoint.getZ_WGS84())) {
                return inputPoint;
            }
        }
        return null;
    }

    public boolean setIdForInputDataPoints() {
        String inputPointId = INPUT_DATA_FILE_WINDOW.pointIdField.getText();
        int pointIdValue = 1;
        if ( !inputPointId.isEmpty() ) {
            try {
                pointIdValue = Integer.parseInt(inputPointId);
            } catch (NumberFormatException e) {
                MessagePane.getInfoMessage("Hib�s pontsz�m �rt�k",
                        "A pontsz�m �r�tke csak pozit�v eg�sz sz�m lehet.", INPUT_DATA_FILE_WINDOW.jFrame);
                return false;
            }
        }
        for (Point inputPoint : INPUT_POINTS) {
            if( inputPoint.getPointId() == null ){
                inputPoint.setPointId(String.valueOf(pointIdValue));
            }
            pointIdValue++;
        }
        if( inputPointId.isEmpty() ){
            return true;
        }
        inputPointId = INPUT_DATA_FILE_WINDOW.pointIdField.getText();
        String prefix = INPUT_DATA_FILE_WINDOW.pointPreIdField.getText();
        String postfix = INPUT_DATA_FILE_WINDOW.pointPostIdField.getText();

        try {
            pointIdValue = Integer.parseInt(inputPointId);
        } catch (NumberFormatException e) {
            MessagePane.getInfoMessage("Hib�s pontsz�m �rt�k",
                    "A pontsz�m �r�tke csak pozit�v eg�sz sz�m lehet.", INPUT_DATA_FILE_WINDOW.jFrame);
            return false;
        }

        if ( MessagePane.getYesNoOptionMessage("Pontsz�moz�s",
                    "Cser�li az �sszes pont sz�m�t?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                for (Point point : INPUT_POINTS) {
                    String pointId = (prefix.isEmpty() ? "" : prefix) + (pointIdValue++) + (postfix.isEmpty()
                            ? "" : postfix);
                    point.setPointId(pointId);
                }
                return true;
            }

        if ( MessagePane.getYesNoOptionMessage("Pontsz�moz�s",
                "Cser�li egyenk�nt a pontok sz�m�t?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {

            for (Point inputPoint : INPUT_POINTS) {

                String pointId = (prefix.isEmpty() ? "" : prefix) + (pointIdValue++) + (postfix.isEmpty()
                        ? "" : postfix);

                if (inputPoint.getPointId() == null) {
                    inputPoint.setPointId(pointId);
                }
                else {
                    if (MessagePane.getYesNoOptionMessage("Beolvasott pont sz�ma: " + inputPoint.getPointId(),
                            "Cser�li a pont sz�m�t? Az �j pontsz�m: " + pointId,
                            INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                        inputPoint.setPointId(pointId);
                    }
                }
            }
        }

        return true;
    }

    public void saveDataFile(String selectedItem) {
        if (INPUT_DATA_FILE_WINDOW.displayer.getTableModel().getHowManyInputPointSaved() == 0) {
            return;
        }
        fileProcess.openDirectory();
        if (FileProcess.FOLDER_PATH == null) {
            return;
        }
        String fileName = INPUT_DATA_FILE_WINDOW.getOutputFileName();
        if (Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(selectedItem) > 0 &&
                6 > Arrays.asList(InputDataFileWindow.KML_DATA_TYPE).indexOf(selectedItem)) {

            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Kor�bban mentett f�jl", "Biztos, hogy fel�l�rod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveKMLDataFile(selectedItem, fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                                FileProcess.FOLDER_PATH + "\\" + fileName,
                                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                        return;
                    }
                } else {
                    return;
                }
            }
            try {
                fileProcess.saveKMLDataFile(selectedItem, fileName);
            } catch (IOException e) {
                MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        }
        else if( selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Kor�bban mentett f�jl", "Biztos, hogy fel�l�rod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveCalcData(fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                                FileProcess.FOLDER_PATH + "\\" + fileName,
                                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                        return;
                    }
                } else {
                    return;
                }
            }
            try {
                fileProcess.saveCalcData(fileName);
            } catch (IOException e) {
                MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        }
        else if (Arrays.asList(InputDataFileWindow.TXT_DATA_TYPE).indexOf(selectedItem) > 0 &&
                16 > Arrays.asList(InputDataFileWindow.TXT_DATA_TYPE).indexOf(selectedItem)) {

            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Kor�bban mentett f�jl", "Biztos, hogy fel�l�rod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveTXTDataFile(selectedItem, fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                                FileProcess.FOLDER_PATH + "\\" + fileName,
                                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                        return;
                    }
                } else {
                    return;
                }
            }
            try {
                fileProcess.saveTXTDataFile(selectedItem, fileName);
            } catch (IOException e) {
                MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        } else if ( selectedItem.startsWith("AutoCad") ) {

            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Kor�bban mentett f�jl", "Biztos, hogy fel�l�rod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveAutoCadDataFile(fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                                FileProcess.FOLDER_PATH + "\\" + fileName,
                                KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                        return;
                    }
                } else {
                    return;
                }
            }
                try {
                    fileProcess.saveAutoCadDataFile(fileName);
                } catch (IOException e) {
                    MessagePane.getInfoMessage("F�jl ment�se sikertelen",
                            FileProcess.FOLDER_PATH + "\\" + fileName,
                            KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                    return;
                }
        }
        MessagePane.getInfoMessage("Sikeres ment�s",
                "F�jl mentve az al�bbi mapp�ba:<br>" + FileProcess.FOLDER_PATH + "\\" + fileName,
                INPUT_DATA_FILE_WINDOW.jFrame);
    }
}