package com.group16.stardewvalley.model.animal;


public class Fish  {
	private FishType fishType;
	private Integer containingEnergy = 20; //TODO Energy not found
	private ProductQuality productQuality;

	public int getSellPrice() {
		return (int) (this.fishType.getPrice() * productQuality());
	}

	public Fish(FishType fishType) {
		this.fishType = fishType;
	}
	public Fish(FishType fishType, ProductQuality productQuality) {
		this.fishType = fishType;
		this.productQuality = productQuality;
	}


}