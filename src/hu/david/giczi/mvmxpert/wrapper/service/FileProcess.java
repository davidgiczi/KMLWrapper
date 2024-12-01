package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Deviation;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.domain.TransformationParam;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static String FILE_NAME;
    public static String FOLDER_PATH;
    public static List<String> INPUT_DATA_LIST;
    private static final String INVALID_CHARACTERS = "[\\\\/:*?\"<>|]";


    public void openInputDataFile() {
        JFileChooser jfc = new JFileChooser() {

            private static final long serialVersionUID = 1L;

            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(
                        new ImageIcon(Objects.requireNonNull(
                                this.getClass().getResource("/logo/MVM.jpg"))).getImage());
                return dialog;
            }
        };
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() ||
                        f.getName().toLowerCase().endsWith(".txt") ||
                        f.getName().toLowerCase().endsWith((".kml"));
            }

            @Override
            public String getDescription() {
                return "*.txt *.kml";
            }
        });
        jfc.setCurrentDirectory(FOLDER_PATH == null ?
                FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
        jfc.setDialogTitle("Adat fájl megnyitása");
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            FILE_NAME = selectedFile.getName();
            FOLDER_PATH = selectedFile.getParent();
            openInputFile();
        } else {
            FILE_NAME = null;
        }
    }

    public void openDirectory() {
        JFileChooser jfc = new JFileChooser() {

            private static final long serialVersionUID = 1L;

            @Override
            protected JDialog createDialog(Component parent) throws HeadlessException {
                JDialog dialog = super.createDialog(parent);
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(
                        new ImageIcon(Objects.requireNonNull(
                                this.getClass().getResource("/logo/MVM.jpg"))).getImage());
                return dialog;
            }
        };
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jfc.setCurrentDirectory(FOLDER_PATH == null ?
                FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
        jfc.setDialogTitle("Mentési mappa választása");
        int returnValue = jfc.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION ) {
            File selectedFile = jfc.getSelectedFile();
            FOLDER_PATH = selectedFile.getAbsolutePath();
        }
        else {
            FOLDER_PATH = null;
        }
    }

    public static void getReferencePoints() {
        List<String> pointsData = getPointsData();
        KMLWrapperController.REFERENCE_POINTS = new ArrayList<>();
        for (String rowData : pointsData) {
            String[] pointData = rowData.split(",");
            Point point = new Point();
            point.setPointId(pointData[0]);
            point.setX_WGS84(Double.parseDouble(pointData[1]));
            point.setY_WGS84(Double.parseDouble(pointData[2]));
            point.setZ_WGS84(Double.parseDouble(pointData[3]));
            point.setY_EOV(Double.parseDouble(pointData[4]));
            point.setX_EOV(Double.parseDouble(pointData[5]));
            point.setM_EOV(Double.parseDouble(pointData[6]));
            point.convertEOVCoordinatesForIUGG67();
            point.convertWGS84XYZCoordinatesForWGS84Geographical();
            KMLWrapperController.REFERENCE_POINTS.add(point);
        }
    }

    private static List<String> getPointsData() {
        List<String> pointsData = new ArrayList<>();
        try (InputStream is = FileProcess.class.getClassLoader().getResourceAsStream("points/common_points.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String row;
            while ((row = br.readLine()) != null) {
                pointsData.add(row);
            }
        } catch (IOException ignored) {
        }
        return pointsData;
    }

    private void openInputFile() {
        INPUT_DATA_LIST = new ArrayList<>();
        File file = new File(FOLDER_PATH + "/" + FILE_NAME);
       try(FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(isr)) {
           String line;
           while ((line = reader.readLine()) != null) {
               INPUT_DATA_LIST.add(line);
           }
       }catch (IOException e){
           e.printStackTrace();
           MessagePane.getInfoMessage("Fájl nem nyitható meg", FOLDER_PATH + "\\" + FILE_NAME,
                   KMLWrapperController.INPUT_DATA_FILE_WINDOW.jFrame);
       }
    }

    public List<String> getAutoCadInputData() {

        List<String> resultData = new ArrayList<>();

        for (String row : INPUT_DATA_LIST) {

            String dataLine;

            if (row.trim().startsWith("pont,")) {

                dataLine = row;

                String[] data = dataLine.trim().split("\\s+");

                String result = data[1].substring(2) + "," + data[2].substring(2) + ","
                        + data[data.length - 1];

                resultData.add(result);
            }

        }

        return resultData;
    }

    public List<String> getInputDataFromKML(){
        List<String> resultData = new ArrayList<>();
        StringBuilder container = new StringBuilder();
        for (String row : INPUT_DATA_LIST) {
            container.append(row.trim()).append("\n");
        }

        List<Integer> startCoordinatesList = getStartIndexList(container.toString(), "<coordinates>");
        List<Integer> endCoordinatesList = getEndIndexList(container.toString(), "</coordinates>");
        for (int i = 0; i < startCoordinatesList.size(); i++) {
         String[] coords = container.substring(startCoordinatesList.get(i), endCoordinatesList.get(i))
                 .trim().split("\\r?\\n");
         resultData.addAll(Arrays.asList(coords));
        }


        return resultData;
    }

    private List<Integer> getStartIndexList(String containerString, String innerString){
        List<Integer> startIndexes = new ArrayList<>();
        int startIndex = 0;
        int equality = 0;
        for (int i = 0; i < containerString.length() - innerString.length(); i++) {

                for (int j = 0; j < innerString.length(); j++) {

                    if( equality == 0 && containerString.charAt(i) == innerString.charAt(0) ){
                        startIndex = i;
                    }
                    if( containerString.charAt(i + j) == innerString.charAt(j) ){
                        equality++;
                    }
            }
            if( equality == innerString.length() ){
                startIndexes.add( startIndex + innerString.length() );
            }
            equality = 0;
            startIndex = 0;
        }

        return startIndexes;
    }
    private List<Integer> getEndIndexList(String containerString, String innerString){

        List<Integer> endIndexes = new ArrayList<>();
        int startIndex = 0;
        int equality = 0;
        for (int i = 0; i < containerString.length() - innerString.length(); i++) {

            for (int j = 0; j < innerString.length(); j++) {

                if( equality == 0 && containerString.charAt(i) == innerString.charAt(0) ){
                    startIndex = i;
                }
                if( containerString.charAt(i + j) == innerString.charAt(j) ){
                    equality++;
                }
            }
            if( equality == innerString.length() ){
                endIndexes.add( startIndex );
            }
            equality = 0;
            startIndex = 0;
        }

        return endIndexes;
    }

    public void saveAutoCadDataFile(String fileName) throws IOException {
        File file = new File(FOLDER_PATH + "/" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osw);
        writer.write("_MULTIPLE _POINT");
        writer.newLine();
        for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
            if( inputPoint.isLeftOut() ){
                writer.write(inputPoint.getFormattedYForEOV() + "," +
                        inputPoint.getFormattedXForEOV() + "," + inputPoint.getFormattedMForEOV());
            }
            writer.newLine();
        }
        writer.close();
        osw.close();
        fos.close();
    }

