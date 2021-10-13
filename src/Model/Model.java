package Model;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Controller.Control;
import Sound.Sound;
import View.GameView;
import View.MenuView;
import javafx.geometry.Point2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class Model {

	/**
	 * Player facing left
	 */
	public static final int FACE_LEFT = 0;
	/**
	 * Player facing right
	 */
	public static final int FACE_RIGHT = 1;

	private MenuView menuView;
	private GameView gameView;
	private int cssStyle;
	private ArrayList<String> backgroundList;



	private Level currentLevel;
	private boolean gameOver;
	public static final int MIN_FLOOR_HEIGHT = MenuView.HEIGHT - 200 ;
	public static final int GRAVITY_FORCE = 7;
	public static final int JUMP_FORCE = 10;
	public static final int COINS_SIZE = 40;
	public static final int HEART_SIZE = 40;
	public static final int ENEMIES_HEIGHT = 80;
	public static final int ENEMIES_WIDTH = 65;
	public static final int TREASURE_HEIGHT = 100;
	public static final int TREASURE_WIDTH = 120;
	public static final int TRAP_WIDTH = 80;
	public static final int TRAP_HEIGHT = 50;
	private Player player;
	private int direction;
	private String imgPlayer="";

	private double time = 30000;

	//private Timer timer;

	private boolean gamePaused;

	private Sound sound;

	public Level LVL_1;
	public Level LVL_2;
	public Level LVL_3;
	public Level LVL_4;	
	public int nbCoinsCollected = 0 ;

	public double maxJumpHeight;
	
	private Timeline timeline;
	private static final Integer STARTTIME = 0; 
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);

	/**
	 * Constructor of the model
	 * @param menu
	 * @param game
	 * @param sound
	 */
	public Model(MenuView menu, GameView game,Sound sound) {
		this.menuView = menu;
		this.gameView = game;
		this.gameOver = false;
		initBackgroundRessources();
		this.player = new Player();
		this.direction = FACE_LEFT;
		this.gamePaused = false;
		this.sound = sound;
		this.imgPlayer="../Crazy-Platformer/img/other/player_right.png";
		//timer = new Timer(this);
	}


	/**
	 * Initialization of the background
	 */
	public void initBackgroundRessources() {
		backgroundList = new ArrayList<String>();
		backgroundList.add("/other/background_clair.png");
		backgroundList.add("/other/background_fonce.png");
		backgroundList.add("/other/background_neon.png");
	}

	/**
	 * Change the Css of the application
	 * @param cssId
	 */
	public void changeCSS(int cssId) {
		cssStyle = cssId;
		switch(cssStyle) {
		case 0 :
			menuView.getScene().getStylesheets().remove(0);
			menuView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("white.css").toExternalForm());

			if(menuView.getGameScene()!=null) {
				menuView.getGameScene().getStylesheets().remove(0);
				menuView.getGameScene().getStylesheets().add(getClass().getClassLoader().getResource("white.css").toExternalForm());
			}

			break;

		case 1 :
			menuView.getScene().getStylesheets().remove(0);
			menuView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("dark.css").toExternalForm());

			if(menuView.getGameScene()!=null) {
				menuView.getGameScene().getStylesheets().remove(0);
				menuView.getGameScene().getStylesheets().add(getClass().getClassLoader().getResource("dark.css").toExternalForm());
			}
			break;

		case 2 :
			menuView.getScene().getStylesheets().remove(0);
			menuView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("neon.css").toExternalForm());

			if(menuView.getGameScene()!=null) {
				menuView.getGameScene().getStylesheets().remove(0);
				menuView.getGameScene().getStylesheets().add(getClass().getClassLoader().getResource("neon.css").toExternalForm());
			}
			break;

		default :
			break;
		}
		menuView.getPrimaryStage().show();
	}

	/**
	 * Method used to get the current player's image
	 * Depends of the current player's direction
	 * @return player image, facing either left, right.
	 */
	public String getImgPlayer() {

		if (this.direction == Model.FACE_LEFT) {
			this.imgPlayer = "../Crazy-Platformer/img/other/player_left.png";

		} else if (this.direction == Model.FACE_RIGHT) {
			this.imgPlayer = "../Crazy-Platformer/img/other/player_right.png";

		} 
		return imgPlayer;
	}

	/**
	 * Move the player in the direction
	 * @param direction
	 */
	public void move(int direction) {
		int moveConstraint = 0;
		for(Block block : this.currentLevel.getBlocks()) {
			if(moveConstraint==0 && (!block.isInvisible() || !player.isInvisible())) {
				moveConstraint = player.isPlayerTouchingBlock(block);
			}
		}
		switch(moveConstraint) {
		case 0:
			if(direction == Control.LEFT) {
				//check if player isn't out of the level in the left side
				if(player.getPosition().getX() - player.getPlayerSpeed() > 30) {
					player.setPosition( new Point2D(player.getPosition().getX() - player.getPlayerSpeed(), player.getPosition().getY()));

				}
			}else if(direction == Control.RIGHT) {
				//check if player isn't out of the level in the right side
				if(player.getPosition().getX() + player.getPlayerSpeed() < currentLevel.maxSize - player.getPlayerSize().getX()) {
					player.setPosition( new Point2D(player.getPosition().getX() + player.getPlayerSpeed(), player.getPosition().getY()));
				}
			}
			break;

		case 1:
			//check if player isn't touching block on left side and allow moving only to right
			if(direction == Control.RIGHT) {
				//check if player isn't out of the level in the right side
				if(player.getPosition().getX() + player.getPlayerSpeed() < currentLevel.maxSize - player.getPlayerSize().getX()) {
					player.setPosition( new Point2D(player.getPosition().getX() + player.getPlayerSpeed(), player.getPosition().getY()));
				}
			}
			break;

		case 2:
			//check if player isn't touching block on right side and allow moving only to left
			if(direction == Control.LEFT) {	
				//check if player isn't out of the level in the left side
				if(player.getPosition().getX() - player.getPlayerSpeed() > 30) {
					player.setPosition( new Point2D(player.getPosition().getX() - player.getPlayerSpeed(), player.getPosition().getY()));
				}
			}

		}

	}


	/**
	 * Method to make the palyer jump
	 */
	public void makePlayerJump() {
		if(!player.isInTheAir()) {
			maxJumpHeight = player.getPosition().getY() + player.getPlayerJump();
			//player.setPosition( new Point2D(player.getPosition().getX(),player.getPosition().getY() + player.getPlayerJump())) ;
			player.setJumping(true);
			player.setInTheAir(true);
		}
	}

	/**
	 * Gravity force, make the palyers go down until hit the floor or a block
	 * Check for collision with coins, enemies, traps, treasure 
	 */
	public void gravityForce() {
		int highiestBlock = Model.MIN_FLOOR_HEIGHT;
		checkCoins();
		checkDamageCollison();
		checkTreasure();
		checkPower();
		for(Block b : currentLevel.getBlocks()) {
			if(!b.isInvisible() || !player.isInvisible()) {// check if it's a special block and the player is invisible to go through it
				if(b.getPosition().getY() >= player.getPosition().getY() + player.getPlayerSize().getY()-10 && (player.getPosition().getX() + player.getPlayerSize().getX() >= b.getPosition().getX() && player.getPosition().getX() <= b.getPosition().getX()+b.getWidth())){
					if((int) b.getPosition().getY() < highiestBlock ) {
						highiestBlock = (int) b.getPosition().getY();

					}
				}
			}
		}


		if(player.isJumping()) {

			if(player.getPosition().getY() > maxJumpHeight) {			
				player.setPosition( new Point2D(player.getPosition().getX(), player.getPosition().getY() - JUMP_FORCE));
			}else {
				player.setJumping(false);
			}

		}else if (player.getPosition().getY() + player.getPlayerSize().getY() < highiestBlock){
			player.setPosition( new Point2D(player.getPosition().getX(), player.getPosition().getY() + GRAVITY_FORCE));
			player.setInTheAir(true);

		}else if(player.getPosition().getY() + player.getPlayerSize().getY() >= highiestBlock){
			player.setPosition( new Point2D(player.getPosition().getX(), highiestBlock - player.getPlayerSize().getY()));
			player.setInTheAir(false);
		}
		gameView.repaint(); 

	}

	/**
	 * Check for collision with enemies and traps
	 * If taking damage, the player is invisible for ?? s
	 */
	private void checkDamageCollison() {
		for(Point2D ennemie : currentLevel.getEnemies()) {
			//collision player coins
			if(player.isPlayerTouchingObject(ennemie, ENEMIES_WIDTH, ENEMIES_HEIGHT) & !this.player.isInvincibleAfterAttack()){	
				this.player.setLife(this.player.getLife() -1);
				if(this.player.getLife() == 0) {
					System.out.println("GAME OVER !!!");
					menuView.getControl().displayMenu(gameView.getGamePane(), gameView.getLooseMenu());

				}else {
					this.player.setInvincibleAfterAttack(true);
					// TODO -> false 
					java.util.Timer t = new java.util.Timer();
					t.schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	player.setInvincibleAfterAttack(false);
					                // close the thread
					                t.cancel();
					            }	
					        }, 
					        1000 // invincible for 1s
					);
				}
				break;
			}
		}

		for(Point2D trap : currentLevel.getTrap()) {
			//collision player coins
			if(player.isPlayerTouchingObject(trap, TRAP_WIDTH, TRAP_HEIGHT) & !this.player.isInvincibleAfterAttack()){	
				this.player.setLife(this.player.getLife() -1);
				if(this.player.getLife() == 0) {
					System.out.println("GAME OVER !!!");
					menuView.getControl().displayMenu(gameView.getGamePane(), gameView.getLooseMenu());
				}else {
					this.player.setInvincibleAfterAttack(true);
					java.util.Timer t = new java.util.Timer();
					t.schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	player.setInvincibleAfterAttack(false);
					                // close the thread
					                t.cancel();
					            }	
					        }, 
					        1000 // invincible for 1s
					);
				}
				break;
			}
		}
	}

	public Timeline getTimeline() {
		return timeline;
	}


	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}
	/**
	 * Check if player is on the treasure
	 * if he as enougth coins, then display the win menu 
	 */
	public void checkTreasure() {
		Point2D treasure =  currentLevel.getTreasure() ;
		//collision player coins
		if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && getNbCoinsCollected() == currentLevel.getMaxCoins() ){	

			//System.out.println("Gagné !!");
			//here add the victory screen ! 
			menuView.getControl().displayMenu(gameView.getGamePane(), gameView.getWinMenu());
			//menuView.getControl().getGravity().interrupt();
		}
		else if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && currentLevel.getCoins().size() < currentLevel.getMaxCoins() ){	

			//System.out.println("Get more coins");
		}
	}

	/**
	 * Check if the user pick a coin
	 */
	public void checkCoins() {
		for(Point2D coin : currentLevel.getCoins()) {
			//collision player coins
			if(player.isPlayerTouchingObject(coin, COINS_SIZE, COINS_SIZE)){	
				currentLevel.getCoins().remove(coin);
				setNbCoinsCollected(getNbCoinsCollected() +1);
				//sound.playCoinsSound();
				break;
			}
		}
	}
	
	
	public void checkPower() {
		for(Power power : currentLevel.getPowers()) {
			//collision player coins
			if(player.isPlayerTouchingObject(power.getPosition(), COINS_SIZE, COINS_SIZE)){	
				currentLevel.getPowers().remove(power);
				this.player.setInvisible(true);
				java.util.Timer t = new java.util.Timer();
				t.schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	System.out.println("Power off");
				            	player.setInvisible(false);
				            	currentLevel.getPowers().add(power);
				                // close the thread
				                t.cancel();
				            }	
				        }, 
				        power.getDuration() // invincible for 1s
				);
				
				break;
			}
		}
	}


	/**
	 * Initialization of the level
	 * @param lvlId
	 * @return lvl
	 */
	public Level initLevel(int lvlId) {

		Level lvl;
		int size;

		ArrayList<Block> blocks;
		ArrayList<Point2D> enemies;
		ArrayList<Point2D> coins;
		ArrayList<Power> powers;
		ArrayList<Point2D> traps;
		Point2D treasure;

		switch(lvlId) {

		case 1:
			size = 2500;

			blocks = new ArrayList<Block>() {
				{ 
					//floor
					add(new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),size,50,false));

					//blocks
					add( new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-100),100,100,false));
					add( new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-100),100,100,false));
					add( new Block(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-270),200,50,false));
					add( new Block(new Point2D(1250,Model.MIN_FLOOR_HEIGHT-430),200,50,false));
					add( new Block(new Point2D(1600,Model.MIN_FLOOR_HEIGHT-550),200,550,false));

					//climbing blocks
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-170),50,20,false));
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-320),50,20,false));
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-470),50,20,false));				
				}
			};

			enemies = new ArrayList<Point2D>(){
				{
					add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));	
				}
			};

			coins = new ArrayList<Point2D>() {
				{
					add(new Point2D(230,Model.MIN_FLOOR_HEIGHT - 150));
					add(new Point2D(560,Model.MIN_FLOOR_HEIGHT -200)); 
					add(new Point2D(1200,Model.MIN_FLOOR_HEIGHT -50)); 
					add(new Point2D(1700,Model.MIN_FLOOR_HEIGHT - 600));
					add(new Point2D(1960,Model.MIN_FLOOR_HEIGHT -200));
				}
			};

			powers = new ArrayList<Power>(){};

			traps = new ArrayList<Point2D>(){
				{
					add(new Point2D(320,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));	
				}
			};

			treasure = new Point2D(size - TREASURE_WIDTH ,Model.MIN_FLOOR_HEIGHT - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure);

			break;

		case 2 :

			size = 2600;

			blocks = new ArrayList<Block>() {
				{
					//Floor Block
					add(new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),size,50,false));
					//blocks
					add(new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-150),50,50,false));
					add(new Block(new Point2D(500,Model.MIN_FLOOR_HEIGHT-250),50,50,false));
					add(new Block(new Point2D(750,Model.MIN_FLOOR_HEIGHT-350),250,50,false));
					add(new Block(new Point2D(1200,Model.MIN_FLOOR_HEIGHT-250),50,50,false));
					add(new Block(new Point2D(1450,Model.MIN_FLOOR_HEIGHT-150),50,50,false));

					//jump block
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-150),50,50,false));
					add(new Block(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-300),50,50,false));
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-450),50,50,false));
					add(new Block(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-600),50,50,false));
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-750),50,50,false));

					//top block
					add(new Block(new Point2D(1950,Model.MIN_FLOOR_HEIGHT-800),150,50,false));
					add(new Block(new Point2D(1600,Model.MIN_FLOOR_HEIGHT-800),150,50,false));
					add(new Block(new Point2D(1100,Model.MIN_FLOOR_HEIGHT-800),250,50,false));

					//Special block
					add(new Block(new Point2D(720,Model.MIN_FLOOR_HEIGHT-750),50,50,true));
					add(new Block(new Point2D(1170,Model.MIN_FLOOR_HEIGHT-1100),50,300,true));

				}
			};		

			enemies = new ArrayList<Point2D>(){
				{
					//add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));	
				}
			};

			coins = new ArrayList<Point2D>() {
				{
					//coins chest
					add(new Point2D(250,Model.MIN_FLOOR_HEIGHT - 150 - Model.COINS_SIZE));
					add(new Point2D(860,Model.MIN_FLOOR_HEIGHT -200)); 

					//jump
					add(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-300 - Model.COINS_SIZE)); 
					add(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-600 - Model.COINS_SIZE)); 
					add(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-450 - Model.COINS_SIZE)); 

					//top
					add(new Point2D(1100,Model.MIN_FLOOR_HEIGHT-800 - Model.COINS_SIZE)); 
					add(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-800 - 2 * Model.COINS_SIZE)); 
					add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT-800 - Model.COINS_SIZE)); 
					
					//Special block
					add(new Point2D(720,Model.MIN_FLOOR_HEIGHT-750 - 200  - Model.COINS_SIZE));
				}
			};

			powers = new ArrayList<Power>(){
				{
					add(new Power(0 , 10000, new Point2D(2200,Model.MIN_FLOOR_HEIGHT-150 - Model.COINS_SIZE))); 
					//add(new Power(0 , 1000, new Point2D(2000,Model.MIN_FLOOR_HEIGHT-150 - Model.COINS_SIZE))); 
				}
				
			};

			traps = new ArrayList<Point2D>(){
				{
					//trap chest
					add(new Point2D(610,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(840,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(1060,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));

					//trap enemie
					add(new Point2D(1900 ,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));

					//trap jump
					add(new Point2D(1900 + 400,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
				}
			};

			treasure = new Point2D(815 ,Model.MIN_FLOOR_HEIGHT-340 - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure);

			break;

		case 3 :

			size = 2000;

			blocks = new ArrayList<Block>() {
				{
					//Floor Block
					add(new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),size,50,false));
					//blocks

					//jump block
					add(new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-200),100,25,false));
					add(new Block(new Point2D(400,Model.MIN_FLOOR_HEIGHT-300),100,25,false));
					add(new Block(new Point2D(675,Model.MIN_FLOOR_HEIGHT-750),10,400,false));
					add(new Block(new Point2D(650,Model.MIN_FLOOR_HEIGHT-200),100,25,false));
					add(new Block(new Point2D(900,Model.MIN_FLOOR_HEIGHT-300),100,25,false));

					//add(new Block(new Point2D(600,Model.MIN_FLOOR_HEIGHT-450),200,50,false));

				}
			};		

			enemies = new ArrayList<Point2D>(){
				{
					//add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));	
				}
			};

			coins = new ArrayList<Point2D>() {
				{

				}
			};

			powers = new ArrayList<Power>(){};

			traps = new ArrayList<Point2D>(){
				{
					//trap chest
					add(new Point2D(500,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(650,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(800,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(950,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
				}
			};

			treasure = new Point2D(820 ,Model.MIN_FLOOR_HEIGHT-340 - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure);

			break;

		default : 
			lvl = initLevel(1);
			break;

		}

		this.currentLevel = lvl;
		return lvl;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getBackgroundList(){
		return backgroundList;
	}

	/**
	 * Getter of the currentLevel
	 * @return currentLevel
	 */
	public Level getCurrentLevel() {
		return currentLevel;
	}

	/**
	 * Setter of the currentLevel
	 * @param currentLevel
	 */
	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}

	/**
	 * Getter of the player
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter for the player
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Getter for gameOver
	 * @return
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * Setter for gameOver
	 * @param gameOver
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Changes the direction of the ship
	 * @param d Direction of the ship.
	 * 			Acceptable values are Model.FACE_LEFT, Model.FACE_RIGHT
	 */
	public void setDirection(int d) {
		this.direction = d;
	}

	/**
	 * Getter for direction
	 * @return
	 */
	public int getDirection() {
		return direction;
	}

	/**
	 * Getter for gamePaused
	 * @return
	 */
	public boolean isGamePaused() {
		return gamePaused;
	}

	/**
	 * Setter for gamePaused
	 * @param gamePaused
	 */
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
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

	/**
	 * Getter for Time
	 * @return time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * Setter for time
	 * @param time
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/**
	 * Modification of time
	 * @param add
	 */
	public void addTime(double add) {
		this.time += add;
	}


}



