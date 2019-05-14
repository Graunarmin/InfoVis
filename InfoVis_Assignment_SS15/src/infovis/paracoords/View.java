package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.Data;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;


import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;

	Font myFont = new Font ("Courier New", 1, 10);

	private static final int WIDTH = 1100;
	private static final int HEIGHT = 600;
	private static final int XOFFST = 50;
	private static final int YOFFST = 50;
	private Line2D axis = new Line2D.Double(XOFFST, YOFFST, XOFFST, YOFFST + HEIGHT);
	private ArrayList<Line2D> connectionLines = new ArrayList<>();
	private Line2D connectionLine = new Line2D.Double(0,0,0,0);
	private int cameraOffset = 0;
	private int clickedX = 0;
	private int clickedY = 0;
	private boolean pointClicked = false;
	private boolean lineClicked = false;

	public void setClicked(int x, int y){
		clickedX = x;
		clickedY = y;
	}

	public void setCameraOffset(){
		//for camera data the x coords of the points need to be shifted a whee bit to the right
		if(model.getUsedFile().equals("cameras.ssv")){
			cameraOffset = 6;
		}
	}

	@Override
	public void paint(Graphics g) {

		setCameraOffset();

		ArrayList<Data> data = model.getList();
		ArrayList<Point2D> dataPoints = new ArrayList<Point2D>();
		int numberOfEntries = model.getList().size();
		int dim = model.getDim();
		int axisDistance = WIDTH / dim;

		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update

		//draw parallel axes (one for each label)
		for(int a = 0; a < dim; a++){
			g2D.draw(axis);
			g2D.translate(axisDistance, 0);
		}
		//translate it back
		g2D.translate( -WIDTH, 0);


		//Horizontal labels
		int i = 0;
		for (String l : model.getLabels()) {
			g.setFont (myFont);
			//g2D.drawString(l,XOFFST+i,YOFFST-5);	//Draws labels starting at position and height
			drawRotate(g2D,XOFFST+i,YOFFST-5,-15,l);
			i += (WIDTH/dim);	//moves next label a bit to the right
		}

		// x-Koordinate ist für jeden Eintrag pro Achse gleich;
		// für die nächste Achse einfach axisDistance addieren
		int xCoord = XOFFST + cameraOffset;

		//y Daten für jede Achse bestimmen:
		for(int y = 0; y < dim; y++){
			ArrayList<Double> yData = new ArrayList<>();
			for(Data d: data) {
				yData.add(d.getValue(y));
//				System.out.println(d.getLabel());
//				System.out.println(d.getValue(y));
			}
			//System.out.println("-");

			AxisDataPara axisdata = new AxisDataPara(yData, HEIGHT, YOFFST);
			ArrayList<Integer> yCoords = axisdata.getPointYCoordinates();

			//combine the coordinates to create points, store points in an array
			for(int c = 0; c < yCoords.size(); c++){
				Point2D dataPoint = new Point2D.Double(xCoord, yCoords.get(c));
				dataPoints.add(dataPoint);
			}

			//für die nächste Achse: x Koordinate verschieben
			xCoord += axisDistance;
			
		}
		//draw point array
		for(Point2D point : dataPoints) {
			g2D.setColor(Color.BLUE);
			g2D.drawOval((int) point.getX(), (int) point.getY(), 3, 3);
			g2D.fillOval((int) point.getX(), (int) point.getY(), 3, 3);
		}

		//draw lines between points to make a path for each object
		for(Point2D point : dataPoints) {
			for(int j = dataPoints.indexOf(point); j < dataPoints.size() - numberOfEntries; j++){
				g2D.setColor(Color.BLUE);
				connectionLine.setLine(dataPoints.get(j).getX(),dataPoints.get(j).getY(),
						dataPoints.get(j + numberOfEntries).getX(),dataPoints.get(j + numberOfEntries).getY());
				connectionLines.add(connectionLine);
				g2D.draw(connectionLine);
			}
		}

		// Highlighting a Path:
		// If a point was clicked:
		// (Problem: when point overlap there is only one path highlighted)
		for(Point2D point: dataPoints){
			if ((Math.abs(point.getX()-clickedX) <= 5) &&
					(Math.abs(point.getY()-clickedY) <= 5)){
				pointClicked = true;
				for(int p = dataPoints.indexOf(point) % numberOfEntries; p < dataPoints.size(); p += numberOfEntries) {
					g2D.setColor(Color.ORANGE);
					g2D.drawOval((int) dataPoints.get(p).getX(), (int) dataPoints.get(p).getY(), 3, 3);
					g2D.fillOval((int) dataPoints.get(p).getX(), (int) dataPoints.get(p).getY(), 3, 3);
					if(p <dataPoints.size() - numberOfEntries){
						connectionLine.setLine(dataPoints.get(p).getX(),dataPoints.get(p).getY(),
								dataPoints.get(p + numberOfEntries).getX(),dataPoints.get(p + numberOfEntries).getY());
						g2D.draw(connectionLine);
					}
				}
			}else{
				pointClicked = false;
			}
		}

		if(!pointClicked){

			// If a line was clicked:
			// NOT WORKING YET
			for(Line2D line : connectionLines){
				//add a little tolerance to the clicked point
				Rectangle2D clicked = new Rectangle(clickedX - 2, clickedY - 2, 4, 4);
				if(line.intersects(clicked)){
					lineClicked = true;
					//for(int l = connectionLines.indexOf(line) % dim; l < dim; l++) {
					g2D.setColor(Color.ORANGE);
					g2D.draw(line);
					//}

				}else{
					lineClicked = false;
				}
			}

			if(!lineClicked){
				//wenn nichts angeklickt wurde: alles zurücksetzen
				repaint();
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

	@Override
	public void update(Graphics g) {
		paint(g);
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}
	
}
