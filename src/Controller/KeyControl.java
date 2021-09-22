package Controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Sound.Sound;

public class KeyControl implements KeyListener {
	
	/**
	 * Control attribute used to issue commands and to access the model from
	 */
	private Control control;
	/**
	 * Sound attribute used by this class and also given to KeyProcessor to play sound effects
	 */
	private Sound sound;
	/**
	 * Boolean attribute telling if the Q key is currently pressed.
	 * Used as a mutex in KeyProcessor thread.
	 */
	private boolean qPressed;
	/**
	 * Boolean attribute telling if the D key is currently pressed
	 * Used as a mutex in KeyProcessor thread.
	 */
	private boolean dPressed;

	/**
	 * KeyControl's constructor.
	 * Sets both qPressed and dPressed at false, and starts the KeyProcessor thread.
	 * It should not be called outside of Control, and is thus package protected.
	 * @param c
	 * @param s
	 */
	KeyControl(Control c, Sound s) {
		this.control = c;
		this.sound = s;
		
		this.qPressed = false;
		this.dPressed = false;
		
		(new KeyProcessor(this.control)).start();
	}
	
	public boolean isQPressed() {
		return this.qPressed;
	}
	
	public boolean isDPressed() {
		return this.dPressed;
	}

	/**
	 * Action to be performed if a key is pressed.
	 * Q, D, and Space are active only when the game is not paused.
	 * Q and D set qPressed and dPressed respectively to true until these keys are released (cf. KeyControl.keyReleased).
	 * Space makes the player's ship jump and plays an appropriate sound effect.
	 * Escape either pauses or resumes the game, depending on Model.gamePaused value.
	 * It also plays an appropriate sound effect, and either pauses or resumes the main theme music.
	 */
	@Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        
            case KeyEvent.VK_Q :
            	
            	if (!this.control.getModel().isGamePaused()) {
            		
            		this.qPressed = true;
            	
            	}
            	
                break;
                
            case KeyEvent.VK_D :
            	
            	if (!this.control.getModel().isGamePaused()) {
            		
            		this.dPressed = true;
            	}
            	
                break;
                
            case KeyEvent.VK_SPACE :
            	
            	System.out.println("Jump");
            	if (!this.control.getModel().isGameOver() && !this.control.getModel().isGamePaused() && !this.control.getModel().getPlayer().isJumping()) {
            		
            		this.sound.playJumpSound();
            		this.control.getModel().makePlayerJump();
            		
            	} 
                break;
                
            case KeyEvent.VK_ESCAPE :
            	
            	if (!this.control.getModel().isGameOver()) {
            		
            		this.sound.playMenuSound();
                	
                	if (!this.control.getModel().isGamePaused()) {
                		
                		
                		this.control.getModel().setGamePaused(true);
                		this.sound.pauseMainTheme();
                		this.control.pauseGame();
                		
                	} else {
                		this.control.resumeGame();
                		this.control.getModel().setGamePaused(false);
                		this.sound.playMainTheme();
                	}
                	
            	} else {
            		System.exit(0);
            	}
            	break;
            	
        }
    }

	/**
	 * Action to be performed if a key is pressed.
	 * It simply changes the values of qPressed and dPressed to false when the appropriate keys are released.
	 */
    @Override
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyCode()) {
	        case KeyEvent.VK_Q :
	        	if (!this.control.getModel().isGamePaused()) {
            		this.qPressed = false;
            	}
	            break;
	        case KeyEvent.VK_D :
	        	if (!this.control.getModel().isGamePaused()) {
            		this.dPressed = false;
            	}
	            break;
    	}
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}

