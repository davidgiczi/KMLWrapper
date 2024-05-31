package hu.david.giczi.mvmxpert.wrapper.service;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TransformationProcess {

    public List<Point> EOV_TO_WGS_REFERENCE_POINTS = Arrays.asList(null, null, null, null, null);
    public List<Point> WGS_TO_EOV_REFERENCE_POINTS = Arrays.asList(null, null, null, null, null);
    private ToEOV toEOV;
    private ToWGS toWGS;

    public TransformationProcess() {
    }

    public void collectReferencePoints(){
        Point avePointForEOV = getAveragePointForEOV();
        if( avePointForEOV.getY_EOV() > 0 && avePointForEOV.getX_EOV() > 0){
            List<Point> sortedPoints = sortEOVReferencePointsByAsc();
            EOV_TO_WGS_REFERENCE_POINTS.set(0, sortedPoints.get(0));
            for (int i = 1; i < sortedPoints.size(); i++) {

            }
        }
        Point avePointForWGS = getAveragePointForWGS();
        if( avePointForWGS.getX_WGS84() > 0 && avePointForWGS.getY_WGS84() > 0 && avePointForWGS.getZ_WGS84() > 0){
            List<Point> sortedPoints = sortWGSReferencePointsByAsc();
            WGS_TO_EOV_REFERENCE_POINTS.set(0, sortedPoints.get(0));
            for (int i = 1; i < sortedPoints.size(); i++) {

            }
        }
    }


    private List<Point> sortEOVReferencePointsByAsc(){
        List<Point> sortedPoints = new ArrayList<>(KMLWrapperController.REFERENCE_POINTS);
        Point avePointForEOV = getAveragePointForEOV();
        for (int i = 0; i < sortedPoints.size() - 1; i++) {
            for (int j = i + 1; j < sortedPoints.size(); j++) {
                    if (sortedPoints.get(i).getDistanceForEOV(avePointForEOV) >
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

    private List<Point> sortWGSReferencePointsByAsc(){
        List<Point> sortedPoints = new ArrayList<>(KMLWrapperController.REFERENCE_POINTS);
        Point avePointForWGS = getAveragePointForWGS();
        for (int i = 0; i < sortedPoints.size() - 1; i++) {
            for (int j = i + 1; j < sortedPoints.size(); j++) {
                    if (sortedPoints.get(i).getDistanceForWGS(avePointForWGS) >
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
