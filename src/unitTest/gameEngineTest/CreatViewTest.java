package unitTest.gameEngineTest;

import control.GameEngine;
import org.junit.*;

import static org.junit.Assert.*;

/**
 * Created by Fish on 2017/5/24.
 */
public class CreatViewTest {

    static GameEngine gameEngine;

    @BeforeClass
    public static void beforeClass(){
        gameEngine = new GameEngine();
    }


    @Test
    public void testCreateView(){

        assertNotNull(gameEngine.getBaseWindo().getMainGameView().getHandBoard().getCardCollection());
    }

    @AfterClass
    public static void after(){
        gameEngine = null;
    }
}
