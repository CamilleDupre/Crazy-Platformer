package Model;

import java.awt.Canvas;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Controller.Control;
import Sound.Sound;
import View.CustomMenuButton;
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
	//public static final int MIN_FLOOR_HEIGHT = MenuView.HEIGHT - 200 ;
	public static final int MIN_FLOOR_HEIGHT = MenuView.HEIGHT - 100 ;
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

	private double time = 0;

	//private Timer timer;

	private boolean gamePaused;

	private Sound sound;

	public Level LVL_1;
	public Level LVL_2;
	public Level LVL_3;
	public Level LVL_4;


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
		this.gamePaused = false;
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
				if(player.getPosition().getX() + player.getPlayerSpeed() < currentLevel.getMaxSize() - player.getPlayerSize().getX()) {
					player.setPosition( new Point2D(player.getPosition().getX() + player.getPlayerSpeed(), player.getPosition().getY()));
				}
			}
			break;

		case 1:
			//check if player isn't touching block on left side and allow moving only to right
			if(direction == Control.RIGHT) {
				//check if player isn't out of the level in the right side
				if(player.getPosition().getX() + player.getPlayerSpeed() < currentLevel.getMaxSize() - player.getPlayerSize().getX()) {
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
		for(Enemy ennemie : currentLevel.getEnemies()) {
			//collision player coins
			if(player.isPlayerTouchingObject(ennemie.getPosition(), ENEMIES_WIDTH, ENEMIES_HEIGHT) && !this.player.isInvincibleAfterAttack() && !ennemie.isDead()){
				this.player.setLife(this.player.getLife() -1);
				if(this.player.getLife() == 0) {
					gameOver = true;
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
					gameOver = true;
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
	 * if he as enough coins, then display the win menu
	 */
	public void checkTreasure() {
		Point2D treasure =  currentLevel.getTreasure() ;
		//collision player coins
		if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && player.getNbCoinsCollected() == currentLevel.getMaxCoins() ){

			this.setGameOver(true);
			//here add the victory screen !
			gameView.score = String.valueOf(currentLevel.getTimeLeft());
			menuView.getControl().displayMenu(gameView.getGamePane(), gameView.getWinMenu());
			//menuView.getControl().getGravity().interrupt();
		}
		else if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && player.getNbCoinsCollected() < currentLevel.getMaxCoins() ){
			//do something if we find an interesting idea

		}
	}

	/**
	 * Check if the user pick a coin
	 */
	public void checkAttack() {
		for(Enemy enemy : currentLevel.getEnemies()) {
			//collision player coins

			/*	if(player.isPlayerTouchingObject(new Point2D(enemy.getPosition().getX()-40, enemy.getPosition().getY()), ENEMIES_WIDTH+40, ENEMIES_HEIGHT+40)){
				enemy.setDead(true);
			}*/

			if (getDirection() == Model.FACE_RIGHT) {
				System.out.println(enemy.getPosition() + " R " + player.getPosition());
				if(player.isPlayerAttackingEnemies(new Point2D(enemy.getPosition().getX(), enemy.getPosition().getY()), ENEMIES_WIDTH+40)){

					enemy.setDead(true);
					break;
				}
			}
			else {
				System.out.println((enemy.getPosition().getX() - ENEMIES_WIDTH-40) + " L " + player.getPosition());
				if(player.isPlayerAttackingEnemies(new Point2D(enemy.getPosition().getX()-ENEMIES_WIDTH, enemy.getPosition().getY()), ENEMIES_WIDTH+40)){

					enemy.setDead(true);
					break;
				}
			}
			//sound.playCoinsSound();
			player.setAttacking(true);
			java.util.Timer t = new java.util.Timer();
			t.schedule(
					new java.util.TimerTask() {
						@Override
						public void run() {
							player.setAttacking(false);
							// close the thread
							t.cancel();
						}
					},
					1000 // invincible for 1s
					);


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
				player.setNbCoinsCollected(player.getNbCoinsCollected() +1);
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

				if (power.getType() == 0) {  // invisible power

					this.player.setInvisible(true);
					java.util.Timer t = new java.util.Timer();
					t.schedule(
							new java.util.TimerTask() {
								@Override
								public void run() {
									player.setInvisible(false);
									currentLevel.getPowers().add(power);
									// close the thread
									t.cancel();
								}
							},
							power.getDuration() // invincible for ?? seconds
							);

					break;
				}
				else if (power.getType() == 1) {  // jump power

					player.setPlayerJump(-500);
					player.setSuperJump(true);
					java.util.Timer t = new java.util.Timer();
					t.schedule(
							new java.util.TimerTask() {
								@Override
								public void run() {
									System.out.println("Power off");
									player.setPlayerJump(-220);
									player.setSuperJump(false);
									currentLevel.getPowers().add(power);
									// close the thread
									t.cancel();
								}
							},
							power.getDuration() // powerjump duration
							);

					break;
				}

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
		ArrayList<Enemy> enemies;
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

					for (int i = 0 ; i< size ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT),50,50,false));
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT +50),50,50,false));
					}

					//blocks
					add( new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-100),50,50,false));
					add( new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-50),50,50,false));
					add( new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-100),50,50,false));
					add( new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-50),50,50,false));


					add( new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-100),50,50,false));
					add( new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-50),50,50,false));
					add( new Block(new Point2D(900,Model.MIN_FLOOR_HEIGHT-100),50,50,false));
					add( new Block(new Point2D(900,Model.MIN_FLOOR_HEIGHT-50),50,50,false));


					for (int i = 1000 ; i< 1200 ; i=i +50){
						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-270),50,50,false));
					}

					for (int i = 1250 ; i< 1450 ; i=i +50){
						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-430),50,50,false));
					}

					for (int i = MIN_FLOOR_HEIGHT - 550 ; i< MIN_FLOOR_HEIGHT ; i=i +50){
						add( new Block(new Point2D(1600,i),50,50,false));
						add( new Block(new Point2D(1650,i),50,50,false));
						add( new Block(new Point2D(1700,i),50,50,false));
						add( new Block(new Point2D(1750,i),50,50,false));
					}

					//climbing blocks
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-170),50,20,false));
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-320),50,20,false));
					add( new Block(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-470),50,20,false));
				}
			};

			enemies = new ArrayList<Enemy>(){
				{
					add(new Enemy(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT),false));
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
					add(new Point2D(1500,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(1520,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
				}
			};

			treasure = new Point2D(size - TREASURE_WIDTH ,Model.MIN_FLOOR_HEIGHT - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure,60);

			break;

		case 2 :

			size = 2600;

			blocks = new ArrayList<Block>() {
				{
					//Floor Block
					for (int i = 0 ; i< size ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT),50,50,false));
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT +50),50,50,false));
					}

					//blocks
					add(new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-150),50,50,false));
					add(new Block(new Point2D(500,Model.MIN_FLOOR_HEIGHT-250),50,50,false));

					for (int i = 750 ; i< 1000 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-350),50,50,false));
					}

					add(new Block(new Point2D(1200,Model.MIN_FLOOR_HEIGHT-250),50,50,false));
					add(new Block(new Point2D(1450,Model.MIN_FLOOR_HEIGHT-150),50,50,false));

					//jump block
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-150),50,50,false));
					add(new Block(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-300),50,50,false));
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-450),50,50,false));
					add(new Block(new Point2D(2400,Model.MIN_FLOOR_HEIGHT-600),50,50,false));
					add(new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-750),50,50,false));

					//top block
					for (int i = 1950 ; i< 1950 + 150 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-800),50,50,false));
					}

					for (int i = 1600 ; i< 1750 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-800),50,50,false));
					}

					for (int i = 1100 ; i< 1350 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-800),50,50,false));

					}

					//Special block
					add(new Block(new Point2D(720,Model.MIN_FLOOR_HEIGHT-750),50,50,true));

					for (int i = Model.MIN_FLOOR_HEIGHT-1100 ; i< Model.MIN_FLOOR_HEIGHT-800 ; i=i +50){
						add(new Block(new Point2D(1170, i),50,50,true));

					}


				}
			};

			enemies = new ArrayList<Enemy>(){
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

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure,60);

			break;

		case 3 :

			size = GameView.CANVAS_WIDTH;

			blocks = new ArrayList<Block>() {
				{
					//Floor Block
					for (int i = 0 ; i< size ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT),50,50,false));
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT +50),50,50,false));
					}

					//blocks

					//vertical block
					for (int i = Model.MIN_FLOOR_HEIGHT-450 ; i< Model.MIN_FLOOR_HEIGHT ; i=i +50){
						add(new Block(new Point2D(500,i),50,50,false));
					}
					for (int i = Model.MIN_FLOOR_HEIGHT-1350 ; i< Model.MIN_FLOOR_HEIGHT-350 ; i=i +50){
						add(new Block(new Point2D(1050,i),50,50,false));
					}
					for (int i = Model.MIN_FLOOR_HEIGHT-1900 ; i< Model.MIN_FLOOR_HEIGHT-1450 ; i=i +50){
						add(new Block(new Point2D(250, i),50,50,false));
					}


					//Horizontal block
					//jump
					add(new Block(new Point2D(1300,Model.MIN_FLOOR_HEIGHT-450),50,50,false));
					add(new Block(new Point2D(1350,Model.MIN_FLOOR_HEIGHT-450),50,50,false));

					add(new Block(new Point2D(1500,Model.MIN_FLOOR_HEIGHT-900),50,50,false));
					add(new Block(new Point2D(1550,Model.MIN_FLOOR_HEIGHT-900),50,50,false));

					for (int i = 0 ; i< 1200 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-1350),50,50,false));
					}


					//plateform
					for (int i = 600 ; i< 900 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-1500),50,50,false));
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-2200),50,50,false));
					}
					//treasure

					for (int i = 1000 ; i< 1300 ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-2000),50,50,false));
					}


					//add(new Block(new Point2D(600,Model.MIN_FLOOR_HEIGHT-450),200,50,false));

				}
			};

			enemies = new ArrayList<Enemy>(){
				{
					//add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));
					add(new Enemy(new Point2D(700,Model.MIN_FLOOR_HEIGHT-1500 - Model.ENEMIES_HEIGHT),false));
				}
			};

			coins = new ArrayList<Point2D>() {
				{
					add(new Point2D(500,Model.MIN_FLOOR_HEIGHT-500 - Model.COINS_SIZE));

					add(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-200 - Model.COINS_SIZE));

					add(new Point2D(1050,Model.MIN_FLOOR_HEIGHT-1350 - Model.COINS_SIZE));

					add(new Point2D(1350,Model.MIN_FLOOR_HEIGHT-450 - Model.COINS_SIZE));
					add(new Point2D(1550,Model.MIN_FLOOR_HEIGHT-900 - Model.COINS_SIZE));

					add(new Point2D(250,Model.MIN_FLOOR_HEIGHT-1900- Model.COINS_SIZE));
					add(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-2000 - 300));
					add(new Point2D(600,Model.MIN_FLOOR_HEIGHT-2200- Model.COINS_SIZE));
				}
			};

			powers = new ArrayList<Power>(){
				{
					add(new Power(1 , 1500, new Point2D(200,Model.MIN_FLOOR_HEIGHT - Model.COINS_SIZE)));
					add(new Power(1 , 6500, new Point2D(1000,Model.MIN_FLOOR_HEIGHT - Model.COINS_SIZE)));
					add(new Power(1 , 4000, new Point2D(650,Model.MIN_FLOOR_HEIGHT-1500 - Model.COINS_SIZE)));
				}
			};

			traps = new ArrayList<Point2D>(){
				{
					//trap chest
					add(new Point2D(550,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(600,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(650,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(700,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(750,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(800,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(850,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));


					add(new Point2D(1800,Model.MIN_FLOOR_HEIGHT-TRAP_HEIGHT));

					add(new Point2D(300,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(350,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(400,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(450,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(500,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(550,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(600,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(650,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(700,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(750,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));
					add(new Point2D(800,Model.MIN_FLOOR_HEIGHT-1350-TRAP_HEIGHT));

				}
			};

			treasure = new Point2D(1100 ,Model.MIN_FLOOR_HEIGHT-2000 - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure,60);

			break;

		case 4 :
			size = 3000;
			blocks = new ArrayList<Block>() {
				{
					//floor

					for (int i = 0 ; i< size ; i=i +50){
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT),50,50,false));
						add(new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT +50),50,50,false));
					}

					//blocks
					add( new Block(new Point2D(300,Model.MIN_FLOOR_HEIGHT-200),50,50,false));
					add( new Block(new Point2D(500,Model.MIN_FLOOR_HEIGHT-400),50,50,false));
					add( new Block(new Point2D(300,Model.MIN_FLOOR_HEIGHT-600),50,50,false));
					add( new Block(new Point2D(500,Model.MIN_FLOOR_HEIGHT-800),50,50,false));
					add( new Block(new Point2D(300,Model.MIN_FLOOR_HEIGHT-1000),50,50,false));
					add( new Block(new Point2D(500,Model.MIN_FLOOR_HEIGHT-1200),50,50,false));


					add( new Block(new Point2D(100,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(150,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(300,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));



					for (int i = 1250 ; i< 1600 ; i=i +50){
						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-50),50,50,false));
						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-100),50,50,false));

						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-300),50,50,true));
						add( new Block(new Point2D(i,Model.MIN_FLOOR_HEIGHT-1400),50,50,true));
					}

					for (int i = Model.MIN_FLOOR_HEIGHT-300 ; i< Model.MIN_FLOOR_HEIGHT-100 ; i=i +50){
						add( new Block(new Point2D(1250,i),50,50,true));
						add( new Block(new Point2D(1550,i),50,50,true));
					}


					//blocks
					add( new Block(new Point2D(2300,Model.MIN_FLOOR_HEIGHT-200),50,50,false));
					add( new Block(new Point2D(2500,Model.MIN_FLOOR_HEIGHT-400),50,50,false));
					add( new Block(new Point2D(2300,Model.MIN_FLOOR_HEIGHT-600),50,50,false));
					add( new Block(new Point2D(2500,Model.MIN_FLOOR_HEIGHT-800),50,50,false));
					add( new Block(new Point2D(2300,Model.MIN_FLOOR_HEIGHT-1000),50,50,false));
					add( new Block(new Point2D(2500,Model.MIN_FLOOR_HEIGHT-1200),50,50,false));


					add( new Block(new Point2D(2100,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(2150,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(2250,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));
					add( new Block(new Point2D(2300,Model.MIN_FLOOR_HEIGHT-1400),50,50,false));


				}
			};

			enemies = new ArrayList<Enemy>(){
				{
					add(new Enemy(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT),false));
					add(new Enemy(new Point2D(200,Model.MIN_FLOOR_HEIGHT-1400 -ENEMIES_HEIGHT),false));
					add(new Enemy(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-1400 - ENEMIES_HEIGHT),false));
				}
			};

			coins = new ArrayList<Point2D>() {
				{

					add(new Point2D(100,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(150,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(200,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(250,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));


					//add(new Point2D(2100,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(2150,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(2200,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));
					add(new Point2D(2250,Model.MIN_FLOOR_HEIGHT-1400 - Model.COINS_SIZE));

				}
			};

			powers = new ArrayList<Power>(){
				{
					add(new Power(0 , 6000, new Point2D(1250 + 175,Model.MIN_FLOOR_HEIGHT - 1400 - 400)));

					add(new Power(1 , 2000, new Point2D(920 + 175,Model.MIN_FLOOR_HEIGHT - Model.COINS_SIZE)));
					add(new Power(1 , 2000, new Point2D(1530 + 175,Model.MIN_FLOOR_HEIGHT - Model.COINS_SIZE)));
					add(new Power(1 , 5000, new Point2D(2100,Model.MIN_FLOOR_HEIGHT  -1400 -Model.COINS_SIZE)));
				}
			};

			traps = new ArrayList<Point2D>(){
				{
					add(new Point2D(320,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(1170,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
					add(new Point2D(1600,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));
				}
			};

			treasure = new Point2D(1490 - TREASURE_WIDTH ,Model.MIN_FLOOR_HEIGHT- 100 - TREASURE_HEIGHT);

			lvl = new Level(lvlId,size,blocks,enemies,coins,powers,traps, treasure,60);

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
