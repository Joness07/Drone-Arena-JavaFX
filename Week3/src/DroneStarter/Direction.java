package DroneStarter;

import java.util.Random;

public enum Direction { //all possible values of direction
	North,
	East,
	South,
	West;
	
	String nextDirection;
	
	private static final Random PRNG = new Random();
	
	public static Direction randomDirection() {
		Direction[]directions = values();
		return directions[PRNG.nextInt(directions.length)];
	}
	
	public static Direction changeDirection(Direction x) {
		if(x == Direction.North) { //returns next direction
			return Direction.East;
		}
		else if(x == Direction.East) {
			return Direction.South;
		}
		else if(x == Direction.South) {
			return Direction.West;
		}
		else if(x == Direction.West) {
			return Direction.North;
		}
		return null;
	}

}
