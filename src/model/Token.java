package model;

/**
 * Class to store JWT Token
 */
public class Token {

    private String token;

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {

        this.token = token;
    }

    public String toBearerString() {
        return "Bearer " + this.token;
    }
}
