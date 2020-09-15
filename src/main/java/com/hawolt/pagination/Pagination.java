package com.hawolt.pagination;

import com.hawolt.SellyException;
import com.hawolt.SellyRequest;

import java.io.IOException;

public abstract class Pagination<T> implements ExceptionalIterable<T> {

    protected Direction direction;

    private String location;
    private int index, total;
    private SellyRequest initial;

    public Pagination(String location, SellyRequest initial, Direction direction) {
        this.total = initial.getTotalPages();
        this.location = location;
        this.direction = direction;
        this.initial = initial;
        this.index = 1 + (direction == Direction.ASCENDING ? 0 : initial.getTotalPages());
    }

    public int getIndex() {
        return index;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public boolean hasNext() {
        return direction == Direction.ASCENDING ? index < total : index > 0;
    }

    @Override
    public T next() throws IOException, SellyException {
        if (index == 1) {
            index += 1;
            return convert(initial);
        }
        int tmp = direction == Direction.ASCENDING ? 1 : -1;
        SellyRequest request = SellyRequest.get(String.format("%s?page=%s", location, index + (tmp)));
        index += tmp;
        return convert(request);
    }

    protected abstract T convert(SellyRequest request);

    public enum Direction {
        ASCENDING, DESCENDING
    }
}
