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
        decimalFormat = new DecimalFormat("0.0000");
        return decimalFormat.format(scale).replace(",", ".");
    }

    public String getRotationXParam(){
    return convertRotationValueInSec(Math.toDegrees(rotationX));
    }

    public String getRotationYParam(){
        return convertRotationValueInSec(Math.toDegrees(rotationY));
    }

    public String getRotationZParam(){
        return convertRotationValueInSec(Math.toDegrees(rotationZ));
    }


    private  String convertRotationValueInSec(double data){
        int angle = (int) data;
        int min = (int) ((data - angle) * 60);
        double sec = ((int) (10 * ((data - angle) * 3600 - min * 60))) / 10.0;
        return (0 > data ? "-" : "+") + Math.abs(angle) + "° " +
                (9 < Math.abs(min) ? Math.abs(min) : "0" + Math.abs(min)) + "' " +
                        (9 < Math.abs(sec) ? Math.abs(sec) : "0" + Math.abs(sec)) + "\"";
    }
}
