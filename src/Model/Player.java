package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Player {
	
	private Point2D position;
	private ArrayList<Power> powerList;
	
	private boolean invincibleAfterAttack;
	private int playerSize;
	
	private int jumpHeight = 10;
	
	public Player(){
		this.position = new Point2D(20,50);//Model.MIN_FLOOR_HEIGHT);
		this.powerList = new ArrayList<Power>();
		this.invincibleAfterAttack = false;
		setPlayerSize(20);
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

	public int getJumpHeight() {
		return jumpHeight;
	}

	public void setJumpHeight(int jumpHeight) {
		this.jumpHeight = jumpHeight;
	}

	public int getPlayerSize() {
		return playerSize;
	}

	public void setPlayerSize(int playerSize) {
		this.playerSize = playerSize;
	}
}
