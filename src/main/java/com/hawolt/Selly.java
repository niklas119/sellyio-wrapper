package com.hawolt;

import com.hawolt.impl.Orders;
import com.hawolt.impl.Payments;

import java.util.Base64;

public class Selly {

    private String email, apiKey;

    public static String AUTHORIZATION, BASE = "https://selly.io/api/v2/";

    private Orders orders = new Orders();
    private Payments payments = new Payments();

    private Selly(String email, String api) {
        this.email = email;
        this.apiKey = api;
        Selly.AUTHORIZATION = Base64.getEncoder().encodeToString(String.format("%s:%s", email, api).getBytes());
    }

    public static Selly authenticate(String email, String api) {
        return new Selly(email, api);
    }

    public String getEmail() {
        return email;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Orders getOrders() {
        return orders;
    }

    public Payments getPayments() {
        return payments;
    }


}
