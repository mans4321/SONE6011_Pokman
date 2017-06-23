package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import model.Card;
import model.Message;
import model.Pokemon;
import model.Users;
import model.ability.AbilityCard;
import view.BaseWindow;
import view.DiscardsCardsFrame;

public class GameEngine {


	private Card cardInfo;
	private Users.CardLocation cardLocation;
	
	private BaseWindow baseWindow;
	private Coordinator coordinator;
	private DiscardsCardsFrame discardsCardsFrame;
	
	
	public GameEngine(){
		coordinator = new Coordinator();
		discardsCardsFrame = new DiscardsCardsFrame();
		coordinator.Load_Assign_Deck();
		coordinator.setTheView();
		baseWindow = coordinator.getBaseWindo();
		createPlayerViewListener();
		CreateAIViewListen();
		createInfoViewButtonsListener();
	}
	
	private void createPlayerViewListener() {
		createPlayerHandAreaListener();
		createPlayerBenchAreaListener();
		createPlayerAttackAreaMouseListener();
		createAttackAreaButtonsListeners();
	}



	private void CreateAIViewListen() {
		createAIHandAreaListener();
		createAIBenchAreaListener();
		createAIAttackArealistener();
	}

	
	private void createPlayerAttackAreaMouseListener() {
		baseWindow.getMainGameView().getAttack_Player().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();
                
                	  if( (x >= 420) && (x <= 500)
                			  && ((y >= 10) && (y <= 100 )) ){
                		  
                		  if(cardInfo != null && cardLocation != null && !coordinator.isAIInControl()){
                				 Message message = coordinator.getPlayer().validateMovingCardToAttackArea(cardInfo , cardLocation);
                				 coordinator.updateCommicuntionPanel(message.getMessage());
                				 if(message.isStatus())
                					 coordinator.UpdatePlayerAttackCard();
                		  }
                		  
                		  if(coordinator.getPlayerAttackCard() != null)//player has click on active or charge it
                			  coordinator.updateCardInfoPanel(coordinator.getPlayerAttackCard().toString());
                	
                          if(coordinator.isAIInControl())// if player try to make while AI turn
                        	  coordinator.updateCommicuntionPanel("It is AI turn so be cool and wait!! ):");
                	  }
                	  cardInfo = null;
    				  cardLocation = null;
                  }
		});
		
	}

	private void createPlayerBenchAreaListener() {
		baseWindow.getMainGameView().getBench_Player().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();
                
                
                Card preClick = null;
                
                if(cardInfo != null)
                	preClick = cardInfo;
                
                Users.CardLocation tempLocation = cardLocation;
                cardInfo = null;
                cardLocation = null;
                
                coordinator.updateCardInfoPanel("");
         
                for (int i = 0 ; i < coordinator.getPlayerBench().getCardCount() ; i++){
              	  if( (x >= ( 50 + (i + 2) * 90  + 5) && (x <= ( 50 + (i + 2) * 90  + 5) + 80 ))
              			  && ((y >= 10) && (y <= 100 )) ){
              		  cardInfo = coordinator.getPlayerBench().getCard(i);
              		  cardLocation = Users.CardLocation.BENCH;
              		  coordinator.updateCardInfoPanel(coordinator.getPlayerBench().getCard(i).toString());
              
              	  }
                }

                if((x >= 230 && x <= 695) &&( y >=5 && y <=120)){ // bench area
                	Message message = null ;
                	if (cardInfo != null && preClick != null && !coordinator.isAIInControl()){
                		 message = coordinator.getPlayer().vaildateChargingOrEvolvingPokemonOnBench((Pokemon)cardInfo, preClick);
                		 cardInfo = null;
                 		 cardLocation = null;
                		}else if(preClick != null && cardInfo == null && !coordinator.isAIInControl()){
                			if(!tempLocation.equals(Users.CardLocation.BENCH)){
                				message = coordinator.getPlayer().validateMovingCardToBench(preClick, tempLocation);
                		}
               			 cardInfo = null;
                 		 cardLocation = null;
                	}
                	
                	if(message != null) // message is != when player make a move
                		coordinator.updateCommicuntionPanel(message.getMessage());
                	if(coordinator.isAIInControl())// if player try to make while AI turn
                		coordinator.updateCommicuntionPanel("It is AI turn so be cool and wait!! ):");
               }

          }
		});
	}

	private void createPlayerHandAreaListener() {
		baseWindow.getMainGameView().getHandBoard_Player().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();
                
                
                if ((x >= 910 && x<= 990 ) && (y >= 10 && y<=110)){ // clicked on player deck 
                	coordinator.showDeckSize(true);
                	return;
                }
                
                  for (int i = 0 ; i < coordinator.getPlayerHand().getCardCount() ; i++){
                	  if( (x >= (10 +  (i + 2) * 90) && (x <= (10 +  (i + 2) * 90) + 80 ))
                			  && ((y >= 10) && (y <= 100 )) ){
                		  cardInfo = coordinator.getPlayerHand().getCard(i);
                		  cardLocation = Users.CardLocation.HAND;
                		  coordinator.updateCardInfoPanel(cardInfo.toString());
                	  }
                  }
            }
		});
		
	}

	
	private void createAIAttackArealistener() {
		baseWindow.getMainGameView().getAttack().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();

                if(coordinator.getAIAttackCard() != null){
                	  if( (x >= 420) && (x <= 500)
                			  && ((y >= 10) && (y <= 100 )) ){
                		  coordinator.updateCardInfoPanel(coordinator.toString());
                	  }
                  }
                cardInfo = null;
            }
		});
		
	}

	private void createAIBenchAreaListener() {
		baseWindow.getMainGameView().getBench().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();
                

                for (int i = 0 ; i < coordinator.getAIBench().getCardCount() ; i++){
                	  if( (x >= ( 50 + (i + 2) * 90  + 5) && (x <= ( 50 + (i + 2) * 90  + 5) + 80 ))
                			  && ((y >= 10) && (y <= 100 )) ){
                		  coordinator.updateCardInfoPanel(coordinator.getAIBench().getCard(i).toString());
                	  }
                }
                cardInfo = null;
            }
           });
		
	}

	private void createAIHandAreaListener() {
		baseWindow.getMainGameView().getHandBoard().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);

                int x = e.getX();
                int y = e.getY();
                
                if ((x >= 910 && x<= 990 ) && (y >= 10 && y<=120)){ // clicked on AI deck 
                	coordinator.showDeckSize(false);
                	return;
                }
                
                for (int i = 0 ; i < coordinator.getAIHand().getCardCount() ; i++){
              	  if( (x >= (10 +  (i + 2) * 90) && (x <= (10 +  (i + 2) * 90) + 80 ))
              			  && ((y >= 10) && (y <= 100 )) ){
              		coordinator.updateCardInfoPanel(coordinator.getAIHand().getCard(i).toString());
              	  }
                }
                
                cardInfo = null;
            }
        });
		
		
	}



	private void createInfoViewButtonsListener(){
		createShowDiscardsCardsButton();
		createCheckDiscardSizListener();
		createEndTurnListener();
	}
	
	private void createShowDiscardsCardsButton(){
		baseWindow.getMainGameView().getInfoView().getDiscardsCardsButton().addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(coordinator.isthereDiscardsCards()){
					coordinator.setDiscardsCardsView(discardsCardsFrame.getDiscardsCardsView());
					discardsCardsFrame.setVisible(true);
				}else {
					JOptionPane.showMessageDialog(baseWindow.getMainGameView(), " neither player or AI yet has Discards Cards to show");
				}
			}
		});
	}
	
	private void createCheckDiscardSizListener(){
		baseWindow.getMainGameView().getInfoView().getDiscardSizes().addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				coordinator.checkDiscardSizes();	
				}
		});
		
	}
	private void createEndTurnListener(){
		baseWindow.getMainGameView().getInfoView().getEndTurn().addActionListener(new  ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				coordinator.AITurn();
			}
		});	
	}
	private void createAttackAreaButtonsListeners(){
		createAttackButtonListener();
		createSwapButtonListener();
		createRetreatButtonListener();
	}
	
	private void createAttackButtonListener(){
		baseWindow.getMainGameView().getAttack_Player().getAttackButoon().addActionListener(new  ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!coordinator.getPlayerAttackCard().canPokemonAttack()){//Status
					coordinator.updateCommicuntionPanel("you cannot attack now you status is " + coordinator.getPlayerAttackCard().getStatus());
					return ;
				}
				
				if(!coordinator.isAIInControl()){
					AbilityCard abilityObj = (AbilityCard) baseWindow.getMainGameView().getAttack_Player().getjComboBox().getSelectedItem();
					if(coordinator.getAIAttackCard() != null){
						if(coordinator.getPlayerAttackCard().isThereEnoughEnergyToActivateAbility(abilityObj.getCat(), abilityObj.getNumOfEnergyCard())){
							coordinator.activateAbility(abilityObj, false);
							coordinator.AITurn();
						}else{
							coordinator.updateCommicuntionPanel("you need more energy card to use this ability \n u need " 
									+ abilityObj.getNumOfEnergyCard() + " of " + abilityObj.getCat().toString());
						}
					}else{
						if(coordinator.getPlayer().isFirstTurn())
							coordinator.updateCommicuntionPanel("you cannot attack now");
						//TODO should declare player has won here in AI not here 
					}
				}else{
					coordinator.updateCommicuntionPanel("you have to have an active card OR it is AI turn so be cool!!");
				}
			}
		});
	}
	
	private void createSwapButtonListener(){
		baseWindow.getMainGameView().getAttack_Player().getSwapButton().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(coordinator.getPlayer().getAttackCard() != null 
						&&(cardInfo != null && cardLocation != null)
						&& cardInfo.getType().equals(Card.CardType.POKEMON) && 
						cardLocation != Users.CardLocation.ATTACK)
					coordinator.swapRetreatPokemon((Pokemon)cardInfo, cardLocation);
				else
					coordinator.updateCommicuntionPanel("you have to pick a pokemon from bench or hand then click swap or you do not have an active Pokemon");
				cardInfo = null ;
				cardLocation = null;
			}
		});
	}
	private void createRetreatButtonListener(){
		baseWindow.getMainGameView().getAttack_Player().getRetreatButoon().addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(!coordinator.getPlayerAttackCard().canPokemonRetreat()){
					coordinator.updateCommicuntionPanel("you cannot Retreat now you status is " + coordinator.getPlayerAttackCard().getStatus());
					return ;
				} 
				if(coordinator.getPlayer().getAttackCard() != null)
					coordinator.retreatPokemon();
			}
		});
		
	}

	//----------------------------------------------------------------------
	public Coordinator getCoordinator() {
		return coordinator;
	}
}
