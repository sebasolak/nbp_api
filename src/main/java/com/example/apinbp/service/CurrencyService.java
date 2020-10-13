package com.example.apinbp.service;

import com.example.apinbp.config.AppFeatures;
import com.example.apinbp.dao.CurrencyDTODao;
import com.example.apinbp.dao.ExchangeRequestDao;
import com.example.apinbp.controller.Message;
import com.example.apinbp.model.Currency;
import com.example.apinbp.model.dtos.CurrencyDTO;
import com.example.apinbp.model.ExchangeRequest;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private final AppFeatures appConfig;
    private final CurrencyDTODao currencyDTODao;
    private final ExchangeRequestDao exchangeRequestDao;

    @Value("${api.url.v1}")
    private String apiUrlEndpoint;

    @Autowired
    public CurrencyService(AppFeatures jsonFromUrl,
                           @Qualifier("currencyDtoDao") CurrencyDTODao currencyDTODao,
                           @Qualifier("exchangeRequestDao") ExchangeRequestDao exchangeRequestDao) {
        this.appConfig = jsonFromUrl;
        this.currencyDTODao = currencyDTODao;
        this.exchangeRequestDao = exchangeRequestDao;
    }

    public Message selectRate(String code) {
        try {
            String currencyName = getCurrencyName(code);
            Double currencyValue = getCurrencyValue(code);

            CurrencyDTO currencyDTO = new CurrencyDTO(currencyName, currencyValue);
            currencyDTODao.save(currencyDTO);

            return new Message(String.format("%s: %.2f", currencyName, currencyValue));
        } catch (Exception e) {

        }
        return new Message("Invalid currency code");
    }

    public List<String> selectAvailableCurrenciesRates() {

       return appConfig.getAvailableCurrencies().stream()
                .map(code -> String.format("1 %s (%s) = %.2f zł",
                        getCurrencyName(code), code, getCurrencyValue(code)))
                .collect(Collectors.toList());
    }


    public Message exchangeMoney(String codeFrom, String codeTo, Long amount,String login) {

        if(!appConfig.getAvailableCurrencies().contains(codeFrom)||
                !appConfig.getAvailableCurrencies().contains(codeTo)){
            return new Message("Currency is not available");
        }

        try {
            String currencyNameFrom = getCurrencyName(codeFrom);
            Double currencyValueFrom = getCurrencyValue(codeFrom);

            String currencyNameTo = getCurrencyName(codeTo);
            Double currencyValueTo = getCurrencyValue(codeTo);

            double receivedMoney = (currencyValueFrom * amount) / currencyValueTo;

            ExchangeRequest exchangeRequest =
                    new ExchangeRequest(codeFrom, codeTo, amount, round(receivedMoney, 2),login);

            exchangeRequestDao.save(exchangeRequest);

            String msg = String.format("Wymieniono %d %s (%s) na %.2f %s (%s)",
                    amount, currencyNameFrom, codeFrom, receivedMoney, currencyNameTo, codeTo);
            return new Message(msg);
        } catch (Exception e) {
            return new Message("Invalid currency code");
        }
    }


    private String getCurrencyName(String code) {
        String nbpJson = appConfig.getJsonFromUrl(apiUrlEndpoint + code + "?format=json");
        Gson gson = new Gson();
        String currencyName = gson.fromJson(nbpJson, Currency.class).currency;
        return currencyName;
    }

    private Double getCurrencyValue(String code) {
        String nbpJson = appConfig.getJsonFromUrl(apiUrlEndpoint + code + "?format=json");
        Gson gson = new Gson();
        Double currencyValue = gson.fromJson(nbpJson, Currency.class).rates[0].mid;
        return currencyValue;
    }

    private double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
