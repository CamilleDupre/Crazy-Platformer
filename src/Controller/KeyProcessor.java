package Controller;

import Model.Model;

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
	public KeyProcessor(Control c) {
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
			
			//if (!this.control.getModel().isGameOver()) {
				if (this.control.isQPressed()) {
					
					this.control.getModel().move(Control.LEFT);
					//this.control.getModel().setDirection(Model.FACE_LEFT);
					
				} else if (this.control.isDPressed()) {
					
					this.control.getModel().move(Control.RIGHT);
					//this.control.getModel().setDirection(Model.FACE_RIGHT);
					
				}
			//}
			
			try {
				Thread.sleep(KeyProcessor.KEY_REPEAT_DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
