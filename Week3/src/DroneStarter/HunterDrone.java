package DroneStarter;

public class HunterDrone extends Drone {
	
	private static int hunterCount;
	private char col = 'b';

	public HunterDrone(int i, int j, double a) {
		super(i, j, a);
		
		hunterCount ++;
	}

}
