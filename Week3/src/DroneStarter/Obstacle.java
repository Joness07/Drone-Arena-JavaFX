package DroneStarter;

public class Obstacle extends Drone {

	public Obstacle(int i, int j) {
		super(i, j, 0);
		type = 'o';
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
