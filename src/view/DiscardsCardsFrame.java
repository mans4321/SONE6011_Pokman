package view;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class DiscardsCardsFrame extends JFrame {


    private final String MENU_TITLE = "PokmonGOBack";
    private DiscardsCardsView discardsCardsView;
    private final static int WINDOW_WIDTH = 350;
    private final static int WINDOW_HEIGHT = 380;
    Dimension screenSize ;
    
	public DiscardsCardsFrame(){
		setTitle(MENU_TITLE);
		discardsCardsView = new DiscardsCardsView();
        setContentPane(discardsCardsView);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(false);
	}

	public DiscardsCardsView getDiscardsCardsView() {
		return discardsCardsView;
	}
	
	
}
