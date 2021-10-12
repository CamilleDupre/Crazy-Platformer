package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	/**
	 * Attribute 
	 */
	int maxSize;
	private int id;
	private int maxCoins;
	
	private ArrayList<Point2D> enemies;
	private ArrayList<Point2D> coins;
	private ArrayList<Block> blocks;
	private ArrayList<Point2D> powers;
	private ArrayList<Point2D> trap;
	private Point2D treasure;

	/**
	 * Constructor of the level
	 * @param id
	 * @param maxSize
	 * @param blocks
	 * @param enemies
	 * @param coins
	 * @param powers
	 * @param trap
	 * @param treasure
	 */
	public Level(int id,int maxSize, ArrayList<Block> blocks, ArrayList<Point2D> enemies , ArrayList<Point2D> coins , ArrayList<Point2D> powers, ArrayList<Point2D> trap, Point2D treasure) {

		this.maxSize = maxSize;
		this.setEnemies(enemies);
		this.setCoins(coins);
		this.setBlocks(blocks);
		this.setPowers(powers);
		this.setTrap(trap);
		this.treasure=treasure;
		this.id = id;
		this.maxCoins = coins.size();

	}


	/**
	 * Getter for the enemies
	 * @return enemies
	 */
	public ArrayList<Point2D> getEnemies() {
		return enemies;
	}

	/**
	 * Setter of enemies
	 * @param enemies
	 */
	public void setEnemies(ArrayList<Point2D> enemies) {
		this.enemies = enemies;
	}

	/**
	 * Getter for Coins
	 * @return coins
	 */
	public ArrayList<Point2D> getCoins() {
		return coins;
	}

	/**
	 * Setter for coins
	 * @param coins
	 */
	public void setCoins(ArrayList<Point2D> coins) {
		this.coins = coins;
	}


	/**
	 * Getter for blocks
	 * @return blocks
	 */
	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	/**
	 * Setter for blocks
	 * @param blocs
	 */
	public void setBlocks(ArrayList<Block> blocs) {
		this.blocks = blocs;
	}

	/**
	 * Getter for powers
	 * @return powers
	 */
	public ArrayList<Point2D> getPowers() {
		return powers;
	}

	/**
	 * Setter for power
	 * @param powers
	 */
	public void setPowers(ArrayList<Point2D> powers) {
		this.powers = powers;
	}

	/**
	 * Getter for trap
	 * @return trap
	 */
	public ArrayList<Point2D> getTrap() {
		return trap;
	}

	/**
	 * Setter for trap
	 * @param trap
	 */
	public void setTrap(ArrayList<Point2D> trap) {
		this.trap = trap;
	}

	/**
	 * Getter for the treasrure 
	 * @return treasure
	 */
	public Point2D getTreasure() {
		return treasure;
	}

	/**
	 * Setter for the treasuer
	 * @param treasure
	 */
	public void setTreasure(Point2D treasure) {
		this.treasure = treasure;
	}

	/**
	 * Getter max coins in the level
	 * @return maxCoins
	 */
	public int getMaxCoins() {
		return this.maxCoins;
	}


	/**
	 * Getter for level ID
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter ID
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
}
