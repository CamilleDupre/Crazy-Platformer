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
	public static final int ENEMIES_HEIGHT = 80;
	public static final int ENEMIES_WIDTH = 65;
	public static final int TREASURE_HEIGHT = 100;
	public static final int TREASURE_WIDTH = 150;
	public static final int TRAP_WIDTH = 100;
	public static final int TRAP_HEIGHT = 60;
	private Player player;
	private int direction;
	private String imgPlayer="";

	private double time = 30000;

	private Timer timer;

	private boolean gamePaused;

	private Sound sound;

	public Level LVL_1 = initLevel(1);
	public Level LVL_2;
	public Level LVL_3;
	public Level LVL_4;	
	public int nbCoinsCollected = 0 ;

	public double maxJumpHeight;

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
		timer = new Timer(this,sound);

	}




	public void initBackgroundRessources() {
		backgroundList = new ArrayList<String>();
		backgroundList.add("/other/background_clair.png");
		backgroundList.add("/other/background_fonce.png");
		backgroundList.add("/other/background_neon.png");
	}

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


	public void move(int direction) {
		int moveConstraint = 0;
		for(Block block : this.currentLevel.getBlocks()) {
			if(moveConstraint==0) {
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


	public void makePlayerJump() {
		if(!player.isInTheAir()) {
			maxJumpHeight = player.getPosition().getY() + player.getPlayerJump();
			//player.setPosition( new Point2D(player.getPosition().getX(),player.getPosition().getY() + player.getPlayerJump())) ;
			player.setJumping(true);
			player.setInTheAir(true);
		}
	}

	public void gravityForce() {
		int highiestBlock = Model.MIN_FLOOR_HEIGHT;
		checkCoins();
		checkEnemieCollison();
		checkTreasure();
		for(Block b : currentLevel.getBlocks()) {
			if(b.getPosition().getY() >= player.getPosition().getY() + player.getPlayerSize().getY()-10 && (player.getPosition().getX() + player.getPlayerSize().getX() >= b.getPosition().getX() && player.getPosition().getX() <= b.getPosition().getX()+b.getWidth())){
				if((int) b.getPosition().getY() < highiestBlock ) {
					highiestBlock = (int) b.getPosition().getY();
					
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

	private void checkEnemieCollison() {
		for(Point2D ennemie : currentLevel.getEnemies()) {
			//collision player coins
			if(player.isPlayerTouchingObject(ennemie, ENEMIES_WIDTH, ENEMIES_HEIGHT) & !this.player.isInvincibleAfterAttack()){	
				this.player.setLife(this.player.getLife() -1);
				if(this.player.getLife() == 0) {
					System.out.println("GAME OVER !!!");
				}else {
					this.player.setInvincibleAfterAttack(true);
					// TODO -> false 
				}
				break;
			}
		}
	}


	public void checkTreasure() {
		Point2D treasure =  currentLevel.getTreasure() ;
			//collision player coins
			if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && getNbCoinsCollected() == currentLevel.getMaxCoins() ){	
				
				System.out.println("Gagné !!");
				//here add the victory screen ! 
			}
			else if(player.isPlayerTouchingObject(treasure, TREASURE_WIDTH, TREASURE_HEIGHT) && currentLevel.getCoins().size() < currentLevel.getMaxCoins() ){	
				
				System.out.println("Get more coins");
			}
	}

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

	public Level initLevel(int lvlId) {

		Level lvl;
		int size;

		ArrayList<Block> blocks;

		Block floorBlock;

		Block block1;
		Block block2;
		Block block3;
		Block block4;
		Block block5;

		ArrayList<Point2D> enemies;
		ArrayList<Point2D> coins;
		ArrayList<Point2D> powers;
		ArrayList<Point2D> traps;
		
		Point2D treasure;


		switch(lvlId) {

		case 1:
			size = 2500;

			blocks = new ArrayList<Block>();

			floorBlock = new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),size,50);
			block1 = new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-100),100,100);
			block2 = new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-100),100,100);
			block3 = new Block(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-270),200,50);
			block4 = new Block(new Point2D(1250,Model.MIN_FLOOR_HEIGHT-430),200,50);
			block5 = new Block(new Point2D(1600,Model.MIN_FLOOR_HEIGHT-550),200,550);

			blocks.add(floorBlock);
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block4);
			blocks.add(block5);


			enemies = new ArrayList<Point2D>(){
				{
					add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));	
				}
			};

			coins = new ArrayList<Point2D>() {
				{
					add(new Point2D(230,Model.MIN_FLOOR_HEIGHT - 150));
					add(new Point2D(500,Model.MIN_FLOOR_HEIGHT -200)); 
					add(new Point2D(1200,Model.MIN_FLOOR_HEIGHT -50)); 
					add(new Point2D(1700,Model.MIN_FLOOR_HEIGHT - 600));
					add(new Point2D(1960,Model.MIN_FLOOR_HEIGHT -200));
				}
			};

			powers = new ArrayList<Point2D>(){};

			traps = new ArrayList<Point2D>(){
				{
					add(new Point2D(480-TRAP_WIDTH,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));	
				}
			};

			treasure = new Point2D(size - TREASURE_WIDTH ,Model.MIN_FLOOR_HEIGHT - TREASURE_HEIGHT);
			
			lvl = new Level(size,blocks,enemies,coins,powers,traps, treasure);

			break;

		default : 
			size = 2500;

			blocks = new ArrayList<Block>();

			floorBlock = new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),size,50);
			block1 = new Block(new Point2D(200,Model.MIN_FLOOR_HEIGHT-100),100,100);
			block2 = new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-100),100,100);
			block3 = new Block(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-270),200,50);
			block4 = new Block(new Point2D(1250,Model.MIN_FLOOR_HEIGHT-430),200,50);
			block5 = new Block(new Point2D(1600,Model.MIN_FLOOR_HEIGHT-550),200,550);

			blocks.add(floorBlock);
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block4);
			blocks.add(block5);


			enemies = new ArrayList<Point2D>(){
				{
					add(new Point2D(1950,Model.MIN_FLOOR_HEIGHT - ENEMIES_HEIGHT));	
				}
			};

			coins = new ArrayList<Point2D>() {
				{
					add(new Point2D(230,Model.MIN_FLOOR_HEIGHT - 150));
					add(new Point2D(500,Model.MIN_FLOOR_HEIGHT -200)); 
					add(new Point2D(1200,Model.MIN_FLOOR_HEIGHT -50)); 
					add(new Point2D(1700,Model.MIN_FLOOR_HEIGHT - 600));
					add(new Point2D(1960,Model.MIN_FLOOR_HEIGHT -200));
				}
			};

			powers = new ArrayList<Point2D>(){};

			traps = new ArrayList<Point2D>(){
				{
					add(new Point2D(480-TRAP_WIDTH,Model.MIN_FLOOR_HEIGHT -TRAP_HEIGHT));	
				}
			};

			treasure = new Point2D(size - TREASURE_WIDTH ,Model.MIN_FLOOR_HEIGHT - TREASURE_HEIGHT);
			
			lvl = new Level(size,blocks,enemies,coins,powers,traps, treasure);

			break;

		}

		this.currentLevel = lvl;
		return lvl;


	}




	public ArrayList<String> getBackgroundList(){
		return backgroundList;
	}


	public Level getCurrentLevel() {
		return currentLevel;
	}


	public void setCurrentLevel(Level currentLevel) {
		this.currentLevel = currentLevel;
	}


	public Player getPlayer() {
		return player;
	}


	public void setPlayer(Player player) {
		this.player = player;
	}


	public boolean isGameOver() {
		return gameOver;
	}


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


	public int getDirection() {
		return direction;
	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public int getNbCoinsCollected() {
		return nbCoinsCollected;
	}

	public void setNbCoinsCollected(int nbCoinsCollected) {
		this.nbCoinsCollected = nbCoinsCollected;
	}


	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}


	public double getTime() {
		return time;
	}


	public void setTime(double time) {
		this.time = time;
	}

	public void addTime(double add) {
		this.time += add;
	}


}



