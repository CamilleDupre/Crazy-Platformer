package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;

public class Player {

	/**
	 * Attribute width of the player
	 */
	public static final int PLAYER_WIDTH = 70;

	/**
	 * Attribute height of the player
	 */
	public static final int PLAYER_HEIGHT = 100;

	/**
	 * Attribute position of the player in the level
	 */
	private Point2D position;
	private ArrayList<Power> powerList;

	/**
	 * Attribute invincibleAfterAttack, player has a 1s invisibility
	 */
	private boolean invincibleAfterAttack;


	private Point2D playerSize;
	
	/**
	 * Attribute playerSpeed, speed for right and left movement
	 */
	private int playerSpeed = 7;
	
	/**
	 * Attribute playerJump, height for a jump
	 */
	private int playerJump = - 220;

	/**
	 * Attribute number of life of the player
	 */
	private int life;

	/**
	 * Attribute nb of coins collected in the level
	 */
	private int nbCoinsCollected;

	/**
	 * Attribute jumping, true if player is jumping
	 */
	private boolean jumping;

	/**
	 * Attribute intheAir, true if the player is in the air
	 */
	private boolean inTheAir;

	/**
	 * Attribute invisible, true if wall pass power up
	 */
	private boolean invisible;
	
	/**
	 *Attribute superJump, true if super jump power true 
	 */
	private boolean superJump;
	
	/**
	 * Attribute Attacking, true if player is Attacking
	 */
	private boolean Attacking;

	/**
	 * Constructor of the Enemy
	 */
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
	}

	/**
	 * Used to know if the player hits a block horizontally and on which side
	 * @param block
	 * @return
	 */
	public int isPlayerTouchingBlock(Block block) {
		//return 0 if not touching 1 if touching on leftside and 2 if touching rightside
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


	/**
	 * used to know if the player hits an object
	 * @param objectPosition
	 * @param objectWidth
	 * @param objectHeight
	 * @return
	 */
	public boolean isPlayerTouchingObject(Point2D objectPosition, int objectWidth, int objectHeight) {
		return (objectPosition.getX() + objectWidth > this.position.getX() &&
				objectPosition.getX() < this.position.getX() + this.playerSize.getX() &&
				objectPosition.getY() + objectHeight > this.position.getY() &&
				objectPosition.getY() < this.position.getY() + this.playerSize.getY());
	}


	/**
	 * used to know if the player hits an enemy
	 * @param objectPosition
	 * @param objectWidth
	 * @return
	 */
	public boolean isPlayerAttackingEnemies(Point2D objectPosition, int objectWidth) {
		return (objectPosition.getX() + objectWidth > this.position.getX() &&
				objectPosition.getX() < this.position.getX() + this.playerSize.getX());
	}


	
	//Getter & Setter 
	
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
	 * @return nbCoinsCollected
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
