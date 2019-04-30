package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,500,500);
	     private Rectangle2D matrixFrame = new Rectangle2D.Double(100,25, 500, 500);
	     private Line2D horizontalSegmentLine = new Line2D.Double(100, 25, 600, 25);
	     private Line2D verticalSegmentLine = new Line2D.Double(100, 25, 100, 525);

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {

			Graphics2D g2D = (Graphics2D) g;


			//Matrix Frame
			g2D.draw(matrixFrame);

			//draw the horizontal lines into the matrix
			for (int i = 0; i < model.getDim(); i++){
				g2D.translate(0, 500/model.getDim());
				g2D.draw(horizontalSegmentLine);
			}

			//translate it back
			g2D.translate(0, -500);

			//draw the vertical lines into the matrix
			for (int i = 0; i < model.getDim(); i++){
				g2D.translate(500/model.getDim(), 0);
				g2D.draw(verticalSegmentLine);

			}




			for (String l : model.getLabels()) {//Categories
				Debug.print(l + "*");
				Debug.print(",  ");
				Debug.println("");	//New line
				
			}
			for (Range range : model.getRanges()) {
				Debug.print(range.toString() + "+");
				Debug.print(",  ");
				Debug.println("");
			}
			for (Data d : model.getList()) {
				Debug.print(d.toString() + "#");
				Debug.println("");
			}
	        
			
		}
		public void setModel(Model model) {
			this.model = model;
		}
}
