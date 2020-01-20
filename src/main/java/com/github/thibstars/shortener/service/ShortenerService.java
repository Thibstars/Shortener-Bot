package com.github.thibstars.shortener.service;

import com.github.thibstars.shortener.service.ShortenerRequest.ShortenerRequestBuilder;
import java.util.Objects;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Thibault Helsmoortel
 */
@Service
@Slf4j
public class ShortenerService {

    public static final String API_KEY_HEADER = "apiKey";
    public static final String WORKSPACE_HEADER = "workspace";

    private final RestTemplate restTemplate;
    private ShortenerRequestBuilder builder;
    private HttpHeaders httpHeaders;

    @Setter
    private String apiKey;

    @Setter
    private String workspace;

    public ShortenerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.builder = ShortenerRequest.builder().domain(new Domain("rebrand.ly"));
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    public ShortenerResponse shorten(String longUrl) {
        log.debug("Shrinking url {}", longUrl);

        if (!longUrl.startsWith("http")) {
            longUrl = "http://" + longUrl;
        }


        httpHeaders.add(API_KEY_HEADER, apiKey);
        httpHeaders.add(WORKSPACE_HEADER, workspace);
        ShortenerRequest request = builder.destination(longUrl).build();
        HttpEntity<ShortenerRequest> requestEntity = new HttpEntity<>(request, httpHeaders);

        ResponseEntity<ShortenerResponse> shortenerResponse = restTemplate
            .postForEntity("https://api.rebrandly.com/v1/links", requestEntity, ShortenerResponse.class);

        return Objects.requireNonNull(shortenerResponse.getBody());

    }

}
