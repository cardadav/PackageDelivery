package com.cardadav.pd.model;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class collects all delivery places.
 */
public class DeliveryPlaces {

	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(DeliveryPlaces.class);
	/**
	 * Singleton instance of {@link DeliveryPlaces}.
	 */
	private volatile static DeliveryPlaces instance;
	/**
	 * Collection of all delivery places.
	 */
	private Map<String, DeliveryPlace> deliveryPlaces;
	/**
	 * Instance of {@link PriceList}.
	 */
	private final PriceList priceList;

	/**
	 * Constructor.
	 */
	private DeliveryPlaces() {
		this.deliveryPlaces = new HashMap<String, DeliveryPlace>();
		this.priceList = new PriceList();
	}

	/**
	 * Singleton method.
	 * 
	 * @return Instance of {@link DeliveryPlaces}.
	 */
	synchronized public static DeliveryPlaces getInstance() {
		if (instance == null) {
			instance = new DeliveryPlaces();
		}
		return instance;
	}

	/**
	 * Getter for price list.
	 * 
	 * @return instance of {@link PriceList}.
	 */
	public PriceList getPriceList() {
		return priceList;
	}
	
	/**
	 * Getter for delivery places.
	 * 
	 * @return Map of delivery places.
	 */
	public Map<String, DeliveryPlace> getDeliveryPlaces() {
		return deliveryPlaces;
	}

	/**
	 * Method validates if package string matches to pattern.
	 * 
	 * @param input Input string from file or console input.
	 */
	public void validatePackage(String input) {
		LOG.debug("Validate package from string. Input=" + input);
		if (input.isEmpty()) {
			LOG.warn("Empty input string.");
			return;
		} else {
			String[] inputs = input.split(" ");
			if (inputs.length == 2) {
				if (!Pattern.matches("^\\d*\\.\\d{1,3}$|^\\d+$", inputs[0])) {
					LOG.error("Value " + inputs[0] + " doesn't match to weight pattern.");
					return;
				}
				if (!Pattern.matches("[0-9]{5}", inputs[1])) {
					LOG.error("Value " + inputs[1] + " doesn't match to postal code pattern.");
					return;
				}
				double weight = Double.parseDouble(inputs[0]);
				if (weight <= 0) {
					LOG.error("Value of weight has to be greater than 0.");
					return;
				}
				addPackage(inputs[1], weight);
			} else {
				LOG.warn("Invalid input string.");
			}
		}
	}

	/**
	 * Adds package to the delivery place with the same postal code.
	 * 
	 * @param postalCode Identification of delivery place.
	 * @param weight Weight of the package.
	 */
	public void addPackage(String postalCode, double weight) {
		LOG.debug("Process new package info. PostalCode=" + postalCode, " weight=" + weight);
		Double cost = priceList.findPrice(weight);
		if (deliveryPlaces.containsKey(postalCode)) {
			DeliveryPlace dp = deliveryPlaces.get(postalCode);
			dp.setWeight(weight += dp.getWeight());
			dp.setCost(cost + dp.getCost());
			deliveryPlaces.put(postalCode, dp);
		} else {
			deliveryPlaces.put(postalCode, new DeliveryPlace(postalCode, weight, cost));
		}
	}

	/**
	 * Prints information of all delivery places.
	 */
	public void printDeliveryPlaces() {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
		df.applyPattern("#0.000");
		System.out.println();
		if (priceList.exists()) {
			DecimalFormat dfc = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
			dfc.applyPattern("#0.00");
			deliveryPlaces.entrySet().stream().forEach(e -> {
				System.out.println(e.getKey() + " " + df.format(e.getValue().getWeight()) + " " + dfc.format(e.getValue().getCost()));
			});
		} else {
			deliveryPlaces.entrySet().stream().forEach(e -> {
				System.out.println(e.getKey() + " " + df.format(e.getValue().getWeight()));
			});
		}
		System.out.println();
	}
}
