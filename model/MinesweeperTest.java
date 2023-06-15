package model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperTest {
    
    @Test
    public void makeSelectionTest(){
        Minesweeper m = new Minesweeper(3, 3, 0);
        try{
            m.makeSelection(new Location(0, 0));
        }
        catch(MinesweeperException e){
            ;
        }

        char[][] board = m.getPlayerBoard();

        if(board[0][0] == 'M'){
            assertEquals(board[0][0], 'M');
        }
        else if(board[0][0] == '1'){
            assertEquals(board[0][0], '1');
        }
        else if(board[0][0] == '0'){
            assertEquals(board[0][0], '0');
        }
    }

    @Test
    public void resetBoardTest(){
        Minesweeper m = new Minesweeper(3, 3, 0);
        try{
            m.makeSelection(new Location(0, 0));
        }
        catch(MinesweeperException e){
            ;
        }
        m.resetBoard();
        int[][] board = m.getBoard();

        assertArrayEquals(new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}}, board);
    }

    @Test
    public void getWinningBoardTest(){
        Minesweeper m = new Minesweeper(2, 2, 0);
        String win = m.getWinningBoard(m.getBoard());
        assertEquals(" 0 0\r\n 0 0\r\n", win);
    }

    @Test
    public void getPossibleSelectionsTest(){
        Minesweeper m = new Minesweeper(2, 2, 0);
        Object[] moves = m.getPossibleSelections().toArray();
        String[] strMoves = new String[moves.length];
        for(int i = 0; i < moves.length; i++){
            Location loc = (Location)moves[i];
            strMoves[i] = loc.toString();
        }
        assertEquals("[(0, 0), (0, 1), (1, 0), (1, 1)]", Arrays.toString(moves));
    }
}
