package se.lexicon.erik.order_management.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appUserId;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    private boolean active;
    private LocalDate regDate;

    public AppUser(int appUserId, String firstName, String lastName, String email, boolean active, LocalDate regDate) {
        this.appUserId = appUserId;
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
        setActive(active);
        this.regDate = regDate;
    }

    /**
     * Needed by Hibernate
     */
    AppUser(){}

    public int getAppUserId() {
        return appUserId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return appUserId == appUser.appUserId &&
                Objects.equals(email, appUser.email) &&
                Objects.equals(regDate, appUser.regDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, email, regDate);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppUser{");
        sb.append("appUserId=").append(appUserId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", active=").append(active);
        sb.append(", regDate=").append(regDate);
        sb.append('}');
        return sb.toString();
    }
}
