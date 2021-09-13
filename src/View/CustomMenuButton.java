package View;

import Controller.Control;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class CustomMenuButton extends Button {

	private Font buttonFont = new Font("Source Code Pro Bold",20);
	
	public CustomMenuButton(String buttonText) {
		
		super(buttonText);
		setFont(buttonFont);
		setCursor(Cursor.HAND);
		setPadding(new Insets(10,10,10,10));
		setPrefSize(GameView.WIDTH/4, GameView.HEIGHT/12);
		getStyleClass().add("menus-button");
	}
	
	
}
