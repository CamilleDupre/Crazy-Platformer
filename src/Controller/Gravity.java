package Controller;

public class Gravity extends Thread{

	/**
	 * Attribute control
	 */
	private Control ctrl;
	
	/**
	 * Attribute active, false at the beginning until we start a level
	 */
	private boolean active = false;
	
	/**
	 * Attribute time_repeat, to decrease the time every second
	 */
	private int timer_repeat;


	/**
	 * Constructor of Gravity
	 * @param myctrl
	 */
	public Gravity(Control myctrl) {
		ctrl = myctrl;
		timer_repeat=0;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		timer_repeat=0;
		while(active) {
			if(!ctrl.getModel().isGamePaused()) {
				ctrl.getModel().gravityForce() ;
				try { Thread.sleep(20);
					if(timer_repeat>=4 && !ctrl.getModel().isGameOver()) {
						timer_repeat = 0;
						ctrl.getModel().getCurrentLevel().setTimeLeft(ctrl.getModel().getCurrentLevel().getTimeLeft()-0.1);
						
						if(ctrl.getModel().getCurrentLevel().getTimeLeft()<0) {
							ctrl.getModel().setGameOver(true);
							ctrl.displayMenu(ctrl.getGameView().getGamePane(), ctrl.getGameView().getLooseMenu());
						}
					}else {
						timer_repeat+=1;
					}
				}
					
				catch (Exception e) { e.printStackTrace(); }
				
			}
		}
	}

	// Getter & Setter //
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public int getTimer_repeat() {
		return timer_repeat;
	}

	public void setTimer_repeat(int timer_repeat) {
		this.timer_repeat = timer_repeat;
	}

}
