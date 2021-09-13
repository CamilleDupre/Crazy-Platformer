package Model;

import java.util.ArrayList;

import View.GameView;

public class Model {

	private GameView gameView;
	private int cssStyle;
	private ArrayList<String> backgroundList;
	
	private int currentLevel;
	
	
	
	public Model(GameView v) {
		this.gameView = v;
		
		initBackgroundRessources();
		
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
	
	
	public ArrayList<String> getBackgroundList(){
		return backgroundList;
	}


	public int getCurrentLevel() {
		return currentLevel;
	}


	public void setCurrentLevel(int currentLevel) {
		this.currentLevel = currentLevel;
	}


	
}



