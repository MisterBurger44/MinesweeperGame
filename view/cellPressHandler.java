package view;

import javax.tools.DocumentationTool.Location;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import model.GameState;
import model.Minesweeper;
import model.MinesweeperException;

public class cellPressHandler implements EventHandler<ActionEvent> {
    private Minesweeper game;
    private MinesweeperGUI gui;
    private model.Location location;

    public cellPressHandler(Minesweeper game, model.Location location, MinesweeperGUI gui){
        this.game = game;
        this.location = location;
        this.gui = gui;
    }
    
    public void revealBoard(){     
        int boardRow = 0;
        int boardCol = 0;
        model.Location currLocation = new model.Location(0, 0);
        for (Node nButton : gui.getGridPane().getChildren()) {
            Button button = (Button)nButton;

            if(boardRow < game.getBoard().length - 1){
                currLocation = new model.Location(boardRow, boardCol);
                boardRow++;
            }
            else if(boardRow == game.getBoard().length - 1){
                currLocation = new model.Location(boardRow, boardCol);
                boardRow = 0;
                boardCol++;
            }

            button.setBackground(new Background(new BackgroundImage(gui.getImage(currLocation), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));;
        }
    }

    @Override
    public void handle(ActionEvent arg0) {
        try {
            game.makeSelection(location);
        } catch (MinesweeperException e) {}
        if(game.getBoard()[location.getRow()][location.getCol()] == -1){
            revealBoard();
            game.setGameState(GameState.LOST);
            gui.getGameLabel().setText("YOU LOSE!");
        }
        

        if(game.getMoveCount() == (game.getBoard().length*game.getBoard()[0].length) - game.getMineCount()){
            System.out.println("You win!");
            revealBoard();
            game.setGameState(GameState.WON);
            gui.getGameLabel().setText("YOU WIN!");
        }

        if(game.getGameState() != GameState.LOST && game.getGameState() != GameState.WON){
            gui.getMoveCountLabel().setText("Moves: " + game.getMoveCount());

        }
    }
}


