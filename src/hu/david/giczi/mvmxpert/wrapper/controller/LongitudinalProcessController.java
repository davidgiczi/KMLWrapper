package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.service.Transformation2D;
import hu.david.giczi.mvmxpert.wrapper.utils.LongitudinalType;
import hu.david.giczi.mvmxpert.wrapper.view.MessagePane;
import javax.swing.*;
import java.awt.*;

public class LongitudinalProcessController {

    private KMLWrapperController controller;
    private final LongitudinalType longitudinalType;
    private JFrame longitudinalWindowFrame;


    public LongitudinalProcessController(LongitudinalType longitudinalType) {
       this.longitudinalType = longitudinalType;
    }

    public void setController(KMLWrapperController controller) {
        this.controller = controller;
    }

    public void setLongitudinalWindowFrame(JFrame longitudinalWindowFrame) {
        this.longitudinalWindowFrame = longitudinalWindowFrame;
    }

    public void onClickLongitudinalOptionProcessButton(){

        if( controller.transformation2D == null ){
            controller.transformation2D = new Transformation2D(longitudinalType);
        }
        int scaleStartValue = longitudinalType == LongitudinalType.HORIZONTAL ?
                validateInputIntegerValueData(
                        KMLWrapperController.
                                TRANSFORMATION_2D_WINDOW.
                                horizontalWindow.getScaleStartValueField().getText().trim()) :
                validateInputIntegerValueData(
                        KMLWrapperController.
                                TRANSFORMATION_2D_WINDOW.
                                verticalWindow.getScaleStartValueField().getText().trim()) ;
        double distortionValue = longitudinalType == LongitudinalType.HORIZONTAL ?
                validateInputPositiveDoubleValueData(
                KMLWrapperController.
                        TRANSFORMATION_2D_WINDOW.
                        horizontalWindow.
                        getDistortionValueField().
                        getText().trim().
                        replace(",", ".")) :
                validateInputPositiveDoubleValueData(
                        KMLWrapperController.
                                TRANSFORMATION_2D_WINDOW.
                                verticalWindow.
                                getDistortionValueField().
                                getText().trim().
                                replace(",", "."));
        Double shiftOnScreenValue = longitudinalType == LongitudinalType.HORIZONTAL ?
                validateInputDoubleValueData(
                       KMLWrapperController.
                               TRANSFORMATION_2D_WINDOW.
                               horizontalWindow.
                               getShiftOnScreenValueField().getText().trim()) :
                validateInputDoubleValueData(
                        KMLWrapperController.
                                TRANSFORMATION_2D_WINDOW.
                                verticalWindow.
                                getShiftOnScreenValueField().getText().trim()) ;
        if( scaleStartValue == -1){
            return;
        }
        else if( distortionValue == 0d ){
            return;
        }
        else if( shiftOnScreenValue == null ){
            return;
        }
        controller.transformation2D.setScaleStartValue(scaleStartValue);
        controller.transformation2D.setDistortionValue(distortionValue);
        controller.transformation2D.setShiftOnScreenValue(shiftOnScreenValue);
        controller.transformation2D.setPreIDValue(longitudinalType == LongitudinalType.HORIZONTAL ?
             KMLWrapperController.TRANSFORMATION_2D_WINDOW.horizontalWindow.getPreIDValue().getText() :
             KMLWrapperController.TRANSFORMATION_2D_WINDOW.verticalWindow.getPreIDValue().getText());
        controller.transformation2D.setPostIDValue(longitudinalType == LongitudinalType.HORIZONTAL ?
                KMLWrapperController.TRANSFORMATION_2D_WINDOW.horizontalWindow.getPostIDValue().getText() :
                KMLWrapperController.TRANSFORMATION_2D_WINDOW.verticalWindow.getPostIDValue().getText());
        if( longitudinalType == LongitudinalType.HORIZONTAL ){
            KMLWrapperController.TRANSFORMATION_2D_WINDOW
                    .longitudinalOptions.
                    setText(KMLWrapperController.TRANSFORMATION_2D_WINDOW.LONGITUDINAL_TEXT + " [horizont�lis]");
        }
        else if( longitudinalType == LongitudinalType.VERTICAL ){
            KMLWrapperController.TRANSFORMATION_2D_WINDOW
                    .longitudinalOptions
                    .setText(KMLWrapperController.TRANSFORMATION_2D_WINDOW.LONGITUDINAL_TEXT + " [vertik�lis]");
        }
        KMLWrapperController.TRANSFORMATION_2D_WINDOW.longitudinalOptions.setForeground(Color.RED);
        longitudinalWindowFrame.setVisible(false);
    }


    private int validateInputIntegerValueData(String inputData){
        int integerValue = -1;
        try{
            integerValue = Integer.parseInt(inputData);
            if( 0 > integerValue ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            MessagePane.getInfoMessage("Hib�s adatok megad�sa",
                    (longitudinalType == LongitudinalType.HORIZONTAL ? "A horizont�lis " : "A vertik�lis ") +
                            "l�pt�k indul� magass�ga csak 0-n�l nem kisebb pozit�v eg�sz sz�m lehet.", longitudinalWindowFrame);
        }
        return integerValue;
    }

    private double validateInputPositiveDoubleValueData(String inputData){
        double doubleValue = 0d;
        try{
            doubleValue = Double.parseDouble(inputData);
            if( 0.0 >= doubleValue ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            MessagePane.getInfoMessage("Hib�s adatok megad�sa",
                    (longitudinalType == LongitudinalType.HORIZONTAL ? "A horizont�lis " : "A vertik�lis ") +
                            "m�retr�ny �rt�ke csak 0-n�l nagyobb pozit�v sz�m lehet.", longitudinalWindowFrame);
        }
        return doubleValue;
    }

    private Double validateInputDoubleValueData(String inputData){
        Double doubleValue = null;
        try{
            doubleValue = Double.parseDouble(inputData);
        }
        catch (NumberFormatException e){
            MessagePane.getInfoMessage("Hib�s adatok megad�sa",
                    "A monitoron val�" +
                            (longitudinalType == LongitudinalType.HORIZONTAL ? " horizont�lis " : " vertik�lis ") +
                            "eltol�s �rt�ke csak sz�m lehet.", longitudinalWindowFrame);
        }
        return doubleValue;
    }

}
