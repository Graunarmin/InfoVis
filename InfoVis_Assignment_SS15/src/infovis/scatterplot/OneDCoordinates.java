package infovis.scatterplot;

import java.util.ArrayList;
import java.util.Arrays;

public class OneDCoordinates {

    private ArrayList<Double> Data = new ArrayList<Double>();
    private int length;
    private int offset;

    public OneDCoordinates(ArrayList<Double> Data, int length, int offset){
        this.Data = Data;
        this.length = length;
        this.offset = offset;

    }

    public ArrayList<Integer> getCoordinates() {
        //Data min und max berechnen, auf length verteilen

        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for(Double v : Data){
            if (v < min){
                min = v;
            }

            if(v > max){
                max = v;
            }
        }

        double range = max - min;
        ArrayList<Integer> coordinates = new ArrayList<Integer>(Data.size());
        if(range == 0){
            for (int value : coordinates){
                value = (length/2) + offset;
            }
        }else{
            double scale = (length / range) * 0.8; //um nicht volle Breite zu nutzen: *0.8

            int i = 0;
            for(Double v : Data){
                //alles auf Ursprung beziehen: min abziehen
                double result = ((v - min) + (0.1 *length)) + offset; // um 10 % vom Ursprung entfernt anzufangen
                coordinates.add(i, (int)result);
                i++;
            }
        }

        return coordinates;
    }
}
