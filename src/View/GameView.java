package View;

import java.io.File;
import java.io.IOException;

import Controller.Control;
import javafx.animation.Timeline;
//import Controller.Control;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
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
import javafx.stage.Stage;


public class GameView extends Application{

	private Stage primaryStage;
	private Scene scene;
	private Control control;

	private StackPane mainPane;
	private BorderPane gamePane;
	private BorderPane winMenu;
	private BorderPane looseMenu;
	private GridPane grid;
	private Timeline timeline;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(0);
	private Label time; 


	public void start(Stage stg) throws IOException {

		primaryStage=stg;
		control = new Control(this);

		mainPane = new StackPane();
		mainPane.setPrefSize(Control.WIDTH, Control.HEIGHT);



		/////// MAIN MENU ///////

		BorderPane mainMenu = new BorderPane();
		mainMenu.setId("mainMenu");
		mainMenu.setPrefSize(Control.WIDTH,Control.HEIGHT );

		////TOP////
		FlowPane mainFP = new FlowPane();
		Text mainTitle = new Text("CRAZY PLATEFORMER");
		mainTitle.getStyleClass().add("title");
		mainTitle.setTextAlignment(TextAlignment.CENTER);
		mainFP.getChildren().add(mainTitle);
		mainFP.setAlignment(Pos.CENTER);
		mainFP.setPadding(new Insets(40,0,0,0));
		mainFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);

		mainMenu.setTop(mainFP);

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

		mainMenu.setCenter(mainVB);
		mainMenu.toFront();

		///////////////////////


		/////// RULES ///////

		BorderPane rulesMenu = new BorderPane();
		rulesMenu.setPrefSize(Control.WIDTH, Control.HEIGHT);


		////TOP////
		FlowPane rulesFP = new FlowPane();
		Text rulesTitle = new Text("ABOUT THE GAME");
		rulesTitle.getStyleClass().add("title");
		rulesTitle.setTextAlignment(TextAlignment.CENTER);
		rulesFP.getChildren().add(rulesTitle);
		rulesFP.setAlignment(Pos.CENTER);
		rulesFP.setPadding(new Insets(20,0,0,0));
		rulesFP.setPrefSize(Control.WIDTH, Control.HEIGHT/8);

		rulesMenu.setTop(rulesFP);

		////CENTER////

		VBox rulesVB = new VBox();
		rulesVB.setSpacing(20);
		rulesVB.setPadding(new Insets(0,5,0,0));

		Text rulesTxt = new Text(" Minesweeper has a very basic gameplay style.\n"
				+ " In its original form, mines are scattered throughout a board.\n"
				+ " This board is divided into cells, which have three states: uncovered, covered and flagged.\n"
				+ " A covered cell is blank and clickable, while an uncovered cell is exposed,\n"
				+ " either containing a number (the mines adjacent to it),\n"
				+ " or a mine. When a cell is uncovered by a player click, and if it bears a mine, the game ends.\n"
				+ " A flagged cell is similar to a covered one, in the way that mines are not triggered when a cell is flagged,\n"
				+ " and it is impossible to lose through the action of flagging a cell.\n"
				+ " However, flagging a cell implies that a player thinks there is a mine underneath,\n"
				+ " which causes the game to deduct an available mine from the display. In order to win the game,\n"
				+ " players must logically deduce where mines exist through the use of the numbers given by uncovered cells.\n"
				+ " To win, all non-mine cells must be uncovered. At this stage, the timer is stopped. Commonly all mine cells\n"
				+ " are also flagged, but this is not required. When a player left-clicks on a cell, the game will uncover it.\n"
				+ " If there are no mines adjacent to that particular cell, the cell will display a blank tile,\n"
				+ " and all adjacent cells will automatically be uncovered. Right-clicking on a cell will flag it,\n"
				+ " causing a flag to appear on it.\n"
				+ " Note that flagged cells are still covered, and a player can click on it to uncover it, like a normal covered cell\n"
				+ " A click will clear the map and place numbers on the grid. The numbers reflect the number of mines touching a square.");


