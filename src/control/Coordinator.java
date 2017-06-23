package control;
import javax.swing.JOptionPane;

import model.Card;
import model.CardCollection;
import model.Deck;
import model.Message;
import model.Pokemon;
import model.LoadingDeck;
import model.Users;
import model.Users.CardLocation;
import model.ability.AbilityBehaviorVisitor;
import model.ability.AbilityCard;
import view.BaseWindow;
import view.DiscardsCardsView;

public class Coordinator {

	private LoadingDeck loadDeck;
	private BaseWindow baseWindo;
	private AI_Mind AIMind ; 
	private Users player;	
	private boolean AIInControl= false;
	private AbilityBehaviorVisitor abilityVisitor;

	public Coordinator(){
		player = new Users();
		AIMind = new AI_Mind(this);
		abilityVisitor = new AbilityBehaviorVisitor();
	}
	
	public void Load_Assign_Deck(){
		loadDeck = LoadingDeck.sharedInstance();
		player.setDeck(loadDeck.assignDecks(true));
		AIMind.getAIInfo().setDeck(loadDeck.assignDecks(false));
	}
	
	public void setTheView(){
		baseWindo = new BaseWindow();
		baseWindo.getMainGameView().getHandBoard().setCardCollection(AIMind.getAIInfo().getHand());
		baseWindo.getMainGameView().getHandBoard_Player().setCardCollection(player.getHand());
		baseWindo.getMainGameView().getBench().setCardCollection(AIMind.getAIInfo().getBench());
		baseWindo.getMainGameView().getBench_Player().setCardCollection(player.getBench());
		baseWindo.getMainGameView().getAttack().setCard(AIMind.getAIInfo().getAttackCard());
		baseWindo.getMainGameView().getAttack_Player().setCard(player.getAttackCard());
	}
	
	public void AITurn(){
		if( player.getAttackCard() != null){
			if(player.isFirstTurn())
				player.setFirstTurn(false);
			if(!AIMind.getAIInfo().isFirstTurn())
				dealCard(false , 1);
			player.removeStatus();
			player.setHasUsedEngOREvo(false);
			AIInControl = true;
			creatThreadForAI();
		}else {
			updateCommicuntionPanel("you have to have one card at the attack area before ending your turn");
		}
	}
	
