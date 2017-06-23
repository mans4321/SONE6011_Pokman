package model;

import java.util.Random;

public class Coin {

	private int face;
	private Random rand;
	private boolean success; 
	public Coin(){
		rand = new Random();
		roll();
	}
	public void roll(){
		setFace(rand.nextInt(50) + 1 % 2);
	}
	public int getFace() {
		return face;
	}
	public void setFace(int face) {
		this.face = face;
	}
	public boolean isSuccess() {
		return face == 1 ? true : false;
	}
}
