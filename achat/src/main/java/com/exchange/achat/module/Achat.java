package com.exchange.achat.module;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private Date date;
    private Double total;

    @ElementCollection
    @CollectionTable(name = "achat_products", joinColumns = @JoinColumn(name = "achat_id"))
    private List<Long> productsIds;
}
