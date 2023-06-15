package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import model.GameState;
import model.Location;
import model.MinesweeperException;

public class MinesweeperCLI extends model.Minesweeper{
    
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int MINECOUNT = 1;

    public MinesweeperCLI(){
        super(ROWS, COLS, MINECOUNT);
    }

    public String pick(int row, int col){
        boolean valid = true;
        while(valid){
            try{
                if(!super.makeSelection(new Location(row, col))){
                    System.out.println("BOOM! Better luck next time!");
                    super.setGameState(GameState.LOST);
                    System.out.println(super.printBoard("final"));
                    return super.getWinningBoard(super.getBoard());
                };
                if(super.getGameState() == GameState.WON){
                    System.out.println("You win!");
                    System.out.println(super.printBoard("final"));
                    return super.getWinningBoard(super.getBoard());
                }
                valid = false;
            }
            catch(MinesweeperException e){
                System.out.println("Invalid move, please try again");
                return null;
            }
        }
        return null;
    }

    

    public void getInput(){
        Scanner scan = new Scanner(System.in);
        String command = "";

        System.out.println(super.toString());

        while(true){
            System.out.println("Enter command: ");
            command = scan.next();
            command = command.toLowerCase();
            if(command.equals("help")){
                System.out.println("Commands:\n\t\thelp - this help message\n\t\tpick <row> <col> - uncovers cell a <row> <col>\n\t\thint - displays a safe selection" + 
                "\n\t\treset - resets to a new game\n\t\tsolve - solves the game\n\t\tquit - quits the game\n");
            }
            else if(command.equals("pick")){
                int row = Integer.parseInt(scan.next());
                int col = Integer.parseInt(scan.next());
                pick(row, col);
            }

            else if(command.equals(("hint"))){
                super.hint();
            }

            else if(command.equals("reset")){
                System.out.println("Resetting to a new game...");
                super.resetBoard();
            }

            else if(command.equals("quit")){
                System.out.println("Goodbye!");
                scan.close();
                break;
            }

            else if(command.equals("solve")){
                super.solve();
            }

            if(!(super.getGameState() == GameState.WON || super.getGameState() == GameState.LOST)){
                System.out.println(super.toString());
            }
            
        }
        
    }

    public static void main(String[] args) {
        MinesweeperCLI m = new MinesweeperCLI();
        m.getInput();
    }
}

