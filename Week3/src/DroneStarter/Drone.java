package DroneStarter;
//testing pull
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
		// TODO Auto-generated constructor stub
		droneID = droneCount; //constructor
		droneCount ++;
		xpos = i;
		ypos = j;
		angle = a;
		type = 'z';
		//this.dir = n;
	}
	
	protected abstract void checkDrone(DroneArena b);
	
	protected abstract void adjustDrones();

	
	protected abstract boolean doHitDrone(Drone d, DroneArena droneArena);

	
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(xpos, ypos, rad, type);
	}
	
	public void displayDrone(ConsoleCanvas c) {
		c.showIt(xpos, ypos, 'D'); //puts D in s
	}

	public String toString() {
		double roundedX = Math.round(xpos * 100)/100.0; //two decimal places
		double roundedY = Math.round(ypos * 100)/100.0; //two decimal places
		double roundedAngle = Math.round(angle * 100)/100.0; //two decimal places
		String res = "Drone " + droneID + " at x=" + (int)xpos + " y=" + (int)ypos + " angle " + (int)angle + " speed " + speed + " rad " + rad + "\n";
		return res; //string for output (drone info)
	}
	public int getDroneC(){
		return droneCount;  //gets drone count
	}
	public double getXpos() { //gets xPos of drone
		return xpos;
	}
	public double getYpos() { //gets yPos of drone
		return ypos;
	}
	public double getRad() { 
		return rad; //gets ball radius
		}
	public int getID() {
		return droneID; 
		}

	protected void tryToMove(DroneArena a) {
		double dx = 0;
		double dy = 0;
		
		double radAngle = Math.toRadians(angle);
		speed = GUI.sliderValue;
		dx = speed * Math.cos(radAngle);
		dy = speed * Math.sin(radAngle);
		
		Wantxpos = xpos + dx;
		Wantypos = ypos + dy;
		
		if(a.canMoveHere(xpos + dx, ypos + dy, rad, droneID)) {
			xpos += dx;
			ypos += dy;
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



