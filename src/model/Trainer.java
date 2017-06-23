package model;

public class Trainer extends Card  {
	TrainerCategory trainerCat;
	
	public Trainer(String name, CardType type, TrainerCategory trainerCat) {
		super(name, type);
		this.trainerCat = trainerCat;
	}

	public TrainerCategory getTrainerCat() {
		return trainerCat;
	}
	
	
	
}
