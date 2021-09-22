package Model;

import java.util.ArrayList;

import javafx.geometry.Point2D;

public class Level {

	int nbLevel;
	int maxSize;
	
	ArrayList<Point2D> ennemies = new ArrayList<Point2D>();
	ArrayList<Point2D> coins = new ArrayList<Point2D>();
	ArrayList<Point2D> blocs = new ArrayList<Point2D>();
	ArrayList<Point2D> powers = new ArrayList<Point2D>();
	
	public Level(int lvl , int maxSize, ArrayList<Point2D> ennemies , ArrayList<Point2D> coins , ArrayList<Point2D> blocs, ArrayList<Point2D> powers) {
	
		this.nbLevel = lvl;
		this.maxSize = maxSize;
		this.ennemies = ennemies;
		this.coins = coins;
		this.blocs = blocs;
		this.powers =powers;
	}
	
	public void addObject(ArrayList<Point2D> Array , Point2D coord ) {
		Array.add(coord);
	}
	
	public void removeObject(ArrayList<Point2D> Array , Point2D coord ) {
		Array.remove(coord);
	}
}
