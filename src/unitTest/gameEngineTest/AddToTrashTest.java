package unitTest.gameEngineTest;

import control.GameEngine;
import model.Card;
import model.CardCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fish on 2017/5/24.
 */
public class AddToTrashTest {
    CardCollection hand, trash;
    Card card;

    static GameEngine gameEngine;

    @BeforeClass
    public static void beforeClass(){
        gameEngine = new GameEngine();
    }

    @Test
    public void testAddToTrash(){
        hand = gameEngine.getPlayer_Hand();
        trash = gameEngine.getPlayer_Trash();
        card = hand.getCard(0);
        gameEngine.addToTrash(card, true);
        assertEquals(1, gameEngine.getPlayer_Trash().getCardCount());
    }

    @After
    public void after(){
        gameEngine = null;
    }
}
