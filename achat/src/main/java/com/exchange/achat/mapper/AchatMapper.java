package com.exchange.achat.mapper;

import com.exchange.achat.dto.AchatDTO;
import com.exchange.achat.dto.AchatReq;
import com.exchange.achat.dto.ProductDTO;
import com.exchange.achat.module.Achat;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AchatMapper {
    public AchatDTO toDto(Achat achat, List<ProductDTO> productDTOs){
        AchatDTO achatDTO = new AchatDTO();
        achatDTO.setId(achat.getId());
        achatDTO.setDate(achat.getDate());
        achatDTO.setCurrency(achat.getCurrency());
        achatDTO.setTotal(achat.getTotal());

        // Set the fetched ProductDTOs
        achatDTO.setProducts(productDTOs);
        return achatDTO;
    }

    public Achat toEntity(AchatDTO achatDTO){
        Achat achat = new Achat();
        achat.setId(achatDTO.getId());
        achat.setDate(achatDTO.getDate());
        achat.setCurrency(achatDTO.getCurrency());
        achat.setTotal(achatDTO.getTotal());

        // Convert List<ProductDTO> to List<Long> (product IDs)
        if (achatDTO.getProducts() != null) {
            achat.setProductsIds(achatDTO.getProducts().stream()
                    .map(ProductDTO::getId)
                    .collect(Collectors.toList()));
        }
        return achat;
    }

    public Achat reqToEntity (AchatReq achatReq){
        Achat achat = Achat.builder()
                //.date(achatReq.getDate())
                .currency(achatReq.getCurrency())
                .total(null)
                .productsIds(achatReq.getProductsIds())
                .build();

        System.out.println(achat);
        return  achat;
    }
}
