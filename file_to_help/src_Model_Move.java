package Model;
import View.View;

/**
 * Thread that moves the player's ship, check if any hit is taken, check if a level is cleared, and refreshes the View
 */
public class Move extends Thread {

	/**
	 * Model attribute to both modify and check game's data
	 */
	private Model model;
	/**
	 * View attribute.
	 * Used only to repaint it.
	 */
	private View view;

	/**
	 * Thread's only constructor
	 * @param m Model used to access and modify data
	 * @param v View used to refresh it
	 */
	public Move(Model m, View v) {
		this.model = m;
		this.view = v;
	}

	/**
	 * Overriden run() method of Move.
	 * While the game is not over, makes the whole game run.
	 * Checks if the track needs a new segment, and adds one if necessary,
	 * Computes ship's speed, adds the position newly traveled to the score (with a coefficient),
	 * Checks obstacles if any is hit and/or overtaken,
	 * Checks if a level is cleared, and repaints the View before sleeping for Model.TIMEOUT ms.
	 */
	@Override
	public void run() {
		while (true) {
			if (!this.model.isGamePaused() && !this.model.isGameOver()) {
				
				if (this.model.getTrack().getPointList().get(this.model.getTrack().getPointList().size() - 1).getY() < Track.LENGTH + Track.SPACING_MIN + this.model.getCoord().getY()) {
					this.model.getTrack().addPoint();
				}
				
				this.model.computeSpeed();
				this.model.addScore((int) (this.model.getSpeed().getY()/10.0));
				
				for (Obstacle o : this.model.getObstacleList()) {
					
					this.model.checkHit(o);
					this.model.checkOvertake(o);
					
					if (Moving.class.isInstance(o)) {
						((Moving) o).computeSpeed();
					}
				}
				this.view.checkLevelPassed();
			}
			
			this.view.repaint();
			
			try {
				Thread.sleep(Model.TIMEOUT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
