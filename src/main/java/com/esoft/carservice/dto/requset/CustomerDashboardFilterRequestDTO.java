package com.esoft.carservice.dto.requset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDashboardFilterRequestDTO {
//    @Temporal(TemporalType.DATE)
//    public Date start;
//    @Temporal(TemporalType.DATE)
//    public Date end;
public long userId;
}
