package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.util.List;

public class CalcData {

    private final List<Point> pointStore;

    public String getUsedPointId(){
        if( pointStore.isEmpty() ){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Point point : pointStore) {
            sb.append(point.getPointId())
                    .append(", ");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }

    public CalcData(List<Point> pointStore) {
        this.pointStore = pointStore;
    }

    public double calcDistance(){
        if( 2 > pointStore.size()){
            return 0.0;
        }
        double distance = 0.0;
        for (int i = 0; i < pointStore.size() - 1; i++) {
            distance += new AzimuthAndDistance(pointStore.get(i),
                    pointStore.get(i + 1)).calcDistance();
        }
        return (int) (100 * distance) / 100.0;
    }
    public double calcPerimeter(){
        if( 3 > pointStore.size()){
            return 0.0;
        }
        double perimeter = 0.0;
        for (int i = 0; i < pointStore.size() - 1; i++) {
            perimeter += new AzimuthAndDistance(pointStore.get(i),
                    pointStore.get(i + 1)).calcDistance();
        }

        perimeter += new AzimuthAndDistance(pointStore.get(pointStore.size() - 1),
                pointStore.get(0)).calcDistance();

        return (int) (100 * perimeter) / 100.0;
    }

    public double calcElevation(){
        if( 2 > pointStore.size() ){
            return 0.0;
        }
        double elevation = 0.0;

        for (int i = 0; i < pointStore.size() - 1; i++) {
            elevation += (pointStore.get(i + 1).getM_EOV() - pointStore.get(i).getM_EOV());
        }
        return (int) (100 * elevation) / 100.0;
    }

    public double calcArea(){
        if( 3 > pointStore.size() ){
            return 0.0;
        }
        double area = 0.0;
        for (int i = 0; i < pointStore.size() - 1; i++) {
            area += pointStore.get(i).getY_EOV() * pointStore.get(i + 1).getX_EOV();
        }
        area += pointStore.get(pointStore.size() - 1).getY_EOV() * pointStore.get(0).getX_EOV();
        for (int i = 0; i < pointStore.size() - 1; i++) {
            area -= pointStore.get(i).getX_EOV() * pointStore.get(i + 1).getY_EOV();
        }
        area -= pointStore.get(pointStore.size() - 1).getX_EOV() * pointStore.get(0).getY_EOV();

        return (int) (10 * Math.abs(0.5 * area)) / 10.0;
    }

}
