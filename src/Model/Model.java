package Model;

import View.GameView;

public class Model {

	private GameView gameView;
	private int cssStyle;
	
	public Model(GameView v) {
		this.gameView = v;
	}
	
	
	
	public void changeCSS(int cssId) {
		cssStyle = cssId;
		switch(cssStyle) {
		case 0 :
			gameView.getScene().getStylesheets().remove(0);
			gameView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("white.css").toExternalForm());
			break;
		
		case 1 :
			gameView.getScene().getStylesheets().remove(0);
			gameView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("dark.css").toExternalForm());
			break;
		
		case 2 :
			gameView.getScene().getStylesheets().remove(0);
			gameView.getScene().getStylesheets().add(getClass().getClassLoader().getResource("neon.css").toExternalForm());
			break;
			
		default :
			break;
		}
		gameView.getPrimaryStage().show();

	}
}
