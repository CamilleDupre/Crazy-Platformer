package Model;

import javafx.geometry.Point2D;

public class Block {

	/**
	 * Attribute position, x & y in the level
	 */
	private Point2D position;
	
	/**
	 * Attribute width of the block
	 */
	private int width;
	
	/**
	 * Attribute height of the block
	 */
	private int height;
	
	/**
	 * Attribute invisible, special block for wall pass power
	 */
	private boolean invisible;
	
	/**
	 * Constructor of Block
	 * @param position
	 * @param width
	 * @param height
	 * @param invisible
	 */
	public Block(Point2D position, int width, int height,boolean invisible) {
		
		this.setPosition(position);
		this.setWidth(width);
		this.setHeight(height);
		this.setInvisible(invisible);
	}
	
	
	//Getter & Setter // 

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
