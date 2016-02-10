package com.trello;

/**
 * Enum of {@link HttpURLConnection} Class that returns a status message
 * for a response code
 * 
 * @author Shruti Vangari
 * 
 */

public enum HttpResponseCode {
	
	/**
     * Standard response for successful HTTP requests.
     */
    HTTP_OK("Successful Connection", 200),
    /**
     * The request has been fulfilled and resulted in a new resource being
     * created.
     */
    HTTP_CREATED("Created", 201),
    /**
     * The request has been accepted for processing, but the processing has not
     * been completed.
     */
    HTTP_ACCEPTED("Accepted", 202),
    /**
     * The server successfully processed the request, but is returning
     * information that may be from another source.
     */
    HTTP_NOT_AUTHORITATIVE("Non-Authoritative Information", 203),
    /**
     * The server successfully processed the request, but is not returning any
     * content.
     */
    HTTP_NO_CONTENT("No Content", 204),
    /**
     * The server successfully processed the request, but is not returning any
     * content.
     */
    HTTP_RESET("Reset Content", 205),
    /**
     * The server is delivering only part of the resource due to a range header
     * sent by the client.
     */
    HTTP_PARTIAL("Partial Content", 206),
    /**
     * Indicates multiple options for the resource that the client may follow.
     */
    HTTP_MULT_CHOICE("Multiple Choices", 300),
    /**
     * This and all future requests should be directed to the given URI.
     */
    HTTP_MOVED_PERM("Moved Permanently", 301),
    /**
     * The request has been moved temporarily
     */
    HTTP_MOVED_TEMP("Temporary Redirect", 302),
    /**
     * The response to the request can be found under another URI using a GET
     * method.
     */
    HTTP_SEE_OTHER("See Other", 303),
    /**
     * Indicates that the resource has not been modified since the version
     * specified by the request headers If-Modified-Since or If-Match.
     */
    HTTP_NOT_MODIFIED("Not Modified", 304),
    /**
     * The requested resource is only available through a proxy, whose address
     * is provided in the response.
     */
    HTTP_USE_PROXY("Use Proxy", 305),
    /**
     * The request cannot be fulfilled due to bad syntax.
     */
    HTTP_BAD_REQUEST("Bad Request", 400),
    /**
     * For use when authentication is required and has failed or has not yet
     * been provided.
     */
    HTTP_UNAUTHORIZED("Unauthorized", 401),
    /**
     * Reserved for future use.
     */
    HTTP_PAYMENT_REQUIRED("Payment Required", 402),
    /**
     * The request was a valid request, but the server is refusing to respond to
     * it.
     */
    HTTP_FORBIDDEN("Forbidden", 403),
    /**
     * The requested resource could not be found but may be available again in
     * the future.
     */
    HTTP_NOT_FOUND("Not Found", 404),
    /**
     * A request was made of a resource using a request method not supported by
     * that resource.
     */
    HTTP_BAD_METHOD("Method Not Allowed", 405),
    /**
     * The requested resource is only capable of generating content not
     * acceptable according to the Accept headers sent in the request.
     */
    HTTP_NOT_ACCEPTABLE("Not Acceptable", 406),
    /**
     * The client must first authenticate itself with the proxy.
     */
    HTTP_PROXY_AUTH("Proxy Authentication Required", 407),
    /**
     * The server timed out waiting for the request.
     */
    HTTP_CLIENT_TIMEOUT("Request Time-Out", 408),
    /**
     * Indicates that the request could not be processed because of conflict in
     * the request.
     */
    HTTP_CONFLICT("Conflict", 409),
    /**
     * Indicates that the resource requested is no longer available and will not
     * be available again.
     */
    HTTP_GONE("Gone", 410),
    /**
     * The request did not specify the length of its content, which is required
     * by the requested resource.
     */
    HTTP_LENGTH_REQUIRED("Length Required", 411),
    /**
     * The server does not meet one of the preconditions that the requester put
     * on the request.
     */
    HTTP_PRECON_FAILED("Precondition Failed", 412),
    /**
     * The request is larger than the server is willing or able to process.
     */
    HTTP_ENTITY_TOO_LARGE("Request Entity Too Large", 413),
    /**
     * The URI provided was too long for the server to process.
     */
    HTTP_REQ_TOO_LONG("Request-URI Too Large", 414),
    /**
     * The request entity has a media type which the server or resource does not
     * support.
     */
    HTTP_UNSUPPORTED_TYPE("Unsupported Media Type", 415),
    /**
     * A generic error message, given when an unexpected condition was
     * encountered and no more specific message is suitable.
     */
    HTTP_INTERNAL_ERROR("Internal Server Error", 500),
    /**
     * The server either does not recognize the request method, or it lacks the
     * ability to fulfill the request.
     */
    HTTP_NOT_IMPLEMENTED("Not Implemented", 501),
    /**
     * The server was acting as a gateway or proxy and received an invalid
     * response from the upstream server.
     */
    HTTP_BAD_GATEWAY("Bad Gateway", 502),
    /**
     * The server is currently unavailable (because it is overloaded or down for
     * maintenance).
     */
    HTTP_UNAVAILABLE("Service Unavailable", 503),
    /**
     * The server was acting as a gateway or proxy and did not receive a timely
     * response from the upstream server.
     */
    HTTP_GATEWAY_TIMEOUT("Gateway Timeout", 504),
    /**
     * The server does not support the HTTP protocol version used in the
     * request.
     */
    HTTP_VERSION("HTTP Version Not Supported", 505),
    /**
     * When the URL is not hosted on a server
     */
    HTTP_UNKNOWN("Invalid Host Url", 0);

    private final String responseMessage;
    private final int responseCode;

    /**
     * @param message
     *            HTTP Status corresponding to the response code
     * 
     */
    private HttpResponseCode(String message, int code) {
        this.responseMessage = message;
        this.responseCode = code;
    }

    /**
     * Message phrase used to describe the response code.
     * 
     * @return Status message of the response code
     */
    public String getMessage() {
        return responseMessage;
    }

    /**
     * @return a HTTP response code from the {@link HttpURLConnection} class
     * 
     */
    public int getCode() {
        return responseCode;
    }

    /**
     * Read and check against the list of Enums defined in this class
     * 
     * @param responseCode
     *            as input corresponding to a HTTP code
     * 
     * @return a {@link HttpResponseCode} corresponding to a HTTP status
     * 
     */
    public static HttpResponseCode valueOf(int responseCode) {
        for (HttpResponseCode code : HttpResponseCode.values()) {
            if (responseCode == code.getCode()) {
                return code;
            }
        }
        return HTTP_UNKNOWN;
    }

}
