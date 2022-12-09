package DroneStarter;

import java.util.Random;

public class Hunter extends Drone{

	public Hunter(int i, int j, double a) {
		super(i, j, a);
		
		type = 'h'; //h char for hunter
		rad = 20; //radius
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void checkDrone(DroneArena b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustDrones() {
		// TODO Auto-generated method stub
		
	}

	protected boolean doHitDrone(Drone d, DroneArena droneArena) {
		if(d instanceof Prey) { //if drone is prey, add to remove
			droneArena.toRemove.add(d); //adds prey to be removed
			return false;
		}
		if(d instanceof Teleporter) { //if the drone hits teleporter then the teleporter gets assigned new xpos & ypos
			randomGen = new Random();
			int valx;
			int valy;
			int counter = 0;
			valx = randomGen.nextInt(droneArena.getXsize()); //creates random xPos
			valy = randomGen.nextInt(droneArena.getYsize());
			if(droneArena.canMoveHere(valx, valy, 10, -1) && counter < 100) { //attempts 100 times, if cannot create then doesn't. Used if arena is full.
				d.xpos = valx;
				d.ypos = valy;
			}
			else {
				counter++; 
				
			}
		}
		else {
			angle = Math.toDegrees(Math.atan2(Wantypos - d.ypos, Wantxpos - d.xpos));
			//if drone is not prey but collides with hunter, bounce off
		}
		return true;
	}
}

