package Controller;

import java.io.IOException;

import Model.Model;
import Model.Player;
import Sound.Sound;
import View.CustomMenuButton;
import View.MenuView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import View.GameView;

public class Control {
	
	/** 
	 * attribute model
	 */
	private Model model;
	
	/**
	 * attribute menuView
	 */
	private MenuView menuView;

	/**
	 * Boolean attribute telling if it is the first time that we load a level
	 */
	private boolean reload;

	private double cursorX=0;
	private Timeline cursorTime= new Timeline();
	public static final double SWIPE_TIME=1; 
	public static double SWIPE_DISTANCY = MenuView.WIDTH/7;
	
	/**
	 * attribute gravity 
	 */
	private Gravity gravity;
	
	/**
	 * attribute gameView
	 */
	private GameView gameView;

	/**
	 * Boolean attribute telling if the Q key is currently pressed.
	 */
	private boolean qPressed;
	/**
	 * Boolean attribute telling if the D key is currently pressed
	 */
	private boolean dPressed;


	/**
	 * Left value for turning ship
	 */
	public static final int LEFT = 1;

	/**
	 * Right value for turning ship
	 */
	public static final int RIGHT = 2;

	private Sound sound;

	/**
	 * Constructor of control
	 * @param v
	 * @param s
	 */
	public Control(MenuView v, Sound s){
		this.menuView = v;

		gravity = new Gravity(this);
		gameView = new GameView(this);

		(new KeyProcessor(this)).start();

		this.setModel(new Model(v, gameView,s));
		this.sound = s;

		this.reload = false;
	}

	public void exitApp() {
		gravity.interrupt();
		System.exit(0);
	}


	/**
	 * Check if a CustomMenuButton have been click or ENTER key have been pressed on it
	 * @param b
	 * @param src
	 * @param target
	 */
	public void checkActions(CustomMenuButton b,BorderPane src, BorderPane target) {
		b.setOnMouseClicked(e -> displayMenu(src, target));
		b.setOnKeyPressed(e ->{
			if(e.getCode()==KeyCode.ENTER) {
				displayMenu(src, target);
			}
		});
	}

	/**
	 * use to hide the source Pane and show the target pane
	 * @param src
	 * @param target
	 */
	public void displayMenu(BorderPane src, BorderPane target) {
		src.setVisible(false);
		target.setVisible(true);

	}

