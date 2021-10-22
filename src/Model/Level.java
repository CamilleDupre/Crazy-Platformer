package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	/**
	 * Attribute maxSize of the level in the x coordinate
	 */
	private int maxSize;
	
	/**
	 * Attribute id of the level
	 */
	private int id;
	
	/**
	 * Attribute max coins, how many coins to unlock the chest
	 */
	private int maxCoins;
	
	/**
	 * Attribute enemies, all the enemies in the level
	 */
	private ArrayList<Enemy> enemies;
	
	/**
	 * Attribute coins, all the coins in the level
	 */
	private ArrayList<Point2D> coins;
	
	/**
	 * Attribute blocks, all the blocks in the level
	 */
	private ArrayList<Block> blocks;
	
	/**
	 * Attribute power, all the power of the level
	 */
	private ArrayList<Power> powers;
	
	/**
	 * Attribute trap, all the traps in the level
	 */
	private ArrayList<Point2D> trap;
	
	/**
	 * Attribute treasure, position of the treasure to finish the level
	 */
	private Point2D treasure;
	
	/**
	 * Attribute timeLimite, max time of the level
	 */
	private double timeLimit;
	
	/**
	 * attribute timeLeft,  how much time player have left in the level
	 */
	private double timeLeft;

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
	public Level(int id,int maxSize, ArrayList<Block> blocks, ArrayList<Enemy> enemies , ArrayList<Point2D> coins , ArrayList<Power> powers, ArrayList<Point2D> trap, Point2D treasure, double timeLimit) {

		this.maxSize = maxSize;
		this.setEnemies(enemies);
		this.setCoins(coins);
		this.setBlocks(blocks);
		this.setPowers(powers);
		this.setTrap(trap);
		this.treasure=treasure;
		this.id = id;
		this.maxCoins = coins.size();
		this.timeLimit = timeLimit;
		this.timeLeft = timeLimit;

	}


	/**
	 * Getter for the enemies
	 * @return enemies
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**
	 * Setter of enemies
	 * @param enemies
	 */
	public void setEnemies(ArrayList<Enemy> enemies) {
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
	public ArrayList<Power> getPowers() {
		return powers;
	}

	/**
	 * Setter for power
	 * @param powers
	 */
	public void setPowers(ArrayList<Power> powers) {
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
	 * Getter for the treasure 
	 * @return treasure
	 */
	public Point2D getTreasure() {
		return treasure;
	}

	/**
	 * Setter for the treasure
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
	
	public int getMaxSize() {
		return maxSize;
	}


	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}


	public double getTimeLimit() {
		return timeLimit;
	}


	public void setTimeLimit(double timeLimit) {
		this.timeLimit = timeLimit;
	}


	public double getTimeLeft() {
		return timeLeft;
	}


	public void setTimeLeft(double timeLeft) {
		this.timeLeft = timeLeft;
	}


}
