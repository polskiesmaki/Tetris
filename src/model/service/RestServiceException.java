package model.service;

/**
 * Exception for REST service to get
 * specific error message for business logic
 */
public class RestServiceException extends Exception {

    private int statusCode;

    public RestServiceException(String message, Exception exp, int statusCode) {
        super(message, exp);
        this.statusCode = statusCode;
    }

    /**
     * @param message    Exception message
     * @param statusCode 401 Zugriff verweigert, 409 Benutzer bereits vergeben, 600 API-Zugriff fehlgeschlagen
     *                   700 Json-Mapping-Fehler
     */
    public RestServiceException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
