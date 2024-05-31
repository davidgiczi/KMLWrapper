package hu.david.giczi.mvmxpert.wrapper.service;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransformationProcess {

    public Point[] FOR_TRANSFORMATION_POINTS = new Point[5];



    public List<Point> sortEOVReferencePointsByAsc(){
        List<Point> sortedPoints = new ArrayList<>(KMLWrapperController.REFERENCE_POINTS);
        Point avePointForEOV = getAveragePointForEOV();
        for (int i = 0; i < sortedPoints.size() - 1; i++) {
            for (int j = i + 1; j < sortedPoints.size(); j++) {
                    if (avePointForEOV.getY_EOV() != 0.0 &&
                        avePointForEOV.getX_EOV() != 0.0 &&
                        sortedPoints.get(i).getDistanceForEOV(avePointForEOV) >
                                sortedPoints.get(j).getDistanceForEOV(avePointForEOV)) {
                    Collections.swap(sortedPoints, i, j);
                }
            }
        }
        return sortedPoints;
    }

    private Point getAveragePointForEOV(){
        Point avePoint = new Point();
        double aveY =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> p.getY_EOV() != null && p.getX_EOV() != null)
                .mapToDouble(Point::getY_EOV).average().orElse(0.0);
        double aveX =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> p.getY_EOV() != null && p.getX_EOV() != null)
                .mapToDouble(Point::getX_EOV).average().orElse(0.0);
        avePoint.setY_EOV(aveY);
        avePoint.setX_EOV(aveX);
        return avePoint;
    }

    public List<Point> sortWGSReferencePointsByAsc(){
        List<Point> sortedPoints = new ArrayList<>(KMLWrapperController.REFERENCE_POINTS);
        Point avePointForWGS = getAveragePointForWGS();
        for (int i = 0; i < sortedPoints.size() - 1; i++) {
            for (int j = i + 1; j < sortedPoints.size(); j++) {
                    if (avePointForWGS.getX_WGS84() != 0.0 &&
                        avePointForWGS.getY_WGS84() != 0.0 &&
                        avePointForWGS.getZ_WGS84() != 0.0 &&
                        sortedPoints.get(i).getDistanceForWGS(avePointForWGS) >
                                sortedPoints.get(j).getDistanceForWGS(avePointForWGS)) {
                    Collections.swap(sortedPoints, i, j);
                }
            }
        }
        return sortedPoints;
    }

    private Point getAveragePointForWGS(){
        Point avePoint = new Point();
        double aveX =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> p.getX_WGS84() != null && p.getY_WGS84() != null && p.getZ_WGS84() != null)
                .mapToDouble(Point::getX_WGS84).average().orElse(0.0);
        double aveY =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> p.getX_WGS84() != null && p.getY_WGS84() != null && p.getZ_WGS84() != null)
                .mapToDouble(Point::getY_WGS84).average().orElse(0.0);
        double aveZ =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> p.getX_WGS84() != null && p.getY_WGS84() != null && p.getZ_WGS84() != null)
                .mapToDouble(Point::getZ_WGS84).average().orElse(0.0);
       avePoint.setX_WGS84(aveX);
       avePoint.setY_WGS84(aveY);
       avePoint.setZ_WGS84(aveZ);
        return avePoint;
    }

}
