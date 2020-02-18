package com.cardadav.pd.io;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cardadav.pd.model.DeliveryPlaces;

/**
 * Class provides console output.
 */
public class ConsoleOutput {

	/**
	 * Instance of logger.
	 */
	private static final Logger LOG = LogManager.getLogger(ConsoleOutput.class);
	/**
	 * Instance of {@link ScheduledExecutorService}.
	 */
	ScheduledExecutorService executor;
	/**
	 * Instance of {@link ScheduledFuture}.
	 */
	ScheduledFuture<?> future;
	/**
	 * The interval value means how often the periodical console output is done.
	 */
	private final int INTERVAL = 60000;

	/**
	 * Method starts periodical console output.
	 */
	public void start() {
		executor = Executors.newSingleThreadScheduledExecutor();
		future = executor.scheduleWithFixedDelay(new Runnable() {

			@Override
			public void run() {
				LOG.debug("Print delivery places info to the console.");
				DeliveryPlaces.getInstance().printDeliveryPlaces();
			}
		}, 0, INTERVAL, TimeUnit.MILLISECONDS);
	}

	/**
	 * Method stops periodical console output.
	 */
	public void stop() {
		LOG.debug("Shutting down console output.");
		future.cancel(true);
		executor.shutdown();
	}
}
