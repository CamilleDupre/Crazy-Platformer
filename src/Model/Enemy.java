package Model;

import javafx.geometry.Point2D;

public class Enemy {

	private boolean dead;
	private Point2D position;
	
	
	public Enemy(Point2D position, boolean dead) {
		this.position = position;
		this.dead = dead;
	}


	public Point2D getPosition() {
		return position;
	}


	public void setPosition(Point2D position) {
		this.position = position;
	}


	public boolean isDead() {
		return dead;
	}


	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
}
