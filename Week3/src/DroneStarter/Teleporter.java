package DroneStarter;

public class Teleporter extends Drone {

	public Teleporter(int i, int j) {
		super(i, j, 0);
		type = 't';
		rad = 15;
	}

	@Override
	protected void checkDrone(DroneArena b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void adjustDrones() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void tryToMove(DroneArena a) {
		// TODO Auto-generated method stub
	 
	}

	@Override
	protected boolean doHitDrone(Drone d, DroneArena droneArena) {
		return true;
		// TODO Auto-generated method stub
		
	}
}