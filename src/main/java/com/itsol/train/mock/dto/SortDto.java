package com.itsol.train.mock.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
public class SortDto {
    private static final String DESC = "DESC";
    private static final String ASC = "ASC";
    private String fieldName;
    private String orderBy;

    public String getOrderBy(){
        if(StringUtils.isBlank(orderBy))
            return ASC;
        return DESC.equals(orderBy.trim().toUpperCase()) ? DESC : ASC;
    }
}
