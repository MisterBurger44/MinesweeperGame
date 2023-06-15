package model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Minesweeper{
    public static final char MINE = 'M';
    public static final char COVERED = '-';
    private int[][] board;
    private int moveCount;
    private GameState gameState;
    private char [][] playerBoard;
    private int minecount;
    private HashSet<MinesweeperObserver> observers;

    public Minesweeper(Minesweeper minesweeper){
        this.minecount = minesweeper.getMineCount();
        
        char[][] playerBoardCopy = new char[minesweeper.getPlayerBoard().length][minesweeper.getPlayerBoard()[0].length];
        for(int k = 0; k < minesweeper.getPlayerBoard().length; k++){
            for(int l = 0; l < minesweeper.getPlayerBoard()[0].length; l++){
                playerBoardCopy[k][l] = minesweeper.getPlayerBoard()[k][l];
            }
        }
        this.playerBoard = playerBoardCopy;

        int[][] boardCopy = new int[minesweeper.getBoard().length][minesweeper.getBoard()[0].length];
        for(int k = 0; k < minesweeper.getBoard().length; k++){
            for(int l = 0; l < minesweeper.getBoard()[0].length; l++){
                boardCopy[k][l] = minesweeper.getBoard()[k][l];
            }
        }

        this.board = boardCopy;

        this.moveCount = minesweeper.getMoveCount();
        this.gameState = minesweeper.getGameState();
        this.observers = new HashSet<>();
    }

    public Minesweeper(int rows, int cols, int minecount){
        this.minecount = minecount;
        this.board = new int [rows][cols];
        this.playerBoard = new char [rows][cols]; 
        this.moveCount = 0;
        this.gameState = GameState.NOT_STARTED;
        this.observers = new HashSet<>();
        Random r = new Random();
        int row;
        int col;
        int tempMineCount = minecount;
        
        for(int k = 0; k < rows; k++){
            for(int j = 0; j < cols; j++){
                playerBoard[k][j] = COVERED;
            }
        }

        while(tempMineCount > 0){
            row = r.nextInt(cols);
            col = r.nextInt(rows);
            if(!(board[row][col] == -1)){
                board[row][col] = -1;
                tempMineCount--;
            }
        }

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != -1){
                    board[i][j] = findBombs(i, j);
                }
            }
        }
    }

    private int findBombs(int row, int col) {
        int mineNum = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if(i >= 0 && i < board.length && j >= 0 && j < board[0].length) {
                    if(board[i][j] == -1) {
                        mineNum++;
                    }
                }
            }
        }
        return mineNum;
    }

    public boolean checkWin(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] != -1 && this.isCovered(new Location(i, j))){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean makeSelection(Location location) throws MinesweeperException{
        if(((location.getRow() >= board.length) || (location.getCol() >= board[0].length))){
            throw new MinesweeperException("That's out of the board!");
        }
        else if(board[location.getRow()][location.getCol()] == -1){
            playerBoard[location.getRow()][location.getCol()] = MINE;
            notifyObserver(location);
            moveCount++;
            gameState = GameState.LOST;
            // System.out.println(printBoard("final"));
            return false;
        }
        else{
            if(playerBoard[location.getRow()][location.getCol()] == COVERED){
                playerBoard[location.getRow()][location.getCol()] = (char)(board[location.getRow()][location.getCol()]+48);
                notifyObserver(location);
                moveCount++;
                if(checkWin()){
                    this.gameState = GameState.WON;
                    // System.out.println(printBoard("final"));
                    
                }
            }

            if(this.gameState == GameState.NOT_STARTED){
                this.gameState = GameState.IN_PROGRESS;
            }

            return true;
        }
    }

    public Object hint(){
        Collection<Location> moveList = new ArrayList<>();
        moveList = getPossibleSelections();
        return (moveList.toArray()[0]);
    }

    public int getMoveCount(){
        return this.moveCount;
    }

    public GameState getGameState(){
        return this.gameState;
    }

    public int[][] getBoard(){
        return board;
    }

    public char[][] getPlayerBoard(){
        return playerBoard;
    }

    public int getMineCount(){
        return minecount;
    }

    public Collection<Location> getPossibleSelections(){
        List<Location> locList = new ArrayList<>();
        
        for(int k = 0; k < playerBoard.length; k++){
            for(int j = 0; j < playerBoard[0].length; j++){
                if(playerBoard[k][j] == COVERED && board[k][j] != -1){
                    locList.add(new Location(k, j));
                }
            }
        }

        return locList;
    }

    public void resetBoard(){
        gameState = GameState.NOT_STARTED;
        
        for(int k = 0; k < playerBoard.length; k++){
            for(int j = 0; j < playerBoard[0].length; j++){
                playerBoard[k][j] = COVERED;
                board[k][j] = 0;
            }
        }
        int tempMineCount = minecount;
        Random r = new Random();
        while(tempMineCount > 0){
            int row = r.nextInt(board.length);
            int col = r.nextInt(board[0].length);
            if(!(board[row][col] == -1)){
                board[row][col] = -1;
                tempMineCount--;
            }
        }
        this.moveCount = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                Location location = new Location(i, j);
                notifyObserver(location);
            }
        }
        observers = new HashSet<>();
    }

    public String getWinningBoard(int[][] finalBoard){
        char[][] win = new char[finalBoard.length][finalBoard[0].length];
        for(int i = 0; i < win.length; i++){
            for(int j = 0; j < win[0].length; j++){
                if(finalBoard[i][j] == -1){
                    win[i][j] = MINE;
                }
                else{
                    win[i][j] = (char)(finalBoard[i][j] + 48);
                }
            }
        }
        String str = "";
        for(int row = 0; row < win.length; row++) {
            for(int col = 0; col < win[row].length; col++) {
               str += " " + win[row][col];
            }
               str += "\r\n";
         }

         return(str);
    }

    public void setGameState(GameState state){
        this.gameState = state;
    }

    public String printBoard(String type){
        if(type.equals("player")){
            String str = "";
        for(int row = 0; row < playerBoard.length; row++) {
            for(int col = 0; col < playerBoard[row].length; col++) {
               str += " " + playerBoard[row][col];
            }
               str += "\r\n";
         }

         str += "\nMoves: " + moveCount;

         return str;
        }
        else if(type.equals("final")){
            String str = "";
            str += getWinningBoard(board) + "\nMoves: " + moveCount;
            return str;
        }
        return null;
    }

    @Override
    public String toString(){
        return printBoard("player");
    }

    public void solve(){
        MinesweeperConfiguration solveConfig = MinesweeperConfiguration.solveGame(this);
        for(int i = 0; i < solveConfig.getBoards().size(); i++){
            try {
                makeSelection(solveConfig.getMoves().get(i));
            } catch (MinesweeperException e) {
            }
            System.out.println(solveConfig.getMoves().get(i));
            String str = "";
            for(int row = 0; row < solveConfig.getBoards().get(i).length; row++) {
                for(int col = 0; col < solveConfig.getBoards().get(i)[row].length; col++) {
                   str += " " + solveConfig.getBoards().get(i)[row][col];
                }
                   str += "\r\n";
             }
             
             System.out.println(str + "Moves: " + moveCount);
        }

        System.out.println("Congratulations");
        System.out.println();
        System.out.println(getWinningBoard(board));
        System.out.println("\nMoves: " + moveCount);
         
    }

    public void register(MinesweeperObserver observer){
        observers.add(observer);
    }

    private void notifyObserver(Location location){
        for(MinesweeperObserver o : observers){
            o.cellUpdated(location);
        }
    } 

    public char getSymbol(Location location){
        if(playerBoard[location.getRow()][location.getCol()] == COVERED){
            return COVERED;
        }
        else if(playerBoard[location.getRow()][location.getCol()] == MINE){
            return MINE;
        }
        else{
            return playerBoard[location.getRow()][location.getCol()];
        }
    }

    public boolean isCovered(Location location){
        if(playerBoard[location.getRow()][location.getCol()] == COVERED){
            return true;
        }
        else{
            return false;
        }
    }

    public static void main(String[] args) {
        Minesweeper m = new Minesweeper(4, 4, 2);
        // System.out.println(m);
        // try{m.makeSelection(new Location(3, 2));}
        // catch(MinesweeperException E){System.out.println("Hello");}
        // System.out.println(m);
        // System.out.println(m.getPossibleSelections());
        m.solve();
        
    }
}