package view;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import model.Location;
import model.MinesweeperObserver;

public class observableButton {
    private Button button;
    private MinesweeperObserver observer;
    private MinesweeperGUI gui;
    private Location location;

    public observableButton(Button button, MinesweeperGUI gui, Location location){
        this.button = button;
        this.gui = gui;
        this.location = location;


        
        this.observer = new MinesweeperObserver() {
            
            
            @Override
            public void cellUpdated(Location location) { 
                getButtonLocation(location).setBackground(new Background(new BackgroundImage(gui.getImage(location), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));;
            }
            
        };

        gui.register(observer);

    }

    public Button getButtonLocation(Location location) {
        for (Node button : gui.getGridPane().getChildren()) {
            if(GridPane.getRowIndex(button) == location.getRow() && GridPane.getColumnIndex(button) == location.getCol()){
                return (Button)button;
            }
        }
        return null;
    }

    public Button getButton(){
        return button;
    }



    
}
