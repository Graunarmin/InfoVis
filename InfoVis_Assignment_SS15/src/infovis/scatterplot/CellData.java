package infovis.scatterplot;

import java.util.ArrayList;

public class CellData {

    //Um die Daten zu speichern/zu berechnen, die in einer Zelle stehen

    //auf x-Achse abgebildete Daten
    private ArrayList<Double> xData;

    //auf y-Achse abgebildete Daten
    private ArrayList<Double> yData;

    private int cellWidth;
    private int cellHeight;


    //Constructors
    public CellData(ArrayList<Double> xData, ArrayList<Double> yData){
        this.xData = xData;
        this.yData = yData;
    }

    public CellData(ArrayList<Double> xData, ArrayList<Double> yData, int cellWidth, int cellHeight){
        this.xData = xData;
        this.yData = yData;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }


    //Get the Point Coordinates
    public ArrayList<Integer> getPointXCoordinates(int cellIndex, int offset){

        //offset for this specific cell
        int off = (cellIndex  * cellWidth) + offset;

        //get the x coordinates of the points
        OneDCoordinates xCoords = new OneDCoordinates(xData, cellWidth, off);
        return xCoords.getCoordinates();

    }

    public ArrayList<Integer> getPointYCoordinates(int cellIndex, int offset){

        //offset for this specific cell
        int off = (cellIndex * cellHeight) + offset;

        //get y coordinates of the points
        OneDCoordinates yCoords = new OneDCoordinates(yData, cellHeight, off);
        return yCoords.getCoordinates();

    }

}
