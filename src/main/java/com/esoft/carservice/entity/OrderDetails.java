package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long orderDetailsId;

    @ManyToOne(fetch = FetchType.LAZY)
    public Order order;
    @ManyToOne(fetch = FetchType.LAZY)
    public Item item;
}
