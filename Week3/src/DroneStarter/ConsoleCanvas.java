package DroneStarter;

public class ConsoleCanvas {
	
	private char[][] droneCanvas;
	
	public ConsoleCanvas(int j, int i) {
		droneCanvas = new char [i][j]; //creates two dimensional array for canvas
	}
	public void showIt(double xpos, double ypos, char k) {
		droneCanvas[(int) (ypos+1)][(int) (xpos+1)] = k;
	}
	public String toString() {
		String Cstring = ""; 
		int length = droneCanvas.length;
		int width = droneCanvas[0].length;
		for(int i = 0; i < length; i++) {
			for(int j = 0; j < width; j++) {
				if(droneCanvas[i][j]!='D') { //if drone canvas is not D, set to a space
					droneCanvas[i][j] = ' '; //sets space
				}
				if(i == 0||i == length-1||j == 0||j == width-1) { //if co-ord is a border, add a #
					droneCanvas[i][j]='#';
				}
				Cstring += droneCanvas[i][j]; //adds to string
			}
			Cstring += "\n";
		}
		return Cstring;
	}

	public static void main(String[] args) {
		ConsoleCanvas c = new ConsoleCanvas(10,5); //creates canvas
		c.showIt(4,3,'D'); //adds drone at 4,3
		System.out.println(c.toString()); //display result
	}
}
