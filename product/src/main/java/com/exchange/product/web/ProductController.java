package com.exchange.product.web;

import com.exchange.product.dto.ProductDTO;
import com.exchange.product.service.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductServiceImp productServiceImp;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        ProductDTO addProduct = productServiceImp.addProduct(productDTO);
        return new ResponseEntity<>(addProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") Long id){
        ProductDTO getProduct = productServiceImp.getProductById(id);
        return  new ResponseEntity<>(getProduct,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        List<ProductDTO> productDTOS = productServiceImp.getAllProducts();
        return  new ResponseEntity<>(productDTOS, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
         productServiceImp.delete(id);
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
