package unitTest.gameEngineTest;

import control.GameEngine;
import model.Card;
import model.CardCollection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by Fish on 2017/5/24.
 */
public class DeleteCardTest {
    GameEngine gameEngine;
    CardCollection hand;

    @Before public void before(){

    }

    @Test public void testDeleteCard(){
        gameEngine = new GameEngine();
        hand = gameEngine.getPlayer_Hand();
        gameEngine.deleteCard(hand.getCard(0),true, true, "Hand");
        assertEquals(6, hand.getCardCount());
    }

    @After
    public void after(){
        gameEngine = null;
    }

}
