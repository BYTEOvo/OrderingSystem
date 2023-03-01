package com.example.orderingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "dish")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;
    private double price;
    private String description;
    private Boolean isSpecial;
    private String imgurl;

    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "tid")
    private DishType dishType;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DishType getDishType() {
        return dishType;
    }

    public Boolean getSpecial() {
        return isSpecial;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setSpecial(Boolean special) {
        isSpecial = special;
    }
}
