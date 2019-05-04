package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,0,0);
	     private Rectangle2D matrixFrame = new Rectangle2D.Double(100,25,650,650);
	     private Line2D horizontalSegmentLine = new Line2D.Double(100,25,750,25);
	     private Line2D verticalSegmentLine = new Line2D.Double(100,32,100,681);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {

		 	int dim = model.getDim();
		 	int segmentLineDistance = 650 / dim;


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
				g2D.drawString(l,180+i,25);	//Draws labels starting at position and height
				i += (650/(dim-2));	//moves next label a bit to the right
			}

			int j = 0;

			//g2D.rotate(1.75);	//Theoretically a way to rotate the labels for the sides but every one rotates differently

			//Vertical labels
			for (String l : model.getLabels()) {
				g2D.drawString(l, 1150, 100 + j);
				j += (650 / (dim - 2));
			}


//			for (Data d : model.getList()) {
//				for (Point2D points : carSpecs.get(d.getLabel().hashCode()) ) {
//					g.setColor(Color.BLUE);
//					g.drawOval((int)points.getX(), (int)points.getY(), 2, 2);
//					g.fillOval((int)points.getX(), (int)points.getY(), 2, 2);
//				}
//			}

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
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
}
