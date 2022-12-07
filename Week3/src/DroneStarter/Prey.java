package DroneStarter;

import javafx.scene.image.Image;

public class Prey extends Drone {
	
	private Image preyImage;
	double nX;
	double nY;

	public Prey(int i, int j, double a) {
		super(i, j, a);
		
		type = 'p';
		rad = 20;
		
	}
	
	@Override
	protected void checkDrone(DroneArena b) {
		angle = b.CheckBallAngle(nX, nY, rad, angle, droneID);
	}

	@Override
	protected void adjustDrones() {
		double radAngle = angle*Math.PI/180;	// put angle in radians
		xpos += speed * Math.cos(radAngle);		// new X position
		ypos += speed * Math.sin(radAngle);		// new Y position
	}



	@Override
	public boolean doHitDrone(Drone d, DroneArena droneArena) {
		System.out.println("Doing Angle: " + angle);
		angle = Math.toDegrees(Math.atan2(Wantypos - d.ypos, Wantxpos - d.xpos)); //gets angle between drones
		return true;
	}
}


