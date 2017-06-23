package model;

public class Users {

	public static enum CardLocation{HAND , BENCH , ATTACK, PRIZE};
	protected CardCollection<Card> hand ;
	protected CardCollection<Pokemon> bench; 
	protected Pokemon attackCard ;
	protected CardCollection<Card> prize;
	protected CardCollection<Card> trash;
	protected Deck deck;
	protected boolean firstTurn;
	protected boolean hasUsedEngOREvo;
	
	public  Users(){
		hand = new CardCollection<Card>();
		bench = new CardCollection<Pokemon>();
		trash = new CardCollection<Card>();
		prize = new CardCollection<Card>();
		hasUsedEngOREvo = false;
		firstTurn = true ;
		preparePrizeCards();
	}
	
	public void addCardToBench(Pokemon pokemon){
		bench.addCard(pokemon);
	}
	public void addCardToHand(Card card){
		hand.addCard(card);
	}

	public void addCardToTrash(Card card){
		trash.addCard(card);
	}
	
	//TODO do I have to implement Factory patterns ??
	public void deleteCard(Card card ,CardLocation location, boolean addToTrash){
		switch(location){
			case HAND :
				hand.removeCard(card);
				if(addToTrash)
					addCardToTrash(card);
				break;
			case BENCH:
				bench.removeCard((Pokemon)card);
				if(addToTrash)
					addCardToTrash(card);
				break;
			case ATTACK:
				attackCard = null;
				if(addToTrash)
					addCardToTrash(card);
				break ;
			case PRIZE:
				prize.removeCard(card);
				if(addToTrash)
					addCardToTrash(card);
				break;
		}
	}
	
	public void dealCard(int amount){
		for (int i = 0 ; i < amount; i++ )
			hand.addCard(deck.dealCard());
	}
	

	/*
	 * false means player or AI has win 
	 */
	public Message dealPrizeCard(){
		if(prize.getCardCount() == 0)
			return new Message(false , "no message");
		Card card = prize.getCard(0);
		deleteCard(card , CardLocation.PRIZE, true);
		hand.addCard(card);
		return new Message(true , card.toString());
	}
	
	public Message validateMovingCardToBench(Card card , CardLocation location ){
		if(bench.getCardCount() < 5){
			if(card.getType().equals(Card.CardType.POKEMON)){
				deleteCard(card, location, false);
				bench.addCard((Pokemon) card);
				return new Message(true , " U have move " + card.getName() + "to bench area");
			}
		}
		return new Message(false, "U only can have 5 cards of type Pokemon in the bench area");
	}
	
	public Message vaildateChargingOrEvolvingPokemonOnBench(Pokemon cardOnBench , Card cardToValidate){
		String message= "" ;
		
		if(cardToValidate.getType().equals(Card.CardType.ENERGY)){
				if( !hasUsedEngOREvo && !firstTurn){
					bench.getCard(cardOnBench).setNumOfENERGYCard(cardOnBench.getNumOfENERGYCard()+1);
					deleteCard(cardToValidate,CardLocation.HAND, true);
					hasUsedEngOREvo = true;
				}else{
					message = "you only can one energy card every turn and you not allow to useenergy card in the first turn....";
					return new Message(false , message);
				}
	
			   
		}else if(cardToValidate.getType().equals(Card.CardType.POKEMON)){
			Pokemon castpokemon = (Pokemon) cardToValidate;
			if(castpokemon.getP_Type().equals(Card.PokemonCategory.STAGEONE)
					&& cardOnBench.getP_Type().equals(Card.PokemonCategory.BASIC) ){
				
				//TODO evolve 
//				Card newCard = new Card (cardToValidate.getName(), cardToValidate.getHp(), 
//						Card.PokmonType.STAGE1 , cardToValidate.getAttakcs());
//				newCard.setDamage(cardInfo.getDamage());
//				newCard.setNumOfENERGYCard(cardInfo.getNumOfENERGYCard());
//				player_Bench.setCardAtPosition(cardInfo.getPosition(), newCard);
//				updateCardInfoPanel(player_Bench.getCard(cardOnBench.getPosition()).cardInfo());
//				deleteCard(cardToValidate, true);

			    return new Message(true , "evolve is not yet implemented");
			}
		}
		
		return new Message(false , " system cannot vaildate this action");
	}
	
