package View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import Controller.Control;
import Controller.KeyControl;
import Model.Block;
import Model.Model;
import Model.Power;
import Sound.Sound;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameView {

	private Control control;


	private Label time;
	private Timeline timeline;

	private BorderPane gamePane = new BorderPane();
	private BorderPane winMenu;
	private BorderPane looseMenu;
	private BorderPane pauseMenu;

	
	CustomMenuButton resume;
	private GraphicsContext context;

	public static final int CANVAS_WIDTH = MenuView.WIDTH;
	public static final int CANVAS_HEIGHT = MenuView.HEIGHT;


	private StackPane mainGameView;

	private IntegerProperty timeSeconds = new SimpleIntegerProperty(0);

	private Canvas canvas;



	private Image imgRight = null;
	private Image imgLeft = null;
	private Image imgCoin = null;
	private Image imgEnemy = null;
	private Image imgHeart=null;
	private Image imgHeartLost=null;
	private Image imgTreasure = null;
	private Image imgSpikes = null;

	private Image cloudPower = null;
	
	
	
	private int displayHorizontalMargin;
	private int displayVerticalMargin;


	public GameView(Control c) {
		control = c;


		mainGameView = new StackPane();
		mainGameView.setPrefSize(MenuView.WIDTH, MenuView.HEIGHT);

		setUpPausePanel();
		setUpVictoryPanel();
		setUpLosingPanel();
		setUpGamePanel();

		mainGameView.getChildren().add(gamePane);
		mainGameView.getChildren().add(pauseMenu);
		mainGameView.getChildren().add(looseMenu);
		mainGameView.getChildren().add(winMenu);

		gamePane.setVisible(true);
		winMenu.setVisible(false);
		looseMenu.setVisible(false);		
		pauseMenu.setVisible(false);

		gamePane.toFront();


		try {
			imgRight = new Image(new FileInputStream("img/other/player_right.png"));
			imgLeft = new Image(new FileInputStream("img/other/player_left.png"));
			imgCoin =new Image(new FileInputStream("img/other/coin.png"));
			imgEnemy = new Image(new FileInputStream("img/other/enemy.png"));
			imgHeart 	= new Image(new FileInputStream("img/other/Heart.png"));
			imgHeartLost= new Image(new FileInputStream("img/other/Heart_lost.png"));
			imgTreasure = new Image(new FileInputStream("img/other/chest.png"));
			imgSpikes = new Image(new FileInputStream("img/other/Spikes.png"));
			
			cloudPower = new Image(new FileInputStream("img/other/Cloudpng.png"));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void setUpGamePanel() {
		/////// GAME PANE ///////


		//	File chronoFile = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/chrono.png/");
		//ImageView imgChrono = new ImageView( new Image(chronoFile.toURI().toURL().toString(),40,40,false,false));
		FlowPane pChrono = new FlowPane();
		pChrono.setAlignment(Pos.CENTER);
		pChrono.setPrefWidth(MenuView.WIDTH/6);
		time = new Label();
		time.textProperty().bind(timeSeconds.asString());
		pChrono.getChildren().add(time);



		////CENTER////
		FlowPane center = new FlowPane();
		center.setId("settingMenu");

		canvas = new Canvas(CANVAS_WIDTH,CANVAS_HEIGHT);
		canvas.setFocusTraversable(true);
		context = canvas.getGraphicsContext2D();



		center.getChildren().add(canvas);

		gamePane.setCenter(center);

		control.checkKeyPressed(gamePane);

	}

	public void repaint() {
		context.setFill(Color.TRANSPARENT);
		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		context = canvas.getGraphicsContext2D();
		
		displayHorizontalMargin = 0;
		displayVerticalMargin = 0;
		
		if(control.getModel().getPlayer().getPosition().getX() + control.getModel().getPlayer().getPlayerSize().getX() >= canvas.getWidth()/2) {
			displayHorizontalMargin = (int) (control.getModel().getPlayer().getPosition().getX() + control.getModel().getPlayer().getPlayerSize().getX() - canvas.getWidth()/2);
		}
		
		if(control.getModel().getPlayer().getPosition().getY() + control.getModel().getPlayer().getPlayerSize().getY() <= canvas.getHeight()/3) {
			displayVerticalMargin = (int) (control.getModel().getPlayer().getPosition().getY() + control.getModel().getPlayer().getPlayerSize().getY() - canvas.getHeight()/3);
		}
		
		//blocks	
		paintBlocks(control.getModel().getCurrentLevel().getBlocks()); 
		//coins
		paintOtherComponent(control.getModel().getCurrentLevel().getCoins(),Model.COINS_SIZE,Model.COINS_SIZE, imgCoin);
		//enemies
		paintOtherComponent(control.getModel().getCurrentLevel().getEnemies(),Model.ENEMIES_WIDTH,Model.ENEMIES_HEIGHT, imgEnemy);
		//traps
		paintOtherComponent(control.getModel().getCurrentLevel().getTrap(),Model.TRAP_WIDTH,Model.TRAP_HEIGHT, imgSpikes);
		
		//Power
		paintPower(control.getModel().getCurrentLevel().getPowers(),Model.COINS_SIZE,Model.COINS_SIZE, cloudPower);

		
		//chest
		context.drawImage(imgTreasure, control.getModel().getCurrentLevel().getTreasure().getX() - displayHorizontalMargin, control.getModel().getCurrentLevel().getTreasure().getY() - displayVerticalMargin, Model.TREASURE_WIDTH, Model.TREASURE_HEIGHT);
		
		//number of life
		for (int i = 0 ; i < control.getModel().getPlayer().getLife() ; i++) {
			context.drawImage(imgHeart,10 + i* Model.HEART_SIZE, 50, Model.HEART_SIZE, Model.HEART_SIZE);
		}
		for (int i = control.getModel().getPlayer().getLife() ; i < 3 ; i++) {
			context.drawImage(imgHeartLost,10 + i* Model.HEART_SIZE, 50, Model.HEART_SIZE, Model.HEART_SIZE);
		}
		
		//number of coins
		context.drawImage(imgCoin,1800, 50, Model.COINS_SIZE, Model.COINS_SIZE);
		context.setStroke(Color.GOLD);
		context.strokeText( control.getModel().getNbCoinsCollected()+" Coins", 1800 + 1.5 *Model.COINS_SIZE, 75 );
		
		if (control.getModel().getDirection() == Model.FACE_RIGHT) {
			context.drawImage(imgLeft,control.getModel().getPlayer().getPosition().getX() - displayHorizontalMargin, control.getModel().getPlayer().getPosition().getY() - displayVerticalMargin,control.getModel().getPlayer().getPlayerSize().getX(), control.getModel().getPlayer().getPlayerSize().getY());
		}
		else {
			context.drawImage(imgRight,control.getModel().getPlayer().getPosition().getX() - displayHorizontalMargin, control.getModel().getPlayer().getPosition().getY()  - displayVerticalMargin,control.getModel().getPlayer().getPlayerSize().getX(), control.getModel().getPlayer().getPlayerSize().getY());
		}
	}


	public void paintBlocks(ArrayList<Block> blockList) {
		
		int marginHeight = 30;
		
		for(Block b : blockList) {
			if(!b.isInvisible()) {
				context.setFill(Color.GREEN);
				context.fillRect(b.getPosition().getX() - displayHorizontalMargin, b.getPosition().getY()- displayVerticalMargin, b.getWidth(), marginHeight);
				context.setFill(Color.SADDLEBROWN);
				context.fillRect(b.getPosition().getX() - displayHorizontalMargin, b.getPosition().getY()+marginHeight- displayVerticalMargin, b.getWidth(), b.getHeight() - marginHeight);
			}else {
				context.setFill(Color.BLANCHEDALMOND);
				context.fillRect(b.getPosition().getX() - displayHorizontalMargin, b.getPosition().getY()- displayVerticalMargin, b.getWidth(), b.getHeight());
			}
		}
	}

	public void paintOtherComponent(ArrayList<Point2D> objPositions, int componentWidth, int componentHeight, Image img) {
		for(Point2D obj : objPositions) {
			context.drawImage(img, obj.getX() - displayHorizontalMargin, obj.getY()- displayVerticalMargin, componentWidth, componentHeight);
		}
	}
	
	public void paintPower(ArrayList<Power> powers, int componentWidth, int componentHeight, Image img) {
		for(Power power : powers) {
			context.drawImage(img, power.getPosition().getX() - displayHorizontalMargin, power.getPosition().getY()- displayVerticalMargin, componentWidth, componentHeight);
		}
	}
	/*public void paintCoin(ArrayList<Point2D> objPositions, int componentWidth, int componentHeight) {
		for(Point2D obj : objPositions) {
			context.drawImage(imgCoin,obj.getX() - displayMargin, obj.getY(), componentWidth, componentHeight);
		}
	}*/


	private void setUpPausePanel() {
		/////// PAUSE MENU ////////

		pauseMenu = new BorderPane();
		pauseMenu.setId("pauseMenu");

		////TOP////
		FlowPane pauseFP = new FlowPane();
		Text pauseTitle = new Text("PAUSE");
		pauseTitle.getStyleClass().add("title");
		pauseTitle.setTextAlignment(TextAlignment.CENTER);
		pauseFP.getChildren().add(pauseTitle);
		pauseFP.setAlignment(Pos.CENTER);
		pauseFP.setPadding(new Insets(20,0,0,0));
		pauseFP.setPrefSize(MenuView.WIDTH, MenuView.HEIGHT/6);


		pauseMenu.setTop(pauseFP);

		////CENTER////

		VBox mainPauseVB = new VBox();
		mainPauseVB.setAlignment(Pos.CENTER);
		mainPauseVB.setSpacing(40);

		resume = new CustomMenuButton("RESUME");			
		CustomMenuButton exitP = new CustomMenuButton("EXIT");

		HBox pauseHB = new HBox();
		pauseHB.setAlignment(Pos.CENTER);
		pauseHB.setSpacing(70);

		//	File soundFileP = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/sound.png/");
		//	ImageView imgSoundP = new ImageView( new Image(soundFileP.toURI().toURL().toString(),50,50,false,false));
		//Button soundP = new Button("",imgSoundP);
		//	soundP.setPrefSize(WIDTH/5,WIDTH/5);
		//	soundP.setCursor(Cursor.HAND);

		//	File musicFileP = new File("C:/Users/daman/eclipse-workspace/Don'tTouchTheMines/assets/img/music.png/");
		//	ImageView imgMusicP = new ImageView( new Image(musicFileP.toURI().toURL().toString(),50,50,false,false));
		//	Button musicP = new Button("",imgMusicP);
		//	musicP.setPrefSize(WIDTH/5,WIDTH/5);
		//	musicP.setCursor(Cursor.HAND);


		VBox pauseVB = new VBox();
		pauseVB.setAlignment(Pos.CENTER_LEFT);
		pauseVB.setSpacing(30);

		CustomMenuButton whiteP = new CustomMenuButton("WHITE THEME");
		whiteP.setOnMouseClicked(e -> control.getModel().changeCSS(0));
		whiteP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(0);
			}
		});
		CustomMenuButton darkP = new CustomMenuButton("DARK THEME");
		darkP.setOnMouseClicked(e -> control.getModel().changeCSS(1));
		darkP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(1);
			}
		});
		CustomMenuButton neonP = new CustomMenuButton("NEON THEME");
		neonP.setOnMouseClicked(e ->{
			control.getModel().changeCSS(2);
			System.out.println("neon");
		}
				);
		neonP.setOnKeyPressed(e->{
			if(e.getCode()==KeyCode.ENTER) {
				control.getModel().changeCSS(2);
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


		///PAUSE///
		//resume.setOnAction(e -> timeline.play());
		control.checkActions(resume, pauseMenu, gamePane);
		//control.checkActions(exitP, pauseMenu, gamePane);
		
		exitP.setOnMouseClicked(e -> control.exitApp());
		exitP.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});
		
	}



	private void setUpVictoryPanel() {
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
		winFP.setPrefSize(MenuView.WIDTH, MenuView.HEIGHT/6);


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

		///WIN///
		//	
		playAgain.setOnMouseClicked(e->{
			//getTimeline().stop();
			/*	try {
					MenuView.start(primaryStage);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
			control.checkActions(playAgain, gamePane, control.getMenuView().getLevelPane()); //TO DO
		});
		playAgain.setOnKeyPressed(e->{
			//getTimeline().stop();
			if(e.getCode()==KeyCode.ENTER) {
				/*	try {
						this.start(primaryStage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
			}
		});
		exitGame.setOnMouseClicked(e -> control.exitApp());
		exitGame.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});
	}





	private void setUpLosingPanel() {
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
		looseFP.setPrefSize(MenuView.WIDTH, MenuView.HEIGHT/6);


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

		///LOOSE///
		//	control.checkActions(tryAgain, looseMenu, difMenu);
		tryAgain.setOnMouseClicked(e->{
			getTimeline().stop();
			/*try {
					this.start(primaryStage);
				} catch (IOException e1) {
					 TODO Auto-generated catch block
					e1.printStackTrace();
				}*/
		});
		tryAgain.setOnKeyPressed(e->{
			getTimeline().stop();
			if(e.getCode()==KeyCode.ENTER) {
				/*try {
						this.start(primaryStage);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/
			}
		});
		exitGame2.setOnMouseClicked(e -> control.exitApp());
		exitGame2.setOnKeyPressed(e -> {
			if(e.getCode()==KeyCode.ENTER) {
				control.exitApp();
			}
		});
	}


	public BorderPane getGamePane() {
		return gamePane;
	}


	public void setGamePane(BorderPane gamePane) {
		this.gamePane = gamePane;
	}

	public Label getTime() {
		return time;
	}

	public void setTime(Label time) {
		this.time = time;
	}

	public Timeline getTimeline() {
		return timeline;
	}


	public void setTimeline(Timeline timeline) {
		this.timeline = timeline;
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

	public IntegerProperty getTimeSeconds() {
		return timeSeconds;
	}

	public BorderPane getPauseMenu() {
		return pauseMenu;
	}

	public void setPauseMenu(BorderPane pauseMenu) {
		this.pauseMenu = pauseMenu;
	}


	public void setTimeSeconds(IntegerProperty timeSeconds) {
		this.timeSeconds = timeSeconds;
	}


	public void setLooseMenu(BorderPane looseMenu) {
		this.looseMenu = looseMenu;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

	public StackPane getMainGameView() {
		return mainGameView;
	}

	public void setMainGameView(StackPane mainGameView) {
		this.mainGameView = mainGameView;
	}

	public Image getImgSpikes() {
		return imgSpikes;
	}

	public void setImgSpikes(Image imgSpikes) {
		this.imgSpikes = imgSpikes;
	}
}
