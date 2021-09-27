package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	int maxSize;

	private Point2D[] ennemies;
	private Point2D[] coins;
	private ArrayList<Block> blocks;
	private Point2D[] powers;
	private Point2D[] trap;


	public Level(int maxSize, ArrayList<Block> blocks, Point2D[] ennemies , Point2D[] coins , Point2D[] powers, Point2D[] trap) {

		this.maxSize = maxSize;
		this.setEnnemies(ennemies);
		this.setCoins(coins);
		this.setBlocks(blocks);
		this.setPowers(powers);
		this.setTrap(trap);

	}



	public Point2D[] getEnnemies() {
		return ennemies;
	}

	public void setEnnemies(Point2D[] ennemies) {
		this.ennemies = ennemies;
	}

	public Point2D[] getCoins() {
		return coins;
	}

	public void setCoins(Point2D[] coins) {
		this.coins = coins;
	}
	public void removeCoin(int c) {
		//this.coins.remove(coins);
		//coins.remove(c);
	}

	public ArrayList<Block> getBlocks() {
		return blocks;
	}

	public void setBlocks(ArrayList<Block> blocs) {
		this.blocks = blocs;
	}

	public Point2D[] getPowers() {
		return powers;
	}

	public void setPowers(Point2D[] powers) {
		this.powers = powers;
	}

	public Point2D[] getTrap() {
		return trap;
	}

	public void setTrap(Point2D[] trap) {
		this.trap = trap;
	}
}
