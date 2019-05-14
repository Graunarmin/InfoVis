package infovis.scatterplot;

import infovis.debug.Debug;
import javafx.scene.control.Cell;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {

	     private Model model = null;
	     private boolean cars = false;
	     private boolean cameras = false;

	     //Matrix scaffold
		 Font myFont;
		 private int WIDTH = 0;
		 private int HEIGHT = 0;
		 private int XOFFSET = 0;
		 private int YOFFSET = 0;


	     private Rectangle2D matrixFrame;
	     private Line2D horizontalSegmentLine;
	     private Line2D verticalSegmentLine;

	     //Marker
		 private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}

		public void setMarkerRectangle(int x, int y, double width, double height){
			markerRectangle.setRect(x, y, width, height);
		}

		public void setScaffold(){

		 	//determine which file is used
			if(model.getUsedFile().equals("cameras.ssv")){
				cameras = true;
				cars = false;
				myFont = new Font ("Arial", 1, 7);
		 		WIDTH = 700;
			 	HEIGHT = 700;
	     		XOFFSET = 110;
	     		YOFFSET = 40;

			}else{
				cars = true;
				cameras = false;
				myFont = new Font ("Arial", 1, 8);
				WIDTH = 650;
				HEIGHT = 650;
				XOFFSET = 120;
				YOFFSET = 25;
			}

			matrixFrame = new Rectangle2D.Double(XOFFSET, YOFFSET, WIDTH, HEIGHT);
			horizontalSegmentLine = new Line2D.Double(XOFFSET, YOFFSET, XOFFSET + WIDTH, YOFFSET);
			verticalSegmentLine = new Line2D.Double(XOFFSET,YOFFSET + 7, XOFFSET, YOFFSET + 7 + HEIGHT - 1);

		}

		// Paint Methode
		@Override
		public void paint(Graphics g) {

		 	setScaffold();

		 	int dim = model.getDim(); // = Amount of Labels
		 	int cellWidth = WIDTH / dim;
		 	int cellHeight = HEIGHT / dim;
		 	int numberOfEntries = model.getList().size();
			ArrayList<Data> data = model.getList();
			ArrayList<Point2D> dataPoints = new ArrayList<Point2D>();
			ArrayList<String> labels = model.getLabels();


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
			g2D.translate(0, - HEIGHT);

			//draw the vertical lines into the matrix
			for (int i = 0; i < dim; i++){	
				g2D.translate(cellWidth, 0);

				//taking out the last line while keeping the right distances
				if(i != dim-1){
					g2D.draw(verticalSegmentLine);
				}
			}

			//translate it back
			g2D.translate(- WIDTH, 0);

			g.setFont (myFont);

			//Horizontal labels
			int i = 0;
			for (String l : model.getLabels()) {
				if(cameras){
					drawRotate(g2D, XOFFSET + 5 + i, YOFFSET,-45, l);
				}
				else{
					g2D.drawString(l,XOFFSET + 10 + i, YOFFSET-5);	//Draws labels starting at position and height
				}

				i += (WIDTH/dim);	//moves next label a bit to the right
			}

			//Vertical labels
			int j = 0;
			for (String l : labels) {
				g2D.drawString(l, 15, (YOFFSET + cellHeight/2) + j);
				j += (HEIGHT / dim);
			}

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
						System.out.println("x: ");
						System.out.println(d.getValue(x));
						yData.add(d.getValue(y));
						System.out.println("y: ");
						System.out.println(d.getValue(y));
					}
					System.out.println("-");

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
				g2D.drawOval((int) point.getX(), (int) point.getY(), 3, 3);
				//g2D.fillOval((int) point.getX(), (int) point.getY(), 4, 4);
			}

			//change color of all corresponding points if point gets selected
			for(Point2D point : dataPoints) {
				if(markerRectangle.contains(point)){
					//labels.get(dataPoints.indexOf(point));
					for(i = dataPoints.indexOf(point) % numberOfEntries; i < dataPoints.size(); i += numberOfEntries){
						g2D.setColor(Color.ORANGE);
						g2D.drawOval((int) dataPoints.get(i).getX(), (int) dataPoints.get(i).getY(), 3, 3);
						g2D.fillOval((int) dataPoints.get(i).getX(), (int) dataPoints.get(i).getY(), 3, 3);
					}
				}
			}
		}

	public static void drawRotate(Graphics2D g2d, double x, double y, int angle, String text) {
		//to rotate the labels right
		g2d.translate((float)x,(float)y);
		g2d.rotate(Math.toRadians(angle));
		g2d.drawString(text,0, 0);
		g2d.rotate(-Math.toRadians(angle));
		g2d.translate(-(float)x,-(float)y);
	}


	public void setModel(Model model) {
			this.model = model;
		}
}
