package com.hawolt;

import java.io.IOException;

public class SellyRequest {

    private int rateLimitRemaining, totalPages;
    private String body;

    private SellyRequest(String location) throws IOException, SellyException {
        this(location, Method.GET, false, null);
    }

    private SellyRequest(String location, Method method, boolean output, Object data) throws IOException, SellyException {
        Request request = new Request(Selly.BASE + location, method, output);
        request.addHeader("Authorization", String.format("Basic %s", Selly.AUTHORIZATION));
        if (output) {
            request.addHeader("Content-Type", "application/json");
            request.write(data.toString());
        }
        Response response = new Response(request);
        if (response.getCode() >= 400) {
            throw new SellyException(response.getCode());
        }
        String remaining = response.getHeaders().get("X-RateLimit-Remaining").get(0);
        this.rateLimitRemaining = Integer.parseInt(remaining);
        String total = response.getHeaders().containsKey("X-Total-Pages") ? response.getHeaders().get("X-Total-Pages").get(0) : "0";
        this.totalPages = Integer.parseInt(total);
        this.body = response.getBody();
    }

    public String getBody() {
        return body;
    }

    public int getRateLimitRemaining() {
        return rateLimitRemaining;
    }


    public int getTotalPages() {
        return totalPages;
    }

    public static SellyRequest get(String location) throws IOException, SellyException {
        return new SellyRequest(location);
    }

    public static SellyRequest post(String location, Object o) throws IOException, SellyException {
        return new SellyRequest(location, Method.POST, true, o);
    }
}
