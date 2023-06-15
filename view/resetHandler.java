package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Minesweeper;

public class resetHandler implements EventHandler<ActionEvent> {
    MinesweeperGUI game;
    
    public resetHandler(MinesweeperGUI game){
        this.game = game;
    }

    @Override
    public void handle(ActionEvent arg0) {

        game.reset(new Stage());
        
    }
}
