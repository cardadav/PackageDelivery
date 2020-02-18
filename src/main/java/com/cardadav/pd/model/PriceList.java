package com.cardadav.pd.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class collects all prices.
 */
public class PriceList {

	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(PriceList.class);
	/**
	 * List of all prices.
	 */
	private List<Price> priceList;

	/**
	 * Getter for price list.
	 * 
	 * @return Collection of all prices.
	 */
	public List<Price> getPriceList() {
		return priceList;
	}

	/**
	 * Method verify if price list is not null and has some values.
	 * 
	 * @return {@code True} if price list is not null and has some values,
	 *         {@code false} otherwise.
	 */
	public boolean exists() {
		return (priceList != null && !priceList.isEmpty());
	}

	/**
	 * Method validates if price string matches to pattern.
	 * 
	 * @param input Line from price list file.
	 */
	public void validatePrice(String input) {
		LOG.debug("Validate price from string. Input=" + input);
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
				if (!Pattern.matches("^\\d*\\.\\d{1,2}$|^\\d+$", inputs[1])) {
					LOG.error("Value " + inputs[1] + " doesn't match to price pattern.");
					return;
				}
				double weight = Double.parseDouble(inputs[0]);
				if (weight <= 0) {
					LOG.error("Value of weight has to be greater than 0.");
					return;
				}
				addPrice(weight, inputs[1]);
			} else {
				LOG.warn("Invalid input string.");
			}
		}
	}

	/**
	 * Adds price value to the price list.
	 * 
	 * @param weight Value of minimum weight for price.
	 * @param price  Value of price.
	 */
	public void addPrice(double weight, String price) {
		if (priceList == null) {
			this.priceList = new ArrayList<Price>();
		}
		LOG.debug("Adding new price value. Weight=" + weight + ", price=" + price + ".");
		priceList.add(new Price(weight, Double.parseDouble(price)));
	}

	/**
	 * Method sorts list of all prices.
	 */
	public void sortPriceList() {
		if (exists()) {
			LOG.debug("Sorting price list.");
			priceList.sort(new Comparator<Price>() {

				public int compare(Price p1, Price p2) {
					return Double.compare(p1.getWeight(), p2.getWeight());
				}
			});
		}
	}

	/**
	 * Method finds price for package weight.
	 * 
	 * @param weight Weight of package.
	 * @return Price for package delivery.
	 */
	public double findPrice(double weight) {
		LOG.debug("Looking for price for package weight=" + weight);
		if (exists()) {
			double priceValue = priceList.get(0).getPrice();
			for (Price price : priceList) {
				if (weight >= price.getWeight()) {
					priceValue = price.getPrice();
				} else {
					return priceValue;
				}
			}
			return priceValue;
		}
		return 0;
	}

	/**
	 * Class represents value from price list.
	 */
	private class Price {
		/**
		 * Value of minimum weight for price.
		 */
		private final double weight;
		/**
		 * Value of price.
		 */
		private final double price;

		/**
		 * Constructor.
		 * 
		 * @param weight Minimum weight for price.
		 * @param price  Value of price.
		 */
		public Price(double weight, double price) {
			this.weight = weight;
			this.price = price;
		}

		/**
		 * Getter for weight.
		 * 
		 * @return Minimum weight for price.
		 */
		public double getWeight() {
			return weight;
		}

		/**
		 * Getter for price.
		 * 
		 * @return Value of price.
		 */
		public double getPrice() {
			return price;
		}
	}
}
