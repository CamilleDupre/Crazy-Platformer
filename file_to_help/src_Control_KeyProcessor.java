package Controller;
import Model.Model;

/**
 * Commands handler thread.
 * Bypasses the (inappropriate) OS's key repetition handler, thus insuring both a smooth gameplay when turning the ship, and the same experience whatever the OS used.
 */
public class KeyProcessor extends Thread {
	
	/**
	 * Time to sleep between two repetition of a key being pressed
	 */
	public static final int KEY_REPEAT_DELAY = 30;
	
	/**
	 * Control attribute used to transfer commands to the model
	 */
	private Control control;

	/**
	 * Constructor.
	 * Should only be used in a (new KeyProcessor(control)).start() manner inside of KeyControl's constructor.
	 * It is thus not public, but package protected.
	 * @param c
	 */
	KeyProcessor(Control c) {
		this.control = c;
	}

	/**
	 * Check which keys are currently pressed.
	 * If q is pressed (or d respectively), it calls Control.turn(Control.LEFT (or Control.RIGHT respectively)), and sets the ship's direction to left (or right respectively).
	 * Both keys cannot be pressed at the same time.
	 * If neither key is pressed, the ship is told to face front.
	 * A new iteration is called after KeyProcessor.KEY_REPEAT_DELAY milliseconds.
	 */
	@Override
	public void run() {
		while (true) {
			
			if (!this.control.getModel().isGameOver()) {
				
				if (this.control.getKeyControl().isQPressed()) {
					
					this.control.turn(Control.LEFT);
					this.control.getModel().setDirection(Model.FACE_LEFT);
					
				} else if (this.control.getKeyControl().isDPressed()) {
					
					this.control.turn(Control.RIGHT);
					this.control.getModel().setDirection(Model.FACE_RIGHT);
					
				} else {
					
					this.control.getModel().setDirection(Model.FACE_FRONT);
					
				}
			}
			
			try {
				Thread.sleep(KeyProcessor.KEY_REPEAT_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}