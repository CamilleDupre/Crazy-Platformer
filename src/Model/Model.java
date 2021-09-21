package Model;

import java.awt.Image;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Controller.Control;
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
	
	
	private int currentLevel;
	private boolean gameOver;
	public static final int MIN_FLOOR_HEIGHT = MenuView.HEIGHT - 100 ;
	private Player player;
	private int direction;
	private ImageIcon imgPlayer;
	
	private double time = 0.0;
	
	private boolean gamePaused;
	
	
		
	
	public Model(MenuView menu, GameView game) {
		this.menuView = menu;
		this.gameView = game;
		this.gameOver = false;
		initBackgroundRessources();
		this.player = new Player();
		this.direction = FACE_RIGHT;
		this.gamePaused = false;
		
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
		System.out.println("dans jump");
	}
	
	public void gravityForce() {
		if (player.getPosition().getY() + player.getPlayerSize().getY() < Model.MIN_FLOOR_HEIGHT ){
			player.setPosition( new Point2D(player.getPosition().getX(), player.getPosition().getY() +8)) ;

		}else if(player.getPosition().getY() + player.getPlayerSize().getY() != Model.MIN_FLOOR_HEIGHT){
			player.setPosition( new Point2D(player.getPosition().getX(), Model.MIN_FLOOR_HEIGHT - player.getPlayerSize().getY()));
			player.setJumping(false);
		}
		gameView.repaint(); 
	}
	
	
	
	public ArrayList<String> getBackgroundList(){
		return backgroundList;
	}


	public int getCurrentLevel() {
		return currentLevel;
	}


	public void setCurrentLevel(int currentLevel) {
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



