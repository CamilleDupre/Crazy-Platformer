package Controller;

import java.io.IOException;

import Model.Model;
import Sound.Sound;
import View.CustomMenuButton;
import View.MenuView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import View.GameView;

public class Control {
	private Model model;
	private MenuView menuView;
	

	

	private double cursorX=0;
	private boolean joystickDrag;
	private Timeline cursorTime= new Timeline();
	public static final double SWIPE_TIME=1; 
	public static double SWIPE_DISTANCY = MenuView.WIDTH/7;
	private Gravity gravity;
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
	 * KeyListener implementation
	 */
	private KeyControl keyControl;
	
	/**
	 * Left value for turning ship
	 */
	public static final int LEFT = 1;
	
	/**
	 * Right value for turning ship
	 */
	public static final int RIGHT = 2;
	
	private Sound sound;

	public Control(MenuView v, Sound s){
		this.menuView = v;
		this.joystickDrag = false;

		gravity = new Gravity(this);
		gameView = new GameView(this);
		
		(new KeyProcessor(this)).start();
	
		this.setModel(new Model(v, gameView,s));
		this.sound = s;
	}

	public void exitApp() {
		gravity.interrupt();
		System.exit(0);
	}



	public void checkActions(CustomMenuButton b,BorderPane src, BorderPane target) {
		b.setOnMouseClicked(e -> displayMenu(src, target));
		b.setOnKeyPressed(e ->{
			if(e.getCode()==KeyCode.ENTER) {
				displayMenu(src, target);
			}
		});
	}

	public void displayMenu(BorderPane src, BorderPane target) {
		src.setVisible(false);
		target.setVisible(true);

	}

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

	public void loadLevel(Stage stg) {
		menuView.getValidatelevel().setOnMouseClicked(e ->{
			model.initLevel(menuView.getLevelId()+1);
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
		
		});

		menuView.getValidatelevel().setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {

				System.out.println(menuView.getLevelId()+1);
				model.initLevel(menuView.getLevelId()+1);
				gameView.getMainGameView().setVisible(true);
				Scene scn = new Scene(gameView.getMainGameView(),MenuView.WIDTH,MenuView.HEIGHT);
				scn.getStylesheets().add(menuView.getScene().getStylesheets().get(0));
				menuView.setGameScene((scn));
				stg.setScene(menuView.getGameScene());
				stg.setResizable(false);
				stg.show();
				
				gravity.setActive(true);
				gravity.start();
			}
		});
		
	}



	/*
	 * this function is used to listen on keyPressed in the game panel and to run the actions corresponding to the key
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
				case SPACE:
					this.getModel().makePlayerJump();
					break;
				case ESCAPE:
					displayMenu(gamePane, gameView.getPauseMenu());
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

	public void pauseGame() {
		displayMenu(gameView.getGamePane(), gameView.getPauseMenu());
	}
	
	public void resumeGame() {
		displayMenu(gameView.getPauseMenu(), gameView.getGamePane());
	}



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

	public KeyControl getKeyControl() {
		return keyControl;
	}

	public void setKeyControl(KeyControl keyControl) {
		this.keyControl = keyControl;
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
