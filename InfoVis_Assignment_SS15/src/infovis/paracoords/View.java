package infovis.paracoords;

import infovis.scatterplot.Model;
import infovis.scatterplot.Data;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

public class View extends JPanel {
	private Model model = null;

	Font myFont = new Font ("Courier New", 1, 10);

	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600;
	private static final int XOFFST = 50;
	private static final int YOFFST = 50;
	private Line2D axis = new Line2D.Double(XOFFST, YOFFST, XOFFST, YOFFST + HEIGHT);

	@Override
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.clearRect(0, 0, getWidth(), getHeight());  //Delete everything before update

		int dim = model.getDim();
		int axisDistance = WIDTH / dim;

		//draw parallel axes (one for each label)
		for(int a = 0; a < dim; a++){
			g2D.draw(axis);
			g2D.translate(axisDistance, 0);

		}

		//translate it back
		g2D.translate(-1000, 0);

		//g2D.scale(0.7, 0.7);	//Scales labels down

		int i = 0;

		//Horizontal labels
		for (String l : model.getLabels()) {
			g.setFont (myFont);
			g2D.drawString(l,XOFFST+i,YOFFST-5);	//Draws labels starting at position and height
			i += (WIDTH/dim);	//moves next label a bit to the right
		}

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
