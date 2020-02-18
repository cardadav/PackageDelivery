package com.cardadav.pd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cardadav.pd.io.ConsoleInput;
import com.cardadav.pd.io.ConsoleOutput;
import com.cardadav.pd.model.DeliveryPlaces;

/**
 * Main class of the PackageDelivery application.
 */
public class App {

	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(App.class);
	/**
	 * Path to initial data.
	 */
	public static String dataPath;
	/**
	 * Path to price list.
	 */
	public static String priceListPath;

	/**
	 * Main method of the PackageDelivery application.
	 * 
	 * @param args Argument from command line.
	 */
	public static void main(String[] args) {
		LOG.info("Package delivery app started.");
		parseArguments(args);
		try {
			readPriceList();
		} catch (IOException e) {
			LOG.error("Invalid path to price list.", e);
		}
		try {
			readInitialData();
		} catch (IOException e) {
			LOG.error("Invalid path to initial data.", e);
		}

		LOG.debug("Starting I/O threads.");
		ConsoleOutput consoleOutput = new ConsoleOutput();
		ConsoleInput consoleInput = new ConsoleInput(consoleOutput);
		consoleInput.start();
		consoleOutput.start();
	}

	/**
	 * Method process command line argument.
	 * 
	 * @param args Argument from command line.
	 */
	protected static void parseArguments(String[] args) {
		for (String arg : args) {
			LOG.debug("Parse argument. Argument=" + arg);
			String[] argument = arg.split("=");
			if (argument[0].equals("-data")) {
				dataPath = argument[1];
			} else if (argument[0].equals("-priceList")) {
				priceListPath = argument[1];
			}
		}
	}

	/**
	 * Read data from price list file.
	 * 
	 * @throws IOException If path to price list is not correct.
	 */
	protected static void readPriceList() throws IOException {
		LOG.debug("Read price list from file. Path=" + priceListPath);
		if (priceListPath != null && !priceListPath.isEmpty()) {
			BufferedReader reader = new BufferedReader(new FileReader(priceListPath));
			String line;
			while ((line = reader.readLine()) != null) {
				DeliveryPlaces.getInstance().getPriceList().validatePrice(line);
			}
			reader.close();
			DeliveryPlaces.getInstance().getPriceList().sortPriceList();
		}
	}

	/**
	 * Read initial data from file.
	 * 
	 * @throws IOException If path to initial data is not correct.
	 */
	protected static void readInitialData() throws IOException {
		LOG.debug("Read initial data from file. Path=" + dataPath);
		if (dataPath != null && !dataPath.isEmpty()) {
			BufferedReader reader = new BufferedReader(new FileReader(dataPath));
			String line;
			while ((line = reader.readLine()) != null) {
				DeliveryPlaces.getInstance().validatePackage(line);
			}
			reader.close();
		}
	}
}
