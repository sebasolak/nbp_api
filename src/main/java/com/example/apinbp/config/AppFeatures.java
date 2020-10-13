package com.example.apinbp.config;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

@Configuration
public class AppFeatures {

    public String getJsonFromUrl(String urlText) {

        try {
            URL myUrl = new URL(urlText);
            StringBuilder jsonText = new StringBuilder();

            InputStream inputStream = myUrl.openStream();
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                jsonText.append(scanner.nextLine());
            }

            return jsonText.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Set<String> getAvailableCurrencies() {
        Set<String> availableCurrencies = new HashSet<>();
        availableCurrencies.add("eur");
        availableCurrencies.add("sek");
        availableCurrencies.add("ils");
        availableCurrencies.add("hkd");
        availableCurrencies.add("nok");
        availableCurrencies.add("dkk");
        availableCurrencies.add("usd");
        availableCurrencies.add("gbp");
        availableCurrencies.add("brl");
        return availableCurrencies;
    }
}
