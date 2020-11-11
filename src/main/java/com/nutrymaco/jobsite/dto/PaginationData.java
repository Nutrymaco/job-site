package com.nutrymaco.jobsite.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

public class PaginationData {
    private final Pageable pageable;

    private PaginationData(Pageable pageable) {
        this.pageable = pageable;
    }

    public static PaginationData extractFrom(MultiValueMap<String, String> filters) {
        String pageString = filters.getFirst("page");
        String sizeString = filters.getFirst("size");
        if (pageString == null && sizeString == null) {
            return new PaginationData(Pageable.unpaged());
        }
        int page = Integer.parseInt(Objects.requireNonNull(filters.getFirst("page")));
        int size = Integer.parseInt(Objects.requireNonNull(filters.getFirst("size")));
        return new PaginationData(PageRequest.of(page, size));
    }

    public static PaginationData extractFrom(VacancyFilter filter) {
        if (filter == null) {
            return new PaginationData(Pageable.unpaged());
        }
        Integer page = filter.getPage();
        Integer size = filter.getSize();
        if (page == null || size == null) {
            return new PaginationData(Pageable.unpaged());
        }
        return new PaginationData(PageRequest.of(page, size));
    }

    public Pageable getPageable() {
        return pageable;
    }
}
