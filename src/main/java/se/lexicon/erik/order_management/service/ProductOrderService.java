package se.lexicon.erik.order_management.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;

import java.util.List;

public interface ProductOrderService {
    @Transactional(rollbackFor = RuntimeException.class)
    ProductOrderDto createNewProductOrder(ProductOrderDto productOrderDto) throws IllegalArgumentException, EntityNotFoundException;

    ProductOrderDto updateProductOrder(ProductOrderDto dto);

    @Transactional(readOnly = true)
    List<ProductOrderDto> findByProductId(int productId);
}
