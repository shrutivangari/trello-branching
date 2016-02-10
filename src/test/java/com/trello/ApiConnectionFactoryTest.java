package com.trello;


import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.net.URL;

import javax.naming.InitialContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.CoreMatchers.instanceOf;


/**
 * Unit tests for {@link ApiConnectionFactory}
 * 
 * @author Shruti Vangari
 *
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({InitialContext.class})
public class ApiConnectionFactoryTest {

	@Mock 
    private InitialContext initialContextMock;
    
    private void setUpMockForUrlPrepender(String urlPrepender) throws Exception {
        whenNew(InitialContext.class).withNoArguments().thenReturn(initialContextMock);
        when(initialContextMock.lookup("java:comp/env/Git/UrlPrepender")).thenReturn(urlPrepender);
    }
    
    /**
     * Unit test to check if the validate method throws a NullPointerException when a
     * null is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test(expected = NullPointerException.class)
    public void testConnectForNullUrl() {
        ApiConnectionFactory.INSTANCE.validateInputUrl(null);
    }
    
    /**
     * Unit test to check if the validate method throws an IllegalArgumentException when a
     * blank string is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConnectForEmptyURL() {
        ApiConnectionFactory.INSTANCE.validateInputUrl("");
    }
    
    /**
     * Unit test to check if the validate method throws an IllegalArgumentException when a
     * empty string is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConnectForBlankURL() {
        ApiConnectionFactory.INSTANCE.validateInputUrl(" ");
        
    }
    
    /**
     * Unit test to check if the validate method creates a valid URL successfully when a
     * valid string is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test
    public void testValidateInputURL() throws Exception {
        setUpMockForUrlPrepender("https://");
        assertThat(ApiConnectionFactory.INSTANCE.validateInputUrl("https://someurl"), instanceOf(URL.class));
    }
    
    /**
     * Unit test to check if the validate method throws an IllegalArgumentException when a
     * non-HTTPS string is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateInputURLForStringInput() throws Exception {
        setUpMockForUrlPrepender("https://");
        ApiConnectionFactory.INSTANCE.validateInputUrl("somestring");
    }
    
    /**
     * Unit test to check if the validate method throws an IllegalArgumentException when a
     * non-HTTPS string is passed to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test(expected = IllegalArgumentException.class)
    public void testValidateInputURLForNonHttpInput() throws Exception {
        setUpMockForUrlPrepender("https://");
        ApiConnectionFactory.INSTANCE.validateInputUrl("http://somestring");
    }
    
    /**
     * Unit test to check if a {@link GitApiConnection} is created when
     * a valid URL is passed as a string to {@link ApiConnectionFactory#validateInputUrl(String)} 
     * 
     */
    @Test
    public void testCreateGitApiConnection() throws Exception {
        setUpMockForUrlPrepender("https://");
        assertThat(ApiConnectionFactory.INSTANCE.createApiConnection("https://ICanPassThisTest"), instanceOf(ApiConnection.class));
    }
    
    /**
     * Unit test to check if {@link ApiConnectionFactory} is singleton when
     * two instances of the class are created, it should be the same INSTANCE
     * 
     */
    @Test
    public void testForSingleton() {
        ApiConnectionFactory factoryInstance = ApiConnectionFactory.INSTANCE;
        assertEquals(factoryInstance, ApiConnectionFactory.INSTANCE);
    }
}
