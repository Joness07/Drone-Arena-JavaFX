package DroneStarter;

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
		else {
			angle = Math.toDegrees(Math.atan2(Wantypos - d.ypos, Wantxpos - d.xpos));
			//if drone is not prey but collides with hunter, bounce off
		}
		return true;
	}
}

