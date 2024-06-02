package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KMLWrapperController {

    public InputDataFileWindow inputDataFileWindow;
    public ManuallyInputDataWindow manuallyInputDataWindow;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;

    public KMLWrapperController() {
        //this.inputDataFileWindow = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        getReferencePoints();
    }


    private void getReferencePoints() {
        List<String> pointsData = getPointsData("points/common_points.txt");
        REFERENCE_POINTS = new ArrayList<>();
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
            REFERENCE_POINTS.add(point);
        }
    }

    private List<String> getPointsData(String filePath) {
        List<String> pointsData = new ArrayList<>();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filePath);
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

}
