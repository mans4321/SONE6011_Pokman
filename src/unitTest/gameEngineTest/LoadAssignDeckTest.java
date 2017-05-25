package unitTest.gameEngineTest;

import control.GameEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fish on 2017/5/24.
 */
public class LoadAssignDeckTest {

    GameEngine gameEngine;

    @Before
    public void before(){
        //gameEngine = new GameEngine();
    }

    @Test
    public void testLoadAssignDeck() {
        gameEngine = new GameEngine();
        assertNotNull(gameEngine.getPlayer_Deck());
        assertNotNull(gameEngine.getAI_Deck());
    }

    @After
    public void after(){
        gameEngine = null;
    }
}
