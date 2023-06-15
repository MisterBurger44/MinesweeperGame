package view;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class TestMinesweeperCLI {

    @Test
    public void TestPick1Mine(){
        MinesweeperCLI m = new MinesweeperCLI();
        m.pick(0, 0);
        
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
    public void TestHint(){
        MinesweeperCLI n = new MinesweeperCLI();
        Object t = n.hint();
        String tString = t.toString();
        assertEquals("(" + 0 + ", " + 0 + ")",tString);
    }

    
}
