package com.exchange.achat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AchatDTO {
    private Long id;
    private String currency;
    private Date date;
    private Double total;
    private List<ProductDTO> products;
}
