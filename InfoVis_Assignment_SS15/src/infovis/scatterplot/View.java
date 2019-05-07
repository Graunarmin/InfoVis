package infovis.scatterplot;

import infovis.debug.Debug;

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
	     private Rectangle2D matrixFrame = new Rectangle2D.Double(120,25, WIDTH, HEIGHT);
	     private Line2D horizontalSegmentLine = new Line2D.Double(120,25, 120 + WIDTH,25);
	     private Line2D verticalSegmentLine = new Line2D.Double(120,32,120, 32 + HEIGHT - 1);

	     //Marker
		 private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {

		 	int dim = model.getDim(); // = Amount of Labels
		 	int segmentLineDistance = WIDTH / dim;
			ArrayList<Data> data = model.getList();
			ArrayList<Point2D> dataPoints = new ArrayList<Point2D>();


			Graphics2D g2D = (Graphics2D) g;
			g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update

			//Matrix Frame
			g2D.draw(matrixFrame);

			//draw the horizontal lines into the matrix
			for (int i = 0; i < dim; i++){
				g2D.translate(0, segmentLineDistance);

				//taking out the last line while keeping the right distances
				if(i != dim -1){
					g2D.draw(horizontalSegmentLine);
				}
			}

			//translate it back
			g2D.translate(0, -650);

			//draw the vertical lines into the matrix
			for (int i = 0; i < dim; i++){	//Cheating a bit by taking out the last line so it doesn't look ugly
				g2D.translate(segmentLineDistance, 0);

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

			for(int a = 0; a < dim; ++a){
				for(int b = 0; b < dim; ++b){


					for(Data d: data) {



						Point2D dataPoint = new Point2D.Double((int) (((d.getValue(a)) * HEIGHT) /   HEIGHT + 50),
								(int) (((d.getValue(b))) * WIDTH) / ( WIDTH + 50));

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
