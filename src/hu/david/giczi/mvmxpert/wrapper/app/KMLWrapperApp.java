package hu.david.giczi.mvmxpert.wrapper.app;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.TransformationProcess;

import java.util.List;

public class KMLWrapperApp {
    public static void main(String[] args) {
        new KMLWrapperController();
        Point point = new Point();
        point.setY_EOV(850000d);
        point.setX_EOV(250000d);
        KMLWrapperController.INPUT_POINTS.add(point);
        TransformationProcess tr = new TransformationProcess();
        List<Point> sorted = tr.sortEOVReferencePointsByAsc();
        sorted.forEach(System.out::println);

    }
}
