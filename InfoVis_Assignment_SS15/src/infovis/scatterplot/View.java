package infovis.scatterplot;

import infovis.debug.Debug;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class View extends JPanel {
	     private Model model = null;
	     private Rectangle2D markerRectangle = new Rectangle2D.Double(0,0,500,500); 

		 public Rectangle2D getMarkerRectangle() {
			return markerRectangle;
		}
		 
		@Override
		public void paint(Graphics g) {

			Graphics2D g2D = (Graphics2D) g;
			
			g2D.draw(markerRectangle);
			
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
