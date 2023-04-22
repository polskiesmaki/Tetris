package de.tetris.server.tetrisserver.jwtutils.models;

import java.io.Serializable;

/**
 * Model for jwt controller responses
 */
public class JwtResponseModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String token;

    public JwtResponseModel(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
