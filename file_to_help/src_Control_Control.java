package Control;
import Model.Model;
import Sound.Sound;
import javafx.geometry.Point3D;
import View.View;
/**
 * Control main class, used to issue commands to the model
 */
public class Control {

	/**
	 * Left value for turning ship
	 */
	public static final int LEFT = 1;
	/**
	 * Right value for turning ship
	 */
	public static final int RIGHT = 2;
	/**
	 * Value to be added to ship's position when turning
	 */
	public static final double TURN = 10;
	/**
	 * Value to be added to ship's position when jumping
	 */
	public static final double JUMP = 150;
	
	/**
	 * Model attribute used when issuing commands
	 */
	private Model model;
	/**
	 * Sound manager used to play sound effects when issuing commands
	 */
	private Sound sound;
	/**
	 * KeyListener implementation
	 */
	private KeyControl keyControl;

	
	/**
	 * Control constructor.
	 * Creates a new KeyControl using params
	 * @param m Model to modify when receiving commands
	 * @param s Sound to call when commands are issued
	 */
	public Control(Model m, Sound s) {
		this.model = m;
		this.sound = s;
		this.keyControl = new KeyControl(this, this.sound);
	}

	public Model getModel() {
		return this.model;
	}
	
	public KeyControl getKeyControl() {
		return this.keyControl;
	}

	/**
	 * Takes either Control.LEFT or Control.RIGHT and moves the ship accordingly
	 * @param dir Direction to move the ship towards. Either Control.LEFT or Control.RIGHT.
	 * @throws IllegalArgumentException When something else than Control.Left or Control.RIGHT has been given.
	 * 									Told you so !
	 */
	public void turn(int dir) throws IllegalArgumentException {
		switch (dir) {
			case Control.LEFT :
				if(this.model.getCoord().getX()>-View.WIDTH/2) {
					this.model.move(new Point3D(-Control.TURN, 0, 0));
				}
					break;
			case Control.RIGHT :
				if(this.model.getCoord().getX()<View.WIDTH/2-100) {
					this.model.move(new Point3D(Control.TURN, 0, 0));
				}
				break;
				
			default :
				throw new IllegalArgumentException();
		}
	}

	/**
	 * Makes the ship jump by adding Control.JUMP to the ship's vertical position.
	 */
	public void jump() {
		this.model.move(new Point3D(0, 0, Control.JUMP));
	}

}
