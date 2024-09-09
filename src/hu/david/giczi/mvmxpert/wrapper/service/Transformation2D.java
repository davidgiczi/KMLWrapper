package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

public class Transformation2D {

   private double deltaDistanceXParam;
   private double deltaDistanceYParam;
   private double rotationParam;
   private double scaleParam;
   private double deltaElevation;
   private DecimalFormat df;
   private List<Point> commonPointList;
   private String delimiter;


   public Transformation2D(String point11Id, String point11Y, String point11X, String point11Z,
                           String point12Id, String point12Y, String point12X, String point12Z,
                           String point21Id, String point21Y, String point21X, String point21Z,
                           String point22Id, String point22Y, String point22X, String point22Z){
    try {
        isValidInputData(point11Id, point11Y, point11X, point11Z,
                         point12Id, point12Y, point12X, point12Z,
                         point21Id, point21Y, point21X, point21Z,
                         point22Id, point22Y, point22X, point22Z);
    }
     catch (InvalidPreferencesFormatException e){
         MessagePane.getInfoMessage("Hib�s bemeneti adat", e.getMessage(),
                 KMLWrapperController.TRANSFORMATION_2D_WINDOW.jFrame);
        return;
     }
    calcTransformation2DParams();
   }

   private void isValidInputData(String point11Id, String point11Y, String point11X, String point11Z,
                                 String point12Id, String point12Y, String point12X, String point12Z,
                                 String point21Id, String point21Y, String point21X, String point21Z,
                                 String point22Id, String point22Y, String point22X, String point22Z)
           throws InvalidPreferencesFormatException {
       double firstSystemPoint1Y;
       try{
           firstSystemPoint1Y =  Double.parseDouble(point11Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 1. " +
                   "vonatkoz�si rendszer 1. pontj�nak 1. koordin�t�ja.");
       }
       double firstSystemPoint1X;
       try{
           firstSystemPoint1X =  Double.parseDouble(point11X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 1. " +
                   "vonatkoz�si rendszer 1. pontj�nak 2. koordin�t�ja.");
       }

       double firstSystemPoint1Z;
       try{
           firstSystemPoint1Z =  Double.parseDouble(point11Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 1. " +
                   "vonatkoz�si rendszer 1. pontj�nak magass�ga.");
       }
       double firstSystemPoint2Y;
       try{

           firstSystemPoint2Y =  Double.parseDouble(point12Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s a 1. " +
                   "vonatkoz�si rendszer 2. pontj�nak 1. koordin�t�ja.");
       }
       double firstSystemPoint2X;
       try{
           firstSystemPoint2X =  Double.parseDouble(point12X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s a 1. " +
                   "vonatkoz�si rendszer 2. pontj�nak 2. koordin�t�ja.");
       }
       double firstSystemPoint2Z;
       try{
           firstSystemPoint2Z =  Double.parseDouble(point12Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 1. " +
                   "vonatkoz�si rendszer 2. pontj�nak magass�ga.");
       }

       double secondSystemPoint1Y;
       try{
           secondSystemPoint1Y =  Double.parseDouble(point21Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 1. pontj�nak 1. koordin�t�ja.");
       }
       double secondSystemPoint1X;
       try{
           secondSystemPoint1X =  Double.parseDouble(point21X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 1. pontj�nak 2. koordin�t�ja.");
       }

       double secondSystemPoint1Z;
       try{
           secondSystemPoint1Z =  Double.parseDouble(point21Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 1. pontj�nak magass�ga.");
       }
       double secondSystemPoint2Y;
       try{
           secondSystemPoint2Y =  Double.parseDouble(point22Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 2. pontj�nak 1. koordin�t�ja.");
       }
       double secondSystemPoint2X;
       try{
           secondSystemPoint2X =  Double.parseDouble(point22X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 2. pontj�nak 2. koordin�t�ja.");
       }

       double secondSystemPoint2Z;
       try{
           secondSystemPoint2Z =  Double.parseDouble(point22Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 2. pontj�nak magass�ga.");
       }

       addCommonPoints(point11Id, firstSystemPoint1Y, firstSystemPoint1X, firstSystemPoint1Z,
                       point12Id, firstSystemPoint2Y, firstSystemPoint2X, firstSystemPoint2Z,
                       point21Id, secondSystemPoint1Y, secondSystemPoint1X, secondSystemPoint1Z,
                       point22Id, secondSystemPoint2Y, secondSystemPoint2X, secondSystemPoint2Z);

   }

   private void addCommonPoints(String point11Id, double point11Y, double point11X, double point11Z,
                                String point12Id, double point12Y, double point12X, double point12Z,
                                String point21Id, double point21Y, double point21X, double point21Z,
                                String point22Id, double point22Y, double point22X, double point22Z ){
       commonPointList = new ArrayList<>();
       Point point11 = new Point( point11Id.isEmpty() ? "11": point11Id, point11Y, point11X, point11Z);
       commonPointList.add(point11);
       Point point12 = new Point( point12Id.isEmpty() ? "12": point12Id, point12Y, point12X, point12Z);
       commonPointList.add(point12);
       Point point21 = new Point( point21Id.isEmpty() ? "21": point21Id, point21Y, point21X, point21Z);
       commonPointList.add(point21);
       Point point22 = new Point( point11Id.isEmpty() ? "22": point22Id, point22Y, point22X, point22Z);
       commonPointList.add(point22);
   }

   private void calcTransformation2DParams(){
        deltaDistanceXParam = commonPointList.get(2).getY_EOV() - commonPointList.get(0).getX_EOV();
        deltaDistanceYParam = commonPointList.get(2).getX_EOV() - commonPointList.get(0).getX_EOV();
        AzimuthAndDistance firstSystemData = new AzimuthAndDistance(commonPointList.get(0), commonPointList.get(1));
        AzimuthAndDistance secondSystemData = new AzimuthAndDistance(commonPointList.get(2), commonPointList.get(3));
        rotationParam = secondSystemData.calcAzimuth() - firstSystemData.calcAzimuth();
        scaleParam = secondSystemData.calcDistance() / firstSystemData.calcDistance();
        deltaElevation = commonPointList.get(3).getM_EOV() - commonPointList.get(2).getM_EOV() -
                commonPointList.get(1).getM_EOV() + commonPointList.get(0).getM_EOV();
    }

    public String getDeltaDistanceXParam() {
       df = new DecimalFormat("0.000");
       return df.format(deltaDistanceXParam).replace(",", ".");
    }

    public String getDeltaDistanceYParam() {
        df = new DecimalFormat("0.000");
        return df.format(deltaDistanceYParam).replace(",", ".");
    }

    public String getRotationParam() {
        return new Point().convertAngleMinSecFormat(0 > rotationParam ?
                Math.toDegrees(rotationParam) + 360 : Math.toDegrees(rotationParam));
    }

    public String getScaleParam() {
       df = new DecimalFormat("0.0000");
       return df.format(scaleParam).replace(",", ".");
    }

    public String getDeltaElevation() {
       df = new DecimalFormat("0.00");
        return df.format(deltaElevation).replace(",", ".");
    }

    public void setDeltaDistanceXParam(String deltaDistanceX) throws InvalidPreferencesFormatException {
       try{
           this.deltaDistanceXParam = Double.parseDouble(deltaDistanceX.replace(",", "."));
       }catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Az X eltol�s �rt�ke csak sz�m lehet.");
       }
    }

    public void setDeltaDistanceYParam(String deltaDistanceY) throws InvalidPreferencesFormatException{
        try{
            this.deltaDistanceYParam = Double.parseDouble(deltaDistanceY.replace(",", "."));
        }catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("Az Y eltol�s �rt�ke csak sz�m lehet.");
        }
    }

    public void setRotationParam(String rotation) throws InvalidPreferencesFormatException{
            String[] rotationData = rotation.split("\\s+");
            if( rotationData.length != 3 ){
                throw new InvalidPreferencesFormatException("Az elforgat�s form�tuma hib�s: " + rotationData[0]);
            }
            int angle;
            try{
                if( rotationData[0].contains("�") ){
                    angle = Integer.parseInt(rotationData[0].substring(0, rotationData[0].indexOf("�")));
                }
                else{
                    throw new NumberFormatException();
                }
                if( 0 > angle || angle > 359 ){
                    throw new NumberFormatException();
                }
            }
            catch (NumberFormatException e){
                throw new InvalidPreferencesFormatException("Az elforgat�s fok �rt�ke hib�s: " + rotationData[0]);
            }
        int sec;
        try{
            if( rotationData[1].contains("'") ){
                sec = Integer.parseInt(rotationData[1].substring(0, rotationData[1].indexOf("'")));
            }
            else {
                throw new NumberFormatException();
            }

            if( 0 > sec || sec > 59 ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("Az elforgat�s perc �rt�ke hib�s: " + rotationData[1]);
        }
        double min;
        try{
            if( rotationData[2].contains("\"") ){
                min = Double.parseDouble(rotationData[2]
                        .substring(0, rotationData[2].indexOf("\"")).replace(",", "."));
            }
            else{
                throw new NumberFormatException();
            }
            if( 0 > min || min > 59 ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("Az elforgat�s m�sodperc �rt�ke hib�s: " + rotationData[2]);
        }
           this.rotationParam = Math.toRadians(angle + min / 60.0 + sec / 3600.0);
    }

    public void setScaleParam(String scale) throws InvalidPreferencesFormatException {
        try{
            this.scaleParam = Double.parseDouble(scale.replace(",", "."));
        }catch (NumberFormatException e){
            throw  new InvalidPreferencesFormatException("A m�retar�ny �rt�ke csak sz�m lehet.");
        }
    }

    public void setDeltaElevation(String deltaElevation) throws InvalidPreferencesFormatException {
        try{
            this.deltaElevation = Double.parseDouble(deltaElevation.replace(",", "."));
        }catch (NumberFormatException e){
            throw  new InvalidPreferencesFormatException("A dM2-dM1 �rt�k csak sz�m lehet.");
        }
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        if( " ".equals(delimiter) ){
            delimiter = "\\s+";
        }
        this.delimiter = delimiter;
    }

    public List<Point> convertFirstSystemData(List<String> firstSystemData,
                                              boolean is2ndSystem,
                                              boolean isUsedCorrection)
    throws InvalidPreferencesFormatException {
       List<Point> convertedDataList = new ArrayList<>();
        for (String inputRowData : firstSystemData) {
            String[] rowData = inputRowData.split(delimiter);
            if( 4 > rowData.length ){
                continue;
            }
            double firstCoordinate;
            try{
                firstCoordinate = Double.parseDouble(rowData[1].replace(",", "."));
            }catch (NumberFormatException n){
                throw new InvalidPreferencesFormatException("Az 1. vonatkoz�si rendszer 1. koordin�t�ja csak sz�m lehet.");
            }
            double secondCoordinate;
            try{
                secondCoordinate = Double.parseDouble(rowData[2].replace(",", "."));
            }catch (NumberFormatException n){
                throw new InvalidPreferencesFormatException("Az 1. vonatkoz�si rendszer 2. koordin�t�ja csak sz�m lehet.");
            }
            double elevation;
            try{
                elevation = Double.parseDouble(rowData[3].replace(",", "."));
            }catch (NumberFormatException n){
                throw new InvalidPreferencesFormatException("Az 1. vonatkoz�si rendszer magass�ga csak sz�m lehet.");
            }
            convertedDataList.add(convertFirstSystemPointData(rowData[0], firstCoordinate,
                    secondCoordinate, elevation, is2ndSystem, isUsedCorrection));
        }

       return convertedDataList;
    }

    private Point convertFirstSystemPointData(String pointId, double firstCoordinate,
                                              double secondCoordinate, double elevationData,
                                              boolean is2ndSystem,
                                              boolean isUsedCorrection) {
       Point point = new Point();
       point.setPointId(pointId);
       point.setY_EOV(deltaDistanceXParam +  scaleParam * (firstCoordinate * Math.cos(rotationParam) -
               secondCoordinate * Math.sin(rotationParam)));
        point.setX_EOV(deltaDistanceYParam + scaleParam * (firstCoordinate * Math.sin(rotationParam) +
                secondCoordinate * Math.cos(rotationParam)));
        if( is2ndSystem && isUsedCorrection ){
            point.setM_EOV(commonPointList.get(2).getM_EOV() +
                    elevationData - commonPointList.get(0).getM_EOV() + deltaElevation);
        }
        else if( !is2ndSystem && isUsedCorrection ){
            point.setM_EOV(elevationData + deltaElevation);
        }
        else if( !is2ndSystem ){
            point.setM_EOV(elevationData);
        }
        else {
            point.setM_EOV(commonPointList.get(2).getM_EOV() + elevationData - commonPointList.get(0).getM_EOV());
        }

       return point;
    }


}
