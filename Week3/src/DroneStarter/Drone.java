package DroneStarter;
import java.io.Serializable;

import java.util.Random;

public abstract class Drone implements Serializable{
	
	protected char type;
	protected Random randomGen;
	protected DroneArena Arena;
	protected double xpos;
	protected double ypos;
	protected double Wantxpos;
	protected double Wantypos;
	protected int droneID;
	static int droneCount;
	protected int rad = 10;
	protected char col = 'r';
	protected double angle;
	protected int speed = 2;
	protected int xSize = 400;//Arena.getXsize(); //fetches xSize
	protected int ySize = 500;//Arena.getYsize(); //fetches YSize

	public Drone(int i, int j, double a) {
		
		droneID = droneCount; //constructor
		droneCount ++;
		xpos = i;
		ypos = j;
		angle = a;
		type = 'z';
	}
	
	//abstract methods either changed or unused in subclasses
	protected abstract void checkDrone(DroneArena b);
	
	protected abstract void adjustDrones();

	protected abstract boolean doHitDrone(Drone d, DroneArena droneArena);

	
	public void drawDrone(MyCanvas mc) { //draws drone on canvas
		mc.showCircle(xpos, ypos, rad, type);
	}

	public String toString() { //display drone details on panel
		double roundedX = Math.round(xpos * 100)/100.0; //two decimal places
		double roundedY = Math.round(ypos * 100)/100.0; //two decimal places
		double roundedAngle = Math.round(angle * 100)/100.0; //two decimal places
		String res = "Drone " + droneID + " at x=" + (int)xpos + " y=" + (int)ypos + " angle " + (int)angle + " speed " + speed + " rad " + rad + "\n";
		return res; //string for output (drone info)
	}
	
	public int getDroneC(){
		return droneCount;  //gets drone count
	}
	public double getXpos() { //gets xPos
		return xpos;
	}
	public double getYpos() { //gets yPos
		return ypos;
	}
	public double getRad() { //gets radius
		return rad; 
		}
	public int getID() { //gets ID
		return droneID; 
		}

	/**
	 * <pre>
	 * Function for moving drones
	 * Includes calculating next position
	 * Checks if it can move
	 * If not, adjusts angle
	 * <pre>
	 * @param a 
	 */
	
	protected void tryToMove(DroneArena a) { //try to move function decides if an object can go to a location
		double dx = 0; 
		double dy = 0;
		
		double radAngle = Math.toRadians(angle);
		speed = GUI.sliderValue;
		dx = speed * Math.cos(radAngle); //dx = temp value for how much x should change
		dy = speed * Math.sin(radAngle); //dy = temp value for how much y should change
		
		Wantxpos = xpos + dx; //Wantxpos = where it wants to move (x)
		Wantypos = ypos + dy; //Wantypos = where it wants to move (y)
		
		if(a.canMoveHere(xpos + dx, ypos + dy, rad, droneID)) { //if canMoveHere returns true, then move drone to new position
			xpos += dx; //updates Xpos
			ypos += dy; //updates Ypos
		}
		else{
			Drone d = a.getDroneAt(Wantxpos, Wantypos, rad, droneID); 
			if(d == null) {
				angle = a.adjustAngle(this);
			}
			else {
				System.out.println("A.CheckDrone Called");
				doHitDrone(d, a);
			}
			
		}
	}
	
	public boolean hitting(double ox, double oy, double or) {
		double distanceD, r;
		distanceD = Math.sqrt(((ox-xpos)*(ox-xpos) + (oy-ypos)*(oy-ypos)));
		r = or+rad;
		System.out.println("r " + r + " distanceD " + distanceD + " ox " + ox + " oy " + oy);
		if (distanceD > r){
			return true;
		}
		else {
			return false;
		}
	}		
	
	public boolean hitting (Drone oDrone) {
		return hitting(oDrone.xpos, oDrone.ypos, oDrone.getRad());
	}
}



