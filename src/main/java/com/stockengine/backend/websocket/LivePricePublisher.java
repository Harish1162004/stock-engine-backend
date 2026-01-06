package com.stockengine.backend.websocket;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class LivePricePublisher {

    private final SimpMessagingTemplate messagingTemplate;
    private final Random random = new Random();

    private double tcs = 3500;
    private double infy = 1500;
    private double reliance = 2900;

    public LivePricePublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void publishPrices() {

        messagingTemplate.convertAndSend("/topic/candles", Map.of(
                "TCS", candle("TCS"),
                "INFY", candle("INFY"),
                "RELIANCE", candle("RELIANCE")
        ));
    }

    private Map<String, Object> candle(String stock) {

        double base =
                stock.equals("TCS") ? tcs :
                        stock.equals("INFY") ? infy : reliance;

        double open = base;
        double close = base + random.nextDouble() * 40 - 20;
        double high = Math.max(open, close) + random.nextDouble() * 15;
        double low = Math.min(open, close) - random.nextDouble() * 15;
        double volume = random.nextInt(5000) + 1000;

        if (stock.equals("TCS")) tcs = close;
        if (stock.equals("INFY")) infy = close;
        if (stock.equals("RELIANCE")) reliance = close;

        Map<String, Object> candle = new HashMap<>();
        candle.put("time", Instant.now().getEpochSecond());
        candle.put("open", open);
        candle.put("high", high);
        candle.put("low", low);
        candle.put("close", close);
        candle.put("volume", volume);

        return candle;
    }
}
