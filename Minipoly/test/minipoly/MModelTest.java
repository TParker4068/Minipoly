package minipoly;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MModelTest {
    
    public MModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    //This test checks how the program handles passing go/finishing a lap of the board
    @Test
    public void testPassGo() {
        MModel instance = new MModel();
        instance.cheatRoll(39);
        instance.endTurn();
        instance.cheatRoll(0);
        instance.endTurn();
        
        instance.cheatRoll(2);
        
        assertTrue(instance.invariant());
        assertTrue(instance.getGameState() != 0 && instance.getGameState() != 6);
        assertTrue(instance.getPlayers()[0].getPosition() == (39 + 2) % 40);
        assertTrue(instance.getPlayers()[0].getPosition() >= 0 && instance.getPlayers()[0].getPosition() <= 39);
    }
    // assert invariant()
    // assert gameState != 0 && gameState != 6
    // assert players[playerTurn].getPosition() == (prePos + i) % 40
    // assert players[playerTurn].getPosition() >=0 && players[playerTurn].getPosition() <= 39:
    
    // This test checks the payRent method
    @Test
    public void testPayRent () {
        MModel instance = new MModel();
        instance.cheatRoll(1);
        instance.purchase();
        instance.endTurn();
        instance.cheatRoll(1);
        
        instance.payRent();
            
        assertTrue(instance.getGameState() == 5 || instance.getGameState() == 6);
        assertTrue(instance.getPlayers()[1].getBalance() == 1995.0);
        assertTrue(instance.getPlayers()[0].getBalance() == 1955.0);
        assertTrue(instance.invariant());
    }
    //assert gameState == 5 || gameState == 6
    //assert players[playerTurn].getBalance() == playPreBal - rent
    //assert assert players[opponent].getBalance() == oppPreBal + rent
    //assert assert invariant()
    
    //This test checks the endTurn method
    @Test
    public void testEndTurn() {
        MModel instance = new MModel();
        instance.cheatRoll(6);
        
        instance.endTurn();
        
        assertTrue(instance.getGameState() == 0);
        assertTrue(instance.getPlayerTurn() == 1);
        assertTrue(instance.getOpponent() == 0);
        assertTrue(instance.invariant());
    }
    //assert gameState == 0
    //assert playerTurn == preOpponent
    //assert opponent == prePlayer
    //assert invariant()
    
    //This test checks purchasing a hotel
    @Test
    public void testBuyHotel() {
        MModel instance = new MModel();
        instance.cheatRoll(1);
        instance.purchase();
        instance.endTurn();
        instance.cheatRoll(0);
        instance.endTurn();
        instance.cheatRoll(2);
        instance.purchase();
        instance.endTurn();
        instance.cheatRoll(0);
        instance.endTurn();
        instance.cheatRoll(1);
        instance.purchase();
        instance.buyHouse();
        instance.buyHouse();
        instance.buyHouse();
        instance.buyHouse();
        
        instance.buyHotel();
        
        assertTrue(instance.getGameState() == 5);
        assertTrue(instance.getBoard()[4].hasHotel());
        assertTrue(instance.getBoard()[4].getRent() == 33.6);
        assertTrue(instance.getPlayers()[0].getBalance() == 1690 - 56);
        assertTrue(instance.getPlayers()[0].getBalance() > 0);
        assertTrue(instance.invariant());
    }
    //assert gameState != 0 || gameState != 3 || gameState != 4 || gameState !=6
    //assert balance == preBalance - cost
    //assert noHouses == preNoHouses + 1
    //assert rent == (2*cost*0.2) + (0.1 * (noHouses*cost))
    //assert players[playerTurn].getBalance() > 0
    //assert invariant()
}
