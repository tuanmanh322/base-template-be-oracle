package com.itsol.train.mock.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BaseSearchDto {
    private List<?> data;
    private long totalRecords;
    private int page;
    List<SortDto> sorts;
    private int pageSize;
}
