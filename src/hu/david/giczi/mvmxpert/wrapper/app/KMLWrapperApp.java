package hu.david.giczi.mvmxpert.wrapper.app;
import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.service.ToEOV;
import hu.david.giczi.mvmxpert.wrapper.service.ToWGS;
import hu.david.giczi.mvmxpert.wrapper.service.TransformationProcess;

import java.util.List;

public class KMLWrapperApp {
    public static void main(String[] args) {
        new KMLWrapperController();
        Point pointWGS = new Point();
        pointWGS.setX_WGS84(4081882.374);
        pointWGS.setY_WGS84(1410011.138);
        pointWGS.setZ_WGS84(4678199.390);
        KMLWrapperController.INPUT_POINTS.add(pointWGS);
        TransformationProcess tr = new TransformationProcess();
        for (Point wgsToEovReferencePoint : tr.WGS_TO_EOV_REFERENCE_POINTS) {
            ToEOV.setCommonPoints(tr.WGS_TO_EOV_REFERENCE_POINTS);
            ToEOV toEOV = new ToEOV(wgsToEovReferencePoint.getX_WGS84(),
                                    wgsToEovReferencePoint.getY_WGS84(),
                                    wgsToEovReferencePoint.getZ_WGS84());
            System.out.println(wgsToEovReferencePoint.getPointId() + " " +
                    (int)  (1000 *  (wgsToEovReferencePoint.getY_EOV() - ToEOV.Y_EOV)) / 1000.0 + " " +
                    (int) (1000 * (wgsToEovReferencePoint.getX_EOV() - ToEOV.X_EOV)) / 1000.0 + " " +
                    (int) (1000 * (wgsToEovReferencePoint.getH_EOV() - ToEOV.H_EOV)) / 1000.0);
        }

        ToEOV eov = new ToEOV(KMLWrapperController.INPUT_POINTS.get(0).getX_WGS84(),
                KMLWrapperController.INPUT_POINTS.get(0).getY_WGS84(),
                KMLWrapperController.INPUT_POINTS.get(0).getZ_WGS84());
        System.out.println((int)(1000 * ToEOV.Y_EOV) / 1000.0 + " " + (int) (1000 * ToEOV.X_EOV) / 1000.0
                + " " + (int) (1000 * ToEOV.H_EOV) / 1000.0);
    }
}
