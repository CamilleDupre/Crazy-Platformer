package Model;

import javafx.geometry.Point2D;

public class Enemy {

	/**
	 * Attribute dead, if dead the enemy can not deal damage
	 */
	private boolean dead;
	
	/**
	 * Attribute position, x & y in the level
	 */
	private Point2D position;
	
	/**
	 * Constructor of the Enemy
	 * @param position
	 * @param dead
	 */
	public Enemy(Point2D position, boolean dead) {
		this.position = position;
		this.dead = dead;
	}

	/**
	 * Getter for gameOver
	 * @return position
	 */
	public Point2D getPosition() {
		return position;
	}

	/**
	 * Setter for position
	 * @param position
	 */
	public void setPosition(Point2D position) {
		this.position = position;
	}

	/**
	 * Getter for gameOver
	 * @return dead
	 */
	public boolean isDead() {
		return dead;
	}

	/**
	 * Setter for dead
	 * @param dead
	 */
	public void setDead(boolean dead) {
		this.dead = dead;
	}
	
	
}
