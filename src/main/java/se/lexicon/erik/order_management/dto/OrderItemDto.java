package se.lexicon.erik.order_management.dto;

import java.math.BigDecimal;

public class OrderItemDto {

    private long orderItemId;
    private ProductDto product;
    private int amount;
    private BigDecimal itemPrice;

    public OrderItemDto(long orderItemId, ProductDto product, int amount, BigDecimal itemPrice) {
        setOrderItemId(orderItemId);
        setProduct(product);
        setAmount(amount);
        setItemPrice(itemPrice);
    }

    public OrderItemDto(){}

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
}