	public Message validateMovingCardToAttackArea(Card card , CardLocation location){
		
		String message = "";
	
		if( attackCard != null){ 
			
			if(card.getType().equals(Card.CardType.ENERGY)){

				if(!hasUsedEngOREvo && !firstTurn){//TODO do we need specific type of energy card
					attackCard.setNumOfENERGYCard(attackCard.getNumOfENERGYCard() + 1); 
					deleteCard(card ,location,true);
					message = "your pokemon has " + attackCard.getNumOfENERGYCard() + " energy cards" ;
					hasUsedEngOREvo = true ; 
					return new Message(true , message);
				}else{
					message = "you only can one energy card every turn and you not allow to useenergy card in the first turn...." ;
					return new Message(false, message);
				}
				
			}else if(card.getType().equals(Card.CardType.POKEMON)){
					
//					Pokemon castPokemon = (Pokemon)card;
//					if (castPokemon.getP_Type().equals(Card.PokmonType.STAGE1) &&
//					attackCard.getP_Type().equals(Card.PokmonType.LEVEL1)) {
//					 deleteCard(card , player);
//					if(player){
//						player_AttackCard = evolve(card);
//						UpdateJComboBox(player_AttackCard);
//					}else{
//						AI_AttackCard = evolve(card);
//					}
//					message = "a pokemon has evolve to stage 1 pokemon";
//					if(player)
//						updateCommicuntionPanel(message);
//					return true;
//				}else{
//					message = "TO move a card to the attack area it has to be a pokemon type(Stage1) or Energy Card";
//					if(player)
//						updateCommicuntionPanel(message);
					return new Message(true , "not yet implemented");
				}
			return new Message(false , "Cannot evolve");
							
	}else{// no card in the attack area

		if(card.getType().equals(Card.CardType.POKEMON)){
			Pokemon castpokemon = (Pokemon) card;
			if(castpokemon.getP_Type().equals(Card.PokemonCategory.BASIC)) {
				attackCard = castpokemon;
				deleteCard(card ,location, false);
				message = " new pokemon in the attack area \n Pokemon Info \n ";
				message += castpokemon.toString();
				return new Message(true , message);
			}
		}
		return new Message(false, "u only can move Level1 Pokemon to attack area");
	}
}

		

	public void swapPokemon(Pokemon benchPokemon , CardLocation cardLocation) {
			deleteCard(benchPokemon ,cardLocation, false);
			if(cardLocation.equals(CardLocation.BENCH))
				bench.addCard(attackCard);
			else
				hand.addCard(attackCard);
			setAttackCard(benchPokemon);
	}	
	
	private void preparePrizeCards(){
		Energy energy1 = new Energy("P_Energy", Card.CardType.ENERGY, Card.EnegeryCategory.COLORLESS);
		Energy energy2 = new Energy("P_Energy2",Card.CardType.ENERGY,Card.EnegeryCategory.COLORLESS);
		prize.addCard(energy1);
		prize.addCard(energy2);	
	}
	
	public void attackCardGotKilled(){
		setAttackCard(null);
	}
	
	/**
	 * to be implemented 
	 * @param card
	 * @return
	 */
//	private Card evolve(Pokemon card){
////		Card newCard = new Card (card.getName(), card.getHp(), Card.PokmonType.STAGE1 , card.getAttakcs());
////		newCard.setDamage(card.getDamage());
////		newCard.setNumOfENERGYCard(card.getNumOfENERGYCard());
////		UpdateJComboBox(newCard); // To update combobox
//		return newCard;
//	}
	
	//------
	public CardCollection<Pokemon> getBench() {
		return bench;
	}
	public Pokemon getAttackCard() {
		return attackCard;
	}
	public void setDeck(Deck deck) {
		this.deck = deck;
		dealCard(7);
	}
	public void setAttackCard(Pokemon card){
		attackCard = card;
	}

	public boolean isFirstTurn() {
		return firstTurn;
	}

	public void setFirstTurn(boolean firstTurn) {
		this.firstTurn = firstTurn;
	}

	public CardCollection<Card> getHand() {
		return hand;
	}

	public void setHasUsedEngOREvo(boolean hasUsedEngOREvo) {
		this.hasUsedEngOREvo = hasUsedEngOREvo;
	}

	public CardCollection<Card> getPrize() {
		return prize;
	}

	public int getDeckSize(){
		return deck.cardsLeft();
	}
	
	public CardCollection<Card> getTrash() {
		return trash;
	}

	public int getTrashSize(){
		return trash.getCardCount();
	}
	public Deck getDeck(){
		return deck;
	}

	public void removeStatus() {
		attackCard.removestatus();		
	}
}
