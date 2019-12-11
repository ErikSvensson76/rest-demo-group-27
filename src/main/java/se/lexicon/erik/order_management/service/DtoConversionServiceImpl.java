package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.erik.order_management.data.AppUserRepository;
import se.lexicon.erik.order_management.data.OrderItemRepository;
import se.lexicon.erik.order_management.data.ProductOrderRepository;
import se.lexicon.erik.order_management.data.ProductRepository;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.dto.OrderItemDto;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.entity.OrderItem;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.entity.ProductOrder;

import java.time.LocalDate;

@Service
public class DtoConversionServiceImpl implements DtoConversionService{

    private AppUserRepository appUserRepository;
    private OrderItemRepository orderItemRepository;
    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Autowired
    public void setOrderItemRepository(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Autowired
    public void setProductOrderRepository(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public AppUser dtoToAppUser(AppUserDto dto) {
        AppUser newUser = new AppUser(
                dto.getAppUserId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.isActive(),
                dto.getRegDate() == null ? LocalDate.now() : dto.getRegDate()
        );

        return newUser;
    }

    @Override
    public AppUserDto appUserToDto(AppUser appUser, boolean withOrders) {
        AppUserDto dto = new AppUserDto(
                appUser.getAppUserId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.isActive(),
                appUser.getRegDate()
        );

        return dto;
    }

    @Override
    public ProductDto productToDto(Product product) {
        return null;
    }

    @Override
    public Product dtoToProduct(ProductDto dto) {
        return null;
    }

    @Override
    public OrderItemDto orderItemToDto(OrderItem orderItem) {
        return null;
    }

    @Override
    public OrderItem dtoToOrderItem(OrderItemDto dto) {
        return null;
    }

    @Override
    public ProductOrderDto productOrderToDto(ProductOrder productOrder) {
        return null;
    }

    @Override
    public ProductOrder dtoToProductOrder(ProductOrderDto dto) {
        return null;
    }
}
