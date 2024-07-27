package hu.david.giczi.mvmxpert.wrapper.domain;

import java.text.DecimalFormat;

public class Deviation {

    private final String pointId;
    private boolean isSave;
    private final double x1;
    private final double y1;
    private final double z1;
    private final double x2;
    private final double y2;
    private final double z2;
    private DecimalFormat decimalFormat;

    public Deviation(String pointId, double x1, double y1, double z1,
                     double x2, double y2, double z2) {
        this.pointId = pointId;
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getXDeviation(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format (x1-x2).replace(",", ".");
    }

    public String getYDeviation(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format (y1-y2).replace(",", ".");
    }

    public String getZDeviation(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format (z1-z2).replace(",", ".");
    }

    public String getPointId() {
        return pointId;
    }

    public boolean isSave() {
        return isSave;
    }
}
