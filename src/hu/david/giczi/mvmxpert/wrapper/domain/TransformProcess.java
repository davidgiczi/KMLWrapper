package hu.david.giczi.mvmxpert.wrapper.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class TransformProcess {

    private final List<Point> REFERENCE_POINTS;

    public TransformProcess() {
        this.REFERENCE_POINTS = getReferencePoints();
    }

    private List<Point> getReferencePoints() {
    List<String> pointsData = getReferencePointsData();
    return null;
    }

    private List<String> getReferencePointsData() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("points/common_points.txt")) {
        }catch (IOException e){

        }
    return null;
    }
}
