package model.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Java http client sends and requests data from server
 */
public class RestRequest {

    private static final String TETRIS_URI = "http://localhost:8080/tetris";
    private static final HttpClient CLIENT = HttpClient
            .newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public HttpResponse<String> getRequest(String uri, String auth) throws RestServiceException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TETRIS_URI + uri))
                .header("Authorization", auth)
                .GET()
                .build();
        return getResponse(request);
    }

    public HttpResponse<String> getRequest(String uri) throws RestServiceException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TETRIS_URI + uri))
                .GET()
                .build();
        return getResponse(request);
    }

    public HttpResponse<String> postRequest(String uri, String body) throws RestServiceException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TETRIS_URI + uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return getResponse(request);
    }

    public HttpResponse<String> postRequest(String uri, String body, String auth) throws RestServiceException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TETRIS_URI + uri))
                .header("Content-Type", "application/json")
                .header("Authorization", auth)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();
        return getResponse(request);
    }

    private static HttpResponse<String> getResponse(HttpRequest request) throws RestServiceException {
        CompletableFuture<HttpResponse<String>> futureResponse = CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            HttpResponse<String> response = futureResponse.get();
            if (response.statusCode() == 200) {
                return response;
            } else {
                throw new RestServiceException("Request failed. Server answered with Status Code"
                        + response.statusCode(), response.statusCode());
            }
        } catch (InterruptedException | ExecutionException exp) {
            throw new RestServiceException("Request failed. Could not call API", exp, 600);
        }
    }

}
