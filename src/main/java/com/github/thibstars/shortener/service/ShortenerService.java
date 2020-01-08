package com.github.thibstars.shortener.service;

import java.util.Objects;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ShortenerService {

    private final String apiKey;
    private final String workspace;
    private final RestTemplate restTemplate;

    private ShortenerResponse shorten(String longUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("apiKey", "");
        headers.add("workspace", "");

        ShortenerRequest request = ShortenerRequest.builder().build();
        HttpEntity<ShortenerRequest> requestEntity = new HttpEntity<>(request, headers);

        ResponseEntity<ShortenerResponse> shortenerResponse = restTemplate
            .postForEntity("https://api.rebrandly.com/v1/links", requestEntity, ShortenerResponse.class);

        return Objects.requireNonNull(shortenerResponse.getBody());

    }

}
