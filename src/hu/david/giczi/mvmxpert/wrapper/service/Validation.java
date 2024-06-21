package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;

public class Validation {

    private final List<String> inputData;
    private String coordinateType;
    private String separator;
    private String[] dataComponents;


    public Validation(List<String> inputData, String dataFormat) throws InvalidPreferencesFormatException {
        this.inputData = inputData;
        investigationDataFormat(dataFormat);
        runInputDataValidation();
    }

    private void runInputDataValidation() throws InvalidPreferencesFormatException {
        String[] rowData;
        for (String row : inputData) {
            try {
                rowData = row.split(separator);
            }
            catch (Exception e){
                throw new InvalidPreferencesFormatException(
                        "Nem a kiv�lasztott elv�laszt� szerepel a beolvasott  f�jl "
                                + inputData.indexOf(row) + ". sor�ban.");
            }

        Point validPoint = parseInputData(rowData, inputData.indexOf(row));
        KMLWrapperController.INPUT_POINTS.add(validPoint);
        }
    }

    private Point parseInputData(String[] rowData, int indexValue) throws InvalidPreferencesFormatException {
        Point point = null;
        try{

            if( "EOV".equals(coordinateType) && dataComponents.length == 2 ){
             double firstData =  Double.parseDouble(rowData[0]);
             double secondData = Double.parseDouble(rowData[1]);
             point = isValidEOVData(firstData, secondData, null, indexValue);
            }
            else if( "EOV".equals(coordinateType) && dataComponents.length == 3 ){
                double firstData =  Double.parseDouble(rowData[0]);
                double secondData = Double.parseDouble(rowData[1]);
                double elevation = Double.parseDouble(rowData[2]);
                point = isValidEOVData(firstData, secondData, elevation, indexValue);
            }
            else if( "WGS84".equals(coordinateType) && dataComponents.length == 2 ){

            }
            else if( "WGS84".equals(coordinateType) && dataComponents.length == 3 ){

            }

        }
        catch (Exception e ){
            throw new InvalidPreferencesFormatException(
                    "Hib�s adat, vagy form�tum a beolvasott f�jl "
                            + indexValue + ". sor�ban.");
        }
        return point;
    }

    private Point isValidEOVData(Double firstData, Double secondData, Double elevation, int indexValue)
    throws InvalidPreferencesFormatException {
        Point pointEOV = new Point();
        if( "Y".equals(dataComponents[0]) && 400000 > firstData ){
            throw new InvalidPreferencesFormatException("Hib�s Y koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (Y_EOV > 400000)");
        }
        else if( "Y".equals(dataComponents[0]) && 400000 < secondData ){
            throw new InvalidPreferencesFormatException("Hib�s X koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (X_EOV < 400000)");
        }
        else if( "X".equals(dataComponents[0]) && 400000 < firstData ){
            throw new InvalidPreferencesFormatException("Hib�s X koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (X_EOV < 400000)");
        }
        else if( "X".equals(dataComponents[0]) && 400000 > secondData ){
            throw new InvalidPreferencesFormatException("Hib�s Y koordin�ta �rt�k a belovasott f�jl " +
                    indexValue + ". sor�ban. (Y_EOV > 400000)");
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
    private Point isValidWGS84Data(Double firstData, Double secondData, Double elevation, int indexValue){
        Point pointWGS84 = new Point();
        //sz�less�g 45-48 �s 48-35
        //hossz�s�g 16-05 �s 22-58
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
