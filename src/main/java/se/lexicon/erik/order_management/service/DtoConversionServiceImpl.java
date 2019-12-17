package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.data.OrderItemRepository;
import se.lexicon.erik.order_management.data.ProductOrderRepository;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DtoConversionServiceImpl implements DtoConversionService{

    private OrderItemRepository orderItemRepository;
    private ProductOrderRepository productOrderRepository;

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setProductOrderRepository(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
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
    @Transactional(readOnly = true)
    public AppUserDto appUserToDto(AppUser appUser, boolean withOrders) {
        AppUserDto dto = new AppUserDto(
                appUser.getAppUserId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.isActive(),
                appUser.getRegDate()
        );

        if(withOrders){
            List<ProductOrder> orders = productOrderRepository.findByAppUserAppUserId(appUser.getAppUserId());
            dto.setOrders(orders
                    .stream()
                    .map(this::productOrderToDto)
                    .collect(Collectors.toList())
            );
        }
        return dto;

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
                orderItem.getOrder().getOrderId(),
                orderItem.getAmount(),
                orderItem.getItemPrice()
        );
    }

    @Override
    public OrderItem dtoToOrderItem(OrderItemDto dto) throws EntityNotFoundException {
        return new OrderItem(
                dto.getOrderItemId(),
                dtoToProduct(dto.getProduct()),
                dto.getOrderId() == 0 ? null : productOrderRepository.findById(dto.getOrderId()).orElseThrow(Exceptions.entityNotFoundException("Conversion to OrderItem failed: Requested Product could not be found")),
                dto.getAmount(),
                0
        );
    }

    @Override
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
    public ProductOrder dtoToProductOrder(ProductOrderDto dto) {
        List<OrderItem> content = dto.getContent()
                .stream()
                .map(this::dtoToOrderItem)
                .collect(Collectors.toList());

        return new ProductOrder(
                dto.getOrderId(),
                dto.getOrderDateTime(),
                dtoToAppUser(dto.getAppUser()),
                content
        );
    }
}
