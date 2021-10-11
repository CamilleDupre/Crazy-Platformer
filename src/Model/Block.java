package Model;

import javafx.geometry.Point2D;

public class Block {

	private Point2D position;
	private int width;
	private int height;
	private boolean invisible;
	
	public Block(Point2D position, int width, int height,boolean invisible) {
		
		this.setPosition(position);
		this.setWidth(width);
		this.setHeight(height);
		this.setInvisible(invisible);
	}
	

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}


	public boolean isInvisible() {
		return invisible;
	}


	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}
	
	
}
