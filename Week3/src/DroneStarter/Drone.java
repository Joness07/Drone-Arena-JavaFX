package DroneStarter;
//testing pull
import java.io.Serializable;

import java.util.Random;

public class Drone implements Serializable{
	private Random randomGen;
	private DroneArena Arena;
	private int xpos;
	private int ypos;
	private int droneID;
	private static int droneCount;
	private Direction dir;
	private int rad = 8;
	private char col = 'r';
	private double angle;
	private float speed = 2;
	private int xSize = 400;//Arena.getXsize(); //fetches xSize
	private int ySize = 500;//Arena.getYsize(); //fetches YSize

	public Drone(int i, int j, double a) {
		// TODO Auto-generated constructor stub
		droneID = droneCount; //constructor
		droneCount ++;
		xpos = i;
		ypos = j;
		angle = a;
		//this.dir = n;
	}
	public void displayDrone(ConsoleCanvas c) {
		c.showIt(xpos, ypos, 'D'); //puts D in s
	}
	public void tryToMove(DroneArena a){
		double dx = 0;
		double dy = 0;
		double radAngle = angle*Math.PI/180;
		dx = speed * Math.cos(radAngle);
		dy = speed * Math.sin(radAngle);	
		boolean canMove = false; //boolean for canMove
		for(Drone d: a.droneArray) { //checks for each position for each drone.
			if(d.isHere(xpos + dx, ypos + dy)) { //passes in the new location 
				canMove = false; //false means cannot move here
			}
			else if(a.canMoveHere(xpos + dx, ypos + dy)) {
				canMove = true; //true set means its free to move
			}
		}
		if(canMove) { //if true, move the drone.
			adjustBall();
			
		}
		else {
			angle = checkBall(xSize,ySize);
			
		}
	}
	public void adjustBall() {
		double radAngle = angle*Math.PI/180;	// put angle in radians
		xpos += speed * Math.cos(radAngle);		// new X position
		ypos += speed * Math.sin(radAngle);		// new Y position
	}
	public double checkBall(int xSize, int ySize) {
		randomGen = new Random();
		double ans = angle;
		double randomBounce = randomGen.nextFloat() * 20 - 10;
		if (xpos < rad || xpos > xSize - rad) {
			ans = 180 - ans;
		}
			// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		else if (ypos < rad || ypos > ySize - rad) {
			ans = -ans;
		}
		if(ans > 360) {
			ans = ans - 360; //keeps angle between 0-360
		}
		if(ans < 0) {
			ans = ans + 360;
		}
		return ans;
			// if ball hit (tried to go through) top or bottom walls, set mirror angle, being -angle
	}
	
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(xpos, ypos, rad, col);
	}
	public String toString() {
		double rounded = Math.round(angle * 100)/100.0; //two decimal places
		String res = "Drone " + droneID + " at x=" +xpos + " y=" + ypos + " angle " + rounded + " speed " + speed + " rad " + rad + "\n";
		return res; //string for output (drone info)
	}
	public int getDroneC(){
		return droneCount;  //gets drone count
	}
	public int getXpos() { //gets xPos of drone
		return xpos;
	}
	public int getYpos() { //gets yPos of drone
		return ypos;
	}
	public double getRad() { 
		return rad; //gets ball radius
		}
	public int getID() {
		return droneID; 
		}
	public boolean isHere(double sx, double sy) {
		if((sx == this.xpos)&&(sy == this.ypos)) { //can't create drone
			return true;
		}
		return false;
	}
	/**
	 * draw the ball into the interface i
	 * @param i
	 */
	//protected abstract void checkBall(droneArena b);
	/**
	 * abstract method for adjusting a ball (?moving it)
	 */
	//protected abstract void adjustBall();	
	/**
	 * is ball at ox,oy size or hitting this ball
	 * @param ox
	 * @param oy
	 * @param or
	 * @return true if hitting
	 */
	public boolean hitting(double ox, double oy, double or) {
		return (ox-xpos)*(ox-xpos) + (oy-ypos)*(oy-ypos) < (or+rad)*(or+rad);
	}		// hitting if dist between ball and ox,oy < ist rad + or
	
	public boolean hitting (Drone oDrone) {
		return hitting(oDrone.getXpos(), oDrone.getYpos(), oDrone.getRad());
	}
}
	/** is ball hitting the other ball
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Drone d = new Drone(5,3);//creates drone at 5,3
		Drone b = new Drone(4,7);//creates drone at 4,7
		System.out.println(d.toString()); //prints drone location of d
		System.out.println(b.toString());//prints drone location of b*/


