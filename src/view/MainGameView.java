package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;


public class MainGameView extends JPanel {

	
	//TODO build the communication panel the card panel 

    
	private ArrayList<CoordinateView> jpanels ;
	
	private HandView handBoard;
	private BenchView bench;
	private AttackCardView attack;
	
	private HandView handBoard_Player;
	private BenchView bench_Player;
	private AttackCardView attack_Player;
	private InfoView infoView ; 
	
	Timer paintingTimer;
    private final int REFRESH_RATE = 200;
    
    /**
     * Constructor sets all views used in the game view.
     */
    public MainGameView() {
 	  
    	init();
    	setMainGameView(); 
    	intiTimerToRefreshScreen();
    }

    
    private void init(){
    	 jpanels = new ArrayList<CoordinateView>();
    	 
    	 handBoard = new HandView( false );
    	 jpanels.add(handBoard);
         bench = new BenchView ();
         jpanels.add(bench);
         attack = new AttackCardView(false);
         jpanels.add(attack);
         
         attack_Player = new AttackCardView(true);
         jpanels.add(attack_Player);
         bench_Player = new BenchView ();
         jpanels.add(bench_Player);
         handBoard_Player = new HandView(true);
         jpanels.add(handBoard_Player);
         
         infoView = new InfoView(); 
    }
   
    private void setMainGameView(){
    	  setBackground(  Color.white);
          setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
          setLayout(null);
          setPlayersView();
    }
    
    private void setPlayersView(){
          infoView.setViewLocation(1005 , 5, 200, 775 );
          add(infoView);
          infoView.refresh();
    	  for (int i = 0 ; i < jpanels.size() ; i++ ){
       	   add((JPanel)jpanels.get(i));
       	   jpanels.get(i).setBackgroundColor(Color.BLUE);
       	   jpanels.get(i).setViewLocation(5, 125 * i + 5 + (5 * i), 1000, 125);
       	   jpanels.get(i).refresh();
          }
    }
    

	private void intiTimerToRefreshScreen(){//TODO it can be replace by observer pattern 
		 paintingTimer = new Timer(REFRESH_RATE, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	refresh();	
	            }
	        });
	        paintingTimer.start();
		
	}
	

	private void refresh(){//TODO we can replace this with a observer pattern( would be nice )
		for(CoordinateView coordinate : jpanels  ){
			coordinate.refresh();
		}
	}

    //------------setter & getter 
    
	public ArrayList<CoordinateView> getJpanels() {
		return jpanels;
	}



	public HandView getHandBoard() {
		return handBoard;
	}


	public BenchView getBench() {
		return bench;
	}
	public AttackCardView getAttack() {
		return attack;
	}
	
	public HandView getHandBoard_Player() {
		return handBoard_Player;
	}
	public BenchView getBench_Player() {
		return bench_Player;
	}

	public AttackCardView getAttack_Player() {
		return attack_Player;
	}

	public InfoView getInfoView() {
		return infoView;
	}  
}
