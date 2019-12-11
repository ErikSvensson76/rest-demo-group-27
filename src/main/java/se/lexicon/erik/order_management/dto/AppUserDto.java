package se.lexicon.erik.order_management.dto;

import java.time.LocalDate;
import java.util.List;

public class AppUserDto {
    private int appUserId;
    private String firstName;
    private String lastName;
    private String email;
    private boolean active;
    private LocalDate regDate;
    private List<ProductOrderDto> orders;

    public AppUserDto(int appUserId, String firstName, String lastName, String email, boolean active, LocalDate regDate) {
        setAppUserId(appUserId);
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setActive(active);
        setRegDate(regDate);
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public List<ProductOrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<ProductOrderDto> orders) {
        this.orders = orders;
    }
}
