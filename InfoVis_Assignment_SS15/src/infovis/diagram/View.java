package infovis.diagram;

import infovis.diagram.elements.Element;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;



public class View extends JPanel{
	private Model model = null;
	private Color color = Color.PINK; //background color frame
	private double scale = 1;
	private double translateX= 0;
	private double translateY=0;
	private Rectangle2D marker = new Rectangle2D.Double(0,0,0,0);
	private Rectangle2D overviewRect = new Rectangle2D.Double(550, 50, 240, 125);   //(x,y, height, width)

	public Model getModel() {
		return model;
	}
	public void setModel(Model model) {
		this.model = model;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	
	public void paint(Graphics g) {
	    
	    Graphics2D g2D = (Graphics2D) g;
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);  //Antialiasing
	    g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update
	    
	    //move and scale the original blobs:
	    g2D.scale(scale, scale);  //Scales big diagram (Zooming)
	    g2D.translate(-translateX, -translateY);	//Move blobs like the move but the other way
	    paintDiagram(g2D);  //Draws big diagram 
	    
	    //overview window
	    g2D.translate(translateX, translateY);  //Translate the overviewRect back so it stays in the same place
	    g2D.scale(1/scale, 1/scale);  //Takes scale away for tiny blobs and overview
	    g2D.setColor(Color.WHITE);  //Sets background color for overviewRect
	    g2D.fill(overviewRect);    //Fills overviewRect
	    g2D.setColor(Color.BLACK);  //Sets frame for overviewRect
	    g2D.draw(overviewRect);
	    
	    //Tiny blobs in overview window:
	    g2D.translate(575, 50);  //Moves &
	    g2D.scale(0.17, 0.17);  //Scales the blobs
	    
	    paintDiagram(g2D);  //repaint to get second set of blobs
	    
	    //Marker
	    g2D.setColor(Color.RED); //Sets frame of marker
	    marker.setRect(0,0,getWidth(),getHeight());  //Adjusts marker to size of window
	    g2D.scale(1/scale, 1/scale);	//Scale marker so it fits with the zooming
	    g2D.translate(translateX, translateY);  //Let's you move marker around 
	    g2D.draw(marker);  //Draw marker
	    
	    
	}
	private void paintDiagram(Graphics2D g2D){
		for (Element element: model.getElements()){
			element.paint(g2D); // paint Methode in Vertex Z. 49
		}
	}
	
	public void setScale(double scale) {
		this.scale = scale;
	}
	public double getScale(){
		return scale;
	}
	public double getTranslateX() {
		return translateX;
	}
	public void setTranslateX(double translateX) {
		this.translateX = translateX;
	}
	public double getTranslateY() {
		return translateY;
	}
	public void setTranslateY(double tansslateY) {
		this.translateY = tansslateY;
	}
	public void updateTranslation(double x, double y){
		setTranslateX(x);
		setTranslateY(y);
	}	
	public void updateMarker(int x, int y){
		marker.setRect(x, y, 16, 10);
	}
	public Rectangle2D getMarker(){
		return marker;
	}
	public boolean markerContains(int x, int y){
		return marker.contains(x, y);
	}
}
 