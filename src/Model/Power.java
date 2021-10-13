package Model;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Power {

	private int type;
	private int duration; //time in milliseconds
	private Point2D position;

	
	public Power(int type, int duration, Point2D position) {
		setType(type);
		setDuration(duration);
		setPosition(position);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	
}
