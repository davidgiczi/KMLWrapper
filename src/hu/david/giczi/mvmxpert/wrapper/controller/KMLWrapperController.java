package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import hu.david.giczi.mvmxpert.wrapper.service.Transformation;
import hu.david.giczi.mvmxpert.wrapper.service.Transformation2D;
import hu.david.giczi.mvmxpert.wrapper.service.Validation;
import hu.david.giczi.mvmxpert.wrapper.view.*;

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
    public static Transformation2DWindow TRANSFORMATION_2D_WINDOW;
    public static Transformation TRANSFORMATION;
    public FileProcess fileProcess;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;
    public Transformation2D transformation2D;
    public static String DELIMITER;


    public KMLWrapperController() {
        this.fileProcess = new FileProcess();
        INPUT_DATA_FILE_WINDOW = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        FileProcess.getReferencePoints();
    }

    public static void setDelimiter(String delimiter) {
        KMLWrapperController.DELIMITER = delimiter;
    }

    public void transformationInputPointData() {
        TRANSFORMATION = new Transformation();
    }
    public void reTransformationInputPointData(String dataType, String pointId, boolean isLeftOut){
        if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[14])){
            for (Point eovToWgsReferencePoint : TRANSFORMATION.EOV_TO_WGS_REFERENCE_POINTS) {
                if( eovToWgsReferencePoint.getPointId().equals(pointId) ){
                    eovToWgsReferencePoint.setLeftOut(!isLeftOut);

                }
            }
        }
        else  if( dataType.equals(InputDataFileWindow.TXT_DATA_TYPE[15])){
            for (Point wgsToEovReferencePoint : TRANSFORMATION.WGS_TO_EOV_REFERENCE_POINTS) {
                if( wgsToEovReferencePoint.getPointId().equals(pointId) ){
                    wgsToEovReferencePoint.setLeftOut(!isLeftOut);
                }
            }
        }
    KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.getTableModel().setCommonPointsDeviationData();
    }

    public void openInputDataFile() {
        if (InputDataFileWindow.EOV_DATA_TYPE[0].equals(INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.getSelectedItem())) {
            MessagePane.getInfoMessage("A fájl nem nyitható meg",
                    "Formátum választása szükséges.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        fileProcess.openInputDataFile();
        if (FileProcess.FILE_NAME == null) {
            INPUT_DATA_FILE_WINDOW.inputDataTypeComboBox.setSelectedItem(InputDataFileWindow.EOV_DATA_TYPE[0]);
            return;
        }
        if (!INPUT_POINTS.isEmpty()) {
            if (MessagePane.getYesNoOptionMessage("Beolvasott pontok lista nem üres",
                    "Törli a korábban beolvasott pontokat?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
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
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public void openKMLDataFile() {
        List<String> dataFromKML = fileProcess.getInputDataFromKML();
        if( dataFromKML.isEmpty() ){
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban",
                    "KML fájl adatok nem olvashatók.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        for (String kmlData : dataFromKML) {
            String[] kmlInputData = kmlData.split(",");
            try{
                Validation.isValidManuallyInputDataForWGS84DecimalFormat
                        (null, kmlInputData[1], kmlInputData[0], kmlInputData[2]);
            }
            catch (InvalidPreferencesFormatException e){
                MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
            }
        }
    }

    public void openAutoCadListDataFile() {
        List<String> autoCadInputDataList = fileProcess.getAutoCadInputData();
        if (autoCadInputDataList.isEmpty()) {
            MessagePane.getInfoMessage("Hiba a beolvasott fájlban",
                    "AutoCad lista adatok nem olvashatók.", INPUT_DATA_FILE_WINDOW.jFrame);
            return;
        }
        for (String autoCadData : autoCadInputDataList) {
            String[] autoCadInputData = autoCadData.split(",");
            try {
                Validation.isValidInputDataForEOV(null, autoCadInputData[0], autoCadInputData[1], autoCadInputData[2]);
            } catch (InvalidPreferencesFormatException e) {
                MessagePane.getInfoMessage("Hibás EOV koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
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
            MessagePane.getInfoMessage("Hibás EOV koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
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
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
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
            MessagePane.getInfoMessage("Hibás WGS84 földrajzi koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
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
            MessagePane.getInfoMessage("Hibás WGS84 térbeli koordináta", e.getMessage(), INPUT_DATA_FILE_WINDOW.jFrame);
        }
    }

    public static void setWindowTitle() {
        if (MANUALLY_INPUT_DATA_WINDOW != null && !INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty()) {
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle("Kézi adatbevitel");
        } else if (INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && INPUT_POINTS.isEmpty()) {
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle("Adatok fájlból beolvasása");
        } else if (MANUALLY_INPUT_DATA_WINDOW != null && MANUALLY_INPUT_DATA_WINDOW.jFrame.isVisible()
                && !INPUT_POINTS.isEmpty()) {
            MANUALLY_INPUT_DATA_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db");
        } else if (INPUT_DATA_FILE_WINDOW.jFrame.isVisible() && !INPUT_POINTS.isEmpty()) {
            INPUT_DATA_FILE_WINDOW.jFrame.setTitle(FileProcess.FILE_NAME == null ?
                    "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db" :
                    FileProcess.FILE_NAME + " - " + "Beolvasott pontok száma: " + INPUT_POINTS.size() + " db");
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
            if (MessagePane.getYesNoOptionMessage("Hozzáadott pont: " + addedPoint.getPointId(),
                    "Korábban már beolvasott pont, biztosan újra hozzáadja?",
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
                MessagePane.getInfoMessage("Hibás pontszám érték",
                        "A pontszám érétke csak pozitív egész szám lehet.", INPUT_DATA_FILE_WINDOW.jFrame);
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
            MessagePane.getInfoMessage("Hibás pontszám érték",
                    "A pontszám érétke csak pozitív egész szám lehet.", INPUT_DATA_FILE_WINDOW.jFrame);
            return false;
        }

        if ( MessagePane.getYesNoOptionMessage("Pontszámozás",
                    "Cseréli az összes pont számát?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                for (Point point : INPUT_POINTS) {
                    String pointId = (prefix.isEmpty() ? "" : prefix) + (pointIdValue++) + (postfix.isEmpty()
                            ? "" : postfix);
                    point.setPointId(pointId);
                }
                return true;
            }

        if ( MessagePane.getYesNoOptionMessage("Pontszámozás",
                "Cseréli egyenként a pontok számát?", INPUT_DATA_FILE_WINDOW.jFrame) == 0) {

            for (Point inputPoint : INPUT_POINTS) {

                String pointId = (prefix.isEmpty() ? "" : prefix) + (pointIdValue++) + (postfix.isEmpty()
                        ? "" : postfix);

                if (inputPoint.getPointId() == null) {
                    inputPoint.setPointId(pointId);
                }
                else {
                    if (MessagePane.getYesNoOptionMessage("Beolvasott pont száma: " + inputPoint.getPointId(),
                            "Cseréli a pont számát? Az új pontszám: " + pointId,
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
                if (MessagePane.getYesNoOptionMessage("Korábban mentett fájl", "Biztos, hogy felülírod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveKMLDataFile(selectedItem, fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("Fájl mentése sikertelen",
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
                MessagePane.getInfoMessage("Fájl mentése sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        }
        else if( selectedItem.equals(InputDataFileWindow.KML_DATA_TYPE[6]) ){
            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Korábban mentett fájl", "Biztos, hogy felülírod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveCalcData(fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("Fájl mentése sikertelen",
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
                MessagePane.getInfoMessage("Fájl mentése sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        }
        else if (Arrays.asList(InputDataFileWindow.TXT_DATA_TYPE).indexOf(selectedItem) > 0 &&
                16 > Arrays.asList(InputDataFileWindow.TXT_DATA_TYPE).indexOf(selectedItem)) {

            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Korábban mentett fájl", "Biztos, hogy felülírod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveTXTDataFile(selectedItem, fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("Fájl mentése sikertelen",
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
                MessagePane.getInfoMessage("Fájl mentése sikertelen",
                        FileProcess.FOLDER_PATH + "\\" + fileName,
                        KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                return;
            }
        } else if ( selectedItem.startsWith("AutoCad") ) {

            if (new File(FileProcess.FOLDER_PATH + "/" + fileName).exists()) {
                if (MessagePane.getYesNoOptionMessage("Korábban mentett fájl", "Biztos, hogy felülírod?",
                        INPUT_DATA_FILE_WINDOW.jFrame) == 0) {
                    try {
                        fileProcess.saveAutoCadDataFile(fileName);
                    } catch (IOException e) {
                        MessagePane.getInfoMessage("Fájl mentése sikertelen",
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
                    MessagePane.getInfoMessage("Fájl mentése sikertelen",
                            FileProcess.FOLDER_PATH + "\\" + fileName,
                            KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
                    return;
                }
        }
        MessagePane.getInfoMessage("Sikeres mentés",
                "Fájl mentve az alábbi mappába:<br>" + FileProcess.FOLDER_PATH + "\\" + fileName,
                INPUT_DATA_FILE_WINDOW.jFrame);
    }

    public void calcParamsForTransformation2D(){
              transformation2D = new Transformation2D(
              TRANSFORMATION_2D_WINDOW.point11NumberField.getText(),
              TRANSFORMATION_2D_WINDOW.point11YField.getText(),
              TRANSFORMATION_2D_WINDOW.point11XField.getText(),
              TRANSFORMATION_2D_WINDOW.point11ZField.getText(),
              TRANSFORMATION_2D_WINDOW.point12NumberField.getText(),
              TRANSFORMATION_2D_WINDOW.point12YField.getText(),
              TRANSFORMATION_2D_WINDOW.point12XField.getText(),
              TRANSFORMATION_2D_WINDOW.point12ZField.getText(),
              TRANSFORMATION_2D_WINDOW.point21NumberField.getText(),
              TRANSFORMATION_2D_WINDOW.point21YField.getText(),
              TRANSFORMATION_2D_WINDOW.point21XField.getText(),
              TRANSFORMATION_2D_WINDOW.point21ZField.getText(),
              TRANSFORMATION_2D_WINDOW.point22NumberField.getText(),
              TRANSFORMATION_2D_WINDOW.point22YField.getText(),
              TRANSFORMATION_2D_WINDOW.point22XField.getText(),
              TRANSFORMATION_2D_WINDOW.point22ZField.getText());
   TRANSFORMATION_2D_WINDOW.deltaDistanceXParamField.setText(transformation2D.getDeltaDistanceXParam());
   TRANSFORMATION_2D_WINDOW.deltaDistanceYParamField.setText(transformation2D.getDeltaDistanceYParam());
   TRANSFORMATION_2D_WINDOW.rotationParamField.setText(transformation2D.getRotationParam());
   TRANSFORMATION_2D_WINDOW.scaleParamField.setText(transformation2D.getScaleParam());
   TRANSFORMATION_2D_WINDOW.deltaElevationField.setText(transformation2D.getDeltaElevation());
    }

    public void transformFirstSystemData(){
        if( transformation2D == null ){
            return;
        }
        try {
            transformation2D.setDeltaDistanceXParam(TRANSFORMATION_2D_WINDOW.deltaDistanceXParamField.getText());
        } catch (InvalidPreferencesFormatException ex) {
            MessagePane.getInfoMessage("Hibás X eltolás paraméter", ex.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
            return;
        }
        try {
            transformation2D.setDeltaDistanceYParam(TRANSFORMATION_2D_WINDOW.deltaDistanceYParamField.getText());
        } catch (InvalidPreferencesFormatException ex) {
            MessagePane.getInfoMessage("Hibás Y eltolás paraméter", ex.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
            return;
        }
        try {
            transformation2D.setRotationParam(TRANSFORMATION_2D_WINDOW.rotationParamField.getText());
        } catch (InvalidPreferencesFormatException ex) {
            MessagePane.getInfoMessage("Hibás elforgatás paraméter", ex.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
            return;
        }
        try {
            transformation2D.setScaleParam(TRANSFORMATION_2D_WINDOW.scaleParamField.getText());
        } catch (InvalidPreferencesFormatException ex) {
            MessagePane.getInfoMessage("Hibás méretarány paraméter", ex.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
            return;
        }
        try {
            transformation2D.setDeltaElevation(TRANSFORMATION_2D_WINDOW.deltaElevationField.getText());
        } catch (InvalidPreferencesFormatException ex) {
            MessagePane.getInfoMessage("Hibás dM2-dM1 paraméter", ex.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
            return;
        }
        if( DELIMITER == null ){
            DELIMITER = MessagePane
                      .getInputDataMessage(KMLWrapperController.TRANSFORMATION_2D_WINDOW.jFrame, null);
            if( DELIMITER == null ){
                return;
            }
        }
        List<String> selectedValues = TRANSFORMATION_2D_WINDOW.firstSystemDataList.getSelectedValuesList();
        List<Point> secondSystemData;
          try{
           secondSystemData = transformation2D.convertFirstSystemData(selectedValues,
                      TRANSFORMATION_2D_WINDOW.secondSystemRadioBtn.isSelected(),
                      TRANSFORMATION_2D_WINDOW.deltaElevationRadioBtn.isSelected());
          }
           catch (InvalidPreferencesFormatException e){
               MessagePane.getInfoMessage("Hibás bemeneti pont adat", e.getMessage(), TRANSFORMATION_2D_WINDOW.jFrame);
              return;
          }

        if( !secondSystemData.isEmpty() ){
            for (Point secondSystemPoint : secondSystemData) {
                TRANSFORMATION_2D_WINDOW.secondSystemDataListModel.
                        addElement(secondSystemPoint.getPointId() + DELIMITER +
                                secondSystemPoint.getFormattedYForEOV() + DELIMITER +
                                secondSystemPoint.getFormattedXForEOV() + DELIMITER +
                                secondSystemPoint.getFormattedMForEOV());
            }
        }
    }

    public void saveSecondSystemData(){
      if( TRANSFORMATION_2D_WINDOW.secondSystemDataListModel.isEmpty() ){
          return;
      }
      List<String> selectedItems = TRANSFORMATION_2D_WINDOW.secondSystemDataList.getSelectedValuesList();
      fileProcess.openDirectory();
      if( FileProcess.FOLDER_PATH == null ){
          return;
      }
      String fileName = MessagePane.getFileNameMessage(TRANSFORMATION_2D_WINDOW.jFrame);
      if( fileName == null ){
          return;
      }
      FileProcess.FILE_NAME = fileName;
        if( selectedItems.isEmpty() ){
            List<String> allItems = new ArrayList<>();
            for (int i = 0; i < TRANSFORMATION_2D_WINDOW.secondSystemDataList.getModel().getSize(); i++) {
                allItems.add(TRANSFORMATION_2D_WINDOW.secondSystemDataList.getModel().getElementAt(i));
            }
            try {
                fileProcess.saveTransformation2Data(allItems);
            } catch (IOException e) {
                e.printStackTrace();
                MessagePane.getInfoMessage("Adatok mentése",
                        "A transzformált pontok mentése sikertelen." , TRANSFORMATION_2D_WINDOW.jFrame);
            }
        }
        else {
            try {
                fileProcess.saveTransformation2Data(selectedItems);
            } catch (IOException e) {
                e.printStackTrace();
                MessagePane.getInfoMessage("Adatok mentése",
                        "A transzformált pontok mentése sikertelen." , TRANSFORMATION_2D_WINDOW.jFrame);
            }
        }
    }


}