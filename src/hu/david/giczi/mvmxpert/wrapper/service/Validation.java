package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;

import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

public class Validation {

    private String coordinateType;
    private String separator;
    private String[] dataComponents;


    public Validation(String dataFormat) throws InvalidPreferencesFormatException {
        investigationDataFormat(dataFormat);
        runInputDataValidation();
    }


    private void runInputDataValidation() throws InvalidPreferencesFormatException {
        String[] rowData;
        for (String row : FileProcess.INPUT_DATA_LIST) {
            rowData = row.split(separator);
            if(rowData.length == 1) {
                throw new InvalidPreferencesFormatException(
                        "Nem a kiválasztott elválasztó szerepel a beolvasott  fájl "
                                + (FileProcess.INPUT_DATA_LIST.indexOf(row) + 1) + ". sorában.");
            }
        Point validPoint = parseInputData(rowData, FileProcess.INPUT_DATA_LIST.indexOf(row) + 1);
        KMLWrapperController.addValidInputPoint(validPoint);
        }
    }

    private Point parseInputData(String[] rowData, int indexValue) throws InvalidPreferencesFormatException {
        Point point = null;
        try{

            if( "EOV".equals(coordinateType) && rowData.length == 2 ){
             double firstData =  Double.parseDouble(rowData[0].replace(",", "."));
             double secondData = Double.parseDouble(rowData[1].replace(",", "."));
             point = isValidEOVData(null, firstData, secondData, 0.0, indexValue);
            }
            else if( "EOV".equals(coordinateType) && !"Psz".equals(dataComponents[0]) && rowData.length == 3 ){
                double firstData =  Double.parseDouble(rowData[0].replace(",", "."));
                double secondData = Double.parseDouble(rowData[1].replace(",", "."));
                double elevation = Double.parseDouble(rowData[2].replace(",", "."));
                point = isValidEOVData(null, firstData, secondData, elevation, indexValue);
            }
            else if( "EOV".equals(coordinateType) && "Psz".equals(dataComponents[0]) && rowData.length == 3 ){
                double firstData =  Double.parseDouble(rowData[1].replace(",", "."));
                double secondData = Double.parseDouble(rowData[2].replace(",", "."));
                point = isValidEOVData(rowData[0], firstData, secondData, 0.0, indexValue);
            }
            else if( "EOV".equals(coordinateType) && rowData.length == 4 ){
                double firstData =  Double.parseDouble(rowData[1].replace(",", "."));
                double secondData = Double.parseDouble(rowData[2].replace(",", "."));
                double elevation = Double.parseDouble(rowData[3].replace(",", "."));
                point = isValidEOVData(rowData[0], firstData, secondData, elevation, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && rowData.length == 2 ){
                double firstData =  Double.parseDouble(rowData[0].replace(",", "."));
                double secondData = Double.parseDouble(rowData[1].replace(",", "."));
                point = isValidWGS84Data(null, firstData, secondData, 0.0, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && !"Psz".equals(dataComponents[0]) && rowData.length == 3 ){
                double firstData =  Double.parseDouble(rowData[0].replace(",", "."));
                double secondData = Double.parseDouble(rowData[1].replace(",", "."));
                double elevation = Double.parseDouble(rowData[2].replace(",", "."));
                point = isValidWGS84Data(null, firstData, secondData, elevation, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && "Psz".equals(dataComponents[0]) && rowData.length == 3 ){
                double firstData =  Double.parseDouble(rowData[1].replace(",", "."));
                double secondData = Double.parseDouble(rowData[2].replace(",", "."));
                point = isValidWGS84Data(rowData[0], firstData, secondData, 0.0, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && rowData.length == 4 ){
                double firstData =  Double.parseDouble(rowData[1].replace(",", "."));
                double secondData = Double.parseDouble(rowData[2].replace(",", "."));
                double elevation = Double.parseDouble(rowData[3].replace(",", "."));
                point = isValidWGS84Data(rowData[0], firstData, secondData, elevation, indexValue);
            }

        }
        catch (NumberFormatException e ){
            throw new InvalidPreferencesFormatException(
                    "Hibás adat, vagy formátum a beolvasott fájl "
                            + indexValue  + ". sorában.");
        }

        return point;
    }

    private Point isValidEOVData(String pointID, Double firstData, Double secondData, Double elevation, int indexValue)
    throws InvalidPreferencesFormatException {
        Point pointEOV = new Point();
        if( "Y".equals(dataComponents[0]) && (400000 > firstData || 960000 < firstData)){
            throw new InvalidPreferencesFormatException("Hibás Y koordináta érték a belovasott fájl " +
                    indexValue + ". sorában. (960km> Y_EOV > 400km)");
        }
        else if( "Y".equals(dataComponents[0]) && (32000 > secondData || 384000 < secondData)){
            throw new InvalidPreferencesFormatException("Hibás X koordináta érték a belovasott fájl " +
                    indexValue + ". sorában. (384km > X_EOV > 32km)");
        }
        else if( "X".equals(dataComponents[0]) && (32000 > firstData || 384000 < firstData)){
            throw new InvalidPreferencesFormatException("Hibás X koordináta érték a belovasott fájl " +
                    indexValue + ". sorában. (384km > X_EOV > 32km)");
        }
        else if( "X".equals(dataComponents[0]) && (400000 > secondData || 960000 < secondData)){
            throw new InvalidPreferencesFormatException("Hibás Y koordináta érték a belovasott fájl " +
                    indexValue + ". sorában. (960km> Y_EOV > 400km)");
        }
        else if( "Psz".equals(dataComponents[0]) && "Y".equals(dataComponents[1]) ){
            pointEOV.setPointId(pointID);
            pointEOV.setY_EOV(firstData);
            pointEOV.setX_EOV(secondData);
            pointEOV.setM_EOV(elevation);
        }
        else if( "Psz".equals(dataComponents[0]) && "X".equals(dataComponents[1]) ){
            pointEOV.setPointId(pointID);
            pointEOV.setY_EOV(secondData);
            pointEOV.setX_EOV(firstData);
            pointEOV.setM_EOV(elevation);
        }
        else if( "Y".equals(dataComponents[0]) ){
            pointEOV.setY_EOV(firstData);
            pointEOV.setX_EOV(secondData);
            pointEOV.setM_EOV(elevation);
        }
        else if( "X".equals(dataComponents[0]) ){
            pointEOV.setY_EOV(secondData);
            pointEOV.setX_EOV(firstData);
            pointEOV.setM_EOV(elevation);
        }

        return pointEOV;
    }
    private Point isValidWGS84Data(String pointId, Double firstData, Double secondData, Double elevation, int indexValue)
            throws InvalidPreferencesFormatException {
        Point pointWGS84 = new Point();
        if( "Szélesség".equals(dataComponents[0]) && (45.74 > firstData || 48.58 < firstData) ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi szélesség fok érték a belovasott fájl " +
                    indexValue + ". sorában. (48.58° > Szélesség fok > 45.74°)");
        }
        else if( "Szélesség".equals(dataComponents[0]) && (16.11 > secondData || 22.9 < secondData) ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi hosszúság fok érték a belovasott fájl " +
                    indexValue + ". sorában. (22.9° > Hosszúság fok > 16.11°)");
        }
        else if( "Hosszúság".equals(dataComponents[0]) && (16.11 > firstData || 22.9 < firstData) ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi hosszúság fok érték a belovasott fájl " +
                    indexValue + ". sorában. (22.9° > Hosszúság fok > 16.11°)");
        }
        else if( "Hosszúság".equals(dataComponents[0]) && (45.74 > secondData || 48.58 < secondData)){
            throw new InvalidPreferencesFormatException("Hibás földrajzi szélesség fok érték a belovasott fájl " +
                    indexValue + ". sorában. (48.58° > Szélesség fok > 45.74°)");
        }
        else if( ("X".equals(dataComponents[0]) || "X".equals(dataComponents[1]))
                && (3750000 > firstData || 4190000 < firstData)
                && (1060000 > secondData || 1420000 < secondData)
                && (4270000 > elevation || 4800000 < elevation)) {
            throw new InvalidPreferencesFormatException("Hibás térbeli koordináta érték a belovasott fájl " +
                    indexValue + ". sorában.<br>(4190km > X_WGS84 > 3750km, 1420km > Y_WGS84 > 1060km, " +
                    "4800km > Z_WGS84 > 4270km)");
        }
        else if( "Psz".equals(dataComponents[0]) && "Szélesség".equals(dataComponents[1]) ){
            pointWGS84.setPointId(pointId);
            pointWGS84.setFi_WGS84(firstData);
            pointWGS84.setLambda_WGS84(secondData);
            pointWGS84.setH_WGS84(elevation);
            List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(firstData, secondData, elevation);
            pointWGS84.setX_WGS84(xyz_WGS84.get(0));
            pointWGS84.setY_WGS84(xyz_WGS84.get(1));
            pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        }
        else if( "Psz".equals(dataComponents[0]) && "Hosszúság".equals(dataComponents[1]) ){
            pointWGS84.setPointId(pointId);
            pointWGS84.setFi_WGS84(secondData);
            pointWGS84.setLambda_WGS84(firstData);
            pointWGS84.setH_WGS84(elevation);
            List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(secondData, firstData, elevation);
            pointWGS84.setX_WGS84(xyz_WGS84.get(0));
            pointWGS84.setY_WGS84(xyz_WGS84.get(1));
            pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        }
        else if( "Psz".equals(dataComponents[0]) && "X".equals(dataComponents[1]) ){
            pointWGS84.setPointId(pointId);
            pointWGS84.setX_WGS84(firstData);
            pointWGS84.setY_WGS84(secondData);
            pointWGS84.setZ_WGS84(elevation);
            List<Double> geo_WGS84 = ToWGS.getGeographicalCoordinatesDegreesForWGS84(firstData, secondData, elevation);
            pointWGS84.setFi_WGS84(geo_WGS84.get(0));
            pointWGS84.setLambda_WGS84(geo_WGS84.get(1));
            pointWGS84.setH_WGS84(geo_WGS84.get(2));
        }
        else if( "Szélesség".equals(dataComponents[0]) ){
            pointWGS84.setFi_WGS84(firstData);
            pointWGS84.setLambda_WGS84(secondData);
            pointWGS84.setH_WGS84(elevation);
            List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(firstData, secondData, elevation);
            pointWGS84.setX_WGS84(xyz_WGS84.get(0));
            pointWGS84.setY_WGS84(xyz_WGS84.get(1));
            pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        }
        else if( "Hosszúság".equals(dataComponents[0]) ){
            pointWGS84.setFi_WGS84(secondData);
            pointWGS84.setLambda_WGS84(firstData);
            pointWGS84.setH_WGS84(elevation);
            List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(secondData, firstData, elevation);
            pointWGS84.setX_WGS84(xyz_WGS84.get(0));
            pointWGS84.setY_WGS84(xyz_WGS84.get(1));
            pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        }
        else if( "X".equals(dataComponents[0]) ){
            pointWGS84.setX_WGS84(firstData);
            pointWGS84.setY_WGS84(secondData);
            pointWGS84.setZ_WGS84(elevation);
            List<Double> geo_WGS84 = ToWGS.getGeographicalCoordinatesDegreesForWGS84(firstData, secondData, elevation);
            pointWGS84.setFi_WGS84(geo_WGS84.get(0));
            pointWGS84.setLambda_WGS84(geo_WGS84.get(1));
            pointWGS84.setH_WGS84(geo_WGS84.get(2));
        }

        return pointWGS84;
    }

    private void investigationDataFormat(String dataFormat){
        coordinateType = dataFormat.split("\\s+")[0];
        String format = dataFormat.substring(dataFormat.indexOf("(") + 1, dataFormat.indexOf(")"));
         if( format.contains(" ") ){
             separator = "\\s+";
             dataComponents = format.split("\\s+");
         }
         else if( format.contains(",") ){
             separator = ",";
             dataComponents = format.split(",");
         }
         else if( format.contains(";") ){
             separator = ";";
             dataComponents = format.split(";");
         }
    }

    public static void isValidInputDataForEOV(String pointId, String Y_EOV, String X_EOV, String M_EOV)
    throws InvalidPreferencesFormatException {
        double Y;
        double X;
        double M;
        try {
            Y = Double.parseDouble(Y_EOV.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidPreferencesFormatException("Az EOV Y koordináta érték csak szám lehet.");
        }
        try {
            X = Double.parseDouble(X_EOV.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidPreferencesFormatException("Az EOV X koordináta érték csak szám lehet.");
        }
        try {
            M = Double.parseDouble(M_EOV.replace(",", "."));
            if( 0 > M ){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            throw new InvalidPreferencesFormatException("Az M magasság érték csak pozitív szám lehet.");
        }
        if (400000 > Y || 960000 < Y) {
            throw new InvalidPreferencesFormatException("Hibás EOV Y koordináta érték. (960km> Y_EOV > 400km)");
        } else if (32000 > X || 384000 < X) {
            throw new InvalidPreferencesFormatException("Hibás EOV X koordináta érték. (384km > X_EOV > 32km)");
        }
        Point pointEOV = new Point();
        pointEOV.setPointId(pointId);
        pointEOV.setY_EOV(Y);
        pointEOV.setX_EOV(X);
        pointEOV.setM_EOV(M);
        KMLWrapperController.addValidInputPoint(pointEOV);
    }
    public static void isValidManuallyInputDataForWGS84DecimalFormat(String pointId, String Fi, String Lambda, String H)
    throws  InvalidPreferencesFormatException{
    double fi;
    double lambda;
    double h;
        try {
            fi = Double.parseDouble(Fi.replace(",", "."));
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("A WGS84 szélesség földrajzi koordináta érték csak szám lehet.");
        }
        try{
            lambda = Double.parseDouble(Lambda.replace(",", "."));
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("A WGS84 hosszúság földrajzi koordináta érték csak szám lehet.");
        }
        try{
            h = Double.parseDouble(H.replace(",", "."));
            if( 0 > h ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException("A WGS84 magasság érték csak pozitív szám lehet.");
        }
        if( 45.74 > fi || 48.58 < fi ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi szélesség fok érték. " +
                    "(48.58° > Szélesség fok > 45.74°)");
        }
        else if( 16.11 > lambda || 22.9 < lambda ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi hosszúság fok érték. " +
                    "(22.9° > Hosszúság fok > 16.11°)");
        }
        Point pointWGS84 = new Point();
        pointWGS84.setPointId(pointId);
        pointWGS84.setFi_WGS84(fi);
        pointWGS84.setLambda_WGS84(lambda);
        pointWGS84.setH_WGS84(h);
        List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(fi, lambda, h);
        pointWGS84.setX_WGS84(xyz_WGS84.get(0));
        pointWGS84.setY_WGS84(xyz_WGS84.get(1));
        pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        KMLWrapperController.addValidInputPoint(pointWGS84);
    }
    public static void isValidManuallyInputDataForWGS84AngleMinSecFormat(String pointId, String Fi_angle, String Fi_min, String Fi_sec,
         String Lambda_angle, String Lambda_min, String Lambda_sec, String H)
    throws InvalidPreferencesFormatException{
        double fi_angle;
        double fi_min;
        double fi_sec;
        double lambda_angle;
        double lambda_min;
        double lambda_sec;
        double h;
        try{
            fi_angle = Integer.parseInt(Fi_angle);
            if( 0 > fi_angle || 59 < fi_angle ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 szélesség földrajzi koordináta fok érték csak pozitív egész szám lehet. (60 > fok >= 0)");
        }
        try{
            fi_min = Integer.parseInt(Fi_min);
            if( 0 > fi_min || 59 < fi_min){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 szélesség földrajzi koordináta perc érték csak pozitív egész szám lehet. (60 > perc >= 0)");
        }
        try{
            fi_sec = Double.parseDouble(Fi_sec.replace(",", "."));
            if( 0 > fi_sec || 59 < fi_sec ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 szélesség földrajzi koordináta másodperc érték csak pozitív szám lehet. (60 > mperc >= 0)");
        }

        try{
            lambda_angle = Integer.parseInt(Lambda_angle);
            if( 0 > lambda_angle || 59 < lambda_angle ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 hosszúság földrajzi koordináta fok érték csak pozitív egész szám lehet. (60 > fok >= 0)");
        }
        try{
            lambda_min = Integer.parseInt(Lambda_min);
            if( 0 > lambda_min || 59 < lambda_min ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 hosszúság földrajzi koordináta perc érték csak pozitív egész szám lehet. (60 > perc >= 0)");
        }
        try{
            lambda_sec = Double.parseDouble(Lambda_sec.replace(",", "."));
            if( 0 > lambda_sec || 59 < lambda_sec){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 hosszúság földrajzi koordináta másodperc érték csak pozitív szám lehet. (60 > mperc >= 0)");
        }
        try{
            h = Double.parseDouble(H.replace(",", "."));
            if( 0 > h ){
                throw new NumberFormatException();
            }
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 magasság érték csak pozitív szám lehet.");
        }

        if( 45.74 > (fi_angle + fi_min / 60.0 + fi_sec / 3600.0) || 48.58 < (fi_angle + fi_min / 60.0 + fi_sec / 3600.0) ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi szélesség fok érték. " +
                    "(48.58° > Szélesség fok > 45.74°)");
        }
        else if( 16.11 > (lambda_angle + lambda_min / 60.0 + lambda_sec / 3600.0) ||
                22.9 < (lambda_angle + lambda_min / 60.0 + lambda_sec / 3600.0) ){
            throw new InvalidPreferencesFormatException("Hibás földrajzi hosszúság fok érték. " +
                    "(22.9° > Hosszúság fok > 16.11°)");
        }
        Point pointWGS84 = new Point();
        pointWGS84.setPointId(pointId);
        double FiValue = fi_angle + fi_min / 60.0 + fi_sec / 3600.0;
        pointWGS84.setFi_WGS84(FiValue);
        double LambdaValue = lambda_angle + lambda_min / 60.0 + lambda_sec / 3600.0;
        pointWGS84.setLambda_WGS84(LambdaValue);
        pointWGS84.setH_WGS84(h);
        List<Double> xyz_WGS84 = ToWGS.getXYZCoordinatesForWGS84ByDegrees(FiValue, LambdaValue, h);
        pointWGS84.setX_WGS84(xyz_WGS84.get(0));
        pointWGS84.setY_WGS84(xyz_WGS84.get(1));
        pointWGS84.setZ_WGS84(xyz_WGS84.get(2));
        KMLWrapperController.addValidInputPoint(pointWGS84);
    }
    public static void isValidManuallyInputDataForWGS84XYZFormat(String pointId, String X, String Y, String Z)
    throws InvalidPreferencesFormatException{
    double x;
    double y;
    double z;
    try{
        x = Double.parseDouble(X.replace(",", "."));
    }
    catch (NumberFormatException e){
        throw new InvalidPreferencesFormatException(
                "A WGS84 térbeli X koordináta érték csak szám lehet.");
    }
        try{
            y = Double.parseDouble(Y.replace(",", "."));
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 térbeli Y koordináta érték csak szám lehet.");
        }
        try{
            z = Double.parseDouble(Z.replace(",", "."));
        }
        catch (NumberFormatException e){
            throw new InvalidPreferencesFormatException(
                    "A WGS84 térbeli Z koordináta érték csak szám lehet.");
        }
        if( 3750000 > x || 4190000 < x ) {
            throw new InvalidPreferencesFormatException("Hibás térbeli X koordináta érték. (4190km > X_WGS84 > 3750km)");
        }
        else if( 1060000 > y || 1420000 < y) {
            throw new InvalidPreferencesFormatException("Hibás térbeli Y koordináta érték . (1420km > Y_WGS84 > 1060km)");
        }
        else if( 4270000 > z || 4800000 < z) {
            throw new InvalidPreferencesFormatException("Hibás térbeli Z koordináta érték. (4800km > Z_WGS84 > 4270km)");
        }
        Point pointWGS84 = new Point();
        pointWGS84.setPointId(pointId);
        pointWGS84.setX_WGS84(x);
        pointWGS84.setY_WGS84(y);
        pointWGS84.setZ_WGS84(z);
        List<Double> geo_WGS84 = ToWGS.getGeographicalCoordinatesDegreesForWGS84(x, y, z);
        pointWGS84.setFi_WGS84(geo_WGS84.get(0));
        pointWGS84.setLambda_WGS84(geo_WGS84.get(1));
        pointWGS84.setH_WGS84(geo_WGS84.get(2));
        KMLWrapperController.addValidInputPoint(pointWGS84);
    }


}
