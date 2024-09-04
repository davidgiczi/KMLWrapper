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
         MessagePane.getInfoMessage("Hibás bemeneti adat", e.getMessage(),
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
           throw new InvalidPreferencesFormatException("Hibás az 1. " +
                   "vonatkozási rendszer 1. pontjának 1. koordinátája.");
       }
       double firstSystemPoint1X;
       try{
           firstSystemPoint1X =  Double.parseDouble(point11X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 1. " +
                   "vonatkozási rendszer 1. pontjának 2. koordinátája.");
       }

       double firstSystemPoint1Z;
       try{
           firstSystemPoint1Z =  Double.parseDouble(point11Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 1. " +
                   "vonatkozási rendszer 1. pontjának magassága.");
       }
       double firstSystemPoint2Y;
       try{

           firstSystemPoint2Y =  Double.parseDouble(point12Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás a 1. " +
                   "vonatkozási rendszer 2. pontjának 1. koordinátája.");
       }
       double firstSystemPoint2X;
       try{
           firstSystemPoint2X =  Double.parseDouble(point12X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás a 1. " +
                   "vonatkozási rendszer 2. pontjának 2. koordinátája.");
       }
       double firstSystemPoint2Z;
       try{
           firstSystemPoint2Z =  Double.parseDouble(point12Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 1. " +
                   "vonatkozási rendszer 2. pontjának magassága.");
       }

       double secondSystemPoint1Y;
       try{
           secondSystemPoint1Y =  Double.parseDouble(point21Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 1. pontjának 1. koordinátája.");
       }
       double secondSystemPoint1X;
       try{
           secondSystemPoint1X =  Double.parseDouble(point21X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 1. pontjának 2. koordinátája.");
       }

       double secondSystemPoint1Z;
       try{
           secondSystemPoint1Z =  Double.parseDouble(point21Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 1. pontjának magassága.");
       }
       double secondSystemPoint2Y;
       try{
           secondSystemPoint2Y =  Double.parseDouble(point22Y.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 2. pontjának 1. koordinátája.");
       }
       double secondSystemPoint2X;
       try{
           secondSystemPoint2X =  Double.parseDouble(point22X.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 2. pontjának 2. koordinátája.");
       }

       double secondSystemPoint2Z;
       try{
           secondSystemPoint2Z =  Double.parseDouble(point22Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hibás az 2. " +
                   "vonatkozási rendszer 2. pontjának magassága.");
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
        deltaDistanceXParam = commonPointList.get(0).getY_EOV() - commonPointList.get(2).getX_EOV();
        deltaDistanceYParam = commonPointList.get(0).getX_EOV() - commonPointList.get(2).getX_EOV();
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
}
