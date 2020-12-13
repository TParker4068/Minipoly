package minipoly;

import static java.lang.Integer.parseInt;
import java.util.Observable;
import java.util.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MView extends Application implements Observer {   
    /*private StackPane goPane;
    private StackPane a1Pane;
    private StackPane blank1;
    private StackPane a2Pane;
    private StackPane a3Pane;
    private StackPane blank2;
    private StackPane b1Pane;
    private StackPane blank3;
    private StackPane b2Pane;
    private StackPane b3Pane;
    private StackPane blank4;
    private StackPane c1Pane;
    private StackPane blank5;
    private StackPane c2Pane;
    private StackPane c3Pane;
    private StackPane blank6; 
    private StackPane d1Pane;
    private StackPane blank7;
    private StackPane d2Pane;
    private StackPane d3Pane;
    private StackPane blank8; 
    private StackPane e1Pane;
    private StackPane blank9;
    private StackPane e2Pane;
    private StackPane e3Pane;
    private StackPane blank10;
    private StackPane f1Pane;
    private StackPane blank11;
    private StackPane f2Pane;
    private StackPane f3Pane;
    private StackPane blank12;
    private StackPane g1Pane;
    private StackPane blank13;
    private StackPane g2Pane;
    private StackPane g3Pane;
    private StackPane blank14;
    private StackPane h1Pane;
    private StackPane blank15;
    private StackPane h2Pane;
    private StackPane h3Pane;*/
    private StackPane[] tiles;
    
    private final Button rollButton = new Button("Roll");
    private final Button endTurnButton = new Button("End Turn");
    private final Button purchPropButton = new Button("Purchase Property");
    private final Button purchHouseButton = new Button("Purchace House");
    private final Button purchHotelButton = new Button("Purchase Hotel");
    private final Button cheatButton = new Button("Cheat Roll");
    private final Button cheatSubmit = new Button("Submit");
    
    private final Label cheatLabel = new Label("Enter your dice roll here:");
    private final TextField cheatField = new TextField();
    private HBox cheatBox = new HBox(3);
    
    private Text player1Info;
    private Text player2Info;
    private Text gameInfo;
    private Circle token1;
    private Circle token2;
    
    private MModel model;
    private MController controller;
    
    private static final int TILE_WIDTH = 60;
    private static final int TILE_HEIGHT = 40;
    private static final int WINDOW_WIDTH = 1280;
    private static final int WINDOW_HEIGHT = 720;
    
    private StackPane makeTile(String s) {
        //creates white rectangle with a black border
        Rectangle rect = new Rectangle(TILE_WIDTH, TILE_HEIGHT);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        //stacks tiles name on top as a label
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rect, new Label(s));
        return stackPane;
    }
    
    public GridPane makePane() {
        //game board will be in a border pane
        BorderPane borderPane = new BorderPane(); 
        borderPane.setMaxSize(800, 800);
        //player tokens red is player 1, blue player 2
        token1 = new Circle(8);
        token1.setFill(Color.RED);
        token2 = new Circle(8);
        token2.setFill(Color.BLUE);
        //4 sides of the border pane
        HBox hBoxBottom = new HBox(11);
        VBox vBoxLeft = new VBox(9);
        HBox hBoxTop = new HBox(11);
        VBox vBoxRight = new VBox(9);
        //making all the tiles and adding them to their side of the board
        StackPane goPane = makeTile("GO");
        goPane.getChildren().add(token1);//adding player tokens to the start position
        goPane.getChildren().add(token2);
        StackPane a1Pane = makeTile("A1\n£50");
        StackPane blank1 = makeTile(" ");
        StackPane a2Pane = makeTile("A2\n£50");
        StackPane a3Pane = makeTile("A3\n£70");
        StackPane blank2 = makeTile(" ");
        StackPane b1Pane = makeTile("B1\n£100");
        StackPane blank3 = makeTile(" ");
        StackPane b2Pane = makeTile("B2\n£100");
        StackPane b3Pane = makeTile("B2\n£120");
        StackPane blank4 = makeTile("100"); 
        hBoxBottom.getChildren().addAll(blank4, b3Pane, b2Pane, blank3, b1Pane, blank2, a3Pane, a2Pane, blank1, a1Pane, goPane);
        
        StackPane c1Pane = makeTile("C1\n£150");
        StackPane blank5 = makeTile(" ");
        StackPane c2Pane = makeTile("C2\n£150");
        StackPane c3Pane = makeTile("C3\n£170");
        StackPane blank6 = makeTile(" "); 
        StackPane d1Pane = makeTile("D1\n£200");
        StackPane blank7 = makeTile(" ");
        StackPane d2Pane = makeTile("D2\n£200");
        StackPane d3Pane = makeTile("D3\n£220");
        vBoxLeft.getChildren().addAll(d3Pane, d2Pane, blank7, d1Pane, blank6, c3Pane, c2Pane, blank5, c1Pane);
        
        StackPane blank8 = makeTile(" "); 
        StackPane e1Pane = makeTile("E1\n£250");
        StackPane blank9 = makeTile(" ");
        StackPane e2Pane = makeTile("E2\n£250");
        StackPane e3Pane = makeTile("E3\n£270");
        StackPane blank10 = makeTile(" ");
        StackPane f1Pane = makeTile("F1\n£300");
        StackPane blank11 = makeTile(" ");
        StackPane f2Pane = makeTile("F2\n£300");
        StackPane f3Pane = makeTile("F3\n£320");
        StackPane blank12 = makeTile(" ");
        hBoxTop.getChildren().addAll(blank8, e1Pane, blank9, e2Pane, e3Pane, blank10, f1Pane, blank11, f2Pane, f3Pane, blank12);        
                
        StackPane g1Pane = makeTile("G1\n£350");
        StackPane blank13 = makeTile(" ");
        StackPane g2Pane = makeTile("G2\n£350");
        StackPane g3Pane = makeTile("G3\n£370");
        StackPane blank14 = makeTile(" ");
        StackPane h1Pane = makeTile("H1\n£400");
        StackPane blank15 = makeTile(" ");
        StackPane h2Pane = makeTile("H2\n£400");
        StackPane h3Pane = makeTile("H3\n£420");        
        
        vBoxRight.getChildren().addAll(g1Pane, blank13, g2Pane, g3Pane, blank14, h1Pane, blank15, h2Pane, h3Pane); 
        
        //text for player turn/describing the last move
        gameInfo = new Text("");
        gameInfo.setTextAlignment(TextAlignment.CENTER);
        
        borderPane.setBottom(hBoxBottom);
        borderPane.setLeft(vBoxLeft);
        borderPane.setTop(hBoxTop);
        borderPane.setRight(vBoxRight);
        borderPane.setCenter(gameInfo);
        
        //array to allow finding of specific tile for moving player tokens
        tiles = new StackPane[]{goPane, a1Pane, blank1, a2Pane, a3Pane, blank2, b1Pane, blank3, b2Pane, b3Pane, blank4, c1Pane, blank5, c2Pane, c3Pane, blank6, d1Pane, blank7, d2Pane, d3Pane, blank8, e1Pane, blank9, e2Pane, e3Pane, blank10, f1Pane, blank11, f2Pane, f3Pane, blank12, g1Pane, blank13, g2Pane, g3Pane, blank14, h1Pane, blank15, h2Pane, h3Pane};
        
        //grid pane to game board and buttons to
        GridPane gridPane = new GridPane();
        gridPane.add(borderPane, 1, 0);
        
        gridPane.add(rollButton, 0, 2);
        gridPane.add(purchPropButton, 0, 3);
        gridPane.add(purchHouseButton, 0, 4);
        gridPane.add(purchHotelButton, 0, 5);
        gridPane.add(endTurnButton, 0, 7);
        gridPane.add(cheatButton, 0, 1);
        //user input for cheat roll
        cheatBox.getChildren().addAll(cheatLabel, cheatField, cheatSubmit);
        gridPane.add(cheatBox, 1, 1);
        cheatBox.setDisable(true);
        
        //buttons call method from controller class
        rollButton.setOnAction((ActionEvent event) -> {
            controller.roll();
        });
        
        purchPropButton.setOnAction((ActionEvent event) -> {
            controller.purchaseProperty();
        });
        
        purchHouseButton.setOnAction((ActionEvent event) -> {
            controller.purchaseHouse();
        });
        
        purchHotelButton.setOnAction((ActionEvent event) -> {
            controller.purchaseHotel();
        });
        
        endTurnButton.setOnAction((ActionEvent event) -> {
            controller.endTurn();
        });
        
        cheatButton.setOnAction((ActionEvent event) -> {
            cheatBox.setDisable(false); // enables cheatBox area
        });
        
        cheatSubmit.setOnAction((ActionEvent event) -> {
            int roll = cheatRoll();
            controller.submit(roll);
            cheatBox.setDisable(true);
        });
        // adds player info properties/balance etc.
        player1Info = new Text(controller.getPlayerInfo(0));        
        player2Info = new Text(controller.getPlayerInfo(1));
        gridPane.add(player1Info, 2, 0);
        gridPane.add(player2Info, 4, 0);
        
        return gridPane;
    }
    
    @Override
    public void start(Stage primaryStage) {
        //sets up mvc
        model = new MModel();
        controller = new MController(model);
        controller.setView(this);
        //puts buttons in correct mode for starting state
        purchPropButton.setDisable(true);
        purchHouseButton.setDisable(true);
        purchHotelButton.setDisable(true);
        endTurnButton.setDisable(true);
        
        GridPane gridPane = makePane();
                
        StackPane root = new StackPane();
        root.getChildren().add(gridPane);
        
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        primaryStage.setTitle("Minipoly");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    //methods for enabling/disabling buttons
    public void setEnableRoll(boolean bool) {
        rollButton.setDisable(bool);
    }
    
    public void setEnableHouse(boolean bool) {
        purchHouseButton.setDisable(bool);
    }
    
    public void setEnableHotel(boolean bool) {
        purchHotelButton.setDisable(bool);
    }
    
    public void setEnableProp(boolean bool) {
        purchPropButton.setDisable(bool);
    }
    
    public void setEnableCheat(boolean bool) {
        cheatButton.setDisable(bool);
    }
    
    public void setEnableEnd(boolean bool) {
        endTurnButton.setDisable(bool);
    }
    //for collecting user input of cheat roll
    public int cheatRoll() {
        String roll = cheatField.getText();
        int r;
        try { //returns 0 if user doesn't input an int
            r = parseInt(roll);
        } catch (NumberFormatException e) {
            r = 0;
        }
        return r;
    }

    public StackPane[] getTiles() {
        return this.tiles;
    }
    //gets the token object for player who just rolled
    public Circle getToken(int turn) {
        if (turn == 0) {
            return token1;
        } else {
            return token2;
        }
    } 
    //updates player info for properties, balance etc.
    public void setPlayerInfo(int turn) {
        if (turn == 0) {
            player1Info.setText(controller.getPlayerInfo(turn));
        } if (turn == 1) {
            player2Info.setText(controller.getPlayerInfo(turn));
        }
    }
    //changes last action text
    public void setGameInfo (String s) {
        gameInfo.setText(s);
    }

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
