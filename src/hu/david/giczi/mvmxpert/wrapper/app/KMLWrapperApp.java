package hu.david.giczi.mvmxpert.wrapper.app;


import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.TransformationProcess;

public class KMLWrapperApp {
    public static void main(String[] args) {
        new KMLWrapperController();
        Point point = new Point();
        point.setY_EOV(870000d);
        point.setX_EOV(350000d);
        KMLWrapperController.INPUT_POINTS.add(point);
        TransformationProcess tr = new TransformationProcess();
        tr.collectReferencePoints();
        tr.EOV_TO_WGS_REFERENCE_POINTS.forEach(System.out::println);
    }
}
