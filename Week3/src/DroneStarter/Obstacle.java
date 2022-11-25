package DroneStarter;

public class Obstacle {
	private int xpos;
	private int ypos;
	private int obstacleID;
	private static int obstacleCount;
	private int rad = 10;
	private char col = 'g';

	public Obstacle(int i, int j){
		obstacleID = obstacleCount;
		obstacleCount ++;
		xpos = i;
		ypos = j;
	}
	public void drawObstacle(MyCanvas mc) {
		mc.showCircle(xpos, ypos, rad, col);
	}
	public int getObstacleC(){
		return obstacleCount;  //gets drone count
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
		return obstacleID; 
		}
	public boolean hittingObs(double ox, double oy, double or) {
		return (ox-xpos)*(ox-xpos) + (oy-ypos)*(oy-ypos) < (or+rad)*(or+rad);
	}	
	
}
