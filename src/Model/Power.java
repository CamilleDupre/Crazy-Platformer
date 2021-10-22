package Model;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Power {

	/**
	 * Attribute type, wall pass or juper jump
	 */
	private int type;
	
	/**
	 * Attribute duration of the power, time in milliseconds
	 */
	private int duration;
	
	/**
	 * Attribute position of the power
	 */
	private Point2D position;

	
	/**
	 * Constructor of Power
	 * @param type
	 * @param duration
	 * @param position
	 */
	public Power(int type, int duration, Point2D position) {
		setType(type);
		setDuration(duration);
		setPosition(position);
	}

	
	// Getter & Setter 
	
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
