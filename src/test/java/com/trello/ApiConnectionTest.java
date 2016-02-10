package com.trello;

import com.trello.ApiConnection;
import com.trello.HttpResponseCode;
import com.trello.UnrecognizedResponseException;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Unit tests for {@link ApiConnection}
 * 
 * @author Shruti Vangari
 * 
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({URL.class, ApiConnection.class})
public class ApiConnectionTest {
    
    @Mock
    private URL urlMock;
    
    private ApiConnection apiTest;
    
    private URL setUpConnection(String urlInput) throws Exception {
        whenNew(URL.class).withArguments(urlInput).thenReturn(urlMock);
        return urlMock;
    }
    
    private void setUpApiConnectionConstructor(String inputUrl) throws Exception {
        apiTest = new ApiConnection(setUpConnection(inputUrl));
    }
    
    private HttpURLConnection setupForResponseCode(int responseCode) throws Exception {
        HttpURLConnection httpUrlConnectionMock = mock(HttpURLConnection.class);
        when(urlMock.openConnection()).thenReturn(httpUrlConnectionMock);
        when(httpUrlConnectionMock.getResponseCode()).thenReturn(responseCode);
        return httpUrlConnectionMock;
    }
    
    private BufferedReader setUpMockForreadOutputFromApi(HttpURLConnection httpUrlConnectionMock) throws Exception {
        InputStream inputStreamMock = mock(InputStream.class);
        when(httpUrlConnectionMock.getInputStream()).thenReturn(inputStreamMock);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStreamMock);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader;
    }
    
    /**
     * Unit test to check if {@link ApiConnection} throws a NullPointerException when a 
     * null string input is sent to the constructor
     * 
     */
    
    @Test(expected = NullPointerException.class)
    public void testURLForNull() throws Exception {
        new ApiConnection(null);
    }
    
    /**
     * Unit test to check if the {@link ApiConnection#getResponseCode(HttpURLConnection)} returns 
     * {@link HttpResponseCode#HTTP_OK} when the response code is 200
     * 
     */
    @Test
    public void testConnectForCorrectUrl() throws Exception {
        String correctUrl = "https://fluff.com";
        setUpApiConnectionConstructor(correctUrl);
        HttpURLConnection httpUrlConnectionMock = setupForResponseCode(200);
        setUpMockForreadOutputFromApi(httpUrlConnectionMock);
        assertEquals(HttpResponseCode.HTTP_OK, apiTest.getResponseCode(httpUrlConnectionMock));
    }
    
    /**
     * Unit test to check if {@link ApiConnection#getGithubData()} throws an 
     * {@link UnrecognizedResponseException} when the {@link HttpURLConnection} throws an IOException
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testReadOutputFromApiForException() throws Exception {
        String correctUrl = "https://fluff.com";
        setUpApiConnectionConstructor(correctUrl);
        HttpURLConnection httpUrlConnectionMock = setupForResponseCode(200);
        when(httpUrlConnectionMock.getInputStream()).thenThrow(new IOException());
        apiTest.getApiData();
    }
    
    /**
     * Unit test to check if {@link ApiConnection#getGithubData()} throws an 
     * {@link UnrecognizedResponseException} when the {@link HttpURLConnection} throws an IOException
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testReadOutputFromApiForJsonOutput() throws Exception {
        String correctUrl = "https://fluff.com";
        setUpApiConnectionConstructor(correctUrl);
        setUpMockForreadOutputFromApi(setupForResponseCode(200));
        assertNotNull(apiTest.getApiData());
    }

    /**
     * Unit test to check if the {@link ApiConnection#getResponseCode(HttpURLConnection)} returns 
     * {@link HttpResponseCode#HTTP_NOT_FOUND} when the response code is 404
     * 
     */
    @Test
    public void testConnectForUrlNotFound() throws Exception {
        String correctUrl = "https://testWithMock.com";
        setUpApiConnectionConstructor(correctUrl);
        assertEquals(HttpResponseCode.HTTP_NOT_FOUND, apiTest.getResponseCode(setupForResponseCode(404)));
    }

    /**
     * Unit test to check if the UnrecognizedResponseCodeException is thrown
     * when an invalid response code is passed to {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testConnectForInvalidResponseCode() throws Exception {
        String someUrl = "https://IamAnInvalidResponseCode";
        setUpApiConnectionConstructor(someUrl);
        setupForResponseCode(1000);
        apiTest.getApiData();
    }
    
    /**
     * Unit test to check if the {@link HttpResponseCode#HTTP_NOT_FOUND} response code is returned when a
     * 404 response code is passed to {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test
    public void testConnectForHttpNotFoundUrl() throws Exception {
        String httpNotFound = "https://github.com/yodaTheBlah";
        setUpApiConnectionConstructor(httpNotFound);
        assertEquals(HttpResponseCode.HTTP_NOT_FOUND, apiTest.getResponseCode(setupForResponseCode(404)));
    }

    /**
     * Unit test to check if an {@link UnrecognizedResponseException} is thrown
     * when a URL that does not contain https:// is passed to {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testConnectForIncorrectUrl() throws Exception {
        String incorrectUrl = "http://www.iamanincorrecturi.com/";
        setUpApiConnectionConstructor(incorrectUrl);
        setupForResponseCode(0); 
        apiTest.getApiData();
    }

    /**
     * Unit test to check if the {@link HttpResponseCode#HTTP_NOT_FOUND} response code is returned when a URL
     * with a wrong PATH parameter is passed to {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test
    public void testConnectForWrongUrl() throws Exception {
        String wrongUrl = "https://www.google.com/IWasNotFound";
        setUpApiConnectionConstructor(wrongUrl);
        assertEquals(HttpResponseCode.HTTP_NOT_FOUND, apiTest.getResponseCode(setupForResponseCode(404)));
    }

    /**
     * Unit test to check {@link UnrecognizedResponseException} is thrown when a
     * non-HTTP URL is passed to {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testConnectForNonHttpUrl() throws Exception {
        String incorrectUrl = "idonothing";
        setUpApiConnectionConstructor(incorrectUrl);
        setupForResponseCode(0);
        apiTest.getApiData();
    }
    
    /**
     * Unit test to check if an UnrecognizedResponseCodeException is thrown 
     * when an IOException is caught in the {@link ApiConnection#getGithubData()} method
     *  
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testConnectForUnrecognizedResponseCode() throws Exception {
        String exceptionUrl = "https://iThrowAnException";
        setUpApiConnectionConstructor(exceptionUrl);
        when(urlMock.openConnection()).thenThrow(new IOException());
        apiTest.getApiData();
    }
    
    /**
     * Unit test to check if an {@link UnrecognizedResponseException} is thrown when an IOException
     * is caught in the readOutpputFromApi method that is called from {@link ApiConnection#getGithubData()} 
     * 
     */
    @Test(expected = UnrecognizedResponseException.class)
    public void testReadOutputFromApiForIlleglStateException() throws Exception {
        String anyUrl = "https://urlThrowsIOException";
        setUpApiConnectionConstructor(anyUrl);
        HttpURLConnection httpUrlConnectionMock = setupForResponseCode(200);
        when(httpUrlConnectionMock.getInputStream()).thenThrow(new IOException());
        apiTest.getApiData();
    }
    
    /**
     * Unit test to check if HttpURLConnection's connection was closed successfully
     * in the finally block
     * 
     */
    @Test
    public void testConnectToCheckDisconnect() throws Exception {
        String anyUrl = "https://verifyIfConnecionDisconnected";
        setUpApiConnectionConstructor(anyUrl);
        HttpURLConnection httpUrlConnectionMock = setupForResponseCode(404);
        apiTest.getApiData();
        verify(httpUrlConnectionMock).disconnect();
    }
}