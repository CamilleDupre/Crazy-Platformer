package View;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import Controller.Control;
import Controller.Gravity;
import Sound.Sound;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class MenuView extends Application{

	private Stage primaryStage;
	private Scene scene;
	private Control control;
	private GameView gameView;

	private BorderPane mainMenu;
	private StackPane mainPane;
	private BorderPane settingMenu;
	private BorderPane rulesMenu;
	private BorderPane levelPane;	
	
	
	CustomMenuButton backButtonR;
	CustomMenuButton backButtonS;
	CustomMenuButton backButtonL;
	
	private Scene gameScene;
	
	private CustomMenuButton validatelevel;
	
	private int levelId=0;
	
	public static int HEIGHT;
	public static int WIDTH;
	private Sound sound;


	public void start(Stage stg) throws IOException {
		
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		HEIGHT = (int) screenBounds.getHeight();
		WIDTH = (int) screenBounds.getWidth();

		primaryStage=stg;
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                control.getGravity().interrupt();
                System.exit(0);
            }
        });
		
		this.sound = new Sound();
		this.control = new Control(this, this.sound);

		mainPane = new StackPane();
		mainPane.setPrefSize(WIDTH, HEIGHT);

		
		setUpRulesPanel();
		setUpLevelSelectorPanel();
		setUpSettingPanel();
		setUpLevelSelectorPanel();
		setUpMainMenuPanel();   
	
		
		mainPane.getChildren().add(mainMenu);
		mainPane.getChildren().add(settingMenu);
		mainPane.getChildren().add(rulesMenu);		
		mainPane.getChildren().add(levelPane);
		
		
		//Action back to main menu
		control.checkActions(backButtonR, rulesMenu, mainMenu);
		control.checkActions(backButtonS,settingMenu, mainMenu);
		control.checkActions(backButtonL,levelPane, mainMenu);

		mainMenu.setVisible(true);
		settingMenu.setVisible(false);
		rulesMenu.setVisible(false);
		levelPane.setVisible(false);

		scene = new Scene(getMainPane(), WIDTH, HEIGHT);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("white.css").toExternalForm());
		
		
		primaryStage.setTitle("Crazy Platformer");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
		primaryStage.setResizable(false);


	}
	
	private void setUpMainMenuPanel() {
	/////// MAIN MENU ///////

		this.mainMenu = new BorderPane();
		this.mainMenu.setId("mainMenu");
		this.mainMenu.setPrefSize(WIDTH,HEIGHT);

		////TOP////
		FlowPane mainFP = new FlowPane();
		Text mainTitle = new Text("CRAZY PLATEFORMER");
		mainTitle.getStyleClass().add("title");
		mainTitle.setTextAlignment(TextAlignment.CENTER);
		mainFP.getChildren().add(mainTitle);
		mainFP.setAlignment(Pos.CENTER);
		mainFP.setPadding(new Insets(HEIGHT/10,0,0,0));
		mainFP.setPrefSize(WIDTH, HEIGHT/6);

		this.mainMenu.setTop(mainFP);

		////CENTER////
		VBox mainVB = new VBox();
		mainVB.setAlignment(Pos.CENTER);

		mainVB.setSpacing(15);
		CustomMenuButton pg = new CustomMenuButton("PLAY GAME");
		CustomMenuButton rules = new CustomMenuButton("ABOUT THE GAME");			
		CustomMenuButton settings = new CustomMenuButton("SETTINGS");			
		CustomMenuButton exit = new CustomMenuButton("EXIT GAME");


		mainVB.getChildren().add(pg);
		mainVB.getChildren().add(settings);
		mainVB.getChildren().add(rules);
		mainVB.getChildren().add(exit);

		this.mainMenu.setCenter(mainVB);
		this.mainMenu.toFront();

		///////////////////////
		
		///MAIN ACTION///

		control.checkActions(pg, this.mainMenu, levelPane);
		control.checkActions(settings, this.mainMenu, settingMenu);
		control.checkActions(rules, this.mainMenu, rulesMenu);

		exit.setOnMouseClicked(e -> control.exitApp());
		exit.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});
	}
	
	

	private void setUpRulesPanel() {	
		/////// RULES ///////

		rulesMenu = new BorderPane();
		rulesMenu.setPrefSize(WIDTH, HEIGHT);
		rulesMenu.setId("rulesMenu");


		////TOP////
		FlowPane rulesFP = new FlowPane();
		Text rulesTitle = new Text("ABOUT THE GAME");
		rulesTitle.getStyleClass().add("title");
		rulesTitle.setTextAlignment(TextAlignment.CENTER);
		rulesFP.getChildren().add(rulesTitle);
		rulesFP.setAlignment(Pos.CENTER);
		rulesFP.setPadding(new Insets(20,0,0,0));
		rulesFP.setPrefSize(WIDTH, HEIGHT/8);

		rulesMenu.setTop(rulesFP);

		////CENTER////

		VBox rulesVB = new VBox();
		rulesVB.setSpacing(20);
		rulesVB.setPadding(new Insets(0,5,0,0));

		Text rulesTxt = new Text(" Welcoom to Crazy Platformer !!\n"
				+ "\n"
				+ " In order to finish a level you have to collect the differents coins and bring them to the chess.\n"
				+ "\n"
				+ " You will be faccing obstacle, like fire, ennemie, platform jump.\n"
				+ "\n"
				+ " In order to move use: \n"
				+ " -  Q to go left\n"
				+ " -  D to go rigth\n"
				+ " -  Space to go jump\n"
				+ "\n"
				+ " GOOD LUCK !");


		rulesTxt.getStyleClass().add("rules");
		
		
		HBox coins = new HBox();
		coins.setSpacing(20);
		coins.setAlignment(Pos.CENTER);
		File leftFile = new File("img/other/coin.png");
		ImageView imgLeft= null;
		try {
			imgLeft = new ImageView( new Image(leftFile.toURI().toURL().toString(),50,50,false,false));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Text coinsText = new Text("Coins you have to collect");
		coins.getChildren().add(imgLeft);
		coins.getChildren().add(coinsText);

		HBox chest = new HBox();
		chest.setSpacing(20);
		chest.setAlignment(Pos.CENTER);
		File rightFile = new File("img/other/Chest.png");
		ImageView imgRight = null;
		try {
			imgRight = new ImageView( new Image(rightFile.toURI().toURL().toString(),50,50,false,false));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Text chestText = new Text("Drop the coins to finish the level");
		chest.getChildren().add(imgRight);
		chest.getChildren().add(chestText);
		
		
		
		HBox heart = new HBox();
		heart.setSpacing(20);
		heart.setAlignment(Pos.CENTER);
		File heartFile = new File("img/other/Heart.png");
		ImageView imgheart = null;
		File heartFileLost = new File("img/other/Heart_lost.png");
		ImageView imgheartLost = null;
		try {
			imgheart = new ImageView( new Image(heartFile.toURI().toURL().toString(),50,50,false,false));
			imgheartLost = new ImageView( new Image(heartFileLost.toURI().toURL().toString(),50,50,false,false));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Text heartText = new Text("Lifes");
		Text heartTextLost = new Text("Lifes lost");
		heart.getChildren().add(imgheart);
		heart.getChildren().add(heartText);
		heart.getChildren().add(imgheartLost);
		heart.getChildren().add(heartTextLost);
		
		HBox enemy = new HBox();
		enemy.setSpacing(20);
		enemy.setAlignment(Pos.CENTER);
		File enemyFile = new File("img/other/enemy.png");
		ImageView imgenemy = null;
		try {
			imgenemy = new ImageView( new Image(enemyFile.toURI().toURL().toString(),50,50,false,false));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Text enemyText = new Text("Enemy");
		enemy.getChildren().add(imgenemy);
		enemy.getChildren().add(enemyText);

		rulesVB.getChildren().add(rulesTxt);
		rulesVB.getChildren().add(coins);
		rulesVB.getChildren().add(chest);
		rulesVB.getChildren().add(heart);
		rulesVB.getChildren().add(enemy);

		rulesMenu.setCenter(rulesVB);

		////BOTTOM////
		HBox botRuleHB = new HBox();
		botRuleHB.setAlignment(Pos.BOTTOM_RIGHT);
		botRuleHB.setPadding(new Insets(0,30,30,30));
		backButtonR = new CustomMenuButton("←");
		backButtonR.setPrefWidth(WIDTH/8);
		
		botRuleHB.getChildren().add(backButtonR);
		rulesMenu.setBottom(botRuleHB);
		
		
		//Action//
		//control.checkActions(backButtonR, rulesMenu, this.mainMenu);
	}
	
	
	
	
	private void setUpSettingPanel() {
		
		 ////// SETTINGS ///////
		 

		settingMenu = new BorderPane();
		settingMenu.setId("settingMenu");

		////TOP////
		FlowPane settingFP = new FlowPane();
		Text settingTitle = new Text("SETTINGS");
		settingTitle.getStyleClass().add("title");
		settingTitle.setTextAlignment(TextAlignment.CENTER);
		settingFP.getChildren().add(settingTitle);
		settingFP.setAlignment(Pos.CENTER);
		settingFP.setPadding(new Insets(40,0,0,0));
		settingFP.setPrefSize(WIDTH, HEIGHT/6);


		settingMenu.setTop(settingFP);

		////CENTER////
		HBox settHB = new HBox();
		settHB.setAlignment(Pos.CENTER);
		settHB.setSpacing(70);
/*
		File soundFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/sound.png/");
		ImageView imgSound = new ImageView( new Image(soundFile.toURI().toURL().toString(),50,50,false,false));
		Button sound = new Button("",imgSound);
		sound.setPrefSize(WIDTH/5,WIDTH/5);
		sound.getStyleClass().add("setting-button");

		File musicFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/music.png/");
		ImageView imgMusic = new ImageView( new Image(musicFile.toURI().toURL().toString(),50,50,false,false));
		Button music = new Button("",imgMusic);
		music.setPrefSize(WIDTH/5,WIDTH/5);
		music.getStyleClass().add("setting-button");
*/

		VBox settVB = new VBox();
		settVB.setAlignment(Pos.CENTER_LEFT);
		settVB.setSpacing(30);

		CustomMenuButton white = new CustomMenuButton("WHITE THEME");
		white.setOnMouseClicked(e -> control.getModel().changeCSS(0));
		white.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(0);
			}
		});
		CustomMenuButton dark = new CustomMenuButton("DARK THEME");
		dark.setOnMouseClicked(e -> control.getModel().changeCSS(1));
		dark.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(1);
			}
		});
		CustomMenuButton neon = new CustomMenuButton("NEON THEME");
		neon.setOnMouseClicked(e -> control.getModel().changeCSS(2));
		neon.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(2);
			}
		});
		settVB.getChildren().add(white);
		settVB.getChildren().add(dark);
		settVB.getChildren().add(neon);

	//	settHB.getChildren().add(sound);
	//	settHB.getChildren().add(music);
		settHB.getChildren().add(settVB);

		settingMenu.setCenter(settHB);
		

		////BOTTOM////
		HBox botSetHB = new HBox();
		botSetHB.setAlignment(Pos.BOTTOM_RIGHT);
		botSetHB.setPadding(new Insets(0,30,30,30));

		backButtonS = new CustomMenuButton("←");
		backButtonS.setPrefWidth(WIDTH/8);
		botSetHB.getChildren().add(backButtonS);
		settingMenu.setBottom(botSetHB);	
		
		control.checkActions(backButtonS,settingMenu, mainMenu);
		
	

		///SETTINGS///
		/*
		music.setOnMouseClicked(e -> control.startStopMusic());
		sound.setOnMouseClicked(e-> control.startStopSound());
		 */
		
		
	}
		
	private void setUpLevelSelectorPanel() {
		/////// LEVEL PANE ///////
		
			levelPane = new BorderPane();
	
			levelPane.setPrefSize(WIDTH,HEIGHT);
			
			FlowPane levelFP = new FlowPane();
			Text levelTitle = new Text("SWIPE TO CHOOSE THE LEVEL");
			levelTitle.getStyleClass().add("title");
			levelTitle.setTextAlignment(TextAlignment.CENTER);
			levelFP.getChildren().add(levelTitle);
			levelFP.setAlignment(Pos.CENTER);
			levelFP.setPadding(new Insets(20,0,0,0));
			levelFP.setPrefSize(WIDTH, HEIGHT/6);
	
			levelPane.setTop(levelFP);
			 
			//// CENTER ////
			StackPane stacklevel = new StackPane();
			stacklevel.setAlignment(Pos.CENTER);
			
	
			ImageView current = new ImageView(control.getModel().getBackgroundList().get(0));
			current.setId("0");
			current.setPreserveRatio(true);
			current.setFitHeight(WIDTH/5);
			Button currentB = new Button("",current);
			currentB.setDisable(true);
			currentB.setOpacity(1);
			Text currentLevelTitle = new Text("Level 1"); 
			currentLevelTitle.getStyleClass().add("title");
			
			ImageView left = new ImageView(control.getModel().getBackgroundList().get(1));
			left.setId("3");
			left.setPreserveRatio(true);
			left.setFitHeight(WIDTH/6);
			Button leftB = new Button("",left);
			leftB.setDisable(true);
			
			
			ImageView right = new ImageView(control.getModel().getBackgroundList().get(2));
			right.setId("1");
			right.setPreserveRatio(true);
			right.setFitHeight(WIDTH/6);
			Button rightB = new Button("",right);
			rightB.setDisable(true);
		
					
			ImageView back = new ImageView(control.getModel().getBackgroundList().get(0));
			back.setId("2");
			back.setPreserveRatio(true);
			back.setFitHeight(WIDTH/6);
			Button backB = new Button("",back);
			backB.setDisable(true);
	
			
			
			
			VBox levelVBFront= new VBox();
			levelVBFront.setAlignment(Pos.CENTER);
			levelVBFront.getChildren().add(currentB);
			levelVBFront.getChildren().add(currentLevelTitle);
			
			
			VBox levelVBBack = new VBox();
			levelVBBack.setSpacing(50);
			levelVBBack.setAlignment(Pos.CENTER);
			
			HBox firstLine = new HBox();
			firstLine.setAlignment(Pos.CENTER);
			firstLine.getChildren().add(backB);
			
			HBox secondLine = new HBox();
			secondLine.setAlignment(Pos.CENTER);
			secondLine.setSpacing(300);
			secondLine.getChildren().add(leftB);
			secondLine.getChildren().add(rightB);
			
			levelVBBack.getChildren().add(firstLine);
			levelVBBack.getChildren().add(secondLine);
			
			
			levelPane.setOnMousePressed(e->{
				control.swipeCheck(e, false);
			});
			
			levelPane.setOnMouseReleased(e->{
				Image temp;
				String tempId;
				if(control.swipeCheck(e, true)==1) {//left
					temp = current.getImage();
					tempId = current.getId();
					
					current.setImage(right.getImage()); 
					current.setId(right.getId());
					
					right.setImage(back.getImage());
					right.setId(back.getId());
					
					back.setImage(left.getImage());
					back.setId(left.getId());
					
					left.setImage(temp);
					left.setId(tempId);
					
				}else if(control.swipeCheck(e, true)==2) {//right
					temp = current.getImage();
					tempId = current.getId();
					
					current.setImage(left.getImage()); 
					current.setId(left.getId());
					
					left.setImage(back.getImage());
					left.setId(back.getId());
					
					back.setImage(right.getImage());
					back.setId(right.getId());
					
					right.setImage(temp);
					right.setId(tempId);
				}
				switch(current.getId()) {
					case "0" :
						levelId = 0;
						break;
					case "1" :
						levelId = 1;
						break;
					case "2" :
						levelId = 2;
						break;
					case "3" :
						levelId = 3;
						break;
				}
				currentLevelTitle.setText("Level "+ (levelId+1));	
				primaryStage.show();
			});
			
			levelPane.setOnKeyPressed(e -> {
				Image temp;
				String tempId;
				if(e.getCode()==KeyCode.D || e.getCode()==KeyCode.RIGHT ) {
					temp = current.getImage();
					tempId = current.getId();
					
					current.setImage(right.getImage()); 
					current.setId(right.getId());
					
					right.setImage(back.getImage());
					right.setId(back.getId());
					
					back.setImage(left.getImage());
					back.setId(left.getId());
					
					left.setImage(temp);
					left.setId(tempId);
				}
				if(e.getCode()==KeyCode.Q || e.getCode()==KeyCode.LEFT) {
					temp = current.getImage();
					tempId = current.getId();
					
					current.setImage(left.getImage()); 
					current.setId(left.getId());
					
					left.setImage(back.getImage());
					left.setId(back.getId());
					
					back.setImage(right.getImage());
					back.setId(right.getId());
					
					right.setImage(temp);
					right.setId(tempId);
				}
				switch(current.getId()) {
				case "0" :
					levelId = 0;
					break;
				case "1" :
					levelId = 1;
					break;
				case "2" :
					levelId = 2;
					break;
				case "3" :
					levelId = 3;
					break;
				}
				currentLevelTitle.setText("Level "+ (levelId+1));	
				primaryStage.show();
			});
			
			currentLevelTitle.setText("Level "+ (levelId+1));
			
			primaryStage.show();
			
			stacklevel.getChildren().add(levelVBBack);
			stacklevel.getChildren().add(levelVBFront);
	
			levelPane.setCenter(stacklevel);
			
			///Bottom///
			HBox validatelevelHB = new HBox();
			validatelevelHB.setPadding(new Insets(0,0,30,0));
			validatelevel = new CustomMenuButton("TRY THIS LEVEL");
			validatelevelHB.getChildren().add(validatelevel);
			validatelevelHB.setAlignment(Pos.CENTER);
			
			backButtonL = new CustomMenuButton("←");
			backButtonL.setPrefWidth(WIDTH/8);
			validatelevelHB.setSpacing(100);
			
			validatelevelHB.getChildren().add(backButtonL);
			
			levelPane.setBottom(validatelevelHB);
			
			control.loadLevel(levelId, primaryStage);
				
	}
	
	
	public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}

	public StackPane getMainPane() {
		return mainPane;
	}

	public Control getControl() {
		return control;
	}

	public void setControl(Control control) {
		this.control = control;
	}


	public Stage getPrimaryStage() {
		return primaryStage;
	}


	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	
	public static void main(String[] args) {
		launch(args);
	}


	public Scene getScene() {
		return scene;
	}


	public void setScene(Scene scene) {
		this.scene = scene;
	}

	
	public int getLevelId() {
		return levelId;
	}


	public void setLevelId(int levelId) {
		this.levelId = levelId;
	}

	public CustomMenuButton getValidatelevel() {
		return validatelevel;
	}

	public void setValidatelevel(CustomMenuButton validatelevel) {
		this.validatelevel = validatelevel;
	}

	public Scene getGameScene() {
		return gameScene;
	}

	public void setGameScene(Scene gameScene) {
		this.gameScene = gameScene;
	}

}

