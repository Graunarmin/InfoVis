package infovis.scatterplot;

import java.util.ArrayList;

public class OneDCoordinates {

    //1-D Koordinaten (man kann halt die x oder die y Achse reinschieben
    //und bekommt dann die x- bzw. die y-Koordinaten der jeweiligen Punkte

    private ArrayList<Double> data;
    private int length;
    private int offset;

    //Constructor
    public OneDCoordinates(ArrayList<Double> data, int length, int offset){
        this.data = data;
        this.length = length;
        this.offset = offset;
    }


    public ArrayList<Integer> getCoordinates() {

        //Data min und max berechnen, auf length verteilen

        //calculate range from min and max value
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;

        for(Double value : data){
            if (value < min){
                min = value;
            }

            if(value > max){
                max = value;
            }
        }
        double range = max - min;


        //calculate coordinates
        ArrayList<Integer> coordinates = new ArrayList<Integer>(data.size());
        double scale = 1;
        if(range == 0) {
            //wenn alle Punkte auf einer Linie liegen: in die Mitte holen
            //scale * 0.8, um nicht die volle Breite der Zelle zu nutzen
            scale = (length/2) * 0.8;
        }
        else{
            //scale * 0.8, um nicht die volle Breite der Zelle zu nutzen
            scale = (length/range) * 0.8;
        }
        int i = 0;
        for(Double v : data){
            //alles auf Ursprung beziehen: min vom value abziehen
            // length * 0.1 um 10 % vom Ursprung entfernt anzufangen
            // + offset, um an die richtige Stelle zu verschieben
            double result = scale * (v - min) + (0.1 * length) + offset;
            coordinates.add(i, (int)result);
            i++;
        }

        return coordinates;
    }
}