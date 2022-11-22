package DroneStarter;

import java.util.Random;
import java.io.Serializable;
import java.util.ArrayList;

public class DroneArena implements Serializable{
	
	private Random randomGen; //constructor variables
	private int sizeX;
	private int sizeY;
	private DroneInterface DroneUI;
	ArrayList<Drone> droneArray = new ArrayList<Drone>();
	
	public void showDrones(ConsoleCanvas c) {
		for(Drone d: droneArray) { //display all drones, read from drone array
			d.displayDrone(c);
		}
	}

	public void drawArena(MyCanvas mc) {
		for (Drone b : droneArray) b.drawDrone(mc);		// draw all balls
	}
	
	public double CheckBallAngle(double x, double y, double rad, double ang, int notID) {
		double ans = ang;
		if (x < rad || x > sizeX - rad) ans = 180 - ans;
			// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		if (y < rad || y > sizeY - rad) ans = - ans;
			// if try to go off top or bottom, set mirror angle
		
		for (Drone b : droneArray) 
			if (b.getID() != notID && b.hitting(x, y, rad)) ans = 180*Math.atan2(y-b.getYpos(), x-b.getXpos())/Math.PI;
				// check all balls except one with given id 	
				// if hitting, return angle between the other ball and this one.
		
		return ans;		// return the angle
	}
	public boolean checkHit(Drone target) {
		boolean ans = false;
		for (Drone b : droneArray)
			if (b instanceof Drone && b.hitting(target)) ans = true;
				// try all balls, if GameBall, check if hitting the target
		return ans;
	}
	
	public DroneArena(int i, int j) {
		sizeX = i; //max size of arena (length)
		sizeY = j; //max size of arena (height)
	}
	public void populateArena() {
		int max = 10; //adds drones based on "max" number
		for(int i = 0; i < max; i++) {
			addDrone();
		}
	}
	public boolean canMoveHere(double x, double y) {
		if(x>=sizeX||y>=sizeY|| x<0||y<0) { //if out of bounds, return false
			return false;
		}
		return getDroneAt(x,y) == null;
	}
	public void moveAllDrones() { 
		for(Drone d: droneArray) { //move all drones, read from drone array
			d.tryToMove(this);
			
		}
	}
	public int getXsize() {
		return sizeX;
	}
	public int getYsize() {
		return sizeY;
	}
	
	public Drone getDroneAt(double x, double y) { //gets drone at location
		for(Drone d : droneArray) {
			if (d.isHere(x, y)) { //if there is a drone at x,y then return d
				return d;
			}
		}
		return null;
	}
	public void addDrone() {
		randomGen = new Random();
		int valx = randomGen.nextInt(sizeX); //creates random xPos
		int valy = randomGen.nextInt(sizeY);
		float angle = randomGen.nextFloat() * 360;
		droneArray.add(new Drone(valx, valy, angle)); //adds drone to array
	}
	public String toString() {
		String Astring = "Arena size = " + sizeX + "*" + sizeY +"\n"; //prints arena size
		for(int i = 0; i < droneArray.size(); i++) 
			Astring += droneArray.get(i);//get drone(i)
		return Astring;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DroneArena a = new DroneArena(20, 10);	// create drone arena
		a.populateArena();//populates arena with drones
		System.out.println(a.toString());	// print where is

	}


}
