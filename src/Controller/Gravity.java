package Controller;

public class Gravity extends Thread{

	private Control ctrl;
	private boolean active = false;



	/*Constructeur*/
	public Gravity(Control myctrl) {
		ctrl = myctrl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while(active) {
			ctrl.moveDown() ;
		}
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}



