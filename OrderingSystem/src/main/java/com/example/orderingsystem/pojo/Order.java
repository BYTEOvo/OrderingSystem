package com.example.orderingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tableId")
    private Tables tables;

    @ManyToOne
    @JoinColumn(name = "delivery")
    private User delivery;

    private Double price;
    private String state;
    private Integer urate;
    private Integer r1;
    private Integer r2;
    private Integer r3;
    private Integer r4;
    private String comments;
    private String address;
    private String phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date payTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date createdTime;

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPayTime() {
        return payTime;
    }

    public Double getPrice() {
        return price;
    }

    public Tables getTables() {
        return tables;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public void setTables(Tables tables) {
        this.tables = tables;
    }

    public Integer getR1() {
        return r1;
    }

    public Integer getR2() {
        return r2;
    }

    public Integer getR3() {
        return r3;
    }

    public Integer getUrate() {
        return urate;
    }

    public void setR1(Integer r1) {
        this.r1 = r1;
    }

    public void setR2(Integer r2) {
        this.r2 = r2;
    }

    public void setR3(Integer r3) {
        this.r3 = r3;
    }

    public void setUrate(Integer urate) {
        this.urate = urate;
    }

    public Integer getR4() {
        return r4;
    }

    public void setR4(Integer r4) {
        this.r4 = r4;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public User getDelivery() {
        return delivery;
    }

    public void setDelivery(User delivery) {
        this.delivery = delivery;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
