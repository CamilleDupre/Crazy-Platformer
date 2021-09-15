package Controller;

import java.io.IOException;

import Model.Model;
import View.CustomMenuButton;
import View.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class Control {
	private Model model;
	private GameView view;
	
	private double cursorX=0;
	private boolean joystickDrag;
	private Timeline cursorTime= new Timeline();
	public static final double SWIPE_TIME=1; 
	public static double SWIPE_DISTANCY = GameView.WIDTH/7;
	private Gravity gravity;
	
	public Control(GameView v){
		this.setModel(new Model(v));
		this.view = v;
		this.joystickDrag = false;
		
		gravity = new Gravity(this);
		gravity.start();
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
	
	public void loadSkin(CustomMenuButton b, BorderPane src, BorderPane target) throws IOException {

		b.setOnMouseClicked(e ->{
			src.setVisible(false);
			target.setVisible(true);
			this.model.setCurrentLevel(view.getLevelId());}
				);

		b.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				src.setVisible(false);
				target.setVisible(true);
				this.model.setCurrentLevel(view.getLevelId());}
		}
				);

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

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public void moveDown() {
		if (model.getPlayer().getPosition().getY() +2 < Model.MIN_FLOOR_HEIGHT ){
			model.getPlayer().setPosition( new Point2D(model.getPlayer().getPosition().getX(),model.getPlayer().getPosition().getY() +2)) ;
			//System.out.println("Je tombe : " + hauteur);
			//monAffichage.repaint();
			//view.getCanvas().repaint();
			System.out.println(model.getPlayer().getPosition().getY());
		}
	}
}
