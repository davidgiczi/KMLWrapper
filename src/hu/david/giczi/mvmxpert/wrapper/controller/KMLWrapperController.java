package hu.david.giczi.mvmxpert.wrapper.controller;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.FileProcess;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;
import hu.david.giczi.mvmxpert.wrapper.view.ManuallyInputDataWindow;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KMLWrapperController {

    public InputDataFileWindow inputDataFileWindow;
    public ManuallyInputDataWindow manuallyInputDataWindow;
    public static List<Point> REFERENCE_POINTS;
    public static List<Point> INPUT_POINTS;

    public KMLWrapperController() {
        //this.inputDataFileWindow = new InputDataFileWindow(this);
        INPUT_POINTS = new ArrayList<>();
        FileProcess.getReferencePoints();
    }

    public static String convertAngleMinSecFormat(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) (10000 * ((data - angle) * 3600 - min * 60))) / 10000.0;
        return angle + "°" + (9 < min ? min : "0" + min) + "'" + (9 < sec ? sec : "0" + sec) + "\"";
    }


}
