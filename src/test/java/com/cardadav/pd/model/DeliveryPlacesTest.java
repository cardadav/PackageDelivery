package com.cardadav.pd.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeliveryPlacesTest {
	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(DeliveryPlacesTest.class);

	@BeforeEach
	public void clearMap() {
		LOG.debug("Clear delivery places data.");
		DeliveryPlaces.getInstance().getDeliveryPlaces().clear();
	}

	@Test
	public void validateEmptyString() {
		LOG.info("===Test Case: empty string===");
		DeliveryPlaces.getInstance().validatePackage("");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().isEmpty());
	}

	@Test
	public void validateInvalidString() {
		LOG.info("===Test Case: invalid string===");
		DeliveryPlaces.getInstance().validatePackage("xxx");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().isEmpty());
	}

	@Test
	public void validateInvalidWeight() {
		LOG.info("===Test Case: invalid weight===");
		DeliveryPlaces.getInstance().validatePackage("xx 12345");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().isEmpty());
	}

	@Test
	public void validateInvalidPostalCode() {
		LOG.info("===Test Case: invalid postal code===");
		DeliveryPlaces.getInstance().validatePackage("0.6 xxxxx");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().isEmpty());
	}

	@Test
	public void validateZeroWeightValue() {
		LOG.info("===Test Case: zero weight value===");
		DeliveryPlaces.getInstance().validatePackage("0 12345");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().isEmpty());
	}

	@Test
	public void validateValidString() {
		LOG.info("===Test Case: valid string===");
		DeliveryPlaces.getInstance().validatePackage("5.5 12345");
		assertTrue(DeliveryPlaces.getInstance().getDeliveryPlaces().containsKey("12345"));
	}
}
