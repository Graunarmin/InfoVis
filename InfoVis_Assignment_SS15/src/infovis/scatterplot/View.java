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

		public void setMarkerRectangle(int x, int y, double width, double height){
			markerRectangle.setRect(x, y, width, height);
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

			//draw marker Rectangle
			g2D.setColor(Color.RED);
			g2D.draw(markerRectangle);
			g2D.setColor(Color.BLACK);


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
			for (int i = 0; i < dim; i++){	
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

			//Vertical labels
			for (String l : model.getLabels()) {
				g2D.drawString(l, 15, 100 + j);
				j += (650 / (dim - 2));
			}
			
			g2D.scale(1/0.7, 1/0.7);

			//Matrix Zellen:
			// (0,0) | (0,1) | (0,2) | (0,3) | ...
			// (1,0) | (1,1) | (1,2) | (1,3) | ...
			// (2,0) | (2,1) | (2,2) | (2,3) | ...
			// ...

			//run through the matrix line by line (cell index [x,y])
			//In Zelle (0,0) sind die xDaten die Markteinführung und die yDaten auch.
			//In Zelle (0,1) ist x dann Hubraum, y bleibt Markteinführung, in Zelle (0,2) ist x PS, y bleibt weiter Markteinführung usw.
			//in der zweiten Zeile ändern sich dann die yDaten, das ist nun Hubraum, und wir gehen wieder alle anderen Kategorien für x durch
			for(int y = 0; y < dim; y++){
				for(int x = 0; x < dim; x++){

					//get the right combination of Data for each cell (identified by it's index)
					ArrayList<Double> xData = new ArrayList<>();
					ArrayList<Double> yData = new ArrayList<>();
					for(Data d: data) {
						xData.add(d.getValue(x));
						yData.add(d.getValue(y));
					}

					//get coordinates
					CellData celldata = new CellData(xData, yData, cellWidth, cellHeight);
					ArrayList<Integer> xCoords = celldata.getPointXCoordinates(x, XOFFSET);
					ArrayList<Integer> yCoords = celldata.getPointYCoordinates(y, YOFFSET);

					//combine the coordinates to create points, store points in an array
					for(int c = 0; c < xCoords.size(); c++){
						Point2D dataPoint = new Point2D.Double(xCoords.get(c), yCoords.get(c));
						dataPoints.add(dataPoint);
					}
				}
			}

			//draw point array
			for(Point2D point : dataPoints) {
				g2D.setColor(Color.BLUE);
				g2D.drawOval((int) point.getX(), (int) point.getY(), 4, 4);
				//g2D.fillOval((int) point.getX(), (int) point.getY(), 4, 4);
			}


			for(Point2D point : dataPoints) {
				if(markerRectangle.contains(point)){
					for(Point2D p : dataPoints) {
						g2D.setColor(Color.ORANGE);
						g2D.drawOval((int) p.getX(), (int) p.getY(), 5, 5);
						g2D.fillOval((int) p.getX(), (int) p.getY(), 5, 5);
					}

				}
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
