package se.lexicon.erik.order_management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.dto.OrderItemDto;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.dto.ProductOrderDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.entity.OrderItem;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.entity.ProductOrder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class ProductOrderServiceImplTest {

    @Autowired private ProductOrderService productOrderService;
    @Autowired private TestEntityManager em;

    static final LocalDateTime DATE_TIME = LocalDateTime.of(2019,12,12,10,11);

    private AppUser appUser;
    private List<Product> products;
    private ProductOrder productOrder;

    @BeforeEach
    public void setUp() {
        appUser = em.persistAndFlush(new AppUser(0, "Test", "Testsson", "test@test.com", true, LocalDate.parse("2019-12-12")));
        products = productData();
        products.forEach(em::persistAndFlush);
        List<OrderItem> orderItems = orderItemData(products);

        productOrder = new ProductOrder(0, DATE_TIME, appUser, orderItems);
        em.persistAndFlush(productOrder);
    }

    public List<Product> productData(){
        return new ArrayList<>(Arrays.asList(
                new Product(0, "product1","product1 description", BigDecimal.valueOf(10)),
                new Product(0, "product2", "product2 description",BigDecimal.valueOf(11.90))
        ));
    }

    public List<OrderItem> orderItemData(List<Product> products){
        return new ArrayList<>(Arrays.asList(
                new OrderItem(products.get(0), 5),
                new OrderItem(products.get(1), 2)
        ));
    }

    @Test
    public void given_valid_productOrderDto_createNewProductOrder_successfully_return_expected_dto(){
        AppUserDto appUserDto = new AppUserDto(appUser.getAppUserId(), appUser.getFirstName(), appUser.getLastName(), appUser.getEmail(),appUser.isActive(),appUser.getRegDate());
        ProductDto productDto = new ProductDto(products.get(0).getProductId(), products.get(0).getProductName(), products.get(0).getProductDescription(), products.get(0).getPrice());
        OrderItemDto orderItemDto = new OrderItemDto(0, productDto, 0, 5, productDto.getPrice());
        ProductOrderDto productOrderDto = new ProductOrderDto(0,DATE_TIME,appUserDto,Arrays.asList(orderItemDto));

        ProductOrderDto result = productOrderService.createNewProductOrder(productOrderDto);
        assertTrue(result.getOrderId() > 0);
        assertNotNull(em.find(ProductOrder.class, result.getOrderId()));
    }

    @Test
    public void given_updated_productOrderDto_updateProductOrder_should_update_and_return_productOrderDto(){
        //Arrange

        AppUserDto appUserDto = new AppUserDto(appUser.getAppUserId(), appUser.getFirstName(), appUser.getLastName(), appUser.getEmail(),appUser.isActive(),appUser.getRegDate());
        ProductDto productDto = new ProductDto(products.get(0).getProductId(), products.get(0).getProductName(), products.get(0).getProductDescription(), products.get(0).getPrice());
        OrderItemDto orderItemDto = new OrderItemDto(0, productDto, 0, 5, productDto.getPrice());
        ProductOrderDto productOrderDto = new ProductOrderDto(0,DATE_TIME,appUserDto,Arrays.asList(orderItemDto));
        ProductOrderDto dto = productOrderService.createNewProductOrder(productOrderDto);

        OrderItemDto newOrderItemDto = new OrderItemDto(
                0,
                new ProductDto(products.get(1).getProductId(), products.get(1).getProductName(), products.get(1).getProductDescription(),products.get(1).getPrice()),
                0,
                10,
                BigDecimal.valueOf(10 * products.get(1).getPrice().doubleValue()).setScale(2)
        );
        dto.getContent().add(newOrderItemDto);

        ProductOrderDto updated = productOrderService.updateProductOrder(dto);
        em.flush();
        assertNotNull(updated);
        assertEquals(2, updated.getContent().size());
        assertEquals(2, em.find(ProductOrder.class, updated.getOrderId()).getContent().size());
    }
}
