package se.lexicon.erik.order_management.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;
    private LocalDateTime orderDateTime;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private AppUser appUser;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            mappedBy = "order",
            orphanRemoval = true
    )
    private List<OrderItem> content;

    public ProductOrder(long orderId, LocalDateTime orderDateTime, AppUser appUser, List<OrderItem> content) {
        this.orderId = orderId;
        setOrderDateTime(orderDateTime);
        setAppUser(appUser);
        setContent(content);
    }

    /**
     * Needed by Hibernate
     */
    ProductOrder(){}

    public long getOrderId() {
        return orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<OrderItem> getContent() {
        return content;
    }

    public void setContent(List<OrderItem> content) {
        if(content == null){
            if(this.content != null){
                for(OrderItem item : this.content){
                    item.setOrder(null);
                }

            }
        }else{
            for(OrderItem orderItem : content){
                addOrderItem(orderItem);
            }
        }
        this.content = content;
    }

    public boolean addOrderItem(OrderItem orderItem){
        if(content == null) setContent(new ArrayList<>());
        if(orderItem == null){
            throw new IllegalArgumentException("OrderItem was null");
        }
        if(!content.contains(orderItem)){
            content.add(orderItem);
            orderItem.setOrder(this);
            return true;
        }
        return false;
    }

    public boolean removeOrderItem(OrderItem orderItem){
        if(content == null) setContent(new ArrayList<>());
        if(orderItem == null){
            throw new IllegalArgumentException("OrderItem was null");
        }
        if(this.content.contains(orderItem)){
            orderItem.setOrder(null);
            return content.remove(orderItem);
        }
        return false;
    }

    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice = BigDecimal.ZERO;
        if(this.content != null){
            for(OrderItem item : content){
                totalPrice = totalPrice.add(item.getItemPrice());
            }
        }
        return totalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductOrder that = (ProductOrder) o;
        return orderId == that.orderId &&
                Objects.equals(orderDateTime, that.orderDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDateTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProductOrder{");
        sb.append("orderId=").append(orderId);
        sb.append(", orderDateTime=").append(orderDateTime);
        sb.append('}');
        return sb.toString();
    }
}
