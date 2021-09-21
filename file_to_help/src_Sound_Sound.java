package Sound;
import java.io.BufferedInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Sound class used for managing sound effects and musics
 */
public class Sound {

	/**
	 * Clip handler for the main theme music
	 */
	private Clip mainThemeClip;
	/**
	 * InputStream of the main theme music file
	 */
	private AudioInputStream mainTheme;
	/**
	 * Clip handler for the menu sound effect
	 */
	private Clip menuClip;
	/**
	 * InputStream of the menu sound effect
	 */
	private AudioInputStream menuSound;
	/**
	 * Clip handler for the jump sound effect
	 */
	private Clip jumpClip;
	/**
	 * InputStream of the jump sound effect
	 */
	private AudioInputStream jumpSound;
	/**
	 * Clip handler for the hit sound effect
	 */
	private Clip hitClip;
	/**
	 * InputStream of the hit sound effect
	 */
	private AudioInputStream hitSound;
	/**
	 * Clip handler for the explosion sound effect
	 */
	private Clip explosionClip;
	/**
	 * InputStream of the explosion sound effect
	 */
	private AudioInputStream explosionSound;
	/**
	 * Clip handler for the game over sound effect
	 */
	private Clip gameOverClip;
	/**
	 * InputStream of the game over sound effect
	 */
	private AudioInputStream gameOverSound;
	
	
	/**
	 * Sound constructor.
	 * Tries to load each sound effect, and creates appropriate Clip handlers
	 */
	public Sound() {
		
		try {
			this.mainThemeClip = AudioSystem.getClip();
			this.mainTheme = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/main.wav")));
			this.mainThemeClip.open(mainTheme);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/main.wav !");
		}
		
		try {
			this.menuClip = AudioSystem.getClip();
			this.menuSound = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/menu.wav")));
			this.menuClip.open(menuSound);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/menu.wav !");
		}
		
		try {
			this.jumpClip = AudioSystem.getClip();
			this.jumpSound = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/jump.wav")));
			this.jumpClip.open(jumpSound);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/jump.wav !");
		}
		
		try {
			this.hitClip = AudioSystem.getClip();
			this.hitSound = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/hit.wav")));
			this.hitClip.open(hitSound);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/hit.wav !");
		}
		
		try {
			this.explosionClip = AudioSystem.getClip();
			this.explosionSound = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/explosion.wav")));
			this.explosionClip.open(explosionSound);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/explosion.wav !");
		}
		
		try {
			this.gameOverClip = AudioSystem.getClip();
			this.gameOverSound = AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getClassLoader().getResourceAsStream("sounds/gameover.wav")));
			this.gameOverClip.open(gameOverSound);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Failed to load sounds/gameover.wav !");
		}
		
	}
	
	/**
	 * Starts or resumes the main theme
	 */
	public void playMainTheme() {
		this.mainThemeClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * Pauses the main theme
	 */
	public void pauseMainTheme() {
		this.mainThemeClip.stop();
	}
	
	/**
	 * Resets the main theme
	 */
	public void resetMainTheme() {
		this.mainThemeClip.setFramePosition(0);
	}
	
	/**
	 * Plays the menu sound effect
	 */
	public void playMenuSound() {
		this.menuClip.setFramePosition(0);
		this.menuClip.start();
	}
	
	/*
	 * Plays the menu sound effect
	 */
	public void playJumpSound() {
		this.jumpClip.setFramePosition(0);
		this.jumpClip.start();
	}
	
	/*
	 * Plays the menu sound effect
	 */
	public void playHitSound() {
		this.hitClip.setFramePosition(0);
		this.hitClip.start();
	}
	
	/*
	 * Plays the menu sound effect
	 */
	public void playExplosionSound() {
		this.explosionClip.setFramePosition(0);
		this.explosionClip.start();
	}
	
	/*
	 * Plays the menu sound effect
	 */
	public void playGameOverSound() {
		this.gameOverClip.setFramePosition(0);
		this.gameOverClip.start();
	}
	
}
