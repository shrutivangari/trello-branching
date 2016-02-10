package com.trello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;
import com.trello.UnrecognizedResponseException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import static org.apache.commons.io.IOUtils.closeQuietly;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Connection to the API - github or trello
 * Returns JSON data in string format
 * 
 * @author Shruti Vangari
 *
 */
public class ApiConnection 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnection.class);
	private URL apiUrl;
    
    /**
     * Constructor for testing the mock apiUrl
     *   
     * @param apiURL URL of the API to connect to.
     *           This parameter cannot be null or empty
     *           Also should begin with "https://"
     *           Eg: https://api.github.com/
     * 
     */
    ApiConnection(URL apiUrl) {
        checkNotNull(apiUrl, "apiUrl:null");
        this.apiUrl = apiUrl;
    }
    
    /**
     * Connects to a RESTful API - Trello or Github
     * 
     * @return JSON data in a String format
     * 
     * @throws UnrecognizedResponseException
     *             when the connection from {@link HttpURLConnection} returns a
     *             response code that is not in the {@link HttpResponseCode} ENUM class
     *             or when while there was a problem while connecting to an API 
     *             or when a valid responseCode cannot be returned  
     *  
     */
    public String getApiData() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            HttpResponseCode httpResponseCode = getResponseCode(connection); 
            switch (httpResponseCode) {
                case HTTP_OK:
                    LOGGER.info("Successful connection to : {} , code : {} , message : {} , ",
                            apiUrl, httpResponseCode.getCode(), httpResponseCode.getMessage());
                    LOGGER.info("Successfully read data from the api");
                    return readOutputFromApi(connection);
                case HTTP_UNKNOWN:
                    LOGGER.warn("Unsuccessful connection to : {} , error code : {} , error message : {}",
                            apiUrl, httpResponseCode.getCode(), httpResponseCode.getMessage());
                    throw new UnrecognizedResponseException("Response Code was not found");
                default:
                    LOGGER.warn(
                            "Unsuccessful connection to : {} , error code : {} , error message : {}",
                            apiUrl, httpResponseCode.getCode(), httpResponseCode.getMessage());
                    return "{}";
            }
        } catch (IOException e) {
            LOGGER.error("The URL passed {} is not valid : {} ", apiUrl, e);
            throw new UnrecognizedResponseException("Response Code was not found", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * @param connection
     *            {@link HttpURLConnection} to the API
     * 
     * @return {@link HttpResponseCode} for the URL input
     * 
     * @throws IOException
     *             if the output from the API cannot be read
     * 
     */
    @VisibleForTesting
    HttpResponseCode getResponseCode(HttpURLConnection connection) throws IOException {
        return HttpResponseCode.valueOf(connection.getResponseCode());
    }
    
    /**
     * @param connection
     *            of type {@link HttpURLConnection}
     * 
     * @return JSON data in string format The output data could be empty {} if
     *         there is no JSON data for the given API
     * 
     * @throws IOException
     *             if the output from the API cannot be read
     * 
     */
    private String readOutputFromApi(HttpURLConnection connection) throws IOException {
        BufferedReader reader = null;
        try {
            InputStream stream = connection.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream);
            reader = new BufferedReader(streamReader);
            return reader.readLine();
        } finally {
                closeQuietly(reader);
        }
    }
	
}


