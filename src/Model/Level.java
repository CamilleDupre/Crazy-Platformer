package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	int maxSize;

	private ArrayList<Point2D> ennemies;
	private ArrayList<Point2D> coins;
	private ArrayList<Block> blocks;
	private ArrayList<Point2D> powers;
	private ArrayList<Point2D> trap;


	public Level(int maxSize, ArrayList<Block> blocks, ArrayList<Point2D> ennemies , ArrayList<Point2D> coins , ArrayList<Point2D> powers, ArrayList<Point2D> trap) {

		this.maxSize = maxSize;
		this.setEnnemies(ennemies);
		this.setCoins(coins);
		this.setBlocks(blocks);
		this.setPowers(powers);
		this.setTrap(trap);

	}



	public ArrayList<Point2D> getEnnemies() {
		return ennemies;
	}

	public void setEnnemies(ArrayList<Point2D> ennemies) {
		this.ennemies = ennemies;
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
}
