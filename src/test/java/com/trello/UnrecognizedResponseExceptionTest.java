package com.trello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.instanceOf;

import org.junit.Test;

import com.trello.UnrecognizedResponseException;

/**
 * Unit tests for UnrecognizedResponseCodeException
 * 
 * @author Shruti Vangari
 *
 */
public class UnrecognizedResponseExceptionTest {

    @Test
    public void testUnrecognizedResponseCodeException() {
        assertThat(new UnrecognizedResponseException(), instanceOf(UnrecognizedResponseException.class));
    }
    
    @Test
    public void testMessageConstructorForMessage() {
        UnrecognizedResponseException e = new UnrecognizedResponseException("some exception message");
        assertEquals(e.getMessage(), "some exception message");
    }
    
    @Test
    public void testMessageConstructorForMessageAndCause() {
        Exception cause = new IllegalArgumentException();
        UnrecognizedResponseException e = new UnrecognizedResponseException("some exception message", cause);
        assertEquals(e.getMessage(), "some exception message");
        assertEquals(e.getCause(), cause);
    }
    
    @Test
    public void testMessageConstructorForCause() {
        Exception cause = new IllegalStateException();
        UnrecognizedResponseException e = new UnrecognizedResponseException(cause);
        assertEquals(e.getCause(), cause);
    }
    
}
