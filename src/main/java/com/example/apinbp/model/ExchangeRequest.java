package com.example.apinbp.model;

import javax.persistence.*;

@Entity
@Table(name = "operations")
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currencyFrom;
    private String currencyTo;
    private Long amount;
    @Column(precision = 3, scale = 15)
    private Double receivedMoney;
    private String requesterLogin;

    public ExchangeRequest(String currencyFrom,
                           String currencyTo,
                           Long amount,
                           Double receivedMoney,
                           String requesterLogin) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.amount = amount;
        this.receivedMoney = receivedMoney;
        this.requesterLogin = requesterLogin;
    }

    public ExchangeRequest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Double getReceivedMoney() {
        return receivedMoney;
    }

    public void setReceivedMoney(Double receivedMoney) {
        this.receivedMoney = receivedMoney;
    }

    public String getRequesterLogin() {
        return requesterLogin;
    }

    public void setRequesterLogin(String requesterLogin) {
        this.requesterLogin = requesterLogin;
    }

    @Override
    public String toString() {
        return "ExchangeRequest{" +
                "id=" + id +
                ", currencyFrom='" + currencyFrom + '\'' +
                ", currencyTo='" + currencyTo + '\'' +
                ", amount=" + amount +
                ", receivedMoney=" + receivedMoney +
                ", requesterLogin='" + requesterLogin + '\'' +
                '}';
    }
}
