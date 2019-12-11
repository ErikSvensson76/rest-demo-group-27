package se.lexicon.erik.order_management.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductOrderDto {

    private long orderId;
    private LocalDateTime orderDateTime;
    private AppUserDto appUser;
    private List<OrderItemDto> content;

    public ProductOrderDto(long orderId, LocalDateTime orderDateTime, AppUserDto appUser, List<OrderItemDto> content) {
        setOrderId(orderId);
        setOrderDateTime(orderDateTime);
        setAppUser(appUser);
        setContent(content);
    }

    public ProductOrderDto(){}

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public AppUserDto getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUserDto appUser) {
        this.appUser = appUser;
    }

    public List<OrderItemDto> getContent() {
        return content;
    }

    public void setContent(List<OrderItemDto> content) {
        this.content = content;
    }
}
