package DroneStarter;

import java.io.Serializable;

public class Drone implements Serializable{
	private int xpos;
	private int ypos;
	private int droneID;
	private static int droneCount;
	private Direction dir;
	private int rad = 8;
	private char col = 'r';

	public Drone(int i, int j, Direction n) {
		// TODO Auto-generated constructor stub
		droneID = droneCount; //constructor
		droneCount ++;
		xpos = i;
		ypos = j;
		this.dir = n;
	}
	public void displayDrone(ConsoleCanvas c) {
		c.showIt(xpos, ypos, 'D'); //puts D in s
	}
	public void tryToMove(DroneArena a){
		int dx = 0;
		int dy = 0;
		switch(dir) {
		case North: //north negative y axis
			dy = -1;
			break;
		case East: //east positive x axis
			dx = 1;
			break;
		case South: //south positive y axis
			dy = 1;
			break;
		case West: //west negative x axis
			dx = -1;
			break;
		}
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
			xpos += dx;
			ypos += dy;
		}
		else {
			dir = Direction.changeDirection(dir); //if cannot move(border or occupied), change the drones direction
		}
	}
	
	public void drawDrone(MyCanvas mc) {
		mc.showCircle(xpos, ypos, rad, col);
	}
	public String toString() {
		String res = "Drone " + droneID + " at x=" +xpos + " y=" + ypos + " direction " + dir.toString() + "\n" ;
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
	public boolean isHere(int sx, int sy) {
		if((sx == this.xpos)&&(sy == this.ypos)) { //can't create drone
			return true;
		}
		return false;
	}
	protected abstract void checkBall(droneArena b);
	/**
	 * abstract method for adjusting a ball (?moving it)
	 */
	protected abstract void adjustBall();	
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
	
	/** is ball hitting the other ball
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*Drone d = new Drone(5,3);//creates drone at 5,3
		Drone b = new Drone(4,7);//creates drone at 4,7
		System.out.println(d.toString()); //prints drone location of d
		System.out.println(b.toString());//prints drone location of b*/
	}
}
