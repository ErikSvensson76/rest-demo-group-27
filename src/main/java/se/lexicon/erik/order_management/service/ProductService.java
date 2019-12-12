package se.lexicon.erik.order_management.service;

import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;

import java.util.List;

public interface ProductService {

    ProductDto createNewProduct(ProductDto productDto) throws IllegalArgumentException;

    ProductDto updateProduct(ProductDto productDto) throws IllegalArgumentException, EntityNotFoundException;

    ProductDto findById(int ProductId) throws EntityNotFoundException;

    List<ProductDto> findAll();

    List<ProductDto> findByProductName(String productName);
}
