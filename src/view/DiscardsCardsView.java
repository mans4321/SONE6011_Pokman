package view;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import model.Card;
import model.CardCollection;

public class DiscardsCardsView extends JPanel  {
	
	 private JComboBox<Card> playerTrashCardsJComboBox ; 
	 private JComboBox<Card> AITrashCardsJComboBox;
	 private JTextArea playerCardInfo;
	 private JTextArea AICardInfo;
	 private Font smallFont;
	 private JScrollPane playercardScroll;
	 private JScrollPane AICardScroll;
	 
	
	 public DiscardsCardsView(){
		 setLayout(null);  
		 smallFont = new Font("SansSerif",Font.PLAIN, 14);
		 playerTrashCardsJComboBox = new JComboBox<Card>();
		 AITrashCardsJComboBox     = new JComboBox<Card>();
		 playerCardInfo = new JTextArea() ; 
		 AICardInfo = new JTextArea() ;
		 setTextArea();
		 playercardScroll = new JScrollPane(playerCardInfo);
		 AICardScroll = new JScrollPane(AICardInfo);
		 playercardScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 AICardScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		 
		 add(playerTrashCardsJComboBox);
		 add(AITrashCardsJComboBox);
		 add(playercardScroll);
		 add(AICardScroll);
		 
		 playerTrashCardsJComboBox.setBounds(5, 10 , 100 , 40);
		 AITrashCardsJComboBox.setBounds(5, 120 , 100 , 40);
		 playercardScroll.setBounds(30, 10 , 100 , 100);
		 AICardScroll.setBounds(30, 120 , 100 , 100);	 
		 setComponentsLocation();
	 }
	 
	 private void setTextArea(){
		 	playerCardInfo.setFont(smallFont);
		 	AICardInfo.setFont(smallFont);
			playerCardInfo.setLineWrap(true);
			AICardInfo.setLineWrap(true);
			playerCardInfo.setWrapStyleWord(true);
			AICardInfo.setWrapStyleWord(true);
			playerCardInfo.setEditable(false);
			AICardInfo.setEditable(false);
	 }
	 
	 private void setComponentsLocation(){
		 playercardScroll.setBounds(5, 10 ,160,140);
		 AICardScroll.setBounds(5, 170 ,160,140);	
		 playerTrashCardsJComboBox.setBounds(180, 65 , 120 , 20);
		 AITrashCardsJComboBox.setBounds(180, 230 , 120 , 20);
	 }
	 
	 public void setPlayerCardInfo(String cardInfo){
		 playerCardInfo.setText(cardInfo);
	 }
	 public void setAICardInfo(String cardInfo){
		 playerCardInfo.setText(cardInfo);
	 }

	 public void setplayerANDTrashCardsJComboBox(CardCollection<Card> playerTrash , CardCollection<Card> AITrash){
		 			Card [] playertrashCards = new Card[playerTrash.getCardCount()] ;
		 			Card [] AItrashCards = new Card[playerTrash.getCardCount()] ;
		 			playerTrash.getArrayList().toArray(playertrashCards);
		 			AITrash.getArrayList().toArray(AItrashCards);
		 			playerTrashCardsJComboBox.setModel(new JComboBox<Card>(playertrashCards).getModel());
		 			AITrashCardsJComboBox.setModel(new JComboBox<Card>(AItrashCards).getModel());
	 }
	 

	public JComboBox<Card> getPlayerTrashCardsJComboBox() {
		return playerTrashCardsJComboBox;
	}

	public JComboBox<Card> getAITrashCardsJComboBox() {
		return AITrashCardsJComboBox;
	}
	 

}
