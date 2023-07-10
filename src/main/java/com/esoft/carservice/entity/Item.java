package com.esoft.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long itemId;
    public String itemName;
    public double sellingPrice;
    public double buyingPrice;
    public String brand;
    public int quantity;
    public String sellerName;

    @ManyToOne(fetch = FetchType.LAZY)
    public ItemCategory itemCategory;
    @OneToMany(mappedBy = "item")
    private List<ServiceDetails> serviceDetailsList;
    @OneToMany(mappedBy = "item")
    private List<OrderDetails> orderDetailsList;
}
