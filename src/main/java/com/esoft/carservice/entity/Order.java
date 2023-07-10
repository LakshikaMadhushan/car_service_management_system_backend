package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long orderId;
    public Date orderDate;
    public double cost;

    @ManyToOne(fetch = FetchType.LAZY)
    public Customer customer;
    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsList;
}