		HBox leftClick = new HBox();
		leftClick.setSpacing(20);
		leftClick.setAlignment(Pos.CENTER);
	//	File leftFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/leftMouse.png/");
	//	ImageView imgLeft = new ImageView( new Image(leftFile.toURI().toURL().toString(),50,50,false,false));
		Text leftTxt = new Text("LEFT CLICK or ENTER to discover whats under the box");
	//	leftClick.getChildren().add(imgLeft);
		leftClick.getChildren().add(leftTxt);

		HBox rightClick = new HBox();
		rightClick.setSpacing(20);
		rightClick.setAlignment(Pos.CENTER);
	//	File rightFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/rightMouse.png/");
	//	ImageView imgRight = new ImageView( new Image(rightFile.toURI().toURL().toString(),50,50,false,false));
		Text rightTxt = new Text("RIGHT CLICK, SPACE or DRAG AND DROP to place a flag on a box where you think there is a bomb");
	//	rightClick.getChildren().add(imgRight);
		rightClick.getChildren().add(rightTxt);

		rulesVB.getChildren().add(rulesTxt);
		rulesVB.getChildren().add(leftClick);
		rulesVB.getChildren().add(rightClick);

		rulesMenu.setCenter(rulesVB);

		////BOTTOM////
		HBox botRuleHB = new HBox();
		botRuleHB.setAlignment(Pos.BOTTOM_RIGHT);
		botRuleHB.setPadding(new Insets(0,10,10,10));
		CustomMenuButton backButtonR = new CustomMenuButton("←");

		botRuleHB.getChildren().add(backButtonR);
		rulesMenu.setBottom(botRuleHB);
		/////////////////////////


		////// DIFFICULTY ///////


		BorderPane difMenu = new BorderPane();
		difMenu.setId("difMenu");

		////TOP////
		FlowPane difFP = new FlowPane();
		Text difTitle = new Text("CHOOSE DIFFICULTY");
		difTitle.getStyleClass().add("title");
		difTitle.setTextAlignment(TextAlignment.CENTER);
		difFP.getChildren().add(difTitle);
		difFP.setAlignment(Pos.CENTER);
		difFP.setPadding(new Insets(40,0,0,0));
		difFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);


		difMenu.setTop(difFP);

		////CENTER////
		VBox difVB = new VBox();
		difVB.setAlignment(Pos.CENTER);
		difVB.setSpacing(15);

		CustomMenuButton zen = new CustomMenuButton("ZEN");
		CustomMenuButton beginner = new CustomMenuButton("BEGINNER");			
		CustomMenuButton advanced = new CustomMenuButton("ADVANCED");			
		CustomMenuButton expert = new CustomMenuButton("EXPERT");

		difVB.getChildren().add(zen);
		difVB.getChildren().add(beginner);
		difVB.getChildren().add(advanced);
		difVB.getChildren().add(expert);

		difMenu.setCenter(difVB);

		////BOTTOM//// 
		HBox difHB = new HBox();
		difHB.setAlignment(Pos.BOTTOM_RIGHT);
		difHB.setPadding(new Insets(0,10,10,10));
		CustomMenuButton backButtonD = new CustomMenuButton("←");
		difHB.getChildren().add(backButtonD);
		difMenu.setBottom(difHB);

		///////////////////////

		
		
	
		
		/****** LEVEL PANE ******/