public void saveKMLDataFile(String selectedItem, String fileName) throws IOException {
    File file = new File(FOLDER_PATH + "/" + fileName);
    ToKMLFormat toKML = new ToKMLFormat(selectedItem, fileName);
    FileOutputStream fos = new FileOutputStream(file);
    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    BufferedWriter writer = new BufferedWriter(osw);
        for (String kmlData : toKML.getKmlDataList()) {
            writer.write(kmlData);
            writer.newLine();
        }
        writer.close();
        osw.close();
        fos.close();
}
public void saveCalcData(String fileName) throws  IOException{
    File file = new File(FOLDER_PATH + "/" + fileName);
    FileOutputStream fos = new FileOutputStream(file);
    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
    BufferedWriter writer = new BufferedWriter(osw);
    writer.write("Felhasznált pontok:");
    writer.newLine();
    for (Point usedPointForCalc : KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.usedForCalcPointList) {
        writer.write(usedPointForCalc.getPointId() + "," +
                usedPointForCalc.getFormattedYForEOV() + "," +
                usedPointForCalc.getFormattedXForEOV() + "," +
                usedPointForCalc.getFormattedMForEOV());
        writer.newLine();
    }
    CalcData calc = new CalcData(KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.usedForCalcPointList);
    writer.write("Távolság: " + calc.calcDistance() + "m");
    writer.newLine();
    writer.write("Terület: " + calc.calcArea() + "m2");
    writer.newLine();
    writer.write("Kerület: " + calc.calcPerimeter() + "m");
    writer.newLine();
    writer.write("Magasságkülönbség: " + calc.calcElevation() + "m");
    writer.newLine();
    writer.close();
    osw.close();
    fos.close();
}
    public void saveTXTDataFile(String selectedItem, String fileName) throws IOException{
        File file = new File(FOLDER_PATH + "/" + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osw);

        if( Arrays.asList(InputDataFileWindow.TXT_DATA_TYPE).indexOf(selectedItem) < 9 ) {
            List<Point> displayedPointList = KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer
                    .getTableModel().displayedPointList;
            for (Point displayedPoint : displayedPointList) {

                if (!displayedPoint.isLeftOut()) {
                    continue;
                }

                if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[1])) {
                    writer.write(displayedPoint.getPointId() + "," +
                            displayedPoint.getFormattedYForEOV() + "," +
                            displayedPoint.getFormattedXForEOV() + "," +
                            displayedPoint.getFormattedMForEOV());
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[2])) {
                    writer.write(displayedPoint.getPointId() + "," +
                            displayedPoint.getFormattedDecimalFiForWGS84() + "," +
                            displayedPoint.getFormattedDecimalLambdaForWGS84() + "," +
                            displayedPoint.getFormattedHForWGS84() + "\n" +
                            displayedPoint.convertAngleMinSecFormat(displayedPoint.getFi_WGS84()) + "," +
                            displayedPoint.convertAngleMinSecFormat(displayedPoint.getLambda_WGS84()) + "," +
                            displayedPoint.getFormattedHForWGS84());
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[3])) {
                    writer.write(displayedPoint.getPointId() + "," +
                            displayedPoint.getFormattedXForWGS84() + "," +
                            displayedPoint.getFormattedYForWGS84() + "," +
                            displayedPoint.getFormattedZForWGS84());
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[4])) {
                    writer.write(displayedPoint.getPointId() + "," +
                            displayedPoint.getFormattedXForIUGG67() + "," +
                            displayedPoint.getFormattedYForIUGG67() + "," +
                            displayedPoint.getFormattedZForIUGG67());
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[5])) {
                    writer.write(displayedPoint.getPointId() + "," +
                            displayedPoint.getFormattedDecimalFiForIUGG67() + "," +
                            displayedPoint.getFormattedDecimalLambdaForIUGG67() + "," +
                            displayedPoint.getFormattedHForIUGG67() + "\n" +
                            displayedPoint.convertAngleMinSecFormat(displayedPoint.getFi_IUGG67()) + "," +
                            displayedPoint.convertAngleMinSecFormat(displayedPoint.getLambda_IUGG67()) + "," +
                            displayedPoint.getFormattedHForIUGG67());
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[6])) {
                    if (displayedPoint.isWGS()) {
                        writer.write(displayedPoint.getPointId() + "," +
                                displayedPoint.getFormattedYForEOV() + "," +
                                displayedPoint.getFormattedXForEOV() + "," +
                                displayedPoint.getFormattedMForEOV());
                    }
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[7])) {
                    if (!displayedPoint.isWGS()) {
                        writer.write(displayedPoint.getPointId() + "," +
                                displayedPoint.getFormattedDecimalFiForWGS84() + "," +
                                displayedPoint.getFormattedDecimalLambdaForWGS84() + "," +
                                displayedPoint.getFormattedHForWGS84() + "\n" +
                                displayedPoint.convertAngleMinSecFormat(displayedPoint.getFi_WGS84()) + "," +
                                displayedPoint.convertAngleMinSecFormat(displayedPoint.getLambda_WGS84()) + "," +
                                displayedPoint.getFormattedHForWGS84());
                    }
                } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[8])) {
                    if (!displayedPoint.isWGS()) {
                        writer.write(displayedPoint.getPointId() + "," +
                                displayedPoint.getFormattedXForWGS84() + "," +
                                displayedPoint.getFormattedYForWGS84() + "," +
                                displayedPoint.getFormattedZForWGS84());
                    }
                }
                writer.newLine();
            }
        }
        else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[9])) {
            for (Point wgsToEovReferencePoint : KMLWrapperController.
                    INPUT_DATA_FILE_WINDOW.displayer.getTableModel().displayedPointList) {
                if( wgsToEovReferencePoint.isLeftOut() ){
                writer.write(wgsToEovReferencePoint.getPointId() + "," +
                        wgsToEovReferencePoint.getFormattedYForEOV() + "," +
                        wgsToEovReferencePoint.getFormattedXForEOV() + "," +
                        wgsToEovReferencePoint.getFormattedMForEOV());
                writer.newLine();
                }
            }
        } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[10])) {
            for (Point eovToWgsReferencePoint : KMLWrapperController.
                    INPUT_DATA_FILE_WINDOW.displayer.getTableModel().displayedPointList) {
                if( eovToWgsReferencePoint.isLeftOut() ) {
                    writer.write(eovToWgsReferencePoint.getPointId() + "," +
                            eovToWgsReferencePoint.getFormattedXForWGS84() + "," +
                            eovToWgsReferencePoint.getFormattedYForWGS84() + "," +
                            eovToWgsReferencePoint.getFormattedZForWGS84());
                    writer.newLine();
                }
            }
        } else if (selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[11])) {
            for (Point eovToWgsReferencePoint : KMLWrapperController.
                    INPUT_DATA_FILE_WINDOW.displayer.getTableModel().displayedPointList) {
                if( eovToWgsReferencePoint.isLeftOut() ) {
                        writer.write(eovToWgsReferencePoint.getPointId() + "," +
                                eovToWgsReferencePoint.getFormattedDecimalFiForWGS84() + "," +
                                eovToWgsReferencePoint.getFormattedDecimalLambdaForWGS84() + "," +
                                eovToWgsReferencePoint.getFormattedHForWGS84() + "\n" +
                                eovToWgsReferencePoint.convertAngleMinSecFormat(eovToWgsReferencePoint.getFi_WGS84()) + "," +
                                eovToWgsReferencePoint.convertAngleMinSecFormat(eovToWgsReferencePoint.getLambda_WGS84()) + "," +
                                eovToWgsReferencePoint.getFormattedHForWGS84());
                        writer.newLine();
                    }
            }
        }
        else if( selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[12])){
            TransformationParam trParam =
                    KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.getTableModel().toWGSParams;
            writer.write(trParam.getDeltaXParam() + "," +
                    trParam.getDeltaYParam() + "," +
                    trParam.getDeltaZParam() + "," +
                    trParam.getScaleParam() + "," +
                    trParam.getRotationXParam() + "," +
                    trParam.getRotationYParam() + "," +
                    trParam.getRotationZParam());
        }
        else if( selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[13])){
            TransformationParam trParam =
                    KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.getTableModel().toEOVParams;
            writer.write(trParam.getDeltaXParam() + "," +
                    trParam.getDeltaYParam() + "," +
                    trParam.getDeltaZParam() + "," +
                    trParam.getScaleParam() + "," +
                    trParam.getRotationXParam() + "," +
                    trParam.getRotationYParam() + "," +
                    trParam.getRotationZParam());
        }
        else if( selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[14])){
            List<Deviation> deviationDataForWGS =
                    KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer.getTableModel().deviationListForWGS;
            for (Deviation deviation : deviationDataForWGS) {
                if( deviation.isSave() ) {
                    writer.write(deviation.getPointId() + "," +
                            deviation.getXDeviation() + "," +
                            deviation.getYDeviation() + "," +
                            deviation.getZDeviation());
                }
                else{
                    continue;
                }
                writer.newLine();
            }
        }
        else if( selectedItem.equals(InputDataFileWindow.TXT_DATA_TYPE[15])){
            List<Deviation> deviationDataForEOV = KMLWrapperController.INPUT_DATA_FILE_WINDOW.displayer
                    .getTableModel().deviationListForEOV;
            for (Deviation deviation : deviationDataForEOV) {
                if( deviation.isSave() ) {
                    writer.write(deviation.getPointId() + "," +
                            deviation.getXDeviation() + "," +
                            deviation.getYDeviation() + "," +
                            deviation.getZDeviation());
                }
                else{
                    continue;
                }
                writer.newLine();
            }
        }

        writer.close();
        osw.close();
        fos.close();
    }

    public void saveTransformation2Data(List<String> secondSystemDataList) throws IOException{
        File file = new File(FOLDER_PATH + "/" +
                FILE_NAME.replaceAll(INVALID_CHARACTERS, "_") + "_tr2D.txt");
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        BufferedWriter writer = new BufferedWriter(osw);
        for (String secondSystemData : secondSystemDataList) {
            writer.write(secondSystemData);
            writer.newLine();
        }
        writer.close();
        osw.close();
        fos.close();
        MessagePane.getInfoMessage("Adatok mentése",
                "A transzformált pontok mentve az alábbi mappába:<br>" + file.getAbsolutePath()
                        , KMLWrapperController.TRANSFORMATION_2D_WINDOW.jFrame);
    }

}