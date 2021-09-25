package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.Model;
import Model.Obstacle;
import javafx.geometry.Point3D;

/**
 * Background class is used to draw all the stuff that the Frame needs
 */
public class Background extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -7551073925343756845L;
	
	/**
	 * Panel's center
	 */
	private int center;
	
	/**
	 * Background image
	 */
	private ImageIcon bkgdImg;
	
	/**
	 * Panel that contains informations like score, level, etc
	 */
	private JPanel infoPanel = new JPanel();
	
	/**
	 * Time left before game over
	 */
	private JLabel timeLabel = new JLabel();
	
	/**
	 * Actual score of the player
	 */
	private JLabel scoreLabel = new JLabel();
	
	/**
	 * Actual Level
	 */
	private JLabel levelLabel = new JLabel();
	
	/**
	 * Attribute used to manage datas
	 */
 	private View view;

	/**
	 * Background constructor used to initialize background panel and add labels
	 * @param v
	 */
	public Background(View v) {
		this.center = 0;
		this.view = v;
		this.setVisible(true);
		this.setOpaque(true);
		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(View.WIDTH, View.HEIGHT));
		
		try {
			this.bkgdImg = new ImageIcon(ImageIO.read(Background.class.getClassLoader().getResourceAsStream("imgs/space1280_800.jpg")));
		} catch (Exception e) {
			System.out.println("Background could not be loaded !");
			e.printStackTrace();
		}
		
		this.infoPanel.add(this.timeLabel);
		this.infoPanel.add(this.scoreLabel);
		this.infoPanel.add(this.levelLabel);
		this.infoPanel.setVisible(true);
		this.add(infoPanel);
		
	}

	public int getCenter() {
		return this.center;
	}
	
	/**
	 *
	 * @param center
	 */
	public void setCenter(int center) {
		this.center = center;
	}
	
	/**
	 * Method used to launch all the drawing methods
	 * @param g
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		// Background
		this.drawBackground(g2d);
		
		// Checkpoint
		this.drawCheckpoint(g2d);
		
		// Rainbow Road
		this.drawRainbowRoad(g2d);
		
		// Obstacle
		this.drawObstacles(g2d);

		// Vaisseau
		this.drawShip(g2d);
		
		// Temps Restant, Score, ...
		this.drawInfo(g2d);
		
		// Game Paused
		if (this.view.getModel().isGamePaused()) {
			this.drawGamePaused(g2d);
		}
		
		// Game Over
		if (this.view.getModel().isGameOver()) {
			this.drawGameOver(g2d);
		}
		
	}

	/**
	 * Method used to draw the background image
	 * @param g2d
	 */
	private void drawBackground(Graphics2D g2d) {
		
		try {
			g2d.drawImage(	this.bkgdImg.getImage(),
							0,
							0,
							View.WIDTH,
							View.HEIGHT,
							null);
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
	}
	
	/**
	 * Method used to draw planets(checkpoints)
	 * @param g2d
	 */
	private void drawCheckpoint(Graphics2D g2d) {
		
		double planetSize = (int) View.PLANET_SIZE*(Model.LEVEL_LENGTH/(this.view.getModel().getLevel()*Model.LEVEL_LENGTH - this.view.getModel().getCoord().getY()));
		
		try {
			g2d.drawImage(	this.view.planet.getImage(),
							(int) (this.view.getModel().translateToView(new Point3D(this.view.getModel().getXTrack(this.view.getModel().getCoord().getY() + 2*View.HEIGHT/3), 0, 0)).getX() - planetSize/2),
							(int) (View.HEIGHT/3 - planetSize/2),
							(int) planetSize,
							(int) planetSize,
							null);
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
	}
	
	/**
	 * Method used to draw the rainbow track
	 */
	private void drawRainbowRoad(Graphics2D g2d) {
		
		ArrayList<Point3D> pList = this.view.getModel().getTrack().getPointList();
		
		for(int i = 0; i < pList.size() - 1; i++) {
			Point3D p1 = this.view.getModel().translateToView(pList.get(i));
			Point3D p2 = this.view.getModel().translateToView(pList.get(i+1));

			// common values

			int y1 = 0;
			int y2 = 0;
			int y3 = 0;
			int y4 = 0;

			// condition to stop the track at the horizon
			if(p1.getY() + this.view.getModel().getCoord().getY() < View.HEIGHT/3) {
				y1 = View.HEIGHT/3;
				y4 = View.HEIGHT/3;
			} else {
				y1 = (int) (p1.getY() + this.view.getModel().getCoord().getY());
				y4 = (int) (p1.getY() + this.view.getModel().getCoord().getY());
			}

			if(p2.getY() + this.view.getModel().getCoord().getY() < View.HEIGHT/3) {
				y2 = View.HEIGHT/3;
				y3 = View.HEIGHT/3;
			} else {
				y2 = (int) (p2.getY() + this.view.getModel().getCoord().getY());
				y3 = (int) (p2.getY() + this.view.getModel().getCoord().getY());
			}

			int[] tY = {y1, y2, y3, y4};

			// Red
			int xR1 = (int) (p1.getX() - View.perspective(y1) * (3.0/5.0) );
			int xR2 = (int) (p2.getX() - View.perspective(y2) * (3.0/5.0) );
			int xR3 = (int) (p2.getX() - View.perspective(y3));
			int xR4 = (int) (p1.getX() - View.perspective(y4));

			int[] tRed = {xR1, xR2, xR3, xR4};
			g2d.setColor(new Color(255,117,117));
			g2d.fillPolygon(tRed, tY, 4);

			// Yellow
			int xY1 = (int) (p1.getX() - View.perspective(y1) * (1.0/5.0) );
			int xY2 = (int) (p2.getX() - View.perspective(y2) * (1.0/5.0) );
			int xY3 = (int) (p2.getX() - View.perspective(y3) * (3.0/5.0) );
			int xY4 = (int) (p1.getX() - View.perspective(y4) * (3.0/5.0) );

			int[] tYellow = {xY1, xY2, xY3, xY4};
			g2d.setColor(new Color(255,255,117));
			g2d.fillPolygon(tYellow, tY, 4);

			// Green
			int xG1 = (int) (p1.getX() + View.perspective(y1) * (1.0/5.0) );
			int xG2 = (int) (p2.getX() + View.perspective(y2) * (1.0/5.0) );
			int xG3 = (int) (p2.getX() - View.perspective(y3) * (1.0/5.0) );
			int xG4 = (int) (p1.getX() - View.perspective(y4) * (1.0/5.0) );

			int[] tGreen = {xG1, xG2, xG3, xG4};
			g2d.setColor(new Color(160,255,117));
			g2d.fillPolygon(tGreen, tY, 4);

			// Blue
			int xB1 = (int) (p1.getX() + View.perspective(y1) * (3.0/5.0) );
			int xB2 = (int) (p2.getX() + View.perspective(y2) * (3.0/5.0) );
			int xB3 = (int) (p2.getX() + View.perspective(y3) * (1.0/5.0) );
			int xB4 = (int) (p1.getX() + View.perspective(y4) * (1.0/5.0) );

			int[] tBlue = {xB1, xB2, xB3, xB4};
			g2d.setColor(new Color(80,117,255));
			g2d.fillPolygon(tBlue, tY, 4);

			// Purple

			int xP1 = (int) (p1.getX() + View.perspective(y1));
			int xP2 = (int) (p2.getX() + View.perspective(y2));
			int xP3 = (int) (p2.getX() + View.perspective(y3) * (3.0/5.0) );
			int xP4 = (int) (p1.getX() + View.perspective(y4) * (3.0/5.0) );

			int[] tPurple = {xP1, xP2, xP3, xP4};
			g2d.setColor(new Color(160,117,255));
			g2d.fillPolygon(tPurple, tY, 4);


			// Border
			g2d.setColor(Color.BLACK);
			g2d.drawLine(xP1, y1, xP2, y2);
			g2d.drawLine(xR4, y4, xR3, y3);
		}
		
	}
	
	/**
	 * Method used to draw obstacles (moving and non moving)
	 * @param g2d
	 */
	private void drawObstacles(Graphics2D g2d) {
		for (Obstacle o : this.view.getModel().getObstacleList()) {
			
			try {
				this.view.obstaclePerspective(o);
				g2d.drawImage(	o.getImageIcon().getImage(),
								(int) (this.view.getModel().translateToView(o.getCoord()).getX()),
								(int) (this.view.getModel().translateToView(new Point3D(0, o.getCoord().getY() - this.view.getModel().getCoord().getY(), 0)).getY()),
								(int) o.getSize().getX(),
								(int) o.getSize().getY(),
								null);
			} catch (NullPointerException e) {
				// https://www.youtube.com/watch?v=dQw4w9WgXcQ
			}
		}
	}
	
	
	/**
	 * Method used to draw the player's spaceship
	 * @param g2d
	 */
	private void drawShip(Graphics2D g2d) {
		
		try {
			g2d.drawImage(	this.view.getModel().getImgSpaceship(),
							(int) this.view.getModel().translateToView(this.view.getModel().getCoord()).getX(),
							View.HEIGHT - View.BOTTOM_PADDING - (int) this.view.getModel().getCoord().getZ(),
							(int) this.view.getModel().getShipSize().getX(),
							(int) this.view.getModel().getShipSize().getY(),
							null);
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
	}
	
	/**
	 * Method used to draw informations like score, level, speed,etc
	 * @param g2d
	 */
	private void drawInfo(Graphics2D g2d) {
		
		g2d.setColor(Color.WHITE);
		
		try {
			g2d.setFont(this.view.eightBitFont.deriveFont(Font.BOLD, 24));
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
		g2d.drawString("SCORE " + (int) this.view.getModel().getScore(), View.WIDTH - 320, View.HEIGHT/3);
		g2d.drawString("LEVEL " + (int) this.view.getModel().getLevel(), View.WIDTH - 210, View.HEIGHT/3 +30);
		g2d.drawString("SPEED " + (int) this.view.getModel().getSpeed().getY() + " m/s", 10, View.HEIGHT-60);
		g2d.drawString("TIME LEFT " + (int) this.view.getModel().getTime() + " s", 10, View.HEIGHT/3);
		
	}
	
	/**
	 * Method used to draw the Game paused view when ESC is pressed
	 * @param g2d
	 */
	private void drawGamePaused(Graphics g2d) {
		
		g2d.setColor(Color.WHITE);
		String gamePaused = "GAME PAUSED";
		String pressEsc = "Press ESC to resume";
		String pressQ = "Press Q to turn left";
		String pressD = "Press D to turn right";
		String pressSpace = "Press SPACE to jump";
		
		try {
			g2d.setFont(this.view.eightBitFont.deriveFont(Font.BOLD, 72));
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
		g2d.drawString(gamePaused, View.WIDTH/2-((gamePaused.length()*72)/2), View.HEIGHT/2);
		
		try {
			g2d.setFont(this.view.eightBitFont.deriveFont(Font.BOLD, 24));
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
		g2d.drawString(pressEsc, View.WIDTH/2-((pressEsc.length()*24)/2), View.HEIGHT/2 - 72);
		g2d.drawString(pressQ, View.WIDTH/2-((pressQ.length()*24)/2), View.HEIGHT/2 + 24);
		g2d.drawString(pressD, View.WIDTH/2-((pressD.length()*24)/2), View.HEIGHT/2 + 48);
		g2d.drawString(pressSpace, View.WIDTH/2-((pressSpace.length()*24)/2), View.HEIGHT/2 + 72);
		
	}
	
	/**
	 * Method used to draw the GameOver view when game is finished
	 * @param g2d
	 */
	private void drawGameOver(Graphics g2d) {
		g2d.setColor(Color.RED);
		
		try {
			g2d.setFont(this.view.eightBitFont.deriveFont(Font.BOLD, 72));
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
		String gameOver = "GAME OVER";
		String pressSpace = "Press SPACE to try again";
		String pressEsc = "Press ESC to exit game";
		
		g2d.drawString(gameOver, View.WIDTH/2-((gameOver.length()*72)/2), View.HEIGHT/2);
		
		try {
			g2d.setFont(this.view.eightBitFont.deriveFont(Font.BOLD, 24));
		} catch (NullPointerException e) {
			// https://www.youtube.com/watch?v=dQw4w9WgXcQ
		}
		
		g2d.drawString(pressSpace, View.WIDTH/2-((pressSpace.length()*24)/2), View.HEIGHT/2 + 24);
		g2d.drawString(pressEsc, View.WIDTH/2-((pressEsc.length()*24)/2), View.HEIGHT/2 + 48);
		
	}
	
}
