package Controller;

public class Gravity extends Thread{

	private Control ctrl;
	private boolean active = false;
	private int timer_repeat;



	public int getTimer_repeat() {
		return timer_repeat;
	}

	public void setTimer_repeat(int timer_repeat) {
		this.timer_repeat = timer_repeat;
	}

	/*Constructeur*/
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
			System.out.println(ctrl.getModel().isGamePaused() + "  " + ctrl.getModel().isGameOver());
			if(!ctrl.getModel().isGamePaused()) {
				ctrl.getModel().gravityForce() ;
				try { Thread.sleep(20);
				System.out.println(timer_repeat);
					if(timer_repeat>=4 && !ctrl.getModel().isGameOver()) {
						timer_repeat = 0;
						ctrl.getModel().getCurrentLevel().setTimeLeft(ctrl.getModel().getCurrentLevel().getTimeLeft()-0.1);
						System.out.println("test");
						
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
