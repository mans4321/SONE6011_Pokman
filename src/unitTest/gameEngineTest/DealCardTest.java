package unitTest.gameEngineTest;

import control.GameEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Fish on 2017/5/24.
 */
public class DealCardTest {
    static GameEngine gameEngine;

    @BeforeClass
    public static void beforeClass(){
        gameEngine = new GameEngine();
    }

    @Test public void testDealCardToPlayer(){
        gameEngine = new GameEngine();
        gameEngine.dealCard(true);
        assertNotNull(gameEngine.getPlayer_Hand());
    }

    @Test public void testDealCardToAI(){
        gameEngine = new GameEngine();
        gameEngine.dealCard(false);
        assertNotNull(gameEngine.getAI_Hand());
    }

    @After public void after(){
        gameEngine = null;
    }
}
