package com.itsol.train.mock.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long productId;
    private String productName;
    private String price;
}
