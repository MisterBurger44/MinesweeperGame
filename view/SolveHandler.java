package view;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import model.GameState;
import model.Location;
import model.Minesweeper;
import model.MinesweeperConfiguration;
import model.MinesweeperException;

public class SolveHandler implements EventHandler<ActionEvent> {

    private Minesweeper game;
    private MinesweeperConfiguration solveConfig;
    private MinesweeperGUI gui;

    public SolveHandler(Minesweeper game, MinesweeperGUI gui){
        this.gui = gui;
        this.game = game;
        solveConfig = MinesweeperConfiguration.solveGame(game);
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
        List<Location> moveList = solveConfig.getMoves();
        for(Location loc : moveList){
            try {
                game.makeSelection(loc);
                gui.getMoveCountLabel().setText("Moves: " + game.getMoveCount());
                if(game.getMoveCount() == (game.getBoard().length*game.getBoard()[0].length) - game.getMineCount()){
                    System.out.println("You win!");
                    revealBoard();
                    game.setGameState(GameState.WON);
                    gui.getGameLabel().setText("YOU WIN!");
                }
            } catch (MinesweeperException e) {}
        }
    }

}