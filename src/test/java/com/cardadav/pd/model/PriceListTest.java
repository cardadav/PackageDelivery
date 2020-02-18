package com.cardadav.pd.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PriceListTest {
	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(PriceListTest.class);

	@BeforeEach
	public void clearMap() {
		if (DeliveryPlaces.getInstance().getPriceList().getPriceList() != null) {
			LOG.debug("Clear price list data.");
			DeliveryPlaces.getInstance().getPriceList().getPriceList().clear();
		}
	}

	@Test
	public void validateEmptyString() {
		LOG.info("===Test Case: empty string===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("");
		assertTrue(DeliveryPlaces.getInstance().getPriceList().getPriceList().isEmpty());
	}

	@Test
	public void validateInvalidString() {
		LOG.info("===Test Case: invalid string===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("xxx");
		assertTrue(DeliveryPlaces.getInstance().getPriceList().getPriceList().isEmpty());
	}

	@Test
	public void validateInvalidWeight() {
		LOG.info("===Test Case: invalid weight===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("xx 0.50");
		assertTrue(DeliveryPlaces.getInstance().getPriceList().getPriceList().isEmpty());
	}

	@Test
	public void validateInvalidPrice() {
		LOG.info("===Test Case: invalid price===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("0.2 xx");
		assertTrue(DeliveryPlaces.getInstance().getPriceList().getPriceList().isEmpty());
	}

	@Test
	public void validateZeroWeightValue() {
		LOG.info("===Test Case: zero weight value===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("0 0.50");
		assertTrue(DeliveryPlaces.getInstance().getPriceList().getPriceList().isEmpty());
	}

	@Test
	public void validateValidString() {
		LOG.info("===Test Case: valid string===");
		DeliveryPlaces.getInstance().getPriceList().validatePrice("0.2 0.50");
		assertEquals(0.5, DeliveryPlaces.getInstance().getPriceList().findPrice(0.2));
	}
}
