package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;
import java.util.List;
import javax.xml.bind.DatatypeConverter;

@Service
public class MarvelApiService {

    @Value("${marvel.api.public.key}")
    private String publicKey;

    @Value("${marvel.api.private.key}")
    private String privateKey;

    private final RestTemplate restTemplate;

    public MarvelApiService() {
        this.restTemplate = new RestTemplate();
    }

    public String getHeroImageUrl(String heroName) {
        try {
            // Timestamp para la API de Marvel
            long timestamp = new Date().getTime();
            String hash = generateHash(timestamp, privateKey, publicKey);

            // Construir URL de la API de Marvel
            String url = UriComponentsBuilder.newInstance()
                    .scheme("https")
                    .host("gateway.marvel.com")
                    .path("/v1/public/characters")
                    .queryParam("ts", timestamp)
                    .queryParam("apikey", publicKey)
                    .queryParam("hash", hash)
                    .queryParam("name", heroName)
                    .build()
                    .toUriString();

            // Llamar a la API
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            // Accedemos a los datos
            Map<String, Object> data = (Map<String, Object>) response.get("data");
            // Accedemos a los resultados
            List<Map<String, Object>> results = (List<Map<String, Object>>) data.get("results");

            if (results != null && !results.isEmpty()) {
                Map<String, Object> character = results.get(0);
                Map<String, Object> thumbnail = (Map<String, Object>) character.get("thumbnail");

                String path = (String) thumbnail.get("path");
                String extension = (String) thumbnail.get("extension");

                // Build the image URL
                String imageUrl = path + "." + extension;
                System.out.println("Image URL: " + imageUrl);

                return imageUrl;
            }

            // Retornar nada
            return null;

        } catch (Exception e) {
            // En caso de error, retornar nada
            return null;
        }
    }

    private String generateHash(long timestamp, String privateKey, String publicKey) throws NoSuchAlgorithmException {
        String value = timestamp + privateKey + publicKey;
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(value.getBytes());
        return DatatypeConverter.printHexBinary(digest).toLowerCase();
    }
}