package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Player {
	
	public static final int PLAYER_WIDTH = 80;
	public static final int PLAYER_HEIGHT = 100;
	
	private Point2D position;
	private ArrayList<Power> powerList;
	
	private boolean invincibleAfterAttack;
	private Point2D playerSize;
	private int playerSpeed = 7;
	private int playerJump = - 300;
	
	private boolean jumping;
	
	public Player(){
		this.position = new Point2D(20,Model.MIN_FLOOR_HEIGHT);
		this.powerList = new ArrayList<Power>();
		this.invincibleAfterAttack = false;
		setPlayerSize(new Point2D(PLAYER_WIDTH, PLAYER_HEIGHT));
		setJumping(false);
		
		this.setJumping(true);
	}
	
	public boolean isPlayerTouchingBlock(Block block) {	
		return (block.getPosition().getX() + block.getWidth() > this.position.getX() &&
				block.getPosition().getX() < this.position.getX() + this.playerSize.getX() &&
				block.getPosition().getY() + block.getHeight() > this.position.getY() &&
				block.getPosition().getY() < this.position.getY() + this.playerSize.getY());
	}
	
	public boolean isPlayerTouchingObject(Block block) {	
		return (block.getPosition().getX() + block.getWidth() > this.position.getX() &&
				block.getPosition().getX() < this.position.getX() + this.playerSize.getX() &&
				block.getPosition().getY() + block.getHeight() > this.position.getY() &&
				block.getPosition().getY() < this.position.getY() + this.playerSize.getY());
	}

	public Point2D getPosition() {
		return position;
	}

	public void setPosition(Point2D position) {
		this.position = position;
	}

	public ArrayList<Power> getPowerList() {
		return powerList;
	}

	public void setPowerList(ArrayList<Power> powerList) {
		this.powerList = powerList;
	}

	public boolean isInvincibleAfterAttack() {
		return invincibleAfterAttack;
	}

	public void setInvincibleAfterAttack(boolean invincibleAfterAttack) {
		this.invincibleAfterAttack = invincibleAfterAttack;
	}


	public Point2D getPlayerSize() {
		return playerSize;
	}

	public void setPlayerSize(Point2D playerSize) {
		this.playerSize = playerSize;
	}

	public int getPlayerJump() {
		return playerJump;
	}

	public void setPlayerJump(int playerJump) {
		this.playerJump = playerJump;
	}

	public int getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
}
