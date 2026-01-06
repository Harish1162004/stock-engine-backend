package com.stockengine.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StockPriceService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public double getLivePrice(String symbol) {

        String url =
                "https://www.alphavantage.co/query?function=GLOBAL_QUOTE"
                        + "&symbol=" + symbol
                        + "&apikey=" + apiKey;

        try {
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            return root
                    .path("Global Quote")
                    .path("05. price")
                    .asDouble();

        } catch (Exception e) {
            return 0.0;
        }
    }
}
