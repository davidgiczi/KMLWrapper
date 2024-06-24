package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
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
                        "Nem a kiv�lasztott elv�laszt� szerepel a beolvasott  f�jl "
                                + (FileProcess.INPUT_DATA_LIST.indexOf(row) + 1) + ". sor�ban.");
            }
        Point validPoint = parseInputData(rowData, FileProcess.INPUT_DATA_LIST.indexOf(row) + 1);
        KMLWrapperController.INPUT_POINTS.add(validPoint);
        }
    }

    private Point parseInputData(String[] rowData, int indexValue) throws InvalidPreferencesFormatException {
        Point point = null;
        try{

            if( "EOV".equals(coordinateType) && rowData.length == 2 ){
             double firstData =  Double.parseDouble(rowData[0]);
             double secondData = Double.parseDouble(rowData[1]);
             point = isValidEOVData(firstData, secondData, 0.0, indexValue);
            }
            else if( "EOV".equals(coordinateType) && rowData.length > 2 ){
                double firstData =  Double.parseDouble(rowData[0]);
                double secondData = Double.parseDouble(rowData[1]);
                double elevation = Double.parseDouble(rowData[2]);
                point = isValidEOVData(firstData, secondData, elevation, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && rowData.length == 2 ){
                double firstData =  Double.parseDouble(rowData[0]);
                double secondData = Double.parseDouble(rowData[1]);
                point = isValidWGS84Data(firstData, secondData, 0.0, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && rowData.length > 2 ){
                double firstData =  Double.parseDouble(rowData[0]);
                double secondData = Double.parseDouble(rowData[1]);
                double elevation = Double.parseDouble(rowData[2]);
                point = isValidWGS84Data(firstData, secondData, elevation, indexValue);
            }

        }
        catch (NumberFormatException e ){
            throw new InvalidPreferencesFormatException(
                    "Hib�s adat, vagy form�tum a beolvasott f�jl "
                            + indexValue  + ". sor�ban.");
        }

        return point;
    }

    private Point isValidEOVData(Double firstData, Double secondData, Double elevation, int indexValue)
    throws InvalidPreferencesFormatException {
        Point pointEOV = new Point();
        if( "Y".equals(dataComponents[0]) && (400000 > firstData || 960000 < firstData)){
            throw new InvalidPreferencesFormatException("Hib�s Y koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (960km> Y_EOV > 400km)");
        }
        else if( "Y".equals(dataComponents[0]) && (32000 > secondData || 384000 < secondData)){
            throw new InvalidPreferencesFormatException("Hib�s X koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (384km > X_EOV > 32km)");
        }
        else if( "X".equals(dataComponents[0]) && (32000 > firstData || 384000 < firstData)){
            throw new InvalidPreferencesFormatException("Hib�s X koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (384km > X_EOV > 32km)");
        }
        else if( "X".equals(dataComponents[0]) && (400000 > secondData || 960000 < secondData) ){
            throw new InvalidPreferencesFormatException("Hib�s Y koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (960km> Y_EOV > 400km)");
        }
        else if( "Y".equals(dataComponents[0]) ){
            pointEOV.setY_EOV(firstData);
            pointEOV.setX_EOV(secondData);
            pointEOV.setH_EOV(elevation);
        }
        else if( "X".equals(dataComponents[0]) ){
            pointEOV.setY_EOV(secondData);
            pointEOV.setX_EOV(firstData);
            pointEOV.setH_EOV(elevation);
        }

        return pointEOV;
    }
    private Point isValidWGS84Data(Double firstData, Double secondData, Double elevation, int indexValue)
            throws InvalidPreferencesFormatException {
        Point pointWGS84 = new Point();
        if( "Sz�less�g".equals(dataComponents[0]) && (45.74 > firstData || 48.58 < firstData) ){
            throw new InvalidPreferencesFormatException("Hib�s f�ldrajzi sz�less�g fok �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (48.58� > Sz�less�g fok > 45.74�)");
        }
        else if( "Sz�less�g".equals(dataComponents[0]) && (16.11 > secondData || 22.9 < secondData) ){

            throw new InvalidPreferencesFormatException("Hib�s f�ldrajzi hossz�s�g fok �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (22.9� > Hossz�s�g fok > 16.11�)");
        }
        else if( "Hossz�s�g".equals(dataComponents[0]) && (16.11 > firstData || 22.9 < firstData) ){
            throw new InvalidPreferencesFormatException("Hib�s f�ldrajzi hossz�s�g fok �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (22.9� > Hossz�s�g fok > 16.11�)");
        }
        else if("Hossz�s�g".equals(dataComponents[0]) && (45.74 > secondData || 48.58 < secondData)){
            throw new InvalidPreferencesFormatException("Hib�s f�ldrajzi sz�less�g fok �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (48.58� > Sz�less�g fok > 45.74�)");
        }
        else if("X".equals(dataComponents[0]) && (3750000 > firstData || 4190000 < firstData) &&
                (1060000 > secondData || 1420000 < secondData) && (4270000 > elevation || 4800000 < elevation)) {
            throw new InvalidPreferencesFormatException("Hib�s t�rbeli koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban.<br>(4190km > X_WGS84 > 3750km, 1420km > Y_WGS84 > 1060km, " +
                    "4800km > Z_WGS84 > 4270km )");
        }
        else if( "Sz�less�g".equals(dataComponents[0]) ){
            pointWGS84.setFi_WGS84(firstData);
            pointWGS84.setLambda_WGS84(secondData);
            pointWGS84.setH_WGS84(elevation);
        }
        else if( "Hossz�s�g".equals(dataComponents[0]) ){
            pointWGS84.setFi_WGS84(secondData);
            pointWGS84.setLambda_WGS84(firstData);
            pointWGS84.setH_WGS84(elevation);
        }
        else if( "X".equals(dataComponents[0]) ){
            pointWGS84.setX_WGS84(secondData);
            pointWGS84.setY_WGS84(firstData);
            pointWGS84.setZ_WGS84(elevation);
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



}
