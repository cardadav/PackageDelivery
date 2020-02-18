package com.cardadav.pd;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(AppTest.class);

	@BeforeEach
	public void cleanArgs() {
		App.dataPath = null;
		App.priceListPath = null;
	}

	@Test
	public void invalidArguments() {
		LOG.info("===Test Case: invalid arguments===");
		String[] args = new String[] { "abc", "def" };
		App.parseArguments(args);
		assertNull(App.dataPath);
		assertNull(App.priceListPath);
	}

	@Test
	public void wrongPath() {
		LOG.info("===Test Case: wrong path===");
		String[] args = new String[] { "-data=.\\src\\main\\resources\\", "-priceList=.\\src\\main\\resources\\" };
		App.parseArguments(args);
		assertEquals(".\\src\\main\\resources\\", App.dataPath);
		assertEquals(".\\src\\main\\resources\\", App.priceListPath);

		assertThrows(IOException.class, () -> {
			App.readPriceList();
		});
		assertThrows(IOException.class, () -> {
			App.readInitialData();
		});
	}

	@Test
	public void validPath() {
		LOG.info("===Test Case: valid path===");
		String[] args = { "-data=.\\src\\main\\resources\\data.txt",
				"-priceList=.\\src\\main\\resources\\priceList.txt" };
		App.parseArguments(args);
		assertEquals(".\\src\\main\\resources\\data.txt", App.dataPath);
		assertEquals(".\\src\\main\\resources\\priceList.txt", App.priceListPath);
		assertDoesNotThrow(() -> {
			App.readPriceList();
		});
		assertDoesNotThrow(() -> {
			App.readInitialData();
		});
	}
}
