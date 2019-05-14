package infovis.paracoords;

import infovis.scatterplot.OneDCoordinates;

import java.util.ArrayList;

public class AxisDataPara {

    //Um die Daten zu speichern/zu berechnen, die in einer Zelle stehen

    //auf x-Achse abgebildete Daten
    private ArrayList<Double> xData;

    //auf y-Achse abgebildete Daten
    private ArrayList<Double> yData;

    private int axisDistance;
    private int axisHeight;


    //Constructor
    public AxisDataPara(/*ArrayList<Double> xData,*/ ArrayList<Double> yData, /*int axisDistance,*/ int axisHeight){
        //this.xData = xData;
        this.yData = yData;
        //this.axisDistance = axisDistance;
        this.axisHeight = axisHeight;
    }


    //Get the Point Coordinates
//    public ArrayList<Integer> getPointXCoordinates(int axisIndex, int offset){
//
//        //offset for this specific cell
//        //int off = (axisIndex  * axisDistance) + offset;
//
//        //get the x coordinates of the points
//        OneDCoordinates xCoords = new OneDCoordinates(xData, axisDistance, off);
//        return xCoords.getCoordinates();
//    }

    public ArrayList<Integer> getPointYCoordinates(int axisIndex, int offset){

        //offset for this specific cell
        int off = (axisIndex * axisHeight) + offset;

        //get y coordinates of the points
        OneDCoordinates yCoords = new OneDCoordinates(yData, axisHeight, off);
        return yCoords.getCoordinates();
    }

}
