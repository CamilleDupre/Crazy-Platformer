package Controller;

import java.io.IOException;

import Model.Model;
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
	private MenuView view;

	private double cursorX=0;
	private boolean joystickDrag;
	private Timeline cursorTime= new Timeline();
	public static final double SWIPE_TIME=1; 
	public static double SWIPE_DISTANCY = MenuView.WIDTH/7;
	private Gravity gravity;
	private GameView gameView;



	public Control(MenuView v){
		this.setModel(new Model(v));
		this.view = v;
		this.joystickDrag = false;

		gravity = new Gravity(this);
		gameView = new GameView(this);

	}

	public void exitApp() {
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
			this.model.setCurrentLevel(view.getLevelId());
		});

		b.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				src.setVisible(false);
				target.setVisible(true);
				this.model.setCurrentLevel(view.getLevelId());
			}
		});

	}

	public void loadLevel(int levelId, Stage stg) {
		view.getValidatelevel().setOnMouseClicked(e ->{
			gravity.setActive(true);
			gravity.start();

			gameView.getMainGameView().setVisible(true);
			view.setGameScene((new Scene(gameView.getMainGameView(),MenuView.WIDTH,MenuView.HEIGHT)));
			stg.setScene(view.getGameScene());
			stg.setResizable(false);
			stg.show();
			view.setGameView(gameView);
		});

		view.getValidatelevel().setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				gravity.setActive(true);
				gravity.start();

				gameView.getMainGameView().setVisible(true);
				view.setGameScene((new Scene(gameView.getMainGameView(),MenuView.WIDTH,MenuView.HEIGHT)));
				stg.setScene(view.getGameScene());
				stg.setResizable(false);
				stg.show();
				view.setGameView(gameView);
			}
		});
	}



	/*
	 * this function is used to listen on keyPressed in the game panel and to run the actions corresponding to the key
	 */
	public void checkKeyPressed(BorderPane gamePane) {
		gamePane.setOnKeyPressed(e -> {
			System.out.println("dans keypressed");
			if(e.getCode()==KeyCode.SPACE) {
				makePlayerJump();
				System.out.println("jump space");
			}
		});

		gamePane.setOnMouseClicked(e ->{
			makePlayerJump();
			System.out.println("jump click");
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



	public void makePlayerJump() {
		if (!model.getPlayer().isJumping()){
			model.getPlayer().setPosition( new Point2D(model.getPlayer().getPosition().getX(),model.getPlayer().getPosition().getY() + model.getPlayer().getPlayerJump())) ;
			model.getPlayer().setJumping(true);
			gameView.repaint(); 
		}
		System.out.println("dans jump");
	}



	public void gravityForce() {
		if (model.getPlayer().getPosition().getY() + model.getPlayer().getPlayerSize() < Model.MIN_FLOOR_HEIGHT ){
			model.getPlayer().setPosition( new Point2D(model.getPlayer().getPosition().getX(),model.getPlayer().getPosition().getY() +8)) ;

		}else if(model.getPlayer().getPosition().getY() + model.getPlayer().getPlayerSize() != Model.MIN_FLOOR_HEIGHT){
			model.getPlayer().setPosition( new Point2D(model.getPlayer().getPosition().getX(), Model.MIN_FLOOR_HEIGHT-model.getPlayer().getPlayerSize()));
			model.getPlayer().setJumping(false);
		}
		gameView.repaint(); 
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
}
