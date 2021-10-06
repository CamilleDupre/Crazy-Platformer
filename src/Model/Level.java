package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	int maxSize;

	private ArrayList<Point2D> enemies;
	private ArrayList<Point2D> coins;
	private ArrayList<Block> blocks;
	private ArrayList<Point2D> powers;
	private ArrayList<Point2D> trap;
	private Point2D treasure;
	private int id;


	public Level(int id,int maxSize, ArrayList<Block> blocks, ArrayList<Point2D> enemies , ArrayList<Point2D> coins , ArrayList<Point2D> powers, ArrayList<Point2D> trap, Point2D treasure) {

		this.maxSize = maxSize;
		this.setEnemies(enemies);
		this.setCoins(coins);
		this.setBlocks(blocks);
		this.setPowers(powers);
		this.setTrap(trap);
		this.treasure=treasure;
		this.id = id;

	}



	public ArrayList<Point2D> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Point2D> enemies) {
		this.enemies = enemies;
	}

	public ArrayList<Point2D> getCoins() {
		return coins;
	}

	public void setCoins(ArrayList<Point2D> coins) {
		this.coins = coins;
	}
	

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocs) {
		this.blocks = blocs;
	}

	public ArrayList<Point2D> getPowers() {
		return powers;
	}

	public void setPowers(ArrayList<Point2D> powers) {
		this.powers = powers;
	}

	public ArrayList<Point2D> getTrap() {
		return trap;
	}

	public void setTrap(ArrayList<Point2D> trap) {
		this.trap = trap;
	}



	public Point2D getTreasure() {
		return treasure;
	}



	public void setTreasure(Point2D treasure) {
		this.treasure = treasure;
	}



	public int getMaxCoins() {
		// TODO Auto-generated method stub
		return 5;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
}
