package model;

public class Energy extends Card {

	private EnegeryCategory category;
	public Energy(String name, CardType type,EnegeryCategory category) {
		super(name, type);
		this.category = category;
	}
	public EnegeryCategory getCategory() {
		return category;
	}
	public void setCategory(EnegeryCategory category) {
		this.category = category;
	}

}
