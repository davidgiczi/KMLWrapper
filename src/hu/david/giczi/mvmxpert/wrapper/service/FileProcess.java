package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static String FILE_NAME;
    public static String FOLDER_PATH;
    public static List<String> INPUT_DATA_LIST;

    public void openInputDataFile() {
        JFileChooser jfc = new JFileChooser(){

            private static final long serialVersionUID = 1L;

            @Override
            protected JDialog createDialog( Component parent ) throws HeadlessException {
                JDialog dialog = super.createDialog( parent );
                dialog.setLocationRelativeTo(null);
                dialog.setIconImage(
                        new ImageIcon(Objects.requireNonNull(
                                this.getClass().getResource("/logo/MVM.jpg"))).getImage() );
                return dialog;
            }
        };
        jfc.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
            }

            @Override
            public String getDescription() {
                return "*.txt";
            }
        });
        jfc.setCurrentDirectory(FOLDER_PATH == null ?
                FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
        jfc.setDialogTitle("Adat fájl megnyitása");
        int returnValue = jfc.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            FILE_NAME = selectedFile.getName();
            FOLDER_PATH = selectedFile.getParent();
            openInputFile();
        }
        else {
            FILE_NAME = null;
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
            point.setH_EOV(Double.parseDouble(pointData[6]));
            point.convertEOVCoordinatesForXYZForIUGG67();
            KMLWrapperController.REFERENCE_POINTS.add(point);
        }
    }

    private static List<String> getPointsData() {
        List<String> pointsData = new ArrayList<>();
        try (InputStream is = FileProcess.class.getClassLoader().getResourceAsStream("points/common_points.txt");
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String row;
            while ((row = br.readLine()) != null){
                pointsData.add(row);
            }
        }
        catch (IOException ignored){
        }
        return pointsData;
    }

    public void openInputFile(){
        INPUT_DATA_LIST = new ArrayList<>();
        File file = new File(FOLDER_PATH + "/" + FILE_NAME);
        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)
        ) {

            String line;
            while ( (line = reader.readLine()) != null) {
                INPUT_DATA_LIST.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



