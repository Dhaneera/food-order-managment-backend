package com.foodmanagement.Repository;

import com.foodmanagement.dto.PaginatedResponse;

public interface NativeOrderRepository {
    public PaginatedResponse<?> getAllOrders(String createdBy, int pageCount);
}
