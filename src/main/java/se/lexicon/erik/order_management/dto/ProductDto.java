package se.lexicon.erik.order_management.dto;

import java.math.BigDecimal;

public class ProductDto {

    private int productId;
    private String productName;
    private String productDescription;
    private BigDecimal price;

    public ProductDto(int productId, String productName, String productDescription, BigDecimal price) {
        setProductId(productId);
        setProductName(productName);
        setProductDescription(productDescription);
        setPrice(price);
    }

    public ProductDto(){}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
