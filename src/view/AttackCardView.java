package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import model.Card;
import model.Pokemon;
import model.ability.AbilityCard;

public class AttackCardView extends JPanel implements CoordinateView {

	   
     private Pokemon card ;
     private Font smallFont;    // Font that will be used to draw the cards.
     private boolean player;
     private JButton attackButoon;
     private JButton retreatButoon ;
     private JButton swapButton;
  
     private JComboBox<AbilityCard> jComboBox ; 
     
     public AttackCardView( boolean player){
    	 this.player = player ; 
    	 smallFont = new Font("SansSerif",Font.PLAIN, 14);
    	 setLayout(null);  
         inti();
	   }
     
     void inti() {
    	 
    	 if(player){
    		 jComboBox = new JComboBox<AbilityCard>();
    		 attackButoon = new JButton("Attack");
    		 retreatButoon = new JButton("Retreat ");
    		 swapButton = new JButton("Swap");
	    	
	    	add(jComboBox);
	    	add(attackButoon);
	    	add(retreatButoon);
	    	add(swapButton);
	    	
	    	jComboBox.setBounds(510, 30, 120, 20);
	    	attackButoon.setBounds(510,55, 120, 20);
	    	retreatButoon.setBounds(280,30, 120, 20);
	    	swapButton.setBounds(280,55, 120, 20);
    	 }
     } 

     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setFont(smallFont);
         drawCard(g, card, getWidth()/2 - 80 , 10);
     }


 	   /**
 	    * Draws a card as a 80 by 100 rectangle with upper left corner at (x,y).
 	    * The card is drawn in the graphics context g.  If card is null, then
 	    * a face-down card is drawn.  (The cards are rather primitive!)
 	    */
 	   public  void drawCard(Graphics g, Card card, int xCoor, int yCoor) {
 	       if (card == null) {  
 	           // Draw a face-down card
 	           g.setColor(Color.RED);
 	           g.fillRect(xCoor , yCoor,80,100);
 	           g.setColor(Color.WHITE);
 	           g.drawRect(xCoor+3 ,yCoor+3 ,73,93);
 	           g.drawRect(xCoor+4 ,yCoor+4 ,71,91);
 	       }
 	       else {
 	           g.setColor(Color.WHITE);
 	           g.fillRect(xCoor ,yCoor ,80,100);
 	           g.setColor(Color.GRAY);
 	           g.drawRect(xCoor ,yCoor ,79,99);
 	           g.drawRect(xCoor+1 ,yCoor+1  ,77,97);
 	           g.setColor(Color.RED);
 	           g.setFont(smallFont);
 	           g.drawString("Name:", xCoor + 5 , yCoor + 15 );
 	           g.setColor(Color.BLACK);
 	           g.drawString( card.getName(), xCoor + 10 , yCoor + 30 );
 	           g.setColor(Color.RED);
 	           g.drawString("Type:", xCoor + 5, yCoor + 50 );
 	           g.setColor(Color.BLACK);
 	           g.drawString(card.getType().toString(), xCoor + 10, yCoor + 65 );
 	       }
 	   } //

 		public Pokemon getCard() {
 			return card;
 		}

 		public void setCard(Pokemon card) {
 			this.card = card;
 			if(this.card != null && player){
 				AbilityCard [] abilityList = new AbilityCard[card.getAbilityCards().size()] ;
	 			this.card.getAbilityCards().toArray(abilityList);
	 			jComboBox.setModel(new JComboBox<AbilityCard>(abilityList).getModel());
 			}
 		}
 		
 	//----------	implements Coordinate

	@Override
	public void refresh() {
		repaint();
	}
	@Override
	public void setBackgroundColor(Color color) {
		setBackground(color);
	}
	@Override
	public void setViewLocation(int x, int y, int width, int height) {
		setBounds(x , y, width , height );
	}

	///--------------------- getter and setter 
	
	
	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}

	public JButton getAttackButoon() {
		return attackButoon;
	}

	public void setAttackButoon(JButton attackButoon) {
		this.attackButoon = attackButoon;
	}

	public JComboBox<AbilityCard> getjComboBox() {
		return jComboBox;
	}

	public void setjComboBox(JComboBox<AbilityCard> jComboBox) {
		this.jComboBox = jComboBox;
	}

	public JButton getRetreatButoon() {
		return retreatButoon;
	}

	public JButton getSwapButton() {
		return swapButton;
	}

	

	
}
