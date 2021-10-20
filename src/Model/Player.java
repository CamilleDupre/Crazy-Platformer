package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Player {

	public static final int PLAYER_WIDTH = 70;
	public static final int PLAYER_HEIGHT = 100;

	private Point2D position;
	private ArrayList<Power> powerList;

	private boolean invincibleAfterAttack;
	private Point2D playerSize;
	private int playerSpeed = 7;
	private int playerJump = - 220;

	private int life;

	private boolean jumping;
	private boolean inTheAir;

	private boolean invisible;

	private int nbCoinsCollected;
	private boolean superJump;
	private boolean Attacking;


	public Player(){
		this.position = new Point2D(20,Model.MIN_FLOOR_HEIGHT);
		this.powerList = new ArrayList<Power>();
		this.invincibleAfterAttack = false;
		setPlayerSize(new Point2D(PLAYER_WIDTH, PLAYER_HEIGHT));
		setJumping(false);
		setInTheAir(true);
		this.setLife(3);
		setInvisible(false);

		this.nbCoinsCollected = 0;
		this.setSuperJump(false) ;


		//this.setJumping(true);
	}

	//return 0 if not touching 1 if touching on leftside and 2 if touching rightside
	public int isPlayerTouchingBlock(Block block) {

		if(block.getPosition().getY() + block.getHeight() > this.position.getY() &&
				block.getPosition().getY() < this.position.getY() + this.playerSize.getY())
		{
			if((block.getPosition().getX() + block.getWidth() < this.position.getX() + this.playerSize.getX())){
				if(block.getPosition().getX() + block.getWidth() > this.position.getX()) {
					return 1;
				}else {
					return 0;
				}
			}else {
				if(block.getPosition().getX() > this.position.getX() + this.playerSize.getX()) {
					return 0;
				}else {
					return 2;
				}
			}

		}
		return 0;
	}



	public boolean isPlayerTouchingObject(Point2D objectPosition, int objectWidth, int objectHeight) {
		return (objectPosition.getX() + objectWidth > this.position.getX() &&
				objectPosition.getX() < this.position.getX() + this.playerSize.getX() &&
				objectPosition.getY() + objectHeight > this.position.getY() &&
				objectPosition.getY() < this.position.getY() + this.playerSize.getY());
	}
	


	public boolean isPlayerAttackingEnemies(Point2D objectPosition, int objectWidth) {
		return (objectPosition.getX() + objectWidth > this.position.getX() &&
				objectPosition.getX() < this.position.getX() + this.playerSize.getX());
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

	public boolean isInTheAir() {
		return inTheAir;
	}

	public void setInTheAir(boolean inTheAir) {
		this.inTheAir = inTheAir;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}


	/**
	 * Getter for nbCoinsCollected
	 * @return
	 */
	public int getNbCoinsCollected() {
		return nbCoinsCollected;
	}

	/**
	 * Setter for nbCoinsCollected
	 * @param nbCoinsCollected
	 */
	public void setNbCoinsCollected(int nbCoinsCollected) {
		this.nbCoinsCollected = nbCoinsCollected;

	}

	public boolean isSuperJump() {
		return superJump;
	}

	public void setSuperJump(boolean superJump) {
		this.superJump = superJump;
	}

	public boolean isAttacking() {
		return Attacking;
	}

	public void setAttacking(boolean attacking) {
		Attacking = attacking;
	}
}
