package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.imageio.plugins.bmp.BMPImageWriteParam;

import backtracker.Backtracker;
import backtracker.Configuration;

public class MinesweeperConfiguration implements Configuration {
    Minesweeper game;
    List<Location> moves;
    List<char[][]> boards;

    public MinesweeperConfiguration(Minesweeper game, List<Location> moves, List<char[][]> boards){
        this.game = game;
        this.moves = moves;
        this.boards = boards;
    }

    @Override
    public Collection<Configuration> getSuccessors() {
        List<Configuration> successors = new ArrayList<>();
        for(int i = 0; i < game.getBoard().length; i++){
            for(int j = 0; j < game.getBoard()[0].length; j++){
                if(game.isCovered(new Location(i, j))){
                    try {
                        Minesweeper copy = new Minesweeper(game);
                        copy.makeSelection(new Location(i, j));

                        // addMove(new Location(i, j));
                        // addBoard(Arrays.copyOf(copy.getPlayerBoard(), copy.getPlayerBoard().length));
                        
                        List<char[][]> boardsCopy = new ArrayList<>();
                        for(char[][] board : boards){
                            boardsCopy.add(board);
                        }

                        List<Location> movesCopy = new ArrayList<>();
                        for(Location move : moves){
                            movesCopy.add(move);
                        }

                        MinesweeperConfiguration mineConfig = new MinesweeperConfiguration(copy, movesCopy, boardsCopy);
                        mineConfig.addMove(new Location(i, j));

                        char[][] boardCopy = new char[copy.getPlayerBoard().length][copy.getPlayerBoard()[0].length];
                        for(int k = 0; k < copy.getPlayerBoard().length; k++){
                            for(int l = 0; l < copy.getPlayerBoard()[0].length; l++){
                                boardCopy[k][l] = copy.getPlayerBoard()[k][l];
                            }
                        }

                        mineConfig.addBoard(boardCopy);

                        successors.add(mineConfig);
                    } catch (MinesweeperException e) {
                    }
                } 
            }
        }
        return successors;
    }

    public static MinesweeperConfiguration solveGame(Minesweeper game){
        MinesweeperConfiguration newConfig = new MinesweeperConfiguration(game, new ArrayList<>(), new ArrayList<>());
        Backtracker backtracker = new Backtracker(false);
        return (MinesweeperConfiguration)backtracker.solve(newConfig);
    }

    @Override
    public boolean isValid() {
        return game.getGameState() != GameState.LOST;
    }

    @Override
    public boolean isGoal() {
        return game.getGameState() == GameState.WON;
    }

    public List<Location> getMoves(){
        return moves;
    }

    public void addBoard(char[][] board){
        boards.add(board);
    }

    public List<char[][]> getBoards(){
        return boards;
    }

    public void addMove(Location move){
        moves.add(move);
    }

    @Override
    public String toString(){
        return "Current moves: " + moves.toString() + "\nBoard:\n" + game.toString();
    }
}
