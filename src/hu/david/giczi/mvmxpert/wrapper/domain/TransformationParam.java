package hu.david.giczi.mvmxpert.wrapper.domain;

import java.text.DecimalFormat;

public class TransformationParam {

    private boolean isSave;
    private final double deltaX;
    private final double deltaY;
    private final double deltaZ;
    private final double scale;
    private final double rotationX;
    private final double rotationY;
    private final double rotationZ;
    private DecimalFormat decimalFormat;

    public TransformationParam(double[][] params){
        this.deltaX = params[0][0];
        this.deltaY = params[1][0];
        this.deltaZ = params[2][0];
        this.scale = params[3][0];
        this.rotationX = params[4][0];
        this.rotationY = params[5][0];
        this.rotationZ = params[6][0];
    }

    public boolean isSave() {
        return isSave;
    }

    public void setSave(boolean save) {
        isSave = save;
    }

    public String getDeltaXParam(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(deltaX).replace(",", ".");
    }

    public String getDeltaYParam(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(deltaY).replace(",", ".");
    }

    public String getDeltaZParam(){
        decimalFormat = new DecimalFormat("0.000");
        return decimalFormat.format(deltaZ).replace(",", ".");
    }

    public String getScaleParam(){
        return decimalFormat.format((int) ((scale - 1) * 1000000));
    }

    public String getRotationXParam(){
    return new Point().convertAngleMinSecFormat(Math.toDegrees(rotationX));
    }

    public String getRotationYParam(){
        return new Point().convertAngleMinSecFormat(Math.toDegrees(rotationY));
    }

    public String getRotationZParam(){
        return new Point().convertAngleMinSecFormat(Math.toDegrees(rotationZ));
    }

}
