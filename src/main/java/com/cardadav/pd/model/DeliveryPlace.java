package com.cardadav.pd.model;

/**
 * Delivery place model.
 */
public class DeliveryPlace {
	/**
	 * Postal code, main identification of delivery place.
	 */
	private String postalCode;
	/**
	 * Total weight of all packages delivered to the postal code.
	 */
	private double weight;
	/**
	 * Total cost of all packages delivered to the postal code.
	 */
	private double cost;

	/**
	 * Constructor.
	 * 
	 * @param postalCode Identification of delivery place.
	 * @param weight     Total weight of all packages.
	 * @param cost       Total cost of all packages.
	 */
	public DeliveryPlace(String postalCode, double weight, double cost) {
		this.postalCode = postalCode;
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * Getter for postal code.
	 * 
	 * @return Identification of delivery place.
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * Setter for postal code.
	 * 
	 * @param postalCode Identification of delivery place.
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * Getter for weight.
	 * 
	 * @return Total weight of all packages.
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * Setter for weight.
	 * 
	 * @param weight Total weight of all packages.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * Getter for cost.
	 * 
	 * @return Total cost of all packages.
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Setter for cost.
	 * 
	 * @param cost Total cost of all packages.
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
