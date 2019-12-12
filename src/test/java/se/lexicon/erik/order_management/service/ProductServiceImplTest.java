package se.lexicon.erik.order_management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class ProductServiceImplTest {

    @Autowired
    ProductService productService;
    @Autowired
    TestEntityManager entityManager;

   private Product testObject;
   private int productId = 0;
   private String productName = "Book";
   private String productDescription = "A really good book";
   private BigDecimal productPrice = BigDecimal.valueOf(99.0);


    @BeforeEach
    void setUp() {
        testObject = entityManager.persistAndFlush(new Product(productId, productName, productDescription, productPrice));
    }

    @Test
    void given_ProductDto_createNewProduct_successfully_return_updated_DTO() {

        ProductDto productDto = new ProductDto(
                productId,
                productName,
                productDescription,
                productPrice
        );
        productDto = productService.createNewProduct(productDto);

        assertTrue(productDto.getProductId() > 0);
    }
    @Test
    void given_existing_Product_createNewProduct_throws_IllegalArgumentException() {

        ProductDto notNew = new ProductDto(
                testObject.getProductId(),
                testObject.getProductName(),
                testObject.getProductDescription(),
                testObject.getPrice()
        );
        assertThrows(IllegalArgumentException.class, ()-> productService.createNewProduct(notNew));
    }

    @Test
    void given_updated_ProductDto_updateProduct_successfully_return_updated_DTO() {

        ProductDto productDto = new ProductDto(
                testObject.getProductId(),
                "Another book",
                "This is also a good book",
                BigDecimal.TEN);

        productService.updateProduct(productDto);

        Product savedProduct = entityManager.find(Product.class, productDto.getProductId());

        assertNotNull(savedProduct);
        assertEquals(productDto.getProductName(), savedProduct.getProductName());
        assertEquals(productDto.getProductDescription(), savedProduct.getProductDescription());
        assertEquals(productDto.getPrice(), savedProduct.getPrice());

    }

    @Test
    void given_new_ProductDto_updateProduct_throws_IllegalArgumentException() {
        ProductDto newDto = new ProductDto(
                0,
                "New book",
                "This is a new book",
                BigDecimal.valueOf(10.0)
                );

        assertThrows(IllegalArgumentException.class, ()-> productService.updateProduct(newDto), "Product had invalid id: "+ newDto.getProductId());
    }

    @Test
    void given_productDtoId_findById_return_ProductDto() {
        int id = testObject.getProductId();

        ProductDto foundProduct = productService.findById(id);

        assertNotNull(foundProduct);
        assertEquals(testObject.getProductName(), foundProduct.getProductName());
    }

    @Test
    void given_nonExisting_Product_findById_throws_EntityNotFoundException() {

        assertThrows(EntityNotFoundException.class,
                () -> productService.findById(0), "Requested Product could not be found" );
    }

    @Test
    void findByAll_return_all_existing_Products() {
        assertEquals(1, productService.findAll().size());
    }

    @Test
    void given_productName_findByProductName_successfully_return_List_of_ProductDto() {

        List<ProductDto> productDtos = productService.findByProductName("bOO");

        assertEquals(1, productDtos.size());
        assertEquals("Book", productDtos.get(0).getProductName());
    }

}
