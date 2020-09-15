package com.hawolt.impl;

import com.hawolt.*;
import com.hawolt.objects.Order;
import com.hawolt.pagination.Pagination;
import com.hawolt.pagination.impl.OrderPagination;
import org.json.JSONObject;

import java.io.IOException;

public class Orders extends SellyResource {

    public Orders() {
        super("orders");
    }

    public Order get(String id) throws IOException {
        SellyRequest request = SellyRequest.get(String.format("%s/%s", location, id));
        return new Order(new JSONObject(request.getBody()));
    }

    public OrderPagination getAll() throws IOException, SellyException {
        return getAll(Pagination.Direction.ASCENDING);
    }

    public OrderPagination getAll(Pagination.Direction direction) throws IOException, SellyException {
        SellyRequest request = SellyRequest.get(location);
        return new OrderPagination(location, request, direction);
    }
}
