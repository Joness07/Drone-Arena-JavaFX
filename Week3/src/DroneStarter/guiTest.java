package DroneStarter;

import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * @author shsmchlr
 * Example with balls, paddles and targets in arena
 */
public class guiTest extends Application {
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private DroneArena arena;
	public static int sliderValue;
	JFileChooser chooser;
	FileFilter filter;
	/**
	 * function to show in a box ABout the programme
	 */
	private void showAbout() {
	    Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
	    alert.setTitle("About");									// say is About
	    alert.setHeaderText(null);
	    alert.setContentText("Drone Simulator");			// give text
	    alert.showAndWait();										// show box and wait for user to close
	}
	
//	void UpdateSpeed(MouseEvent event) {
//		sliderValue = (int) speedSlider.getValue();
//	}

	 /**
	  * set up the mouse event - when mouse pressed, put ball there
	  * @param canvas
	  */
	void setMouseEvents (Canvas canvas) {
	       canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED event
	    	       new EventHandler<MouseEvent>() {
	    	           @Override
	    	           public void handle(MouseEvent e) {
	    	        	   	//arena.setPaddle(e.getX(), e.getY());
	  		            	drawWorld();							// redraw world
	  		            	drawStatus();
	    	           }
	    	       });
	}
	/**
	 * set up the menu of commands for the GUI
	 * @return the menu bar
	 */
	MenuBar setMenu() {
		MenuBar menuBar = new MenuBar();						// create main menu
	
		Menu mFile = new Menu("File");							// add File main menu
		MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
		MenuItem mSave = new MenuItem("Save");
		MenuItem mLoad = new MenuItem("Load");
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mLoad.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is							
		    	loadArena();
	            drawWorld();
	            drawStatus();
		    }
		});
		mSave.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is							
		    	saveArena();
	            drawWorld();
	            drawStatus();
		    }
		});
		mFile.getItems().addAll(mExit, mSave, mLoad);							// add exit to File menu
		Menu mHelp = new Menu("Help");							// create Help menu
		MenuItem mAbout = new MenuItem("About");				// add About sub men item
		mAbout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
            	showAbout();									// and its action to print about
            }	
		});
		mHelp.getItems().addAll(mAbout);						// add About to Help main item
		
		menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
		return menuBar;											// return the menu
	}

	/**
	 * set up the horizontal box for the bottom with relevant buttons
	 * @return
	 */
	private HBox setButtons() {
		
	
		String bStyle = "-fx-font-family: Comic Sans; -fx-font-size: 14px; -fx-font-weight: bold;";
		
	    Button btnStart = new Button("Start");
	    btnStart.setStyle(bStyle);
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setStyle(bStyle);
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    Button btnAdd = new Button("Another Ball");				// now button for stop
	    btnAdd.setStyle(bStyle);
	    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addDrone();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnEat = new Button("Eater Drone");				// now button for stop
	    btnEat.setStyle(bStyle);
	    btnEat.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addEater();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnHun = new Button("Hunter Drone");				// now button for stop
	    btnHun.setStyle(bStyle);
	    btnHun.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addHunter();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnObs = new Button("Add Obstacle");				// now button for stop
	    btnObs.setStyle(bStyle);
	    btnObs.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addObstacle();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    
		Slider slider = new Slider(0, 10, 2);
		slider.setPrefWidth(200);
		slider.setShowTickMarks(true);
		slider.setShowTickLabels(true);
		slider.setMajorTickUnit(1);
		slider.setMinorTickCount(0);
		slider.setBlockIncrement(1);
		slider.setSnapToTicks(true);
		
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				sliderValue = (int)slider.getValue();


			}
		});
		
		Label run = new Label("Run: ");
		run.setStyle(bStyle);
		Label add = new Label("Add: ");
		add.setStyle(bStyle);
		Label speedLabel = new Label("Speed");
		speedLabel.setStyle(bStyle);
		
		
	    return new HBox(run, btnStart, btnStop, add, btnAdd, btnEat,btnObs,btnHun, slider, speedLabel);// now add these buttons + labels to a HBox
	}

	/**
	 * Show the score .. by writing it at position x,y
	 * @param x
	 * @param y
	 * @param score
	 */
	public void showScore (double x, double y, int score) {
		mc.showText(x, y, Integer.toString(score));
	}
	/** 
	 * draw the world with ball in it
	 */
	public void drawWorld () {	
	 	mc.clearCanvas();						// set beige colour
	 	arena.drawArena(mc);
	}
	
	/**
	 * show where ball is, in pane on right
	 */
	public void drawStatus() {
		rtPane.getChildren().clear();					// clear rtpane
		//ArrayList<String> allBs = arena.toString();
		rtPane.getChildren().add(new Label(arena.toString()));	// add label
		/*for (String s : allBs) {
			Label l = new Label(s); 		// turn description into a label
			rtPane.getChildren().add(l);	// add label	
		}*/
	}


	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		
	   	 chooser = new JFileChooser("C:/Users/SamJo/Desktop/DroneIO");
	   	 filter = new FileFilter() {

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
				
		// TODO Auto-generated method stub
		primaryStage.setTitle("Space Simulator");
		primaryStage.getIcons().add(new Image("C:/Users/SamJo/Desktop/DroneSimulator/Icon.png"));
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( 400, 500 );
	    Image bg = new Image("C:/Users/SamJo/Desktop/DroneSimulator/background.jpg");
	    ImageView mv = new ImageView(bg);
	    root.getChildren().addAll( mv, canvas );
	    bp.setLeft(root);												// load canvas to left area
	
	    mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

	    setMouseEvents(canvas);											// set up mouse events

	    arena = new DroneArena(400, 500);								// set up arena
	    drawWorld();
	    
	    timer = new AnimationTimer() {									// set up timer
	        public void handle(long currentNanoTime) {					// and its action when on
	        		arena.moveAllDrones();
	        		arena.deleteDrones();// check the angle of all balls								// move all balls
		            drawWorld();
		            drawStatus();// redraw the world;
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    
	    Scene scene = new Scene(bp, 1000, 900);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
	  

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    Application.launch(args);			// launch the GUI

	}
	
	public void loadArena() {
	   	 int returnVal;
			returnVal = chooser.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) { //stores user chosen file
				File selFile = chooser.getSelectedFile();
				if(!selFile.getAbsolutePath().endsWith(".arena")) { //if file is not arena file, display error
					System.out.println("File not .Arena");
					return;
				}
				try {
					FileInputStream fis = new FileInputStream(selFile); //creates input stream to read file
					try {
						ObjectInputStream ois = new ObjectInputStream(fis); //reads the file input stream as object
						try {
							arena = (DroneArena)ois.readObject(); //reading the object stream and storing as droneArena
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
		}
	public void saveArena() {
	   	 int returnVal;
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
						oos.writeObject(arena); //writes arena into file
						oos.close();//close files
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
}