package hu.david.giczi.mvmxpert.wrapper.service;

import hu.david.giczi.mvmxpert.wrapper.domain.Point;
import java.util.ArrayList;
import java.util.List;

public class WrapDataInKML {

    private final List<Point> pointList;
    private final String dataType;
    private final String fileName;
    private List<String> kmlDataList;


    public WrapDataInKML(List<Point> pointList, String dataType, String fileName) {
        this.pointList = pointList;
        this.dataType = dataType;
        this.fileName = fileName;
    }

    public List<String> getKmlDataList() {
        return kmlDataList;
    }
    public void createDataListForKML(){
        getTemplateDataForKML();
        switch (dataType) {
            case "Pontok":
                wrapPointsInKML();
                break;
            case "Vonal":
                wrapPointsForLineInKML();
                break;
            case "Kerület":
                wrapPointsForPerimeterInKML();
                break;
        }
    }

    private void getTemplateDataForKML()  {
        kmlDataList = new ArrayList<>();


    }

    private void wrapPointsInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Points</name>");
        for (Point point : pointList) {
           wrapPoint(point);
        }
        kmlDataList.add("</Folder>");
        kmlDataList.add("</Document>");
        kmlDataList.add("</kml>");
    }

    private void wrapPoint(Point point){
       kmlDataList.add("<Placemark>");
              kmlDataList.add( "<name>" + point.getPointId() + "</name>");
              kmlDataList.add("<description><![CDATA[Name=" + point.getPointId() + "]]></description>");
              kmlDataList.add("<styleUrl>#placemark</styleUrl>");
              kmlDataList.add("<Point>");
              kmlDataList.add("<coordinates>" + point  + "</coordinates>");
              kmlDataList.add("</Point>");
              kmlDataList.add("</Placemark>");
    }

    private void wrapPointsForLineInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Line</name>");
        kmlDataList.add("<Placemark>");
        kmlDataList.add( "<name>" +
                pointList.get(0).getPointId()+ "-" +
                pointList.get(pointList.size() - 1).getPointId() +  "_track</name>");
        kmlDataList.add("<styleUrl>#linestyle</styleUrl>");
        kmlDataList.add("<LineString>");
        kmlDataList.add("<tessellate>1</tessellate>");
        kmlDataList.add("<coordinates>");
        for (Point point : pointList) {
            kmlDataList.add(null);
        }
        kmlDataList.add("</coordinates>");
        kmlDataList.add("</LineString>");
        kmlDataList.add("</Placemark>");
        kmlDataList.add("</Folder>");
        kmlDataList.add("</Document>");
        kmlDataList.add("</kml>");
    }

    private void wrapPointsForPerimeterInKML(){
        kmlDataList.add("<Folder>");
        kmlDataList.add("<name>Perimeter</name>");
        kmlDataList.add("<Placemark>");
        kmlDataList.add( "<name>" +
                pointList.get(0).getPointId() + "-" +
                pointList.get(pointList.size() - 1).getPointId() +  "_perimeter</name>");
        kmlDataList.add("<styleUrl>#linestyle</styleUrl>");
        kmlDataList.add("<LineString>");
        kmlDataList.add("<tessellate>1</tessellate>");
        kmlDataList.add("<coordinates>");
        for (Point measPoint : pointList) {
            kmlDataList.add(null);
        }
        kmlDataList.add(null);
        kmlDataList.add("</coordinates>");
        kmlDataList.add("</LineString>");
        kmlDataList.add("</Placemark>");
        kmlDataList.add("</Folder>");
        kmlDataList.add("</Document>");
        kmlDataList.add("</kml>");
    }

}
