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

    public IdentityToken clientCredentialsToken() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT + "?" + GRANT_TYPE + "=" + CLIENT_CREDENTIALS))
//                .headers(GRANT_TYPE, CLIENT_CREDENTIALS)
                .build();
        IdentityToken identityToken = parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
        return identityToken;
    }

    public IdentityToken resourceOwnerToken(String key, String secret) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT))
                .headers(GRANT_TYPE, PASSWORD)
                .build();
        return parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
//        try {
//            HttpRequestBase httpRequest = getHttpRequest(baseUrl + OAUTH_TOKEN_ENDPOINT, POST
//                    , PASSWORD, null, key, secret);
//            return parseIdentityToken(EntityUtils.toString(httpClient.execute(httpRequest).getEntity()));
//        } catch (IOException | URISyntaxException e) {
//            throw new IrretrievableIdentityToken(e);
//        }
    }

    public IdentityToken refreshToken(String refreshTokenValue) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(baseUrl + OAUTH_TOKEN_ENDPOINT))
                .headers(GRANT_TYPE, REFRESH_TOKEN, REFRESH_TOKEN, refreshTokenValue)
                .build();
        return parseIdentityToken(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
//        try {
//            HttpRequestBase httpRequest = getHttpRequest(baseUrl + OAUTH_TOKEN_ENDPOINT, POST
//                    , REFRESH_TOKEN, refreshTokenValue, credentials.key, credentials.value);
//            return parseIdentityToken(EntityUtils.toString(httpClient.execute(httpRequest).getEntity()));
//        } catch (IOException | URISyntaxException e) {
//            throw new IrretrievableIdentityToken(e);
//        }
    }

    public SignatureKey signatureKey() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(baseUrl + OAUTH_TOKEN_KEY_ENDPOINT))
                .build();
        return parseSignatureKey(httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).join().body());
//        try {
//            HttpRequestBase httpRequest = getHttpRequest(baseUrl + OAUTH_TOKEN_KEY_ENDPOINT, GET
//                    , null, null, credentials.key, credentials.value);
//            return parseSignatureKey(EntityUtils.toString(httpClient.execute(httpRequest).getEntity()));
//        } catch (IOException | URISyntaxException e) {
//            throw new IrretrievableSignatureKey(e);
//        }
    }

//    private HttpRequest getHttpRequest(String endpoint, String method, String grant_type, String refreshTokenValue, String creds_key, String creds_value) throws URISyntaxException {
//        UsernamePasswordCredentials httpCredentials
//                = new UsernamePasswordCredentials(creds_key, creds_value);
//        provider.setCredentials(AuthScope.ANY, httpCredentials);
//
//        List nameValuePairs = new ArrayList();
//        if (!grant_type.isEmpty()) {
//            nameValuePairs.add(new BasicNameValuePair(GRANT_TYPE, grant_type));
//        }
//        if (!refreshTokenValue.isEmpty()) {
//            nameValuePairs.add(new BasicNameValuePair(REFRESH_TOKEN, refreshTokenValue));
//        }
//
//        if (method.equals(POST)) {
//            HttpPost httpRequest = new HttpPost(endpoint);
//
//            URI uri = new URIBuilder(httpRequest.getURI())
//                    .addParameters(nameValuePairs)
//                    .build();
//            httpRequest.setURI(uri);
//            return httpRequest;
//
//        } else if (method.equals(GET)) {
//            HttpGet httpRequest = new HttpGet(endpoint);
//
//            URI uri = new URIBuilder(httpRequest.getURI())
//                    .addParameters(nameValuePairs)
//                    .build();
//            httpRequest.setURI(uri);
//            return httpRequest;
//        } else {
//            throw new IrretrievableIdentityToken(new Exception(method + " Http method not supported "));
//        }
//    }

    private IdentityToken parseIdentityToken(String response) throws IOException {
        try {
            return new ObjectMapper().readValue(response, IdentityToken.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing IdentityToken", e);
        }
        return null;
//        if (response == null) {
//            return IdentityToken.of(null);
//        } else {
//            JsonParser parser = new JsonParser();
//            JsonObject jsonObject = parser.parse(response).getAsJsonObject();
//            JsonElement accessElement = jsonObject.get("access_token");
//            JsonElement refreshElement = jsonObject.get("refresh_token");
//            return new IdentityToken(accessElement, refreshElement);
//        }
    }

    public SignatureKey parseSignatureKey(String response) throws IOException {
        try {
            return new ObjectMapper().readValue(response, SignatureKey.class);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing SignatureKey", e);
        }
        return null;
//        if (response == null) {
//            return SignatureKey.of(null);
//        } else {
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(response).getAsJsonObject().get("value");
//            String signatureKey = emptyJson(element) ? null : element.getAsString();
//            return SignatureKey.of(signatureKey);
//        }
    }

//    private static String token(JsonElement accessElement) {
//        return emptyJson(accessElement) ? null : accessElement.getAsString();
//    }
//
//    private static boolean emptyJson(JsonElement jsonElement) {
//        return jsonElement == null || jsonElement.isJsonNull();
//    }

}

