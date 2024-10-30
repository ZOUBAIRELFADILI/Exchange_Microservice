package com.exchange.product.service;

import com.exchange.product.dto.ProductDTO;
import com.exchange.product.mapper.ProductMapper;
import com.exchange.product.module.Product;
import com.exchange.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImp implements ProductService{
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product=productMapper.toEntity(productDTO);
        Product savedProduct=productRepository.save(product);
        return productMapper.toDTO(savedProduct);

    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional=productRepository.findById(id);
        if(productOptional.isPresent()){
            Product product=productOptional.get();
            product.setProductName(productDTO.getProductName());
            product.setCat(product.getCat());
            product.setPrice(product.getPrice());
            Product updateProduct=productRepository.save(product);
            return productMapper.toDTO(updateProduct);
        }else{
            throw new RuntimeException("product not existe with id: "+ id);
        }
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("this product not existe"));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not existe"));
        productRepository.delete(product);

    }
}