	private void creatThreadForAI(){
		Thread thread = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	AIMind.play(); 
		    }
		});

		thread.start();
	}
	
	public void dealCard(boolean isPlayer , int amount){
		try{
			if(isPlayer)		
				player.dealCard(amount);
			else
				AIMind.getAIInfo().dealCard(amount);
			}catch(IllegalStateException e){
				if(isPlayer)
					updateCommicuntionPanel("you do not have any more cards in your deck");
				else 
					updateCommicuntionPanel("AI Deck has no more card");
			}
		}
	

	public void dealPrizeCard(boolean isPlayer){
		Message message = null;
		if(isPlayer){
			message = player.dealPrizeCard();
			if(message.isStatus())
				JOptionPane.showMessageDialog(baseWindo.getMainGameView(), message.getMessage());
			else
				restartGame(true);	
		}else{
			message = AIMind.getAIInfo().dealPrizeCard();
			if(message.isStatus())
				JOptionPane.showMessageDialog(baseWindo.getMainGameView(), message.getMessage());
			else
				restartGame(true);	
		}
	}
	
	public void showDeckSize(boolean isPlayer) {
		int deckSize ;
			if(isPlayer)
				deckSize = player.getDeckSize();
			else 
				deckSize = AIMind.getAIInfo().getDeckSize();
			updateCommicuntionPanel("Deck Size is " + deckSize);
	}
	

	public void swapPokemon(Pokemon tragetPokemon,Users.CardLocation cardLocation , boolean isPlayer) {
		if(isPlayer)
			player.swapPokemon(tragetPokemon, cardLocation);
		else
			AIMind.getAIInfo().swapPokemon(tragetPokemon, cardLocation);
	}
	
	public void checkDiscardSizes() {
		updateCommicuntionPanel("player \n Discard cards Sizes is " + player.getTrashSize() + "\n-------------\n AI \n Discard cards Sizes is "
		+ AIMind.getAIInfo().getTrashSize());
	}
	
	public void setDiscardsCardsView(DiscardsCardsView discardsCardsView){
		discardsCardsView.setplayerANDTrashCardsJComboBox(player.getTrash() , AIMind.getAIInfo().getTrash());
	}
	public void retreatPokemon() {
		if(player.getBench().getCardCount() < 5 && player.getAttackCard() !=null
				&& player.getAttackCard().isThereEnoughEnergyToActivateAbility(Card.EnegeryCategory.COLORLESS,
						player.getAttackCard().getRetreat())){
			player.addCardToBench(player.getAttackCard());
			player.setAttackCard(null);
			UpdatePlayerAttackCard();
		}else{
			updateCommicuntionPanel("you to have an active Pokemon or if you have an active Pokemon, use swap button since you bench has 5 cards");
		}
		
	}

	public void restartGame(boolean player){
		if(player)
		 JOptionPane.showMessageDialog(baseWindo.getMainGameView(), "you win : )");
		else
			JOptionPane.showMessageDialog(baseWindo.getMainGameView(), " you lost ) :");
		this.baseWindo.getMainGameView().setVisible(false);
		new GameEngine().getCoordinator().getBaseWindo().getWindow().setVisible(true);
	}
	
	public void UpdatePlayerAttackCard(){
		baseWindo.getMainGameView().getAttack_Player().setCard(player.getAttackCard());
	}
	
	public void updateAIAttackCard(){
		baseWindo.getMainGameView().getAttack().setCard(AIMind.getAIInfo().getAttackCard());
	}

	
	public void updateCommicuntionPanel(String message){
		baseWindo.getMainGameView().getInfoView().getT_communication().setText(message);
	}
	
	public void updateCardInfoPanel(String message){
		baseWindo.getMainGameView().getInfoView().getT_cardInfo().setText(message);
	}
	
	public boolean isthereDiscardsCards(){
		if(player.getTrashSize() == 0 && AIMind.getAIInfo().getTrashSize() == 0)
			return false;
		return true;
	}
	public void shufflePlayerDeck(){
		player.getDeck().shuffle();
	}
	public void shuffleAIDeck(){
		AIMind.getAIInfo().getDeck().shuffle();
	}
	

	public void removeFromHandToDeck(boolean isPlayer, int amount) {
		if(isPlayer)
			for(int i = 0; i < amount;i++ ){
				Card card = player.getHand().getCard(i);
			    player.deleteCard(card, Users.CardLocation.HAND, false);
			    player.getDeck().addCard(card);
		}else{
			for(int i = 0; i < amount;i++ ){
				Card card = AIMind.getAIInfo().getHand().getCard(i);
				AIMind.getAIInfo().deleteCard(card, Users.CardLocation.HAND, false);
				AIMind.getAIInfo().getDeck().addCard(card);
			}
		}
	}

	public void swapRetreatPokemon(Pokemon cardInfo, CardLocation cardLocation) {
		if(cardInfo.isThereEnoughEnergyToActivateAbility(Card.EnegeryCategory.COLORLESS,
				cardInfo.getRetreat())){
			cardInfo.chargePokemonForRetreasting(cardInfo.getRetreat());
			swapPokemon(cardInfo,cardLocation, true);
		}
	}
	
	public void deletePokemon(Pokemon pokemon, CardLocation pokeomnLocation, boolean isPlayer) {
		if(isPlayer)
			player.deleteCard(pokemon, pokeomnLocation, true);
		else 
			AIMind.getAIInfo().deleteCard(pokemon, pokeomnLocation, true);
	}
	
	public void activateAbility(AbilityCard abilityObj, boolean isPlayer) {
		abilityObj.activityAbilities(abilityVisitor, this, isPlayer);
	}
	//-------------------getters & setters 
	

	public void setAIInControl(boolean aIInControl) {
		AIInControl = aIInControl;
	}
	public BaseWindow getBaseWindow() {
		return baseWindo;
	}

	public void setBaseWindo(BaseWindow baseWindo) {
		this.baseWindo = baseWindo;
	}

	public boolean isAIInControl() {
		return AIInControl;
	}
	public BaseWindow getBaseWindo() {
		return baseWindo;
	}
	
	public CardCollection<Card> getAIHand(){
		return AIMind.getAIInfo().getHand();
	}
	public CardCollection<Pokemon> getAIBench(){
		return AIMind.getAIInfo().getBench();
	}
	
	public CardCollection<Card> getPlayerHand(){
		return player.getHand();
	}
	public CardCollection<Pokemon> getPlayerBench(){
		return player.getBench();
	}
	
	public Pokemon getAIAttackCard(){
		return AIMind.getAIInfo().getAttackCard();
	}
	
	public Pokemon getPlayerAttackCard(){
		return player.getAttackCard();
	}

	public Users getPlayer() {
		return player;
	}


	public Deck getPlayerDeck() {
		return player.getDeck();
	}

	public Deck getAIDeck() {
		return player.getDeck();
	}
	public CardCollection<Card> getAITrash() {
		return AIMind.getAIInfo().getTrash();
	}
	
	public CardCollection<Card> getPlayerTrash() {
		return player.getTrash();
	}

	public void addCardTOAIHand(Card card) {
		AIMind.getAIInfo().getHand().addCard(card);
	}
	public void addCardTOPlayerHand(Card card) {
		player.getHand().addCard(card);
	}


	
}
