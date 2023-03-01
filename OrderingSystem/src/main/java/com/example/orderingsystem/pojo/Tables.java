package com.example.orderingsystem.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "tables")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    private String tableState;
    private String name;

    public Integer getId() {
        return id;
    }

    public String getTableState() {
        return tableState;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTableState(String tableState) {
        this.tableState = tableState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
