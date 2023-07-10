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
public class MechanicServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long mechanicServiceCategoryId;
    public String name;

    @OneToMany(mappedBy = "mechanicServiceCategory")
    private List<MechanicService> mechanicServiceList;
}
