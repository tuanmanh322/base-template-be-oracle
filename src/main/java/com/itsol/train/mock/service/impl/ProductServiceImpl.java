package com.itsol.train.mock.service.impl;

import com.itsol.train.mock.dto.ProductDto;
import com.itsol.train.mock.domain.Product;
import com.itsol.train.mock.repo.ProductRepository;
import com.itsol.train.mock.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl implements ProductService {

    private Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> getAllProduct() {
        log.trace("Service to get all Product");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void create(ProductDto productDto) {
        log.trace("Service to create product: {}", productDto);
        Product product = modelMapper.map(productDto, Product.class);
        productRepository.save(product);
    }

    @Override
    public void update(ProductDto productDto) {
        log.trace("Service to update product: {}", productDto);
        Product product = productRepository.getOne(productDto.getProductId());
        product.setProductName(productDto.getProductName());
        product.setPrice(Double.valueOf(productDto.getPrice()));
    }

    @Override
    public void delete(Long productId) {
        log.trace("Service to delete product by productId = {}", productId);
        productRepository.deleteById(productId);
    }

    @Override
    public ProductDto getProduct(Long productId) {
        log.trace("Service to get product by productId = {}", productId);
        Optional<Product> productOptional = productRepository.findById(productId);
        return productOptional
                .map(product->modelMapper.map(product, ProductDto.class))
                .orElse(null);
    }
}
