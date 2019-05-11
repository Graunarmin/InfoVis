package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.CellData;
import infovis.scatterplot.Data;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
	

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update
		ArrayList<Data> data = model.getList();
		int dim = model.getDim();
		int axisDistance = WIDTH / dim;

		//draw parallel axes (one for each label)
		for(int a = 0; a < dim; a++){
			g2D.draw(axis);
			g2D.translate(axisDistance, 0);

		}

		//translate it back
		g2D.translate(-1100, 0);

		int i = 0;

		//Horizontal labels
		for (String l : model.getLabels()) {
			g.setFont (myFont);
			//g2D.drawString(l,XOFFST+i,YOFFST-5);	//Draws labels starting at position and height
			drawRotate(g2D,XOFFST+i,YOFFST-5,-15,l);
			i += (WIDTH/dim);	//moves next label a bit to the right
		}
		
		for(int y = 0; y < dim; y++){
			
			ArrayList<Double> yData = new ArrayList<>();
			for(Data d: data) {
				
				yData.add(d.getValue(y));
				System.out.println(d.getValue(y));
			}
			System.out.println("-");
			
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
