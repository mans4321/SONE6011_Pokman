package unitTest.gameEngineTest;

import control.GameEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fish on 2017/5/24.
 */
public class PrepareHandTest {

    GameEngine gameEngine;

    @Before public void before(){
        //gameEngine = new GameEngine();
    }

    @Test public void testPrepareHand(){
        gameEngine = new GameEngine();
        assertEquals(7, gameEngine.getPlayer_Hand().getCardCount());
        assertEquals(7, gameEngine.getAI_Hand().getCardCount());
    }
    @After
    public void after(){
        gameEngine = null;
    }
}
