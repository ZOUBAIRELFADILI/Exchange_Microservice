package com.exchange.achat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AchatReq {
    //private Date date;
    private String currency;
    private List<Long> productsIds;
}
