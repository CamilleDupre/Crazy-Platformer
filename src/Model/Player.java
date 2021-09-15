package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Player {
	
	private Point2D position;
	private ArrayList<Power> powerList;
	
	private boolean invincibleAfterAttack;
	private int playerSize;
	private int playerSpeed=10;
	private int playerJump=100;
	
	private boolean jumping;
	
	public Player(){
		this.position = new Point2D(20,50);//Model.MIN_FLOOR_HEIGHT);
		this.powerList = new ArrayList<Power>();
		this.invincibleAfterAttack = false;
		setPlayerSize(80);
		setJumping(false);
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


	public int getPlayerSize() {
		return playerSize;
	}

	public void setPlayerSize(int playerSize) {
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
