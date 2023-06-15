package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import model.Location;
import model.Minesweeper;

public class hintHandler implements EventHandler<ActionEvent> {
    private Minesweeper game;
    private MinesweeperGUI gui;

    public hintHandler(Minesweeper game, MinesweeperGUI gui){
        this.game = game;
        this.gui = gui;
    }



    @Override
    public void handle(ActionEvent arg0) {
        Location hint = (Location) game.hint();
        for (Node button : gui.getGridPane().getChildren()) {
            if(GridPane.getRowIndex(button) == hint.getRow() && GridPane.getColumnIndex(button) == hint.getCol()){
                Button button2 = (Button) button;
                button2.setBackground(new Background(new BackgroundImage(MinesweeperGUI.HINT, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));;
            }
        }

        
    }
    
}
