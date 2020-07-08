package com.itsol.train.mock.service;

import com.itsol.train.mock.dto.ProductDto;

import java.util.List;

public interface ProductService {
    /**
     * Get all product
     *
     * @return
     */
    List<ProductDto> getAllProduct();

    /**
     * create product
     *
     * @param productDto
     */
    void create(ProductDto productDto);

    /**
     * update product
     *
     * @param productDto
     */
    void update(ProductDto productDto);

    /**
     *
     * @param productId
     */
    void delete(Long productId);

    /**
     * Get one product
     * @param productId
     * @return
     */
    ProductDto getProduct(Long productId);
}
