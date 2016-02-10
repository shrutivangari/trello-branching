package com.trello;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Creates an instance of the {@link ApiConnection} class
 * 
 * @author Shruti Vangari
 *
 */

public enum ApiConnectionFactory {

	INSTANCE;

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnectionFactory.class);

	/**
	 * Validates a string and creates a new URL if it is a valid string
	 * 
	 * @param inputUrl
	 *            The URL input as a string to be validated Cannot be null or
	 *            blank or empty Should begin with UrlPrepender https://
	 * 
	 * @return a {@link URL}
	 * 
	 * @throws IllegalStateException
	 *             If a URL could not be created from the given string input
	 * 
	 */
	@VisibleForTesting
	URL validateInputUrl(String inputUrl) {
		checkNotNull(inputUrl, "inputUrl:null");
		checkArgument(!inputUrl.trim().isEmpty(), "inputUrl:blank");
		checkArgument(inputUrl.startsWith("https://"),"inputUrl:invalid");
		LOGGER.info("The inputUrl {} is valid", inputUrl);
		try {
			return new URL(inputUrl);
		} catch (MalformedURLException e) {
			LOGGER.error("Could not create new URL");
			throw new IllegalStateException("Could not create new URL", e);
		}
	}

	/**
	 * @param inputUrl
	 *            The URL input as a string to be validated Cannot be null or
	 *            blank or empty Should begin with UrlPrepender https://
	 * 
	 * @return a {@link ApiConnection} instance
	 * 
	 * @throws IllegalStateException
	 *             If a URL could not be created from the given string input
	 * 
	 */
	public ApiConnection createApiConnection(String inputUrl) {
		return new ApiConnection(validateInputUrl(inputUrl));
	}

}
