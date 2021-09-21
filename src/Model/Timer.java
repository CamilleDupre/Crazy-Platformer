package Model;

import Sound.Sound;

public class Timer extends Thread {

	/**
	 * Amount of time in milliseconds to wait between two iterations
	 */
	public static int TIMEOUT = 10;

	/**
	 * Model attribute used to change gameOver value, and to access data from
	 */
	private Model model;
	/**
	 * Sound attribute used to manage GameOver sound effects
	 */
	private Sound sound;

	/**
	 * Timer only constructor
	 * @param m Model used to access and modify data from (time, gameOver, and gamePaused mainly)
	 * @param s Sound attribute used to manage sound effects when game is lost
	 */
	public Timer(Model m, Sound s) {
		this.model = m;
		this.sound = s;
	}

	/**
	 * Overriden run() method.
	 * While game is not over, checks if model.time has reached zero, and if not, decrement amount of time left.
	 * Also ends game if time has reached zero, and if so, pause the main theme music and plays the game over sound effect
	 */
	@Override
	public void run() {
		while (!this.model.isGameOver()) {
			
			if (!this.model.isGamePaused()) {
				
				if (this.model.getTime() <= 0) {
					//this.model.endGame();
					this.sound.pauseMainTheme();
					this.sound.playGameOverSound();
				} else {
					this.model.addTime(-Timer.TIMEOUT*(1.0/1000.0));
				}
				
			}
			
			try {
				Thread.sleep(Timer.TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
