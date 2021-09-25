package Model;

import java.awt.Image;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Controller.Control;
import Sound.Sound;
import View.GameView;
import View.MenuView;
import javafx.geometry.Point2D;

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
	private Player player;
	private int direction;
	private ImageIcon imgPlayer;
	
	private double time = 30000;
	
	private Timer timer;
	
	private boolean gamePaused;
	
	private Sound sound;
	
	public Level LVL_1 = initLevel(1);
	public Level LVL_2;
	public Level LVL_3;
	public Level LVL_4;	
		
	
	public Model(MenuView menu, GameView game,Sound sound) {
		this.menuView = menu;
		this.gameView = game;
		this.gameOver = false;
		initBackgroundRessources();
		this.player = new Player();
		this.direction = FACE_RIGHT;
		this.gamePaused = false;
		this.sound = sound;
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
			break;
		
		case 1 :
			menuView.getScene().getStylesheets().remove(0);
			menuView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("dark.css").toExternalForm());
			break;
		
		case 2 :
			menuView.getScene().getStylesheets().remove(0);
			menuView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("neon.css").toExternalForm());
			break;
			
		default :
			break;
		}
		menuView.getPrimaryStage().show();

	}
	
	
	
	/**
	 * Method used to get the current ship's image
	 * Depends of the current ship's direction
	 * @return Ship's image, facing either left, right, or front.
	 */
	public Image getImgPlayer() {
		
		if (this.direction == Model.FACE_LEFT) {
			try {
				this.imgPlayer = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("img/.png")));
			} catch (Exception e) {
				System.out.println("Player image could not be loaded !");
			}
		} else if (this.direction == Model.FACE_RIGHT) {
			try {
				this.imgPlayer = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("img/.png")));
			} catch (Exception e) {
				System.out.println("Player image could not be loaded !");
			}
		} 
		return imgPlayer.getImage();
	}
	
	
	public void move(int direction) {
		if(direction == Control.LEFT) {
			//check if player isn't out of the level in the left side
			if(player.getPosition().getX() - player.getPlayerSpeed() > 30) {
				player.setPosition( new Point2D(player.getPosition().getX() - player.getPlayerSpeed(), player.getPosition().getY()));
			}
		}else if(direction == Control.RIGHT) {
			//check if player isn't out of the level in the right side
			if(player.getPosition().getX() + player.getPlayerSpeed() < MenuView.WIDTH-30) {
				player.setPosition( new Point2D(player.getPosition().getX() + player.getPlayerSpeed(), player.getPosition().getY()));
			}
		}
	}
	
	
	public void makePlayerJump() {
		if(!player.isJumping()) {
			player.setPosition( new Point2D(player.getPosition().getX(),player.getPosition().getY() + player.getPlayerJump())) ;
			player.setJumping(true);
			gameView.repaint(); 
		}
	}
	
	public void gravityForce() {
		/*
		int highiestBlock = Model.MIN_FLOOR_HEIGHT;
		for(Block b : currentLevel.getBlocks()) {
			if(b.getPosition().getY() > player.getPosition().getY() + player.getPlayerSize().getY() && player.getPosition().getX())
		}*/
		
		if (player.getPosition().getY() + player.getPlayerSize().getY() < Model.MIN_FLOOR_HEIGHT ){
			player.setPosition( new Point2D(player.getPosition().getX(), player.getPosition().getY() +8)) ;

		}else if(player.getPosition().getY() + player.getPlayerSize().getY() != Model.MIN_FLOOR_HEIGHT){
			player.setPosition( new Point2D(player.getPosition().getX(), Model.MIN_FLOOR_HEIGHT - player.getPlayerSize().getY()));
			player.setJumping(false);
		}
		gameView.repaint(); 
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

		Point2D[] ennemies;
		Point2D[] coins;
		Point2D[] powers;
		Point2D[] traps;


		switch(lvlId) {

		case 1:
			size = 1500;

			blocks = new ArrayList<Block>();

			floorBlock = new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),GameView.CANVAS_WIDTH,50);
			block1 = new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-100),200,100);
			block2 = new Block(new Point2D(750,Model.MIN_FLOOR_HEIGHT-100),200,100);
			block3 = new Block(new Point2D(1000,Model.MIN_FLOOR_HEIGHT-250),200,50);
			block4 = new Block(new Point2D(1250,Model.MIN_FLOOR_HEIGHT-400),200,50);
			block5 = new Block(new Point2D(1500,Model.MIN_FLOOR_HEIGHT-550),200,550);

			blocks.add(floorBlock);
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block4);
			blocks.add(block5);


			ennemies = new Point2D[]{new Point2D(2500,GameView.CANVAS_HEIGHT -300)};
			coins = new Point2D[]{new Point2D(300,GameView.CANVAS_HEIGHT - 400), new Point2D(600,GameView.CANVAS_HEIGHT -300), new Point2D(900,GameView.CANVAS_HEIGHT -200), new Point2D(1200,GameView.CANVAS_HEIGHT -700), new Point2D(1800,GameView.CANVAS_HEIGHT -300)};
			powers = new Point2D[]{};
			traps = new Point2D[]{new Point2D(600,GameView.CANVAS_HEIGHT -200)};

			lvl = new Level(1500,blocks,ennemies,coins,powers,traps);

			break;

		default : 
			size = 1500;

			blocks = new ArrayList<Block>();

			floorBlock = new Block(new Point2D(0,Model.MIN_FLOOR_HEIGHT),GameView.CANVAS_WIDTH,50);
			block1 = new Block(new Point2D(250,Model.MIN_FLOOR_HEIGHT-100),200,100);
			block2 = new Block(new Point2D(750,Model.MIN_FLOOR_HEIGHT-100),200,100);
			block3 = new Block(new Point2D(850,Model.MIN_FLOOR_HEIGHT-250),200,50);
			block4 = new Block(new Point2D(950,Model.MIN_FLOOR_HEIGHT-400),200,50);
			block5 = new Block(new Point2D(1050,Model.MIN_FLOOR_HEIGHT-550),200,550);

			blocks.add(floorBlock);
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block4);
			blocks.add(block5);


			ennemies = new Point2D[]{new Point2D(2500,GameView.CANVAS_HEIGHT -300)};
			coins = new Point2D[]{new Point2D(300,GameView.CANVAS_HEIGHT - 400), new Point2D(600,GameView.CANVAS_HEIGHT -300), new Point2D(900,GameView.CANVAS_HEIGHT -200), new Point2D(1200,GameView.CANVAS_HEIGHT -700), new Point2D(1800,GameView.CANVAS_HEIGHT -300)};
			powers = new Point2D[]{};
			traps = new Point2D[]{new Point2D(600,GameView.CANVAS_HEIGHT -200)};

			lvl = new Level(1500,blocks,ennemies,coins,powers,traps);

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


	public boolean isGamePaused() {
		return gamePaused;
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



