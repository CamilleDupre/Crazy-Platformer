package Controller;

public class Gravity extends Thread{

	private Control ctrl;
	private boolean active = false;
	private int timer_repeat;



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
			ctrl.getModel().gravityForce() ;
			try { Thread.sleep(20);
				if(timer_repeat==4 && !ctrl.getModel().isGameOver()) {
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
