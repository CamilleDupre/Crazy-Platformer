package Model;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Sound.Sound;
import View.View;
import javafx.geometry.Point3D;

/**
 * Class that takes care of all the game's data, such as score, obstacles, current ship's position and speed, etc...
 */
public class Model {

	/**
	 * Gravity value of the track
	 */
	private static final double GRAVITY = 0.8;
	/**
	 * Acceleration induced on the track
	 */
	private static final double ACCEL = 1;
	/**
	 * Deceleration outside of the track
	 */
	private static final double DECEL = ACCEL/5;
	/**
	 * Friction value (implemented in a fluid-friction model)
	 */
	private static final double FROTTEMENT = 0.0005;
	/**
	 * Amount of time threads depending on Model will sleep between two iterations
	 */
	public static final int TIMEOUT = 50;
	/**
	 * Amount of time the timer will start with
	 */
	public static final int START_TIME = 30;
	/**
	 * Amount of time added when passing a checkpoint
	 */
	public static final int CHECKPOINT_TIME_BONUS = 10;
	/**
	 * Length of a level in pixels
	 */
	public static final int LEVEL_LENGTH = 10000;
	/**
	 * Speed malus when hitting an obstacle
	 */
	public static final int HIT_SLOWDOWN = 10;
	/**
	 * Score bonus when overtaking an enemy ship
	 */
	public static final int OVERTAKE_BONUS = 500;
	/**
	 * Minimum space between two obstacles spawn points
	 */
	public static final int OBSTACLE_MINIMUM_SPACING = 1000;
	/**
	 * Ship facing front
	 */
	public static final int FACE_FRONT = 0;
	/**
	 * Ship facing left
	 */
	public static final int FACE_LEFT = 1;
	/**
	 * Ship facing right
	 */
	public static final int FACE_RIGHT = 2;

	/**
	 * Sound attribute for managing sounds
	 */
	private Sound sound;
	/**
	 * Track attribute for storing track points
	 */
	private Track track;
	/**
	 * Coordinates of the player's ship
	 */
	private Point3D coord;
	/**
	 * Speed of the player's ship
	 */
	private Point3D speed;
	/**
	 * Acceleration of the player's ship
	 */
	private Point3D acc;
	/**
	 * 3D player's ship size
	 */
	private Point3D shipSize;
	/**
	 * Random attribute for generating random numbers (used for track's generation)
	 */
	private Random rand;
	/**
	 * ArrayList of obstacles to be spawned
	 */
	private ArrayList<Obstacle> obstacleList;
	/**
	 * Amount of time left in the Timer
	 */
	private float time;
	/**
	 * Current level
	 */
	private int level;
	/**
	 * Current score
	 */
	private int score;
	/**
	 * Timer thread. Used for computing time left
	 */
	private Timer timer;
	/**
	 * Boolean attribute used as a mutex to tell if the game is paused or not
	 */
	private boolean gamePaused = true;
	/**
	 * Boolean attribute used as a mutex to tell if the game is lost or not
	 */
	private boolean gameOver = false;
	/**
	 * Image of the player's ship
	 */
	private ImageIcon imgSpaceship;
	/**
	 * Direction of the player's ship. Used to know if it is facing Left, Right, or Front
	 */
	private int direction;

