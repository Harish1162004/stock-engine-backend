package com.stockengine.backend.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class LivePriceService {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    private final Map<String, Double> prices = new HashMap<>();

    public LivePriceService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;

        prices.put("TCS", 3500.0);
        prices.put("INFY", 1500.0);
        prices.put("HCL", 1600.0);
        prices.put("WIPRO", 420.0);
        prices.put("RELIANCE", 2900.0);
    }

    @Scheduled(fixedRate = 5000)
    public void sendLivePrices() {

        prices.forEach((stock, price) -> {
            double change = (random.nextDouble() - 0.5) * 20;
            prices.put(stock, price + change);
        });

        messagingTemplate.convertAndSend("/topic/prices", prices);
    }
}
