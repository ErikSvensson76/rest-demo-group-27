package se.lexicon.erik.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.service.ProductService;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/product")
    public ResponseEntity<List<ProductDto>> find(){
        List<ProductDto> productDtos = productService.findAll();
        return ResponseEntity.ok(productDtos);
    }

    @PostMapping("/api/product")
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto){
        return  ResponseEntity.status(HttpStatus.CREATED).body(productService.createNewProduct(dto));
    }

    @GetMapping("/api/product/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable int id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/api/product/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable int id, @RequestBody ProductDto updated){
        if(id != updated.getProductId()){
            throw new IllegalArgumentException();
        }
        ProductDto returnDto = productService.updateProduct(updated);
        return ResponseEntity.ok(returnDto);
    }

}