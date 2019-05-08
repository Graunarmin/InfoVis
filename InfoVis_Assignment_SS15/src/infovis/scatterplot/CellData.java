package infovis.scatterplot;

import javafx.util.Pair;

import java.util.ArrayList;

public class CellData {

    private ArrayList<Double> xData = new ArrayList<Double>();

    //auf y abgebildete Daten
    private ArrayList<Double> yData = new ArrayList<Double>();

    private Pair<Integer, Integer> cellIndex;
    private int cellWidth;
    private int cellHeight;

    public CellData(ArrayList<Double> xData, ArrayList<Double> yData, Pair<Integer, Integer> cellIndex){
        this.xData = xData;
        this.yData = yData;
    }

    public CellData(ArrayList<Double> xData, ArrayList<Double> yData, Pair<Integer,
            Integer> cellIndex, int cellWidth, int cellHeight){
        this.xData = xData;
        this.yData = yData;
        this.cellWidth = cellWidth;
        this.cellHeight = cellHeight;
    }

    public ArrayList<Integer> getPointCoordinatesX(int cellIndex, int offset){

        int off = (cellIndex  * cellWidth) + offset;


        OneDCoordinates xCoords = new OneDCoordinates(xData, cellWidth, off);

        return xCoords.getCoordinates();

    }

    public ArrayList<Integer> getPointCoordinatesY(int cellIndex, int offset){

        int off = (cellIndex * cellHeight) + offset;

        OneDCoordinates yCoords = new OneDCoordinates(yData, cellHeight, off);
        return yCoords.getCoordinates();

    }

}
