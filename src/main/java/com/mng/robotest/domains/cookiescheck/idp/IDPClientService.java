package com.mng.robotest.domains.cookiescheck.idp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ForkJoinPool;

import static org.slf4j.LoggerFactory.getLogger;

public class IDPClientService {
    private static final Logger logger = getLogger(IDPClientService.class);

    public static final String PASSWORD = "password";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String CLIENT_CREDENTIALS = "client_credentials";
    public static final String GRANT_TYPE = "grant_type";
    private static final String OAUTH_TOKEN_KEY_ENDPOINT = "oauth/token_key";
    private static final String OAUTH_TOKEN_ENDPOINT = "oauth/token";

    private final String baseUrl;
    private final HttpClient httpClient;


    public IDPClientService(String baseUrl, IdpCredentials credentials) {
        this.baseUrl = baseUrl;
        httpClient = HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                credentials.getKey(),
                                credentials.getValue().toCharArray());
                    }
                })
                .executor(ForkJoinPool.commonPool())
                .build();
    }

    public IdentityToken clientCredentialsToken() throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT + "?" + GRANT_TYPE + "=" + CLIENT_CREDENTIALS))
                .build();
        
        return parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
    }

    public IdentityToken resourceOwnerToken() throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT))
                .headers(GRANT_TYPE, PASSWORD)
                .build();
        return parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
    }

    public IdentityToken refreshToken(String refreshTokenValue) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT))
                .headers(GRANT_TYPE, REFRESH_TOKEN, REFRESH_TOKEN, refreshTokenValue)
                .build();
        return parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
    }

    public SignatureKey signatureKey() throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + OAUTH_TOKEN_KEY_ENDPOINT))
                .build();
        return parseSignatureKey(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
    }

    private IdentityToken parseIdentityToken(String response) throws IOException {
        try {
            return new ObjectMapper().readValue(response, IdentityToken.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing IdentityToken", e);
        }
        return null;
    }

    public SignatureKey parseSignatureKey(String response) throws IOException {
        try {
            return new ObjectMapper().readValue(response, SignatureKey.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing SignatureKey", e);
        }
        return null;
    }
}

