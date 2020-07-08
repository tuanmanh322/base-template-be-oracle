package com.itsol.train.mock.rest;

import com.itsol.train.mock.dto.ProductDto;
import com.itsol.train.mock.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductResource {
    private Logger log = LoggerFactory.getLogger(getClass());

    private final ProductService productService;

    public ProductResource(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        log.trace("REST to request get all product.");
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> saveProduct(@RequestBody ProductDto productDto){
        log.trace("REST to request create product: {}", productDto);
        try{
            productService.create(productDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/find-one/{productId}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long productId){
        log.trace("REST to request get product: {}", productId);
        ProductDto productDto = productService.getProduct(productId);
        if(productDto==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(productDto, HttpStatus.OK);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto){
        log.trace("REST to request update product: {}", productDto);
        try{
            productService.update(productDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        log.trace("REST to request delete product: {}", productId);
        try{
            productService.delete(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception ex){
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
