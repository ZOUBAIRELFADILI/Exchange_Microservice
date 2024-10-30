package com.exchange.product.service;

import com.exchange.product.dto.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public ProductDTO addProduct(ProductDTO productDTO);
    public ProductDTO updateProduct(Long id,ProductDTO productDTO);
    public ProductDTO getProductById(Long id);
    public List<ProductDTO> getAllProducts();
    public void delete(Long id);
}
