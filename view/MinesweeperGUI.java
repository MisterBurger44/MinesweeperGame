package view;

import javax.swing.plaf.basic.BasicBorders.ButtonBorder;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Location;
import model.Minesweeper;
import model.MinesweeperException;
import model.MinesweeperObserver;

public class MinesweeperGUI extends Application {
    
    private static final Image COVERED_SQUARE = new Image("file:media/images/covered.png");
    private static final Image UNCOVERED_SQUARE = new Image("file:media/images/uncovered.png");
    private static final Image BOMB_SQUARE = new Image("file:media/images/bomb.png");
    private static final Image ONE = new Image("file:media/images/one.png");
    private static final Image TWO = new Image("file:media/images/two.png");
    private static final Image THREE = new Image("file:media/images/three.png");
    private static final Image FOUR = new Image("file:media/images/four.png");
    private static final Image FIVE = new Image("file:media/images/five.png");
    private static final Image SIX = new Image("file:media/images/six.png");
    private static final Image SEVEN = new Image("file:media/images/seven.png");
    private static final Image EIGHT = new Image("file:media/images/eight.png");
    public static final Image HINT = new Image("file:media/images/hint.png");

    private Minesweeper board;
    private GridPane gridPane;
    private Label moveCount;
    private Label gameStateLabel;
    private Button hint;

    public observableButton makeButton(int row, int col){
        Button button = new Button("");
        button.setBackground(new Background(new BackgroundImage(COVERED_SQUARE, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        button.setMaxHeight(24);
        button.setMaxWidth(24);
        button.setPrefHeight(24);
        button.setPrefWidth(24);

        observableButton observableButton = new observableButton(button, this, new Location(row, col));

        button.setOnAction(new cellPressHandler(board, new Location(row, col), this));
        return observableButton;
    }

    public Image getImage(Location location){
        if(board.getBoard()[location.getRow()][location.getCol()] == 1){
            return ONE;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 2){
            return TWO;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 3){
            return THREE;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 4){
            return FOUR;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 5){
            return FIVE;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 6){
            return SIX;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 7){
            return SEVEN;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == 8){
            return EIGHT;
        }
        else if(board.getBoard()[location.getRow()][location.getCol()] == -1){
            return BOMB_SQUARE;
        }
        else{
            return UNCOVERED_SQUARE;
        }
    }

    public Label getGameLabel(){
        return gameStateLabel;
    }

    public GridPane getGridPane(){
        return gridPane;
    }

    public Label getMoveCountLabel(){
        return moveCount;
    }

    public void register(MinesweeperObserver observer){
        board.register(observer);
    }


    public void reset(Stage stage){
        board = new Minesweeper(20, 20, 50);
        gridPane.getChildren().clear();
        this.hint.setOnAction(new hintHandler(board, this));
        this.gameStateLabel.setText("");
        for(int col = 0; col < board.getBoard()[0].length; col++){
            for(int row = 0; row < board.getBoard().length; row++){
                gridPane.add(makeButton(row, col).getButton(), col, row);
            }
        }
    }

    @Override
    public void start(Stage stage){

        board = new Minesweeper(20, 20, 50);
        VBox vbox = new VBox();
        gridPane = new GridPane();
        for(int col = 0; col < board.getBoard()[0].length; col++){
            for(int row = 0; row < board.getBoard().length; row++){
                gridPane.add(makeButton(row, col).getButton(), col, row);
            }
        }

        Button reset = new Button("Reset");
        reset.setOnAction(new resetHandler(this));
        this.hint = new Button("Hint");
        hint.setOnAction(new hintHandler(board, this));
        moveCount = new Label("Moves: " + board.getMoveCount());
        Label mineCount = new Label("Mines: " + board.getMineCount());

        Button solve = new Button("Solve");
        solve.setOnAction(new SolveHandler(board, this));

        this.gameStateLabel = new Label("");

        HBox hbox = new HBox();
        hbox.getChildren().addAll(reset, hint, solve, moveCount, mineCount, gameStateLabel);

        vbox.getChildren().addAll(gridPane, hbox);

        

        stage.setScene(new Scene(vbox));
        stage.setTitle("Minesweeper");
        stage.show();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}
