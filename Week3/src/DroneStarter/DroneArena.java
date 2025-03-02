package DroneStarter;

import java.util.Random;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class DroneArena implements Serializable{
	
	private Random randomGen; //constructor variables
	private int sizeX;
	private int sizeY;
	private DroneInterface DroneUI;
	ArrayList<Drone> allDrones = new ArrayList<Drone>();
	ArrayList<Drone> toRemove = new ArrayList<Drone>();
	ArrayList<Drone> toAdd = new ArrayList<Drone>();
	//ArrayList<Drone> AddedRDrone
	
	public void drawArena(MyCanvas mc) { //DO NOT TOUCH
		for (Drone b : allDrones) {
			b.drawDrone(mc);		// draw all balls
		}
	}
		// check all balls except one with given id 	
				// if hitting, return angle between the other ball and this one.
	public double CheckBallAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x < rad || x > sizeX - rad) ans = 180 - ans;
			// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (y < rad || y > sizeY - rad) ans = - ans;
			// if try to go off top or bottom, set mirror angle
		return ans;		// return the angle
	}

	public void deleteDrones() { //Collision (prey hits hunter)
		for (Drone b : toRemove) {
			allDrones.remove(b);
		}
		toRemove.clear();
	}
	
	public void addDrones() {
		for (Drone b : toAdd) {
			allDrones.add(b);
		
		}
		toAdd.clear();
	}
	
	public boolean checkHitWithD(Drone target) {
		boolean ans = false;
		for (Drone b : allDrones)
			if (b instanceof Drone && b.hitting(target)) {
				ans = true;
			}
				// try all balls, if GameBall, check if hitting the target
		return ans;
	}
	
	public DroneArena(int i, int j) {
		sizeX = i; //max size of arena (length)
		sizeY = j; //max size of arena (height)
	}
	public boolean canMoveHere(double x, double y,int rad, int ID) {
		if(x>=sizeX-rad||y>=sizeY-rad|| x<rad||y<rad) { //if out of bounds, return false
			return false; //walls
		}
		return getDroneAt(x,y,rad, ID) == null;
	}
	
	public void moveAllDrones() { 
		for(Drone d: allDrones) { //move all drones, read from drone array
			d.tryToMove(this);
		}
	}
	
	public double adjustAngle(Drone d) {
		double result = d.angle;
		if(d.Wantxpos<d.rad || d.Wantxpos>sizeX-d.rad) { //checks for side wall
			result = 180 - d.angle;
			System.out.print("Wall \n");
		}
		if(d.Wantypos<d.rad || d.Wantypos>sizeY-d.rad) { //checks for top & bottom wall
			System.out.print("Top/Bottom \n");
			result = -d.angle;
		}
		
		if(result < 0) {
			result += 360; //makes result 0-360
		}
		if(result >= 360) {
			result -= 360; //makes result 0-360
		}
		return result;
	}
	
	public boolean checkDrone(Drone drone) {
		for (Drone d: allDrones) {
			if(d.getID()!=drone.getID()) {
				 System.out.println("DoHitCalled");
				 drone.doHitDrone(d, this);
				 return true;
			}
		}
		return false;
	}
	public void adjustBalls() {
		for (Drone d : allDrones) { //adjust all balls
			d.adjustDrones();
			checkDrone(d);
		}
	}
	public int getXsize() {
		return sizeX; //returns arena X
	}
	public int getYsize() {
		return sizeY; //returns arena Y
	}
	
	/**
	 * <pre>
	 * Function for seeing if a drone is at location passed in
	 * Considers radius values 
	 * <pre>
	 * @param x
	 * @param y
	 * @param rad
	 * @param ID
	 * @return
	 */
	
	public Drone getDroneAt(double x, double y, int rad, int ID) { //gets drone at location
		for(Drone d : allDrones) {
			if(d.getID()==ID) {
				continue;
			}
			double distance = Math.sqrt(Math.pow(Math.abs(d.xpos - x), 2) + Math.pow(Math.abs(d.ypos - y), 2));
            if (distance <= d.rad + rad) {
            	return d;
            }
		}
		return null;
	}

	public void clearDrones() {
		allDrones = null;
	}
	public void addPrey() { //function for adding Prey Drones
		randomGen = new Random();
		int valx;
		int valy;
		double angle;
		int counter = 0;
		do {
			valx = randomGen.nextInt(sizeX); //creates random xPos
			valy = randomGen.nextInt(sizeY);
			angle = randomGen.nextFloat() * 360;
			counter ++;
		}
	
		while(!canMoveHere(valx, valy, 20, -1) && counter < 100);
			allDrones.add(new Prey(valx, valy, angle)); //adds drone to array
	}
	public void addPrey(int x, int y) { //function for adding Prey Drones via Teleporter Class
		randomGen = new Random();
		double angle;
		int counter = 0;
		do {
			angle = randomGen.nextFloat() * 360;
			counter ++;
		}
	
		while(!canMoveHere(x, y, 20, -1) && counter < 100);
		if(canMoveHere(x,y,20,-1)) {
			toAdd.add(new Prey(x, y, angle)); //adds drone to array
		}
	}
	
	public void addObs() { //function for adding Obstacles
		randomGen = new Random();
		int valx;
		int valy;
		int counter = 0;
		do {
			valx = randomGen.nextInt(sizeX); //creates random xPos
			valy = randomGen.nextInt(sizeY);
			counter ++;
		}
	
		while(!canMoveHere(valx, valy, 10, -1) && counter < 100);
			allDrones.add(new Obstacle(valx, valy)); //adds drone to array
	}
	public void addHunt() { //function for adding Hunter Drones
		randomGen = new Random();
		int valx;
		int valy;
		double angle;
		int counter = 0;
		do {
			valx = randomGen.nextInt(sizeX); //creates random xPos
			valy = randomGen.nextInt(sizeY);
			angle = randomGen.nextFloat() * 360;
			counter ++;
		}
	
		while(!canMoveHere(valx, valy, 10, -1) && counter < 100);
			allDrones.add(new Hunter(valx, valy, angle)); //adds drone to array
	}
	public void addTele() {
		randomGen = new Random();
		int valx;
		int valy;
		int counter = 0;
		do {
			valx = randomGen.nextInt(sizeX); //creates random xPos
			valy = randomGen.nextInt(sizeY);
			counter ++;
		}
	
		while(!canMoveHere(valx, valy, 10, -1) && counter < 100);
			allDrones.add(new Teleporter(valx, valy)); //adds drone to array
	}
	public void reset() { //clears ALL drones
		for(Drone d : allDrones) {
			toRemove.add(d);
		}
		deleteDrones();
	}
	public void clearPrey() { //clears prey drones
		for(Drone d : allDrones) {
			if(d instanceof Prey) {
				toRemove.add(d);
			}
		}
		deleteDrones();
	}
	public void clearObs() { //clears obstacle drones
		for(Drone d : allDrones) {
			if(d instanceof Obstacle) {
				toRemove.add(d);
			}
		}
		deleteDrones();
	}
	public void clearHunter() {//clears hunter  drones
		for(Drone d : allDrones) {
			if(d instanceof Hunter) {
				toRemove.add(d);
			}
		}
		deleteDrones();
	}
	public void clearTele() { //clears teleport drones
		for(Drone d : allDrones) {
			if(d instanceof Teleporter) {
				toRemove.add(d);
			}
		}
		deleteDrones();
	}


	public String toString() { //string for arena size
		String Astring = "Arena size = " + sizeX + "*" + sizeY +"\n"; //prints arena size
		for(int i = 0; i < allDrones.size(); i++) 
			Astring += allDrones.get(i);//get drone(i)
		return Astring;
	}
}
