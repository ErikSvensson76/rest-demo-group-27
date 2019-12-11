package se.lexicon.erik.order_management.service;

import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.dto.OrderItemDto;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.entity.OrderItem;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.entity.ProductOrder;

public interface DtoConversionService {
    AppUser dtoToAppUser(AppUserDto dto);
    AppUserDto appUserToDto(AppUser appUser, boolean withOrders);
    ProductDto productToDto(Product product);
    Product dtoToProduct(ProductDto dto);
    OrderItemDto orderItemToDto(OrderItem orderItem);
    OrderItem dtoToOrderItem(OrderItemDto dto);
    ProductOrderDto productOrderToDto(ProductOrder productOrder);
    ProductOrder dtoToProductOrder(ProductOrderDto dto);
}
