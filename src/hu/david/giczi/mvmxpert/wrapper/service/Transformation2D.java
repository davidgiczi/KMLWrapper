package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;

import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

public class Transformation2D {

   private double distanceParam;
   private double rotationParam;
   private double scaleParam;
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
         MessagePane.getInfoMessage("Hib�s bemeneti adat", e.getMessage(),
                 KMLWrapperController.TRANSFORMATION_2D_WINDOW.jFrame);
        return;
     }


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
           secondSystemPoint1Z =  Double.parseDouble(point22Z.trim().replace(",", "."));
       }
       catch (NumberFormatException e){
           throw new InvalidPreferencesFormatException("Hib�s az 2. " +
                   "vonatkoz�si rendszer 2. pontj�nak magass�ga.");
       }
   }

}
