package infovis.paracoords;


import java.util.ArrayList;

public class AxisDataPara {

    //Um die Daten zu speichern/zu berechnen, die auf einer Achse liegen

    //auf y-Achse abgebildete Daten
    private ArrayList<Double> yData;
    private int axisHeight;
    private int yAxisOffset;


    //Constructor
    public AxisDataPara(ArrayList<Double> yData, int axisHeight, int yOffset){
        this.yData = yData;
        this.axisHeight = axisHeight;
        this.yAxisOffset = yOffset;
    }

    public ArrayList<Integer> getPointYCoordinates(){

        //get y coordinates of the points
        OneDCoordinatesPara yCoords = new OneDCoordinatesPara(yData, axisHeight, yAxisOffset);
        return yCoords.getCoordinates();
    }

}
