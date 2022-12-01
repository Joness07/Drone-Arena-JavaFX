package DroneStarter;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class DroneInterface {

	private ConsoleCanvas myCanvas; //constructor variables
	private Scanner s;// scanner used for input from user
	private DroneArena myArena;		
	int aX;
	int aY;// arena in which drones are shown
	    /**A
	    	 * constructor for DroneInterface
	    	 * sets up scanner used for input and the arena
	    	 * then has main loop allowing user to enter commands
	     */
	    public DroneInterface() {
	    	 s = new Scanner(System.in);// set up scanner for user input
	    	 myArena = new DroneArena(20, 10);	// create arena of size 20*10 (Default Arena, can be replaced)
		    	 JFileChooser chooser = new JFileChooser("C:/Users/SamJo/Desktop/DroneIO");
		    	 FileFilter filter = new FileFilter() {

					@Override
					public boolean accept(File f) {
						if(f.getAbsolutePath().endsWith(".arena")) {
							return true;
						}
						if(f.isDirectory()) {
							return true;
						}
						return false;
					}
					public String getDescription() {
						return "arena";
					}};
					chooser.setFileFilter(filter);
		    	 int returnVal;
	        char ch = ' ';
	        do {
	        	System.out.print("(A)dd drone\n(B)uild arena\n(I)nformation\n(D)isplay Drones\n(M)ove Drones,\n(X)sequence\n(S)ave file\n(L)oad file\n(E)xit\n");
	        	ch = s.next().charAt(0);
	        	s.nextLine();
	        	switch (ch) {
	    				case 'A' :
	    				case 'a' :
	    					myArena.addDrone();	// add a new drone to arena
    						break;
	        		case 'I' :
	        		case 'i' :
    						System.out.print(myArena.toString());//prints drone info
							break;
	        		case 'e' : 	ch = 'E';				// when X detected program ends
	        							break;
	        		case 'D':
	        		case 'd':
    	        			doDisplay(); //displays arena and drones
							break;
	        		case 'M':
	        		case 'm':
    						myArena.moveAllDrones(); //moves all drones
							break;
	        		case 'X':
	        		case 'x':
    						Animation(); //moves all drones 10 times
    						break;
	        		case 'B':
	        		case 'b':
	        			System.out.print("Build Arena Option: Please enter X value"); //x input
	        			aX= s.nextInt();
	        			System.out.print("Build Arena Option: Please enter Y value"); //y input
	        			aY = s.nextInt();
	        			myArena = new DroneArena(aX,aY); //asks user for new arena width and height
	        			break;
	        		case 'S': //saving file (write)
	        		case 's':
	        			returnVal = chooser.showSaveDialog(null); //opens file directory window
	        			if (returnVal == JFileChooser.APPROVE_OPTION) {
	        				File selFile = chooser.getSelectedFile();
	        				if(!selFile.getAbsolutePath().endsWith(".arena")) {
	        					selFile = new File(selFile.getAbsolutePath()+".arena");
	        				}
	        				try {
								FileOutputStream fos = new FileOutputStream(selFile); 
								try {
									ObjectOutputStream oos = new ObjectOutputStream(fos);
									oos.writeObject(myArena); //writes arena into file
									oos.close();//close files
									fos.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
	        			}
	        			break;
	        		case 'L':
	        		case 'l': //loading file (read)
	        			returnVal = chooser.showOpenDialog(null);
	        			if (returnVal == JFileChooser.APPROVE_OPTION) { //stores user chosen file
	        				File selFile = chooser.getSelectedFile();
	        				if(!selFile.getAbsolutePath().endsWith(".arena")) { //if file is not arena file, display error
	        					System.out.println("File not .Arena");
	        					break;
	        				}
	        				try {
								FileInputStream fis = new FileInputStream(selFile); //creates input stream to read file
								try {
									ObjectInputStream ois = new ObjectInputStream(fis); //reads the file input stream as object
									try {
										myArena = (DroneArena)ois.readObject(); //reading the object stream and storing as droneArena
										ois.close();//close files
										fis.close();
									} catch (ClassNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	        				
	        			}

	        			break;
	        	}
	    		} while (ch != 'E');// test if end
	        
	       s.close(); // close scanner
	    }
	    
	    void doDisplay() {
	    	myCanvas = new ConsoleCanvas(aX + 2,aY + 2); //displays canvas (two more for borders)
	    	myArena.showDrones(myCanvas); //show drones function called inside canvas
	    	System.out.println(myCanvas.toString()); //prints canvas
	    }
		 void Animation() {
			for(int i = 0;i < 10; i++) { //moves drones 10 times, displaying after each turn
				System.out.print(myArena.toString()); //information
				doDisplay(); //Displays new
				myArena.moveAllDrones(); //moves drones
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		public static void main(String[] args) {
			DroneInterface r = new DroneInterface();	// just call the interface
		}

	}
