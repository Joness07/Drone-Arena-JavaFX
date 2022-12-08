package DroneStarter;

import java.util.Random;

public class Teleporter extends Drone {

	public Teleporter(int i, int j) {
		super(i, j, 0);
		type = 't';
		rad = 15;
		randomGen = new Random();
	}

	@Override
	protected void checkDrone(DroneArena b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustDrones() {
		// TODO Auto-generated method stub
		
	}

	private int timer = 0;
	@Override
	protected void tryToMove(DroneArena a) {
		if(timer == 60*10) {
			double angle = randomGen.nextFloat() * 360;
			double vx = xpos + (rad+25)*Math.cos(angle); //creates position outside radius
			double vy = ypos + (rad+25)*Math.sin(angle);
			a.addPrey((int)vx,(int)vy); //adds drone 
			timer = 0;
		}
		else timer++;
	}

	@Override
	protected boolean doHitDrone(Drone d, DroneArena droneArena) {
		return true;
		// TODO Auto-generated method stub
		
	}
}