	/**
	 * Model Constructor used for managing data such as position, speed, acceleration, score, etc...
	 * @param s Sound Class used for managing sound effects
	 */
	public Model(Sound s) {
		this.sound = s;
		
		try {
			this.imgSpaceship = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("imgs/spaceship/spaceship.png")));
		} catch (Exception e) {
			System.out.println("Player's ship could not be loaded !");
		}
		
		this.shipSize = new Point3D(100, 100, 20);
		this.coord = new Point3D(-this.imgSpaceship.getIconWidth()/2, 0, 0);
		this.speed = new Point3D(0, 10, 0);
		this.acc = new Point3D(0, 0, 0);
		this.time = Model.START_TIME;
		this.score = 0;
		this.level = 1;
		this.timer = new Timer(this, this.sound);
		this.track = new Track(this);
		this.direction = 0;
		
		this.rand = new Random();
		this.obstacleList = new ArrayList<Obstacle>();
		
		Point3D pos = new Point3D(this.rand.nextInt(View.WIDTH) - View.WIDTH/2,
								this.rand.nextInt(2*View.HEIGHT/3),
								0);
		
		this.obstacleList.add(Obstacle.generateRandomObstacle(this, pos));
		
		for (int i = 1; i < 10; i++) {
			
			pos = new Point3D(	this.rand.nextInt(View.WIDTH) - View.WIDTH/2,
								this.obstacleList.get(i-1).getCoord().getY() + Model.OBSTACLE_MINIMUM_SPACING + this.rand.nextInt(2*View.HEIGHT/3),
								0);
			this.obstacleList.add(Obstacle.generateRandomObstacle(this, pos));
		}
		
		this.timer.start();
	}

	public Track getTrack() {
		return this.track;
	}

	public Point3D getCoord() {
		return this.coord;
	}

	public Point3D getSpeed() {
		return this.speed;
	}

	public Point3D getAcc() {
		return this.acc;
	}
	
	/**
	 * Tells if the player's ship is currently jumping
	 * @return True if ship is in air, False if not
	 */
	public boolean isInAir() {
		return this.coord.getZ() > 0;
	}
	
	/**
	 * Method used to access the obstacles list outside of Model
	 * Gives only the obstacles visible on the View plus a margin so no useless obstacles are computed
	 * @return Model's pertinent obstacles
	 */
	public ArrayList<Obstacle> getObstacleList() {
		ArrayList<Obstacle> res = new ArrayList<Obstacle>();
		
		for (Obstacle o : this.obstacleList) {
			
			if (o.getCoord().getY() > this.coord.getY() - 2*View.HEIGHT/3 &&
				o.getCoord().getY() < this.coord.getY() + 2*View.HEIGHT/3) {
				
				res.add(o);
			}
		}
		
		return res;
	}

	public Point3D getShipSize() {
		return this.shipSize;
	}
	
	/**
	 * Adds a 3D vector to the current ship's position
	 * @param vect 3D vector to be added to the ship's position
	 */
	public void move(Point3D vect) {
		this.coord = new Point3D(this.coord.getX() + vect.getX(), this.coord.getY() + vect.getY(), this.coord.getZ() + vect.getZ());
	}

	/**
	 * Deprecated. Used to add a 3D vector to ship's speed
	 * @param acc 3D vector to be added to the ship's speed
	 */
	public void impulse(Point3D acc) {
		this.speed = new Point3D(this.speed.getX() + acc.getX(), this.speed.getY() + acc.getY(), this.speed.getZ() + acc.getZ());
	}

	public float getTime() {
		return this.time;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public boolean isGamePaused() {
		return this.gamePaused;
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	/**
	 * Method used to add a value to the player's score
	 * @param n score to be added. Can be either positiove or negative.
	 */
	public void addScore(int n) {
		this.score += n;
	}
	
	/**
	 * Pauses the game
	 */
	public void pauseGame() {
		this.gamePaused = true;
	}
	
	/**
	 * Resumes the game
	 */
	public void resumeGame() {
		this.gamePaused = false;
	}
	
	/**
	 * Ends the game
	 */
	public void endGame() {
		this.gameOver = true;
	}

	/**
	 * Method used to add an amount of time to the timer
	 * @param t Time to be added. Can be either positive or negative.
	 */
	public void addTime(double t) {
		this.time += t;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * Method used to increment current level by one, when passing a checkpoint for example
	 */
	public void incrLevel() {
		this.level++;
	}
	
	/**
	 * Method used to get the current ship's image
	 * Depends of the current ship's direction
	 * @return Ship's image, facing either left, right, or front.
	 */
	public Image getImgSpaceship() {
		
		if (this.direction == Model.FACE_LEFT) {
			try {
				this.imgSpaceship = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("imgs/spaceship/spaceship_left.png")));
			} catch (Exception e) {
				System.out.println("Ship could not be loaded !");
			}
		} else if (this.direction == Model.FACE_RIGHT) {
			try {
				this.imgSpaceship = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("imgs/spaceship/spaceship_right.png")));
			} catch (Exception e) {
				System.out.println("Ship could not be loaded !");
			}
		} else {
			try {
				this.imgSpaceship = new ImageIcon(ImageIO.read(Model.class.getClassLoader().getResourceAsStream("imgs/spaceship/spaceship.png")));
			} catch (Exception e) {
				System.out.println("Ship could not be loaded !");
			}
		}
		
		return imgSpaceship.getImage();
	}
	
	/**
	 * Changes the direction of the ship
	 * @param d Direction of the ship.
	 * 			Acceptable values are Model.FACE_LEFT, Model.FACE_RIGHT, Model.FACE_FRONT
	 */
	public void setDirection(int d) {
		this.direction = d;
	}
	
	/**
	 * Computes acceleration of the ship.
	 * Depends of whether the ship is above track, ship's current speed, or if it's in the air
	 * Track's acceleration is Model.ACCEL above track, and -Model.DECEL else
	 * Friction is analog to a fluid friction model
	 * Gravity is analog to earth's gravity with a Model.GRAVITY value instead of 9.81
	 * Anti-Reverse prevents the ship from going backwards
	 * @return 3D vector containing ship's acceleration on the 3 axis
	 */
	public Point3D calcAcc() {
		Point3D res = new Point3D(0, 0, 0);

		// Friction
		res = new Point3D(	res.getX(), 
							res.getY() - Model.FROTTEMENT*this.speed.getY()*this.speed.getY(), 
							res.getZ());
		
		// Track's Acceleration
		if (this.coord.getX() > this.getXTrack(this.coord.getY() + View.BOTTOM_PADDING) - Track.WIDTH/2 &&
			this.coord.getX() < this.getXTrack(this.coord.getY() + View.BOTTOM_PADDING) + Track.WIDTH/2) {
			res = new Point3D(	res.getX(), 
								res.getY() + Model.ACCEL, 
								res.getZ());
		} else {
			res = new Point3D(	res.getX(), 
								res.getY() - Model.DECEL, 
								res.getZ());
		}
		
		
		// Gravity
		if (this.coord.getZ() > 0) {
			res = new Point3D(	res.getX(), 
								res.getY(), 
								res.getZ() - Model.GRAVITY);
		} else {
			this.speed = new Point3D(this.speed.getX(), this.speed.getY(), 0);
			this.coord = new Point3D(this.coord.getX(), this.coord.getY(), 0);
		}
		
		// Anti-Reverse
		if (this.speed.getY() < 0) {
			res = new Point3D(res.getX(), 0, res.getZ());
			this.speed = new Point3D(this.speed.getX(), 0, this.speed.getZ());
		}

		return res;
	}

	/**
	 * Adds acceleration value to the current speed, and speed value to the current coordinates.
	 * Called in Move thread in each iteration (if the game is neither paused nor lost).
	 */
	public void computeSpeed() {
		this.acc = this.calcAcc();
		this.speed = new Point3D(this.speed.getX()+this.acc.getX(), this.speed.getY()+this.acc.getY(), this.speed.getZ()+this.acc.getZ());
		this.coord = new Point3D(this.coord.getX()+this.speed.getX(), this.coord.getY()+this.speed.getY(), this.coord.getZ()+this.speed.getZ());
	}
	
	/**
	 * Takes a y-axis coordinate, and computes its x-axis projection on the track's center
	 * @param y y-axis value to be computed
	 * @return X-axis projection of y on the track's center
	 */
	public double getXTrack(double y) {
		
		double res = 0;
		ArrayList<Point3D> pl = this.track.getPointList();
		
		for (int i = 0; i < pl.size() - 1; i++) {
			if (y >= pl.get(i).getY() && y < pl.get(i+1).getY()) {
				Point3D lastP = pl.get(i);
				Point3D nextP = pl.get(i+1);
				
				double coeff = (nextP.getX() - lastP.getX()) / (nextP.getY() - lastP.getY());
				
				res = coeff*(y - lastP.getY()) + lastP.getX();
				return res;
			}
		}
		
		return res;
	}
	
	/**
	 * Takes a 3DPoint in the model's inertial frame, and translate it to usable coordinates for the View
	 * @param p 3DPoint to be translated
	 * @return p translated into Swing's inertial frame
	 */
	public Point3D translateToView(Point3D p) {
		return new Point3D(p.getX() + View.WIDTH/2, View.HEIGHT - p.getY(), 0);
	}
	
	/**
	 * Checks if the player is hitting an obstacle.
	 * Plays a hit sound if so, and decrease the ship's speed value by Model.HIT_SLOWDOWN
	 * @param o Obstacle with which to check if there is a hit
	 */
	public void checkHit(Obstacle o) {
		if ((this.coord.getX() + this.shipSize.getX()/2 > o.getCoord().getX() - o.getSize().getX()/2 && this.coord.getX() - this.shipSize.getX()/2 < o.getCoord().getX() + o.getSize().getX()/2) &&
			(this.coord.getY() + this.shipSize.getY()/2 + View.BOTTOM_PADDING > o.getCoord().getY() - o.getSize().getY()/2 && this.coord.getY() - this.shipSize.getY()/2 + View.BOTTOM_PADDING < o.getCoord().getY() + o.getSize().getY()/2) &&
			(this.coord.getZ() + this.shipSize.getZ()/2 > o.getCoord().getZ() - o.getSize().getZ()/2 && this.coord.getZ() - this.shipSize.getZ()/2 < o.getCoord().getZ() + o.getSize().getZ()/2)) {
			this.sound.playHitSound();
			this.speed = new Point3D(this.speed.getX(), this.speed.getY() - Model.HIT_SLOWDOWN, this.speed.getZ());
		}
	}
	
	/**
	 * Check if the player is overtaking an obstacle.
	 * Used in order to remove obstacles that are not useful anymore from obstacleList.
	 * An obstacle can be overtaken only once, obviously.
	 * If an obstacle is overtaken, a new one is generated and added to obstacleList.
	 * If the obstacle is an enemy ship, adds Model.OVERTAKE_BONUS to player's score.
	 * @param o Obstacle with which to check if it is overtaken.
	 */
	public void checkOvertake(Obstacle o) {
		if (this.coord.getY() > o.getCoord().getY() && !o.isOvertaken()) {
			o.overtake();
			Point3D pos = new Point3D(this.rand.nextInt(View.WIDTH) - View.WIDTH/2,
									  this.obstacleList.get(this.obstacleList.size()-1).getCoord().getY() + Model.OBSTACLE_MINIMUM_SPACING + this.rand.nextInt(2*View.HEIGHT/3),
									  0);
			this.obstacleList.add(Obstacle.generateRandomObstacle(this, pos));
			this.obstacleList.remove(0);
			
			if (Moving.class.isInstance(o)) {
				this.addScore(Model.OVERTAKE_BONUS);
			}
		}
		
	}
	
	/**
	 * Resets game by setting all values to default.
	 * Regenerate obstacles and track.
	 */
	public void resetGame() {
		
		this.shipSize = new Point3D(100, 100, 20);
		this.coord = new Point3D(-this.imgSpaceship.getIconWidth()/2, 0, 0);
		this.speed = new Point3D(0, 10, 0);
		this.acc = new Point3D(0, 0, 0);
		this.time = Model.START_TIME;
		this.score = 0;
		this.level = 1;
		this.timer = new Timer(this, this.sound);
		this.track = new Track(this);
		this.direction = 0;
		this.gamePaused = true;
		this.gameOver = false;
		
		this.obstacleList = new ArrayList<Obstacle>();
		
		Point3D pos = new Point3D(this.rand.nextInt(View.WIDTH) - View.WIDTH/2,
								this.rand.nextInt(2*View.HEIGHT/3),
								0);
		
		this.obstacleList.add(Obstacle.generateRandomObstacle(this, pos));
		
		for (int i = 1; i < 10; i++) {
			
			pos = new Point3D(	this.rand.nextInt(View.WIDTH) - View.WIDTH/2,
								this.obstacleList.get(i-1).getCoord().getY() + Model.OBSTACLE_MINIMUM_SPACING + this.rand.nextInt(2*View.HEIGHT/3),
								0);
			this.obstacleList.add(Obstacle.generateRandomObstacle(this, pos));
		}
		
		this.sound.resetMainTheme();
		
		this.timer.start();
		
	}

}
