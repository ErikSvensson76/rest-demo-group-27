package se.lexicon.erik.order_management.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private ProductOrder order;
    private int amount;
    private BigDecimal itemPrice;

    public OrderItem(long orderItemId, Product product, ProductOrder order, int amount, int discount) {
        if(discount < 0 || discount > 100) throw new IllegalArgumentException("discount was " + discount + ". Valid range is 0 - 100");
        this.orderItemId = orderItemId;
        setProduct(product);
        setOrder(order);
        setAmount(amount);

        BigDecimal fullPrice = BigDecimal.valueOf(product.getPrice().doubleValue() * amount);
        BigDecimal multiplicand = null;
        if(discount > 0){
            multiplicand = BigDecimal.valueOf(1.0 - (discount / 100.0)).setScale(2, RoundingMode.HALF_UP);
        }
        setItemPrice(discount == 0 ? fullPrice : fullPrice.multiply(multiplicand));
    }

    public OrderItem(long orderItemId, Product product, int amount, int discount) {
        this(orderItemId, product, null,amount, discount);
    }

    public OrderItem(Product product, int amount){
        this(0, product, amount, 0);
    }

    OrderItem(){}

    public long getOrderItemId() {
        return orderItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductOrder getOrder() {
        return order;
    }

    public void setOrder(ProductOrder order) {
        this.order = order;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getItemPrice() {
        return itemPrice.setScale(2);
    }

    void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice.setScale(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return orderItemId == orderItem.orderItemId &&
                amount == orderItem.amount &&
                Objects.equals(product, orderItem.product) &&
                Objects.equals(order, orderItem.order) &&
                Objects.equals(itemPrice, orderItem.itemPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, product, order, amount, itemPrice);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderItem{");
        sb.append("orderItemId=").append(orderItemId);
        sb.append(", product=").append(product);
        sb.append(", amount=").append(amount);
        sb.append(", itemPrice=").append(itemPrice);
        sb.append('}');
        return sb.toString();
    }
}