/*
		BorderPane skinPane = new BorderPane();

		skinPane.setPrefSize(Control.WIDTH,Control.HEIGHT);
		
		FlowPane skinFP = new FlowPane();
		Text skinTitle = new Text("SWIPE TO CHOOSE PLAYER SKIN");
		skinTitle.getStyleClass().add("title");
		skinTitle.setTextAlignment(TextAlignment.CENTER);
		skinFP.getChildren().add(skinTitle);
		skinFP.setAlignment(Pos.CENTER);
		skinFP.setPadding(new Insets(20,0,0,0));
		skinFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);

		skinPane.setTop(skinFP);
		 
		//// CENTER ////
		StackPane stackSkin = new StackPane();
		stackSkin.setAlignment(Pos.CENTER);
		

		ImageView current = new ImageView(warrior);
		current.setId("0");
		current.setPreserveRatio(true);
		current.setFitHeight(Control.WIDTH/5);
		Button currentB = new Button("",current);
		currentB.setDisable(true);
		currentB.setOpacity(1);
		
		ImageView left = new ImageView(archer);
		left.setId("1");
		left.setPreserveRatio(true);
		left.setFitHeight(Control.WIDTH/6);
		Button leftB = new Button("",left);
		leftB.setDisable(true);
		
		
		ImageView right = new ImageView(soldier);
		right.setId("3");
		right.setPreserveRatio(true);
		right.setFitHeight(Control.WIDTH/6);
		Button rightB = new Button("",right);
		rightB.setDisable(true);
	
		
		ImageView back = new ImageView(wizard);
		back.setId("2");
		back.setPreserveRatio(true);
		back.setFitHeight(Control.WIDTH/6);
		Button backB = new Button("",back);
		backB.setDisable(true);
		
		
		
		VBox skinVBFront= new VBox();
		skinVBFront.setAlignment(Pos.CENTER);
		skinVBFront.getChildren().add(currentB);
		
		
		VBox skinVBBack = new VBox();
		skinVBBack.setSpacing(70);
		skinVBBack.setAlignment(Pos.CENTER);
		
		HBox firstLine = new HBox();
		firstLine.setAlignment(Pos.CENTER);
		firstLine.getChildren().add(backB);
		
		HBox secondLine = new HBox();
		secondLine.setAlignment(Pos.CENTER);
		secondLine.setSpacing(200);
		secondLine.getChildren().add(leftB);
		secondLine.getChildren().add(rightB);
		
		skinVBBack.getChildren().add(firstLine);
		skinVBBack.getChildren().add(secondLine);
		
		
		skinPane.setOnMousePressed(e->{
			ctrl.swipeCheck(e, false);
		});
		
		skinPane.setOnMouseReleased(e->{
			Image temp;
			String tempId;
			if(ctrl.swipeCheck(e, true)==1) {//left
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
				
			}else if(ctrl.swipeCheck(e, true)==2) {//right
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
					skinId = 0;
					break;
				case "1" :
					skinId = 1;
					break;
				case "2" :
					skinId = 2;
					break;
				case "3" :
					skinId = 3;
					break;
			}
			
			primaryStage.show();
		});
		
		stackSkin.getChildren().add(skinVBBack);
		stackSkin.getChildren().add(skinVBFront);

		skinPane.setCenter(stackSkin);
		
		///Bottom///
		HBox validateSkinHB = new HBox();
		validateSkinHB.setPadding(new Insets(0,0,20,0));
		CustomMenuButton validateSkin = new CustomMenuButton("CHOOSE THIS SKIN");
		validateSkinHB.getChildren().add(validateSkin);
		validateSkinHB.setAlignment(Pos.CENTER);
		skinPane.setBottom(validateSkinHB);
		*/


		////// SETTINGS ///////

		BorderPane settingMenu = new BorderPane();
		settingMenu.setId("settingMenu");

		////TOP////
		FlowPane settingFP = new FlowPane();
		Text settingTitle = new Text("SETTINGS");
		settingTitle.getStyleClass().add("title");
		settingTitle.setTextAlignment(TextAlignment.CENTER);
		settingFP.getChildren().add(settingTitle);
		settingFP.setAlignment(Pos.CENTER);
		settingFP.setPadding(new Insets(40,0,0,0));
		settingFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);


		settingMenu.setTop(settingFP);

		////CENTER////
		HBox settHB = new HBox();
		settHB.setAlignment(Pos.CENTER);
		settHB.setSpacing(70);
