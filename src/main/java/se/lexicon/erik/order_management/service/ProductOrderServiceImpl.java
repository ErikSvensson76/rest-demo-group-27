package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.data.AppUserRepository;
import se.lexicon.erik.order_management.data.ProductOrderRepository;
import se.lexicon.erik.order_management.data.ProductRepository;
import se.lexicon.erik.order_management.dto.OrderItemDto;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.entity.OrderItem;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.entity.ProductOrder;
import se.lexicon.erik.order_management.exception.Exceptions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;
    private AppUserRepository appUserRepository;
    private ProductRepository productRepository;
    private DtoConversionService conversionService;

    @Autowired
    public void setProductOrderRepository(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Autowired
    public void setConversionService(DtoConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Autowired
    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     *
     * @param productOrderDto - ProductOrderDto of a new order. OrderId has to be 0
     * @return ProductOrderDto that based on persisted ProductOrder
     * @throws IllegalArgumentException when OrderId is not 0     *
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductOrderDto createNewProductOrder(ProductOrderDto productOrderDto) throws IllegalArgumentException{
        if(productOrderDto.getOrderId() != 0)throw new IllegalArgumentException("ProductOrderDto had invalid id: " + productOrderDto.getOrderId());

        AppUser appUser = appUserRepository.findById(productOrderDto.getAppUser().getAppUserId()).orElseThrow(Exceptions.entityNotFoundException("Invalid AppUser"));
        List<OrderItem> orderItems= new ArrayList<>();
        for(OrderItemDto dto: productOrderDto.getContent()){
            Product product = productRepository.findById(dto.getProduct().getProductId()).orElseThrow(Exceptions.entityNotFoundException("Invalid Product"));
            orderItems.add(new OrderItem(product, dto.getAmount()));
        }

        ProductOrder productOrder = new ProductOrder(
                0,
                productOrderDto.getOrderDateTime() == null ? LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES) : productOrderDto.getOrderDateTime(),
                appUser,
                orderItems
        );

        productOrder = productOrderRepository.save(productOrder);
        return conversionService.productOrderToDto(productOrder);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ProductOrderDto updateProductOrder(ProductOrderDto dto) {
       if(dto.getOrderId() == 0) throw  new IllegalArgumentException("Invalid OrderId");

       ProductOrder productOrder = productOrderRepository.findById(dto.getOrderId())
               .orElseThrow(Exceptions.entityNotFoundException("Could not find a ProductOrder with id " + dto.getOrderId()));

       ProductOrder updated = conversionService.dtoToProductOrder(dto);

       productOrder.setContent(null);
       productOrder.setContent(updated.getContent());

       productOrderRepository.save(productOrder);

       return dto;
    }


}