	/**
	 * 
	 * @param b
	 * @param src
	 * @param target
	 * @throws IOException
	 */
	public void loadLevelSelector(CustomMenuButton b, BorderPane src, BorderPane target) throws IOException {

		b.setOnMouseClicked(e ->{
			src.setVisible(false);
			target.setVisible(true);
			this.model.initLevel(menuView.getLevelId());
		});

		b.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				src.setVisible(false);
				target.setVisible(true);
				this.model.initLevel(menuView.getLevelId());
			}
		});

	}
	
	/**
	 * used to reload the current Level after winning or losing
	 * @param b
	 * @param src
	 * @param target
	 */
	public void tryAgainLevel(CustomMenuButton b,BorderPane src, BorderPane target) {

		b.setOnMouseClicked(e -> {

			qPressed = false;
			dPressed = false;
			displayMenu(src, target);
			this.model.initLevel(menuView.getLevelId());
			this.model.setPlayer(new Player());
			model.setGameOver(false);

		});
		b.setOnKeyPressed(e ->{
			if(e.getCode()==KeyCode.ENTER) {
				qPressed = false;
				dPressed = false;
				displayMenu(src, target);
				this.model.initLevel(menuView.getLevelId());
				this.model.setPlayer(new Player());
				model.setGameOver(false);
			}
		});
	}

	/**
	 * used to load the selected level and change the scene the first time
	 * @param stg
	 */
	public void loadLevel(Stage stg) {
		menuView.getValidatelevel().setOnMouseClicked(e ->{
			if(reload) {
				reloadLevel(stg);
			}else {
				menuView.setPrimaryStage(stg);
				model.initLevel(menuView.getLevelId());
				gameView.getMainGameView().setVisible(true);
				Scene scn = new Scene(gameView.getMainGameView(),MenuView.WIDTH,MenuView.HEIGHT);
				scn.getStylesheets().add(menuView.getScene().getStylesheets().get(0));
				menuView.setGameScene((scn));
				stg.setScene(menuView.getGameScene());
				stg.setResizable(false);
				stg.show();
				menuView.setGameView(gameView);

				gravity.setActive(true);
				gravity.start();
				reload = true;
			}

		});

		menuView.getValidatelevel().setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				if(reload) {
					reloadLevel(stg);
				}else {
					menuView.setPrimaryStage(stg);
					model.initLevel(menuView.getLevelId());
					gameView.getMainGameView().setVisible(true);
					Scene scn = new Scene(gameView.getMainGameView(),MenuView.WIDTH,MenuView.HEIGHT);
					scn.getStylesheets().add(menuView.getScene().getStylesheets().get(0));
					menuView.setGameScene((scn));
					stg.setScene(menuView.getGameScene());
					stg.setResizable(false);
					stg.show();
					menuView.setGameView(gameView);

					gravity.setActive(true);
					gravity.start();
					reload = true;
				}
			}
		});

	}

	/**
	 * used load le level and chnage the scne if it's not the first time
	 * @param stg
	 */
	public void reloadLevel(Stage stg) {

		menuView.setPrimaryStage(stg);
		model.initLevel(menuView.getLevelId());
		gameView.getMainGameView().setVisible(true);
		stg.setScene(menuView.getGameScene());
		stg.setResizable(false);
		stg.show();
		menuView.setGameView(gameView);

		qPressed = false;
		dPressed = false;
		displayMenu(gameView.getLooseMenu(), gameView.getGamePane());
		displayMenu(gameView.getWinMenu(), gameView.getGamePane());
		this.model.initLevel(menuView.getLevelId());
		this.model.setPlayer(new Player());
		model.setGameOver(false);
		gravity.setTimer_repeat(0);
	}


	/**
	 * This function is used to listen on keyPressed in the game panel and to run the actions corresponding to the key
	 * @param gamePane
	 */
	public void checkKeyPressed(BorderPane gamePane) {
		gamePane.setOnKeyPressed(e -> {

			switch(e.getCode()) {
			case Q:
				this.qPressed = true;

				break;
			case D:
				this.dPressed = true;

				break;
			case A:
				this.getModel().checkAttack();

				break;
			case SPACE:
				this.getModel().makePlayerJump();
				break;
			case ESCAPE:
				displayMenu(gamePane, gameView.getPauseMenu());
				model.setGamePaused(true);
				break;
			default:
				break;
			}	
		});	

		gamePane.setOnKeyReleased(e -> {

			switch(e.getCode()) {
			case Q:
				this.qPressed = false;
				break;
			case D:
				this.dPressed = false;
				break;
			default:
				break;

			}
		});	
	}

	/**
	 * this function is used to calculate the direction and intensity of swiping interaction and return the direction 
	 * @param e
	 * @param boo
	 * @return
	 */
	public int swipeCheck(MouseEvent e,boolean boo) {
		if(!boo) {
			cursorX = e.getX();	

			IntegerProperty i = new SimpleIntegerProperty(5);
			cursorTime.getKeyFrames().add( new KeyFrame(Duration.seconds(5), new KeyValue(i, 0)) );
			cursorTime.playFromStart();
		}
		if(boo) {
			double currentPosX = e.getX();

			double swipeValue = (cursorX-currentPosX);
			if(cursorTime.getCurrentTime().toSeconds()<SWIPE_TIME ) {

				if(swipeValue > SWIPE_DISTANCY) {//swipeLeft
					cursorTime.stop();
					return 1;

				}else if(swipeValue < -SWIPE_DISTANCY) {//swipeRight
					cursorTime.stop();
					return 2;	
				}

			}
		}
		return 0;

	}
	
	
	/**
	 * used to go from WinMenu or LooseMenu to level selection when selectLevel button is click or press ENTER key on it
	 * @param playAgain
	 * @param winMenu
	 * @param gamePane
	 */
	public void levelSelect(CustomMenuButton playAgain, BorderPane winMenu, BorderPane gamePane) {
		playAgain.setOnMouseClicked(e ->{
			model.initLevel(menuView.getLevelId());			
			menuView.getPrimaryStage().setScene(menuView.getScene());
			menuView.getPrimaryStage().setResizable(false);
			menuView.getPrimaryStage().show();

		});

		playAgain.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {

				model.initLevel(menuView.getLevelId());			
				menuView.getPrimaryStage().setScene(menuView.getScene());
				menuView.getPrimaryStage().setResizable(false);
				menuView.getPrimaryStage().show();

			}
		});

	}
	

	/**
	 * use to display the pause Menu
	 */
	public void pauseGame() {
		displayMenu(gameView.getGamePane(), gameView.getPauseMenu());
	}

	/**
	 * use to go from pause menu to game Menu
	 */
	public void resumeGame() {
		displayMenu(gameView.getPauseMenu(), gameView.getGamePane());
	}

	/* Getter & setter */

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}


	public Gravity getGravity() {
		return gravity;
	}

	public void setGravity(Gravity gravity) {
		this.gravity = gravity;
	}


	public GameView getGameView() {
		return this.gameView;
	}


	public boolean isQPressed() {
		return this.qPressed;
	}

	public boolean isDPressed() {
		return this.dPressed;
	}


	public Sound getSound() {
		return sound;
	}

	public void setSound(Sound sound) {
		this.sound = sound;
	}

	public MenuView getMenuView() {
		return menuView;
	}

	public void setMenuView(MenuView menuView) {
		this.menuView = menuView;
	}


}