/*
		File soundFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/sound.png/");
		ImageView imgSound = new ImageView( new Image(soundFile.toURI().toURL().toString(),50,50,false,false));
		Button sound = new Button("",imgSound);
		sound.setPrefSize(Control.WIDTH/5,Control.WIDTH/5);
		sound.getStyleClass().add("setting-button");

		File musicFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/music.png/");
		ImageView imgMusic = new ImageView( new Image(musicFile.toURI().toURL().toString(),50,50,false,false));
		Button music = new Button("",imgMusic);
		music.setPrefSize(Control.WIDTH/5,Control.WIDTH/5);
		music.getStyleClass().add("setting-button");
*/

		VBox settVB = new VBox();
		settVB.setAlignment(Pos.CENTER_LEFT);
		settVB.setSpacing(30);

		CustomMenuButton white = new CustomMenuButton("WHITE THEME");
	//	white.setOnMouseClicked(e -> control.getModel().changeCSS(0));
		white.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
		//		control.getModel().changeCSS(0);
			}
		});
		CustomMenuButton dark = new CustomMenuButton("DARK THEME");
	//	dark.setOnMouseClicked(e -> control.getModel().changeCSS(1));
		dark.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
		//		control.getModel().changeCSS(1);
			}
		});
		CustomMenuButton neon = new CustomMenuButton("NEON THEME");
	//	neon.setOnMouseClicked(e -> control.getModel().changeCSS(2));
		neon.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
		//		control.getModel().changeCSS(2);
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
		botSetHB.setPadding(new Insets(0,10,10,10));

		CustomMenuButton backButtonS = new CustomMenuButton("←");
		botSetHB.getChildren().add(backButtonS);
		settingMenu.setBottom(botSetHB);


		/////////////////////////



		/////// GAME PANE ///////

		gamePane = new BorderPane();


		////TOP////
		HBox gameHB = new HBox();
		gameHB.setAlignment(Pos.CENTER);
		gameHB.setSpacing(40);
		gameHB.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY,Insets.EMPTY)));

		FlowPane pBomb = new FlowPane();
		pBomb.setAlignment(Pos.CENTER);
		pBomb.setPrefWidth(Control.WIDTH/6);
	//	Label nbBomb = new Label("" + control.getModel().getNbBombMissing());
	//	File bombFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/bomb.png/");
	//	ImageView imgBomb = new ImageView( new Image(bombFile.toURI().toURL().toString(),40,40,false,false));
	//	pBomb.getChildren().add(nbBomb);

		CustomMenuButton pauseButton = new CustomMenuButton("Pause");
		pauseButton.setPrefHeight(Control.HEIGHT/20 );
				
		Button flagButton = new Button("");
		flagButton.setOnMouseDragged(e ->{
	//		control.setFlagDragged(true);
			flagButton.setOpacity(1);
		});
		flagButton.setOnMouseClicked(e-> {
			flagButton.setOpacity(0.5);
	//		control.setFlagDragged(false);
			
		});
		
		//flagButton.setGraphic(new ImageView(new Image("/flag.png",30,30,false,false)));

		
		
		CustomMenuButton finishButton = new CustomMenuButton("End");
		finishButton.setPrefSize(Control.WIDTH/15,Control.HEIGHT/20 );
		finishButton.setDisable(true);

	//	File chronoFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/chrono.png/");
		//ImageView imgChrono = new ImageView( new Image(chronoFile.toURI().toURL().toString(),40,40,false,false));
		FlowPane pChrono = new FlowPane();
		pChrono.setAlignment(Pos.CENTER);
		pChrono.setPrefWidth(Control.WIDTH/6);
		time = new Label();
		time.textProperty().bind(timeSeconds.asString());
		pChrono.getChildren().add(time);

		pauseButton.setPrefWidth(Control.WIDTH/5);

		gameHB.getChildren().add(pBomb);
	//	gameHB.getChildren().add(imgBomb);
		gameHB.getChildren().add(pauseButton);
		gameHB.getChildren().add(flagButton);
		gameHB.getChildren().add(finishButton);
		//gameHB.getChildren().add(imgChrono);
		gameHB.getChildren().add(pChrono);

		gamePane.setTop(gameHB);;

		////CENTER////
	//	grid = control.initGrid(finishButton,nbBomb);
	//	grid.setAlignment(Pos.CENTER);
		gamePane.setCenter(grid);

		//////////////////////////


		/////// PAUSE MENU ////////

		BorderPane pauseMenu = new BorderPane();
		pauseMenu.setId("pauseMenu");

		////TOP////
		FlowPane pauseFP = new FlowPane();
		Text pauseTitle = new Text("PAUSE");
		pauseTitle.getStyleClass().add("title");
		pauseTitle.setTextAlignment(TextAlignment.CENTER);
		pauseFP.getChildren().add(pauseTitle);
		pauseFP.setAlignment(Pos.CENTER);
		pauseFP.setPadding(new Insets(20,0,0,0));
		pauseFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);


		pauseMenu.setTop(pauseFP);

		////CENTER////

		VBox mainPauseVB = new VBox();
		mainPauseVB.setAlignment(Pos.CENTER);
		mainPauseVB.setSpacing(40);

		CustomMenuButton resume = new CustomMenuButton("RESUME");			
		CustomMenuButton exitP = new CustomMenuButton("EXIT TO MAIN MENU");

		HBox pauseHB = new HBox();
		pauseHB.setAlignment(Pos.CENTER);
		pauseHB.setSpacing(70);

	//	File soundFileP = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/sound.png/");
	//	ImageView imgSoundP = new ImageView( new Image(soundFileP.toURI().toURL().toString(),50,50,false,false));
		//Button soundP = new Button("",imgSoundP);
	//	soundP.setPrefSize(Control.WIDTH/5,Control.WIDTH/5);
	//	soundP.setCursor(Cursor.HAND);

	//	File musicFileP = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/music.png/");
	//	ImageView imgMusicP = new ImageView( new Image(musicFileP.toURI().toURL().toString(),50,50,false,false));
	//	Button musicP = new Button("",imgMusicP);
	//	musicP.setPrefSize(Control.WIDTH/5,Control.WIDTH/5);
	//	musicP.setCursor(Cursor.HAND);


		VBox pauseVB = new VBox();
		pauseVB.setAlignment(Pos.CENTER_LEFT);
		pauseVB.setSpacing(30);

		CustomMenuButton whiteP = new CustomMenuButton("WHITE THEME");
	//	whiteP.setOnMouseClicked(e -> control.getModel().changeCSS(0));
		whiteP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
	//			control.getModel().changeCSS(0);
			}
		});
		CustomMenuButton darkP = new CustomMenuButton("DARK THEME");
	//	darkP.setOnMouseClicked(e -> control.getModel().changeCSS(1));
		darkP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
		//		control.getModel().changeCSS(1);
			}
		});
		CustomMenuButton neonP = new CustomMenuButton("NEON THEME");
	//	neonP.setOnMouseClicked(e -> control.getModel().changeCSS(2));
		neonP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
	//			control.getModel().changeCSS(2);
			}
		});
		pauseVB.getChildren().add(whiteP);
		pauseVB.getChildren().add(darkP);
		pauseVB.getChildren().add(neonP);

	//	pauseHB.getChildren().add(soundP);
	//	pauseHB.getChildren().add(musicP);
		pauseHB.getChildren().add(pauseVB);

		mainPauseVB.getChildren().add(resume);
		mainPauseVB.getChildren().add(pauseHB);
		mainPauseVB.getChildren().add(exitP);


		pauseMenu.setCenter(mainPauseVB);


		/////////////////////////

		////////WIN MENU/////////

		winMenu = new BorderPane();
		winMenu.setId("winMenu");

		////TOP////
		FlowPane winFP = new FlowPane();
		Text winTitle = new Text("YOU WIN !!!!!!");
		winTitle.getStyleClass().add("title");
		winTitle.setTextAlignment(TextAlignment.CENTER);
		winFP.getChildren().add(winTitle);
		winFP.setAlignment(Pos.CENTER);
		winFP.setPadding(new Insets(20,0,0,0));
		winFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);


		winMenu.setTop(winFP);

		////CENTER////


		HBox winHB = new HBox();
		winHB.setAlignment(Pos.CENTER);
		winHB.setSpacing(70);
		Text winScore = new Text("YOUR SCORE : ");
		winScore.getStyleClass().add("title");
		winScore.setTextAlignment(TextAlignment.CENTER);
		winHB.getChildren().add(winScore);

		winMenu.setCenter(winHB);
		////BOTTOM////

		HBox botWinHB = new HBox();
		botWinHB.setAlignment(Pos.BOTTOM_RIGHT);
		botWinHB.setSpacing(30);
		botWinHB.setPadding(new Insets(0,20,20,20));

		CustomMenuButton playAgain = new CustomMenuButton("PLAY AGAIN");
		CustomMenuButton exitGame = new CustomMenuButton("EXIT GAME");
		botWinHB.getChildren().add(playAgain);
		botWinHB.getChildren().add(exitGame);

		winMenu.setBottom(botWinHB);

		/////////////////////////

		////////LOOSE MENU/////////

		looseMenu = new BorderPane();
		looseMenu.setId("looseMenu");

		////TOP////
		FlowPane looseFP = new FlowPane();
		Text looseTitle = new Text("OH NO...");
		looseTitle.getStyleClass().add("title");
		looseTitle.setTextAlignment(TextAlignment.CENTER);
		looseFP.getChildren().add(looseTitle);
		looseFP.setAlignment(Pos.CENTER);
		looseFP.setPadding(new Insets(20,0,0,0));
		looseFP.setPrefSize(Control.WIDTH, Control.HEIGHT/6);


		looseMenu.setTop(looseFP);

		////CENTER////


		HBox looseHB = new HBox();
		looseHB.setAlignment(Pos.CENTER);
		looseHB.setSpacing(70);
		Text looseScore = new Text("YOU LOOSE...\n MAYBE THE NEXT TIME");
		looseScore.getStyleClass().add("title");
		looseScore.setTextAlignment(TextAlignment.CENTER);
		looseHB.getChildren().add(looseScore);

		looseMenu.setCenter(looseHB);
		////BOTTOM////

		HBox botlooseHB = new HBox();
		botlooseHB.setAlignment(Pos.BOTTOM_RIGHT);
		botlooseHB.setPadding(new Insets(0,20,20,20));
		botlooseHB.setSpacing(30);

		CustomMenuButton tryAgain = new CustomMenuButton("TRY AGAIN");
		CustomMenuButton exitGame2 = new CustomMenuButton("EXIT GAME");
		botlooseHB.getChildren().add(tryAgain);
		botlooseHB.getChildren().add(exitGame2);

		looseMenu.setBottom(botlooseHB);
		/////////////////////////

		mainPane.getChildren().add(difMenu);
		mainPane.getChildren().add(settingMenu);
		mainPane.getChildren().add(rulesMenu);
		mainPane.getChildren().add(gamePane);
		mainPane.getChildren().add(mainMenu);
		mainPane.getChildren().add(pauseMenu);
		mainPane.getChildren().add(winMenu);
		mainPane.getChildren().add(looseMenu);

		difMenu.setVisible(false);
		settingMenu.setVisible(false);
		gamePane.setVisible(false);
		rulesMenu.setVisible(false);
		pauseMenu.setVisible(false);
		winMenu.setVisible(false);
		looseMenu.setVisible(false);

		/////// ACTIONS ///////

		///MAIN///


		exit.setOnMouseClicked(e -> control.exitApp());
		exit.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});

	//	control.checkActions(pg,mainMenu, difMenu);
	//	control.checkActions(settings,mainMenu, settingMenu);
	//	control.checkActions(rules,mainMenu, rulesMenu);
	//	control.checkActions(backButtonR,(BorderPane)backButtonR.getParent().getParent(), mainMenu);
	//	control.checkActions(backButtonD,(BorderPane)backButtonD.getParent().getParent(), mainMenu);
	//	control.checkActions(backButtonS,(BorderPane)backButtonS.getParent().getParent(), mainMenu);


		///SETTINGS///
		/*
		 music.setOnMouseClicked(e -> control.startStopMusic());
		sound.setOnMouseClicked(e-> control.startStopSound());
		 */

		///DIFFICULTY///
	//	control.loadGrid(zen,difMenu,gamePane,0);
	//	control.loadGrid(beginner,difMenu,gamePane,1);
	//	control.loadGrid(advanced,difMenu,gamePane,2);
	//	control.loadGrid(expert,difMenu,gamePane,3);

		//GAME
		finishButton.setOnMouseClicked(e ->{
	//		if(control.checkWin()) {
	//			int score = (int) (control.getModel().getInitTime() - timeline.getCurrentTime().toSeconds());
	//			winScore.setText("YOUR SCORE : " + score );
	//		}
	//		control.displayEndGame(control.checkWin());
		});
		finishButton.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
	//			if(control.checkWin()) {
	//				int score = (int) (control.getModel().getInitTime() - timeline.getCurrentTime().toSeconds());
	//				winScore.setText("YOUR SCORE : " +  score );
	//			}
	//			control.displayEndGame(control.checkWin());
			}
				
		});
		
		pauseButton.setOnAction(e-> timeline.pause());
	//	control.checkActions(pauseButton, gamePane, pauseMenu);
		


		///PAUSE///
		resume.setOnAction(e -> timeline.play());
	//	control.checkActions(resume, pauseMenu, gamePane);
	//	control.checkActions(exitP, pauseMenu, mainMenu);
		exitP.setOnAction(e -> {
			getTimeline().stop();
			try {
				this.start(primaryStage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		exitP.setOnKeyPressed(e->{
			getTimeline().stop();
			if(e.getCode()==KeyCode.ENTER) {
				try {
					this.start(primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		///WIN///
	//	control.checkActions(playAgain, winMenu, difMenu);
		playAgain.setOnMouseClicked(e->{
			getTimeline().stop();
			try {
				this.start(primaryStage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		playAgain.setOnKeyPressed(e->{
			getTimeline().stop();
			if(e.getCode()==KeyCode.ENTER) {
				try {
					this.start(primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		exitGame.setOnMouseClicked(e -> control.exitApp());
		exitGame.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});

		///LOOSE///
	//	control.checkActions(tryAgain, looseMenu, difMenu);
		tryAgain.setOnMouseClicked(e->{
			getTimeline().stop();
			try {
				this.start(primaryStage);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		tryAgain.setOnKeyPressed(e->{
			getTimeline().stop();
			if(e.getCode()==KeyCode.ENTER) {
				try {
					this.start(primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		exitGame2.setOnMouseClicked(e -> control.exitApp());
		exitGame2.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});
		
		
		scene = new Scene(getMainPane(), Control.WIDTH, Control.HEIGHT);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("white.css").toExternalForm());
		primaryStage.setTitle("Crazy Platformer");
		primaryStage.setScene(scene);
		primaryStage.show();

	}


	public Label getTime() {
		return time;
	}

	public void setTime(Label time) {
		this.time = time;
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


	public BorderPane getWinMenu() {
		return winMenu;
	}


	public void setWinMenu(BorderPane winMenu) {
		this.winMenu = winMenu;
	}


	public BorderPane getLooseMenu() {
		return looseMenu;
	}


	public void setLooseMenu(BorderPane looseMenu) {
		this.looseMenu = looseMenu;
	}


	public BorderPane getGamePane() {
		return gamePane;
	}


	public void setGamePane(BorderPane gamePane) {
		this.gamePane = gamePane;
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


	public Timeline getTimeline() {
		return timeline;
	}


	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
	}


	public IntegerProperty getTimeSeconds() {
		return timeSeconds;
	}


	public void setTimeSeconds(IntegerProperty timeSeconds) {
		this.timeSeconds = timeSeconds;
	}
	

}

