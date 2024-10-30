package com.exchange.product.mapper;

import com.exchange.product.dto.ProductDTO;
import com.exchange.product.module.Product;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public Product toEntity(ProductDTO productDTO){
        Product product=new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
