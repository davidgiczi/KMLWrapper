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
        Point point = new Point();
        point.setY_EOV(650684.461);
        point.setX_EOV(237444.184);
        point.setH_EOV(137.210);
        KMLWrapperController.INPUT_POINTS.add(point);
        TransformationProcess tr = new TransformationProcess();
        List<Double> IUGG = ToEOV.getXYZCoordinatesForIUGG67(point.getY_EOV(), point.getX_EOV(), point.getH_EOV());
        ToWGS.setCommonPoints(tr.EOV_TO_WGS_REFERENCE_POINTS);
        ToWGS toWGS = new ToWGS(IUGG.get(0), IUGG.get(1), IUGG.get(2));
        System.out.println(ToWGS.X_WGS + " "+ ToWGS.Y_WGS + " " + ToWGS.Z_WGS);

        /*for (Point eovToWgsReferencePoint : tr.EOV_TO_WGS_REFERENCE_POINTS) {
            ToWGS.setCommonPoints(tr.EOV_TO_WGS_REFERENCE_POINTS);
            List<Double> IUGG = ToEOV.getXYZCoordinatesForIUGG67(eovToWgsReferencePoint.getY_EOV(),
                    eovToWgsReferencePoint.getX_EOV(), eovToWgsReferencePoint.getH_EOV());
          ToWGS toWGS = new ToWGS(IUGG.get(0), IUGG.get(1), IUGG.get(2));
          System.out.println(eovToWgsReferencePoint.getPointId() + " " +
                  (int) (1000  * (eovToWgsReferencePoint.getX_WGS84() - ToWGS.X_WGS)) / 1000.0 + " " +
                  (int) (1000 * (eovToWgsReferencePoint.getY_WGS84() - ToWGS.Y_WGS)) / 1000.0 + " " +
                  (int) (1000 * (eovToWgsReferencePoint.getZ_WGS84() - ToWGS.Z_WGS)) / 1000.0);
        }*/

        Point point1 = new Point();
        point1.setX_WGS84(4081882.374);
        point1.setY_WGS84(1410011.138);
        point1.setZ_WGS84(4678199.390);
        KMLWrapperController.INPUT_POINTS.add(point1);
        TransformationProcess tr1 = new TransformationProcess();
        ToEOV.setCommonPoints(tr1.WGS_TO_EOV_REFERENCE_POINTS);
        ToEOV toEOV = new ToEOV(point1.getX_WGS84(), point1.getY_WGS84(), point1.getZ_WGS84());
        System.out.println(ToEOV.Y_EOV + " " + ToEOV.X_EOV + " " + ToEOV.H_EOV);
       /* for (Point wgsToEovReferencePoint : tr1.WGS_TO_EOV_REFERENCE_POINTS) {
            ToEOV.setCommonPoints(tr1.WGS_TO_EOV_REFERENCE_POINTS);
           ToEOV toEOV = new ToEOV(wgsToEovReferencePoint.getX_WGS84(), wgsToEovReferencePoint.getY_WGS84(),
                   wgsToEovReferencePoint.getZ_WGS84());
            System.out.println(wgsToEovReferencePoint.getPointId() + " " +
                    (int) (1000  * (wgsToEovReferencePoint.getY_EOV() - ToEOV.Y_EOV)) / 1000.0 + " " +
                    (int) (1000 * (wgsToEovReferencePoint.getX_EOV() - ToEOV.X_EOV)) / 1000.0 + " " +
                    (int) (1000 * (wgsToEovReferencePoint.getH_EOV() - ToEOV.H_EOV)) / 1000.0);
        }*/


    }
}
