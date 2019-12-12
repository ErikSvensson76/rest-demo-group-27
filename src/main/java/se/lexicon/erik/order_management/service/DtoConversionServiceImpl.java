package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.data.OrderItemRepository;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.dto.OrderItemDto;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.entity.OrderItem;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.entity.ProductOrder;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;
import se.lexicon.erik.order_management.exception.Exceptions;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoConversionServiceImpl implements DtoConversionService{

    private OrderItemRepository orderItemRepository;

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public AppUser dtoToAppUser(AppUserDto dto) {
        return new AppUser(
                dto.getAppUserId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.isActive(),
                dto.getRegDate() == null ? LocalDate.now() : dto.getRegDate()
        );
    }

    @Override
    public AppUserDto appUserToDto(AppUser appUser, boolean withOrders) {
        return new AppUserDto(
                appUser.getAppUserId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.isActive(),
                appUser.getRegDate()
        );
    }

    @Override
    public ProductDto productToDto(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getPrice()
        );
    }

    @Override
    public Product dtoToProduct(ProductDto dto) {
        return new Product(
              dto.getProductId(),
              dto.getProductName(),
              dto.getProductDescription(),
              dto.getPrice()
        );
    }

    @Override
    public OrderItemDto orderItemToDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getOrderItemId(),
                productToDto(orderItem.getProduct()),
                orderItem.getAmount(),
                orderItem.getItemPrice()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItem dtoToOrderItem(OrderItemDto dto) throws EntityNotFoundException {
        return orderItemRepository.findById(dto.getOrderItemId())
                .orElseThrow(Exceptions.entityNotFoundException("Conversion failed: Requested OrderItem could not be found"));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductOrderDto productOrderToDto(ProductOrder productOrder) {
        List<OrderItemDto> content = productOrder.getContent()
                .stream()
                .map(this::orderItemToDto)
                .collect(Collectors.toList());


        return new ProductOrderDto(
                productOrder.getOrderId(),
                productOrder.getOrderDateTime(),
                appUserToDto(productOrder.getAppUser(), false),
                content
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductOrder dtoToProductOrder(ProductOrderDto dto) {
        List<Long> contentIds = dto.getContent().stream()
                .map(OrderItemDto::getOrderItemId)
                .collect(Collectors.toList());
        List<OrderItem> content = (List<OrderItem>) orderItemRepository.findAllById(contentIds);
        return new ProductOrder(
                dto.getOrderId(),
                dto.getOrderDateTime(),
                dtoToAppUser(dto.getAppUser()),
                content
        );
    }
}
