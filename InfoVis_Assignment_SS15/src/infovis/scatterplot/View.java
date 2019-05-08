package infovis.scatterplot;

import infovis.debug.Debug;
import javafx.scene.control.Cell;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

import javax.swing.JPanel;

public class View extends JPanel {

	     private Model model = null;

	     //Matrix scaffold
	     private static final int WIDTH = 650;
	     private static final int HEIGHT = 650;
	     private static final int XOFFSET = 120;
	     private static final int YOFFSET = 25;
	     private Rectangle2D matrixFrame = new Rectangle2D.Double(XOFFSET, YOFFSET, WIDTH, HEIGHT);
	     private Line2D horizontalSegmentLine = new Line2D.Double(XOFFSET, YOFFSET, XOFFSET + WIDTH,YOFFSET);
	     private Line2D verticalSegmentLine = new Line2D.Double(XOFFSET,YOFFSET + 7, XOFFSET, YOFFSET + 7 + HEIGHT - 1);

	     //Marker
		 private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}

		// Paint Methode
		@Override
		public void paint(Graphics g) {

		 	int dim = model.getDim(); // = Amount of Labels
		 	int cellWidth = WIDTH / dim;
		 	int cellHeight = HEIGHT / dim;
			ArrayList<Data> data = model.getList();
			ArrayList<Point2D> dataPoints = new ArrayList<Point2D>();


			Graphics2D g2D = (Graphics2D) g;
			g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update

			//Matrix Frame
			g2D.draw(matrixFrame);

			//draw the horizontal lines into the matrix
			for (int i = 0; i < dim; i++){
				g2D.translate(0, cellWidth);

				//taking out the last line while keeping the right distances
				if(i != dim -1){
					g2D.draw(horizontalSegmentLine);
				}
			}

			//translate it back
			g2D.translate(0, -650);

			//draw the vertical lines into the matrix
			for (int i = 0; i < dim; i++){	//Cheating a bit by taking out the last line so it doesn't look ugly
				g2D.translate(cellWidth, 0);

				//taking out the last line while keeping the right distances
				if(i != dim-1){
					g2D.draw(verticalSegmentLine);
				}
			}

			//translate it back
			g2D.translate(-650, 0);

			g2D.scale(0.7, 0.7);	//Scales labels down

			int i = 0;

			//Horizontal labels
			for (String l : model.getLabels()) {
				g2D.drawString(l,200+i,25);	//Draws labels starting at position and height
				i += (650/(dim-2));	//moves next label a bit to the right
			}

			int j = 0;

			//g2D.rotate(1.75);	//Theoretically a way to rotate the labels for the sides but every one rotates differently

			//Vertical labels
			for (String l : model.getLabels()) {
				g2D.drawString(l, /*1150*/ 15, 100 + j);
				j += (650 / (dim - 2));
			}

			//Matrix Zellen:
			// (0,0) | (0,1) | (0,2) | (0,3) | ...
			// (1,0) | (1,1) | (1,2) | (1,3) | ...
			// (2,0) | (2,1) | (2,2) | (2,3) | ...
			// ...

			for(int y = 0; y < dim; ++y){
				for(int x = 0; x < dim; ++x){

					ArrayList<Double> xData = new ArrayList<>();
					ArrayList<Double> yData = new ArrayList<>();

					for(Data d: data) {
						xData.add(d.getValue(x));
						yData.add(d.getValue(y));



//						Point2D dataPoint = new Point2D.Double((int)(d.getValue(a) + 25),
//								(int) (d.getValue(b)) + 120);
//
//						dataPoints.add(dataPoint);
					}

					//get coordinates
					CellData celldata = new CellData(xData, yData, cellWidth, cellHeight);
					ArrayList<Integer> xCoords = celldata.getPointXCoordinates(x, XOFFSET);
					ArrayList<Integer> yCoords = celldata.getPointYCoordinates(y, YOFFSET);

					for(int c = 0; c < xCoords.size(); c++){
						Point2D dataPoint = new Point2D.Double(xCoords.get(c), yCoords.get(c));
						dataPoints.add(dataPoint);
					}
				}
			}

			for(int k = 0; k <dataPoints.size(); ++k) {
				g2D.setColor(data.get(k % data.size()).getColor());
				g2D.drawOval( (int) dataPoints.get(k).getX() - 3, (int) dataPoints.get(k).getY() - 3, 6, 6);
			}



			Debug.print("Ranges [smallest and greatest value]: ");
			Debug.println("");
			for (Range range : model.getRanges()) {

				Debug.print(range.toString());
				Debug.print(",  ");
				Debug.println("");
			}

			Debug.print("Lists: ");
			Debug.println("");
			for (Data d : model.getList()) {
				Debug.print(d.toString());
				Debug.println("");
			}
			Debug.println("");
			Debug.print("List Size: " + model.getList().size());
		}


		public void setModel(Model model) {
			this.model = model;
		}
}
