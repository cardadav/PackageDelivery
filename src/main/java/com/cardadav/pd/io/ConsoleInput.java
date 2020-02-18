package com.cardadav.pd.io;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cardadav.pd.model.DeliveryPlaces;

/**
 * Class provides console input.
 */
public class ConsoleInput extends Thread {

	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(ConsoleInput.class);
	/**
	 * Flag of thread cycle.
	 */
	private AtomicBoolean isRunning = new AtomicBoolean(false);
	/**
	 * Instance of {@link ConsoleOutput}.
	 */
	private ConsoleOutput output;

	/**
	 * Constructor.
	 * 
	 * @param output instance of {@link ConsoleOutput}.
	 */
	public ConsoleInput(ConsoleOutput output) {
		this.output = output;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public void run() {
		isRunning.set(true);
		while (isRunning.get()) {
			Scanner scanner = new Scanner(System.in);
			String text = scanner.nextLine();
			if (text.equals("quit")) {
				LOG.info("Terminate package delivery app.");
				scanner.close();
				isRunning.set(false);
				output.stop();
			} else {
				DeliveryPlaces.getInstance().validatePackage(text);
			}
		}
	}
}
