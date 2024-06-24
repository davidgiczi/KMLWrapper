package hu.david.giczi.mvmxpert.wrapper.domain;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.service.ToEOV;

import java.util.List;
import java.util.Objects;

public class Point {

    private String pointId;
    private Double y_EOV;
    private Double x_EOV;
    private Double h_EOV;
    private Double x_IUGG67;
    private Double y_IUGG67;
    private Double z_IUGG67;
    private Double fi_IUGG67;
    private Double lambda_IUGG67;
    private Double h_IUGG67;
    private Double x_WGS84;
    private Double y_WGS84;
    private Double z_WGS84;
    private Double fi_WGS84;
    private Double lambda_WGS84;
    private Double h_WGS84;


    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public Double getY_EOV() {
        return y_EOV;
    }

    public void setY_EOV(Double y_EOV) {
        this.y_EOV = y_EOV;
    }

    public Double getX_EOV() {
        return x_EOV;
    }

    public void setX_EOV(Double x_EOV) {
        this.x_EOV = x_EOV;
    }

    public Double getH_EOV() {
        return h_EOV;
    }

    public void setH_EOV(Double h_EOV) {
        this.h_EOV = h_EOV;
    }

    public Double getX_IUGG67() {
        return x_IUGG67;
    }


    public Double getY_IUGG67() {
        return y_IUGG67;
    }


    public Double getZ_IUGG67() {
        return z_IUGG67;
    }

    public Double getX_WGS84() {
        return x_WGS84;
    }

    public void setX_WGS84(Double x_WGS84) {
        this.x_WGS84 = x_WGS84;
    }

    public Double getY_WGS84() {
        return y_WGS84;
    }

    public void setY_WGS84(Double y_WGS84) {
        this.y_WGS84 = y_WGS84;
    }

    public Double getZ_WGS84() {
        return z_WGS84;
    }

    public void setZ_WGS84(Double z_WGS84) {
        this.z_WGS84 = z_WGS84;
    }

    public Double getFi_IUGG67() {
        return fi_IUGG67;
    }

    public void setFi_IUGG67(Double fi_IUGG67) {
        this.fi_IUGG67 = fi_IUGG67;
    }

    public Double getLambda_IUGG67() {
        return lambda_IUGG67;
    }

    public void setLambda_IUGG67(Double lambda_IUGG67) {
        this.lambda_IUGG67 = lambda_IUGG67;
    }

    public Double getH_IUGG67() {
        return h_IUGG67;
    }

    public void setH_IUGG67(Double h_IUGG67) {
        this.h_IUGG67 = h_IUGG67;
    }

    public Double getFi_WGS84() {
        return fi_WGS84;
    }

    public void setFi_WGS84(Double fi_WGS84) {
        this.fi_WGS84 = fi_WGS84;
    }

    public Double getLambda_WGS84() {
        return lambda_WGS84;
    }

    public void setLambda_WGS84(Double lambda_WGS84) {
        this.lambda_WGS84 = lambda_WGS84;
    }

    public Double getH_WGS84() {
        return h_WGS84;
    }

    public void setH_WGS84(Double h_WGS84) {
        this.h_WGS84 = h_WGS84;
    }

    public double getDistanceForEOV(Point point){
        return Math.sqrt(Math.pow(this.y_EOV - point.y_EOV, 2) + Math.pow(this.x_EOV - point.x_EOV, 2));
    }
    public double getDistanceForWGS(Point point){
        return Math.sqrt(Math.pow(this.x_WGS84 - point.x_WGS84, 2) +
                Math.pow(this.y_WGS84 - point.y_WGS84, 2) +  Math.pow(this.z_WGS84 - point.z_WGS84, 2));
    }
    public void convertEOVCoordinatesForXYZForIUGG67(){
        if( y_EOV == null || x_EOV == null || h_EOV == null ){
            return;
        }
        List<Double> xyzForIUGG67 = ToEOV.getXYZCoordinatesForIUGG67(y_EOV, x_EOV, h_EOV);
        this.x_IUGG67 = xyzForIUGG67.get(0);
        this.y_IUGG67 = xyzForIUGG67.get(1);
        this.z_IUGG67 = xyzForIUGG67.get(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(pointId, point.pointId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointId);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x_WGS84=" + x_WGS84 +
                ", y_WGS84=" + y_WGS84 +
                ", z_WGS84=" + z_WGS84 +
                '}';
    }
}
