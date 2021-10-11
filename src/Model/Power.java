package Model;

import javafx.geometry.Pos;

public class Power {

	private int type;
	private int duration; //time in milliseconds
	private Pos position;
	private boolean disable;
	
	public Power(int type, int duration, Pos position, boolean disable) {
		setType(type);
		setDuration(duration);
		setPosition(position);
		setDisable(disable);
		
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

	public Pos getPosition() {
		return position;
	}

	public void setPosition(Pos position) {
		this.position = position;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	
	
}
