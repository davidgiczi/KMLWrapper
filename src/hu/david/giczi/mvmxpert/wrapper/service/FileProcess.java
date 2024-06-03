package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileProcess {

    public static void getReferencePoints() {
        List<String> pointsData = getPointsData("points/common_points.txt");
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

    private static List<String> getPointsData(String filePath) {
        List<String> pointsData = new ArrayList<>();
        try (InputStream is = FileProcess.class.getClassLoader().getResourceAsStream(filePath);
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
