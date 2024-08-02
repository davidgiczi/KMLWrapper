package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.controller.KMLWrapperController;
import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import hu.david.giczi.mvmxpert.wrapper.view.InputDataFileWindow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ToKMLFormat {

    private final String dataType;
    private final String fileName;
    private List<String> kmlDataList;


    public ToKMLFormat(String dataType, String fileName) {
        this.dataType = dataType;
        this.fileName = fileName;
        createDataListForKML();
    }

    public List<String> getKmlDataList() {
        return kmlDataList;
    }
    private void createDataListForKML(){
        getTemplateDataForKML();
        if(dataType.equals(InputDataFileWindow.KML_DATA_TYPE[1])) {
            wrapPointsInKML();
            closeFile();
        }
        else if(dataType.equals(InputDataFileWindow.KML_DATA_TYPE[2])){
           wrapPointsForLineInKML();
           closeFile();
        }
        else if(dataType.equals(InputDataFileWindow.KML_DATA_TYPE[3])) {
            wrapPointsForPerimeterInKML();
            closeFile();
        }
        else if(dataType.equals(InputDataFileWindow.KML_DATA_TYPE[4])) {
            wrapPointsInKML();
            wrapPointsForLineInKML();
            closeFile();
        }
        else if(dataType.equals(InputDataFileWindow.KML_DATA_TYPE[5])){
             wrapPointsInKML();
             wrapPointsForPerimeterInKML();
             closeFile();
        }
    }

    private void getTemplateDataForKML()  {
        kmlDataList = new ArrayList<>();
        try (InputStream is = FileProcess.class.getClassLoader().getResourceAsStream("kml/template.kml");
             BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is)))) {
            String row;
            while ((row = br.readLine()) != null) {
                if( row.contains("<name>") ){
                    kmlDataList.add("<name>" + fileName + "</name>");
                    continue;
                }
                kmlDataList.add(row);
            }
        } catch (IOException ignored) {
        }

    }

    private void wrapPointsInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Points</name>");
        for (Point point : KMLWrapperController.INPUT_POINTS) {
            if( point.isSave() ){
                wrapPoint(point);
            }
        }
        kmlDataList.add("</Folder>");
    }

    private void wrapPoint(Point point){
       kmlDataList.add("<Placemark>");
              kmlDataList.add( "<name>" + point.getPointId() + "</name>");
              kmlDataList.add("<description><![CDATA[Name=" + point.getPointId() + "]]></description>");
              kmlDataList.add("<styleUrl>#placemark</styleUrl>");
              kmlDataList.add("<Point>");
              kmlDataList.add("<coordinates>" + point.getFormattedDecimalLambdaForWGS84() + "," +
                      point.getFormattedDecimalFiForWGS84() + "," + point.getFormattedHForWGS84() + "</coordinates>");
              kmlDataList.add("</Point>");
              kmlDataList.add("</Placemark>");
    }

    private void wrapPointsForLineInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Line</name>");
        kmlDataList.add("<Placemark>");
        kmlDataList.add( "<name>" +
                KMLWrapperController.INPUT_POINTS.get(0).getPointId()+ "-" +
                KMLWrapperController.INPUT_POINTS.get(KMLWrapperController.INPUT_POINTS
                        .size() - 1).getPointId() +  "_track</name>");
        kmlDataList.add("<styleUrl>#linestyle</styleUrl>");
        kmlDataList.add("<LineString>");
        kmlDataList.add("<tessellate>1</tessellate>");
        kmlDataList.add("<coordinates>");
        for (Point point : KMLWrapperController.INPUT_POINTS) {
            if( point.isSave() ){
                kmlDataList.add(point.getFormattedDecimalLambdaForWGS84() + ","
                        + point.getFormattedDecimalFiForWGS84() + "," + point.getFormattedHForWGS84());
            }
        }
        kmlDataList.add("</coordinates>");
        kmlDataList.add("</LineString>");
        kmlDataList.add("</Placemark>");
        kmlDataList.add("</Folder>");
    }

    private void wrapPointsForPerimeterInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Perimeter</name>");
        kmlDataList.add("<Placemark>");
        kmlDataList.add( "<name>" +
                KMLWrapperController.INPUT_POINTS.get(0).getPointId() + "-" +
                KMLWrapperController.INPUT_POINTS.get(KMLWrapperController.INPUT_POINTS.size() - 1)
                        .getPointId() +  "_perimeter</name>");
        kmlDataList.add("<styleUrl>#linestyle</styleUrl>");
        kmlDataList.add("<LineString>");
        kmlDataList.add("<tessellate>1</tessellate>");
        kmlDataList.add("<coordinates>");
        for (Point point : KMLWrapperController.INPUT_POINTS) {
            if( point.isSave() ){
                kmlDataList.add(point.getFormattedDecimalLambdaForWGS84() + ","
                        + point.getFormattedDecimalFiForWGS84() + "," + point.getFormattedHForWGS84());
            }
        }
        for (Point point : KMLWrapperController.INPUT_POINTS) {
            if ( point.isSave()) {
                kmlDataList.add(point.getFormattedDecimalLambdaForWGS84() + ","
                        + point.getFormattedDecimalFiForWGS84() + "," + point.getFormattedHForWGS84());
                break;
            }
        }
        kmlDataList.add("</coordinates>");
        kmlDataList.add("</LineString>");
        kmlDataList.add("</Placemark>");
        kmlDataList.add("</Folder>");
    }

    private void closeFile(){
        kmlDataList.add("</Document>");
        kmlDataList.add("</kml>");
    }
}
