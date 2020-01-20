package com.github.thibstars.shortener.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Thibault Helsmoortel
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenerResponse {

    private String shortUrl;

}
