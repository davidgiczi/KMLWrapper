package hu.david.giczi.mvmxpert.wrapper.app;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.TransformationProcess;

public class KMLWrapperApp {
    public static void main(String[] args) {
        new KMLWrapperController();
        Point point = new Point();
        point.setX_WGS84(4081882.463);
        point.setY_WGS84(1410011.144);
        point.setZ_WGS84(4678199.471);
        KMLWrapperController.INPUT_POINTS.add(point);
        new TransformationProcess();
    }
}
