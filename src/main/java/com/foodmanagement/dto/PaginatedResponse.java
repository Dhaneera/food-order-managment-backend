package com.foodmanagement.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaginatedResponse<T> {
    private List<T> content;
    private Pageable pageable;
    private boolean last;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private int size;
    private int number;
    private Sort sort;
    private int numberOfElements;
    private boolean empty;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Pageable {
        private int pageNumber;
        private int pageSize;
        private Sort sort;
        private long offset;
        private boolean unpaged;
        private boolean paged;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sort {
        private boolean empty;
        private boolean unsorted;
        private boolean sorted;
    }
}