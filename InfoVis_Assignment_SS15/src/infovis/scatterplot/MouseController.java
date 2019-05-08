package infovis.scatterplot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController implements MouseListener, MouseMotionListener {

	private Model model = null;
	private View view = null;

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent event) {
		//switch off marker rectangle with one click:
		int x = event.getX();
		int y = event.getY();
		//Iterator<Data> iter = model.iterator();
		view.getMarkerRectangle().setRect(x, y,0,0);
		view.repaint();
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseDragged(MouseEvent event) {
		int x = event.getX();
		int y = event.getY();

		//get position of marker rectangle
		int markerX = (int) view.getMarkerRectangle().getX();
		int markerY = (int) view.getMarkerRectangle().getY();

		//calculate how far the marker has been dragged
		double markerWidth = x - markerX;
		double markerHeight = y - markerY;

		//set Marker Rectangle to new position and size
		view.setMarkerRectangle(markerX, markerY, markerWidth, markerHeight);
		view.repaint();
	}

	public void mouseMoved(MouseEvent arg0) {
	}

	public void setModel(Model model) {
		this.model  = model;	
	}

	public void setView(View view) {
		this.view  = view;
	}

}
