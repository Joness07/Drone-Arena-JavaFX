/**
 * 
 */
package DroneStarter;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
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

/**
 * @author shsmchlr
 * Example with balls, paddles and targets in arena
 */
public class guiTest extends Application {
	private MyCanvas mc;
	private AnimationTimer timer;								// timer used for animation
	private VBox rtPane;										// vertical box for putting info
	private DroneArena arena;

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
		mExit.setOnAction(new EventHandler<ActionEvent>() {
		    public void handle(ActionEvent t) {					// action on exit is
	        	timer.stop();									// stop timer
		        System.exit(0);									// exit program
		    }
		});
		mFile.getItems().addAll(mExit);							// add exit to File menu
		
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
	    Button btnStart = new Button("Start");					// create button for starting
	    btnStart.setOnAction(new EventHandler<ActionEvent>() {	// now define event when it is pressed
	        @Override
	        public void handle(ActionEvent event) {
	        	timer.start();									// its action is to start the timer
	       }
	    });

	    Button btnStop = new Button("Pause");					// now button for stop
	    btnStop.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	timer.stop();									// and its action to stop the timer
	       }
	    });

	    Button btnAdd = new Button("Another Ball");				// now button for stop
	    btnAdd.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addDrone();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnEat = new Button("Eater Drone");				// now button for stop
	    btnEat.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addEater();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    Button btnObs = new Button("Add Obstacle");				// now button for stop
	    btnObs.setOnAction(new EventHandler<ActionEvent>() {
	        @Override
	        public void handle(ActionEvent event) {
	           	arena.addObstacle();								// and its action to stop the timer
	           	drawWorld();
	       }
	    });
	    														// now add these buttons + labels to a HBox
	    return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), btnAdd, btnEat,btnObs); //btnObs);
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
		// TODO Auto-generated method stub
		primaryStage.setTitle("RJMs attempt at Moving Ball");
	    BorderPane bp = new BorderPane();
	    bp.setPadding(new Insets(10, 20, 10, 20));

	    bp.setTop(setMenu());											// put menu at the top

	    Group root = new Group();										// create group with canvas
	    Canvas canvas = new Canvas( 400, 500 );
	    root.getChildren().add( canvas );
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
		            drawStatus();// redraw the world
	        }
	    };

	    rtPane = new VBox();											// set vBox on right to list items
		rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
		rtPane.setPadding(new Insets(5, 75, 75, 5));					// padding
 		bp.setRight(rtPane);											// add rtPane to borderpane right
		  
	    bp.setBottom(setButtons());										// set bottom pane with buttons

	    Scene scene = new Scene(bp, 700, 600);							// set overall scene
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

}