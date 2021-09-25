package View;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Control.Control;
import Model.Model;
import Model.Move;
import Model.Moving;
import Model.NonMoving;
import Model.Obstacle;
import Model.Track;
import Sound.Sound;
import javafx.geometry.Point3D;

/**
 * Class that leads graphical contents, View is the main frame
 */
public class View extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Width of the frame
	 */
	public static final int WIDTH = 1280;
	
	/**
	 * Height of the frame
	 */
	public static final int HEIGHT = 800;
	
	/**
	 * Padding between spaceship and bottom of the frame
	 */
	public static final int BOTTOM_PADDING = 150;
	
	/**
	 * Initial size of the planet(checkpoint)
	 */
	public static final int PLANET_SIZE = 20;
	
	/**
	 * Number of different planets
	 */
	private static final int NB_PLANETS = 5;
	
	/**
	 * Initial size of obstacle
	 */
	private static final int INITIAL_OBST_SIZE = 30;
	
	/**
	 *  Random attribute for generating random numbers (used for track's generation)
	 */
	private Random rand;
	
	/**
	 * First image of planet(Checkpoint) 
	 */
	public ImageIcon planet;
	
	/**
	 * Panel that contains the background
	 */
	private JPanel panel;
	
	/**
	 * Sound attribute for managing sounds
	 */
	private Sound sound;
	
	/**
	 * Control attribute to manage key controls
	 */
	@SuppressWarnings("unused")
	private Control control;
	
	/**
	 * Model attribute used to manage user(spaceship) datas
	 */
	private Model model;
	
	/**
	 * Font used in the frame
	 */
	public Font eightBitFont;

	/**
	 * View Constructor used to initialized and managed all the graphical contents and choosed what should be display
	 * @param m
	 */
	public View(Model m, Sound s) {
		this.model = m;
		this.sound = s;
		
		try {
			this.planet = new ImageIcon(ImageIO.read(View.class.getClassLoader().getResourceAsStream("imgs/planets/red_dark_planet.jpg")));
		} catch (Exception e) {
			System.out.println("Planet could not be loaded !");
		}

		new Move(this.model, this).start();

		this.setPreferredSize(new Dimension(View.WIDTH, View.HEIGHT));
		this.setTitle("spaceXtrem");
		this.panel = new Background(this);
		this.getContentPane().add(this.panel);

		this.setVisible(true);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.rand = new Random();
		this.generateRandomPlanet();

		try {
			this.eightBitFont = Font.createFont(Font.TRUETYPE_FONT, View.class.getClassLoader().getResourceAsStream("fonts/SuperLegendBoy-4w8Y.ttf"));
		} catch (Exception e) {
			System.out.println("Font could not be loaded !");
		}
	}

	public Model getModel() {
		return this.model;
	}

	/**
	 * calcul de la perspective soit la diminution de la largeur de la track en fonction de sa proximité a l'horizon
	 * obtenu en faisant:  (largeurMax - largeurMin) * ( (ordonnée-tailleFenetre/3) / taille de la track) + largeurMin
	 * autrement dit : largeur variable  * un coeff + largeurMin   se qui donne largeurMax quand un point est en bas de la fenetre et largeurMin a l'horizon
	 */
	public static int perspective(int ord) {
		return (int) ((Track.WIDTH - Track.WIDTH_MIN) * ((double) (ord-(View.HEIGHT/3.0)) / Track.LENGTH ) + Track.WIDTH_MIN);
	}
	
	/**
	 * Same as persepective but for obstacles
	 * @param o
	 */
	public void obstaclePerspective(Obstacle o) {
		int maxSize=0;
		if(o instanceof Moving) {
			maxSize = Moving.SIZE; 
		}else {
			NonMoving nm = (NonMoving)o;
			maxSize = (int) nm.getSizeObs();
		}
		o.setSize(new Point3D(
				(int) ((maxSize - View.INITIAL_OBST_SIZE)* (getModel().translateToView(new Point3D(0,o.getCoord().getY()-getModel().getCoord().getY(),0)).getY()
						- (View.HEIGHT/3.0))/(2*View.HEIGHT/3.0))+View.INITIAL_OBST_SIZE,
				(int) ((maxSize - View.INITIAL_OBST_SIZE)* (getModel().translateToView(new Point3D(0,o.getCoord().getY()-getModel().getCoord().getY(),0)).getY() 
						- (View.HEIGHT/3.0))/(2*View.HEIGHT/3.0))+View.INITIAL_OBST_SIZE,
			20));
	}

	public JPanel getPanel() {
		return this.panel;
	}

	/**
	 * repaint the frame
	 */
	@Override
	public void paint(Graphics g) {
		this.panel.repaint();
	}
	
	/**
	 * Method used to choose a random planet image(checkpoint)
	 */
	public void generateRandomPlanet() {
		
		int r = this.rand.nextInt(View.NB_PLANETS);
		String planet;
		
		switch(r) {
		
		case 0 :
			planet = "imgs/planets/red_dark_planet.png";
			break;
		case 1 :
			planet = "imgs/planets/red_planet.png"; 
			break;
		case 2 :
			planet = "imgs/planets/blue_planet.png"; 
			break;
		case 3 :
			planet = "imgs/planets/green_planet.png";
			break;
		case 4 :
			planet = "imgs/planets/purple_planet.png";
			break;
		default :
			planet = "imgs/planets/red_dark_planet.png";
		
		}
		
		this.planet = new ImageIcon(planet);
		try {
			this.planet = new ImageIcon(ImageIO.read(View.class.getClassLoader().getResourceAsStream(planet)));
		} catch (Exception e) {
			System.out.println("Planet could not be loaded !");
		}
	}
	
	/**
	 * Method used to set controls
	 * @param c
	 */
	public void setControl(Control c) {
		this.control = c;
		this.panel.setFocusable(true);
		this.panel.requestFocus();
		this.panel.addKeyListener(c.getKeyControl());
	}
	
	/**
	 * Method used to check if checkpoint has been passed, add time and increase level
	 */
	public void checkLevelPassed() {
		if (this.model.getCoord().getY() > this.model.getLevel()*Model.LEVEL_LENGTH) {
			this.sound.playExplosionSound();
			this.model.incrLevel();
			this.model.addTime((double) Model.CHECKPOINT_TIME_BONUS);
			this.generateRandomPlanet();
		}
	}

}
