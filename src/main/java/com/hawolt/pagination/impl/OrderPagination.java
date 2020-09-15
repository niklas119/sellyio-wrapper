package com.hawolt.pagination.impl;

import com.hawolt.SellyRequest;
import com.hawolt.objects.Order;
import com.hawolt.pagination.Pagination;
import org.json.JSONArray;

import java.util.LinkedList;
import java.util.List;

public class OrderPagination extends Pagination<List<Order>> {

    public OrderPagination(String location, SellyRequest initial, Direction direction) {
        super(location, initial, direction);
    }

    @Override
    public List<Order> convert(SellyRequest request) {
        JSONArray array = new JSONArray(request.getBody());
        List<Order> list = new LinkedList<>();
        if (direction == Direction.ASCENDING) {
            for (int i = 0; i < array.length(); i++) {
                list.add(new Order(array.getJSONObject(i)));
            }
        } else {
            for (int i = array.length() - 1; i >= 0; i--) {
                list.add(new Order(array.getJSONObject(i)));
            }
        }
        return list;
    }

}
