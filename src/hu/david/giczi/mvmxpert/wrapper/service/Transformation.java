package hu.david.giczi.mvmxpert.wrapper.service;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Transformation {

    public final List<Point> EOV_TO_WGS_REFERENCE_POINTS =
            Arrays.asList(null, null, null, null, null, null, null, null);
    public final List<Point> WGS_TO_EOV_REFERENCE_POINTS =
            Arrays.asList(null, null, null, null, null, null, null, null);
    public ToEOV toEOV;
    public ToWGS toWGS;

    public Transformation() {
        collectReferencePoints();
        transformInputPoints();
    }
    private void transformInputPoints(){
        for (Point inputPoint : KMLWrapperController.INPUT_POINTS) {
            if( !inputPoint.isWGS() ){
                toWGS = new ToWGS(inputPoint.getX_IUGG67(), inputPoint.getY_IUGG67(), inputPoint.getZ_IUGG67(),
                        EOV_TO_WGS_REFERENCE_POINTS);
                inputPoint.setX_WGS84(ToWGS.X_WGS84);
                inputPoint.setY_WGS84(ToWGS.Y_WGS84);
                inputPoint.setZ_WGS84(ToWGS.Z_WGS84);
                inputPoint.convertWGS84XYZCoordinatesForWGS84Geographical();
            }
            else {
                toEOV = new ToEOV(inputPoint.getX_WGS84(), inputPoint.getY_WGS84(),
                        inputPoint.getZ_WGS84(), WGS_TO_EOV_REFERENCE_POINTS);
                inputPoint.setY_EOV(ToEOV.Y_EOV);
                inputPoint.setX_EOV(ToEOV.X_EOV);
                inputPoint.setM_EOV(ToEOV.M_EOV);
                inputPoint.convertEOVCoordinatesForIUGG67();
            }
        }
    }
    private void collectReferencePoints(){
        Point avePointForEOV = getAveragePointForEOV();
        if( avePointForEOV.getY_EOV() > 0 && avePointForEOV.getX_EOV() > 0){
            collectReferencePointsForEOV();
        }
        Point avePointForWGS = getAveragePointForWGS();
        if( avePointForWGS.getX_WGS84() > 0 && avePointForWGS.getY_WGS84() > 0 && avePointForWGS.getZ_WGS84() > 0){
            collectReferencePointsForWGS();
        }
    }

    private void collectReferencePointsForEOV(){
        Point avePointForEOV = getAveragePointForEOV();
        sortEOVPointsByAscDistance(KMLWrapperController.REFERENCE_POINTS);
        EOV_TO_WGS_REFERENCE_POINTS.set(0, KMLWrapperController.REFERENCE_POINTS.get(0));
        for (int i = 1; i < KMLWrapperController.REFERENCE_POINTS.size(); i++) {
            if( EOV_TO_WGS_REFERENCE_POINTS.get(1) == null &&
                    avePointForEOV.getY_EOV() > KMLWrapperController.REFERENCE_POINTS.get(i).getY_EOV() &&
                    avePointForEOV.getX_EOV() < KMLWrapperController.REFERENCE_POINTS.get(i).getX_EOV()){
                    EOV_TO_WGS_REFERENCE_POINTS.set(1, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( EOV_TO_WGS_REFERENCE_POINTS.get(2) == null &&
                    avePointForEOV.getY_EOV() < KMLWrapperController.REFERENCE_POINTS.get(i).getY_EOV() &&
                    avePointForEOV.getX_EOV() < KMLWrapperController.REFERENCE_POINTS.get(i).getX_EOV()){
                    EOV_TO_WGS_REFERENCE_POINTS.set(2, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( EOV_TO_WGS_REFERENCE_POINTS.get(3) == null &&
                    avePointForEOV.getY_EOV() < KMLWrapperController.REFERENCE_POINTS.get(i).getY_EOV() &&
                    avePointForEOV.getX_EOV() > KMLWrapperController.REFERENCE_POINTS.get(i).getX_EOV()){
                    EOV_TO_WGS_REFERENCE_POINTS.set(3, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( EOV_TO_WGS_REFERENCE_POINTS.get(4) == null &&
                    avePointForEOV.getY_EOV() > KMLWrapperController.REFERENCE_POINTS.get(i).getY_EOV() &&
                    avePointForEOV.getX_EOV() > KMLWrapperController.REFERENCE_POINTS.get(i).getX_EOV()){
                    EOV_TO_WGS_REFERENCE_POINTS.set(4, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
        }
        for (Point sortedPoint : KMLWrapperController.REFERENCE_POINTS) {

            for (int j = 0; j < EOV_TO_WGS_REFERENCE_POINTS.size(); j++) {
                if (EOV_TO_WGS_REFERENCE_POINTS.get(j) == null) {
                    if (!EOV_TO_WGS_REFERENCE_POINTS.contains(sortedPoint)) {
                        EOV_TO_WGS_REFERENCE_POINTS.set(j, sortedPoint);
                    }
                }
            }
        }

        sortEOVPointsByAscDistance(EOV_TO_WGS_REFERENCE_POINTS);
    }

    private void collectReferencePointsForWGS(){
        Point avePointForWGS = getAveragePointForWGS();
        sortWGSPointsByAscDistance(KMLWrapperController.REFERENCE_POINTS);
        WGS_TO_EOV_REFERENCE_POINTS.set(0, KMLWrapperController.REFERENCE_POINTS.get(0));
        for (int i = 1; i < KMLWrapperController.REFERENCE_POINTS.size(); i++) {
            if( WGS_TO_EOV_REFERENCE_POINTS.get(1) == null &&
                    avePointForWGS.getX_WGS84() > KMLWrapperController.REFERENCE_POINTS.get(i).getX_WGS84() &&
                    avePointForWGS.getY_WGS84() < KMLWrapperController.REFERENCE_POINTS.get(i).getY_WGS84()){
                WGS_TO_EOV_REFERENCE_POINTS.set(1, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( WGS_TO_EOV_REFERENCE_POINTS.get(2) == null &&
                    avePointForWGS.getX_WGS84() < KMLWrapperController.REFERENCE_POINTS.get(i).getX_WGS84() &&
                    avePointForWGS.getY_WGS84() < KMLWrapperController.REFERENCE_POINTS.get(i).getY_WGS84()){
                WGS_TO_EOV_REFERENCE_POINTS.set(2, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( WGS_TO_EOV_REFERENCE_POINTS.get(3) == null &&
                    avePointForWGS.getX_WGS84() < KMLWrapperController.REFERENCE_POINTS.get(i).getX_WGS84() &&
                    avePointForWGS.getY_WGS84() > KMLWrapperController.REFERENCE_POINTS.get(i).getY_WGS84()){
                WGS_TO_EOV_REFERENCE_POINTS.set(3, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
            else if( WGS_TO_EOV_REFERENCE_POINTS.get(4) == null &&
                    avePointForWGS.getX_WGS84() > KMLWrapperController.REFERENCE_POINTS.get(i).getX_WGS84() &&
                    avePointForWGS.getY_WGS84() > KMLWrapperController.REFERENCE_POINTS.get(i).getY_WGS84()){
                WGS_TO_EOV_REFERENCE_POINTS.set(4, KMLWrapperController.REFERENCE_POINTS.get(i));
            }
        }
        for (Point sortedPoint : KMLWrapperController.REFERENCE_POINTS) {
            for (int j = 0; j < WGS_TO_EOV_REFERENCE_POINTS.size(); j++) {
                if (WGS_TO_EOV_REFERENCE_POINTS.get(j) == null) {
                    if (!WGS_TO_EOV_REFERENCE_POINTS.contains(sortedPoint)) {
                        WGS_TO_EOV_REFERENCE_POINTS.set(j, sortedPoint);
                    }
                }
            }
        }
        sortWGSPointsByAscDistance(WGS_TO_EOV_REFERENCE_POINTS);
    }

    private void sortEOVPointsByAscDistance(List<Point> points){
        Point avePointForEOV = getAveragePointForEOV();
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                    if (points.get(i).getDistanceForEOV(avePointForEOV) >
                                points.get(j).getDistanceForEOV(avePointForEOV)) {
                    Collections.swap(points, i, j);
                }
            }
        }
    }

    private Point getAveragePointForEOV(){
        Point avePoint = new Point();
        double aveY =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> !p.isWGS())
                .mapToDouble(Point::getY_EOV).average().orElse(0.0);
        double aveX =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(p -> !p.isWGS())
                .mapToDouble(Point::getX_EOV).average().orElse(0.0);
        avePoint.setY_EOV(aveY);
        avePoint.setX_EOV(aveX);
        return avePoint;
    }

    private void sortWGSPointsByAscDistance(List<Point> points){
        Point avePointForWGS = getAveragePointForWGS();
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                    if (points.get(i).getDistanceForWGS(avePointForWGS) >
                                points.get(j).getDistanceForWGS(avePointForWGS)) {
                    Collections.swap(points, i, j);
                }
            }
        }
    }

    private Point getAveragePointForWGS(){
        Point avePoint = new Point();
        double aveX =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(Point::isWGS)
                .mapToDouble(Point::getX_WGS84).average().orElse(0.0);
        double aveY =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(Point::isWGS)
                .mapToDouble(Point::getY_WGS84).average().orElse(0.0);
        double aveZ =  KMLWrapperController.INPUT_POINTS.stream()
                .filter(Point::isWGS)
                .mapToDouble(Point::getZ_WGS84).average().orElse(0.0);
       avePoint.setX_WGS84(aveX);
       avePoint.setY_WGS84(aveY);
       avePoint.setZ_WGS84(aveZ);
        return avePoint;
    }

}
