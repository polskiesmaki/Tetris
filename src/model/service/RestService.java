package model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.HistoryEntry;
import model.Token;
import model.User;


import java.net.http.HttpResponse;
import java.util.List;

/**
 * Service class for REST client
 * Defines services
 * Maps received data
 * Maps request data
 * Stores JWT Webtoken
 * Defines REST-API URIs
 */
public class RestService {

    private Token token;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final RestRequest REST_REQUEST = new RestRequest();

    public RestService() {
    }

    ;

    public static Boolean checkConnection() throws RestServiceException {
        return REST_REQUEST.getRequest("/hello").body().equals("hello");
    }

    public Boolean signIn(String username, String password) throws RestServiceException {
        User user = new User(username, password);
        try {
            String body = OBJECT_MAPPER.writeValueAsString(user);
            token = toToken(REST_REQUEST.postRequest("/login", body));
            return token.getToken() != null;
        } catch (JsonProcessingException exp) {
            throw new RestServiceException("Failed map response to Json", exp, 700);
        }
    }

    public static Boolean signUp(String username, String password) throws RestServiceException {
        User user = new User(username, password);
        try {
            String body = OBJECT_MAPPER.writeValueAsString(user);
            return REST_REQUEST.postRequest("/user/add", body).statusCode() == 200;
        } catch (JsonProcessingException exp) {
            throw new RestServiceException("Failed map response to Json", exp, 700);
        }
    }


    public List<HistoryEntry> getHistory() throws RestServiceException {
        try {
            return toHistoryEntries(REST_REQUEST.getRequest("/history/user", token.toBearerString()));
        } catch (JsonProcessingException exp) {
            throw new RestServiceException("Failed map response to Json", exp, 700);
        }
    }

    public Integer getMaxLevel() throws RestServiceException {
        try {
            return Integer.parseInt((REST_REQUEST.getRequest("/history/max-level", token.toBearerString()).body()));
        } catch (NumberFormatException exp) {
            throw new RestServiceException("No History entries available.", exp, 900);
        }
    }

    public Boolean addHistory(HistoryEntry historyEntry) throws RestServiceException {
        try {
            String body = OBJECT_MAPPER.writeValueAsString(historyEntry);
            return REST_REQUEST.postRequest("/history/add", body, token.toBearerString()).statusCode() == 200;
        } catch (JsonProcessingException exp) {
            throw new RestServiceException("Failed map response to Jason", exp, 700);
        }
    }

    private static Token toToken(HttpResponse<String> response) throws JsonProcessingException {
        JavaType returnType = OBJECT_MAPPER.getTypeFactory().constructType(Token.class);
        return OBJECT_MAPPER.readValue(response.body(), returnType);

    }

    private static List<HistoryEntry> toHistoryEntries(HttpResponse<String> response) throws JsonProcessingException {

        JavaType returnType = OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, HistoryEntry.class);
        return OBJECT_MAPPER.readValue(response.body(), returnType);

    }
}

