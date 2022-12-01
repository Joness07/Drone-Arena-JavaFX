package DroneStarter;

import java.util.Random;

public class EaterDrone {
	private Random randomGen;
	private int xpos;
	private int ypos;
	private int eaterID;
	private static int eaterCount;
	private int rad = 15;
	private char col = 'b';
	private double angle;
	private int dxInt = 0;
	private int dyInt = 0;
	private int speed = 2;
	private int xSize = 400;
	private int ySize = 500;
	

	public EaterDrone(int i, int j, double a ) {
		eaterID = eaterCount; //constructor
		eaterCount ++;
		xpos = i;
		ypos = j;
		angle = a;
	}
	
	protected double checkBall(DroneArena b) {
		return angle = b.CheckBallAngle(xpos, ypos, rad, angle, eaterID);
	}
	
	public void tryToMove(DroneArena a){
		double radAngle = angle*Math.PI/180;
		double dx = speed * Math.cos(radAngle);
		double dy = speed * Math.sin(radAngle);
		
		if(dx< 1) { //rounds to nearest one.
			dx += 1;
		}
		if(dy< 1) {
			dy += 1;
		}
		dxInt = (int)dx;
		dyInt = (int)dy;
		double nX = xpos + dxInt;
		double nY = ypos + dyInt;
		
		boolean canMove = false; //boolean for canMove
		boolean eatDrone = false;
		for(EaterDrone e: a.eaterArray) {
			if(a.canMoveHere(xpos + dx, ypos + dy, rad)) {
				canMove = true; //true set means its free to move
			}
		}
		if(canMove) {
			adjustEater();
		}
		else {
			angle = adjustAngle(xSize, ySize, nX, nY);
		}
	}
	public void adjustEater() {
		double radAngle = angle*Math.PI/180;	// put angle in radians
		xpos += speed * Math.cos(radAngle);		// new X position
		ypos += speed * Math.sin(radAngle);		// new Y position
	}
	public double adjustAngle(int xSize, int ySize, double nX, double nY) {
		double ans = angle;
		randomGen = new Random();
		if(ypos <= rad && nX<= rad || nY <= rad && nX >= xSize - rad || nY >= ySize - rad && nX<= rad || nY >= ySize - rad && nX >= xSize - rad) {
			//CORNER HIT
			double randAngle = randomGen.nextFloat() * 90;
			if(angle>=180) { //if angle over 180
				ans = ans - 180 + randAngle;
			}
			else { //if angle is less than 180.
				ans = ans + 180;
			}
		}
		if (nX <= rad|| nX >= xSize - rad) {
			ans = 180 - ans;
		}
			// if ball hit (tried to go through) left or right walls, set mirror angle, being 180-angle
		else if (nY <= rad|| nY >= ySize - rad) {
			ans = 0 - ans;
		}
		else {
			System.out.println(eaterID + " Option 4");
		}
		while(ans<0) {
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
		String res = "Drone " + eaterID + " at x=" +xpos + " y=" + ypos + " angle " + rounded + " speed " + speed + " rad " + rad + "\n" + "dx: " + dxInt + " dx: " + dyInt ;
		return res; //string for output (drone info)
	}
	public int getDroneC(){
		return eaterCount;  //gets drone count
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
		return eaterID; 
		}
	public boolean isHere(double sx, double sy) {
		if((sx == this.xpos)&&(sy == this.ypos)) { //can't create drone
			return true;
		}
		return false;

	}
	public boolean hittingEat(double ox, double oy, double or) {
		return (ox-xpos)*(ox-xpos) + (oy-ypos)*(oy-ypos) < (or+rad)*(or+rad);
	}
}
