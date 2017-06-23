package view;

import javax.swing.JFrame;

public class BaseWindow {

    public final static int WINDOW_WIDTH = 1210;
    public final static int WINDOW_HEIGHT = 815;
    
   private MainGameView mainGameView ; 
    private JFrame window ;
    public BaseWindow(){
		
        window = new JFrame("PokmonGoPack");
        mainGameView = new MainGameView();
        window.setContentPane(mainGameView);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocation(120,70);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setVisible(false);
	}

	public MainGameView getMainGameView() {
		return mainGameView;
	}

	public JFrame getWindow() {
		return window;
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}	
	
}
