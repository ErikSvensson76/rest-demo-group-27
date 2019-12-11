package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.erik.order_management.data.ProductRepository;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl {

    private ProductRepository productRepository;
    private DtoConversionService dtoConversionService;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    public void setDtoConversionService(DtoConversionService dtoConversionService) {
        this.dtoConversionService = dtoConversionService;
    }

    public ProductDto createNewProduct(ProductDto productDto){
        if (productDto.getProductId() != 0){
            throw new IllegalArgumentException("Invalid Product ID need to be 0");
        }

        Product product = dtoConversionService.dtoToProduct(productDto);

        product = productRepository.save(product);

        return dtoConversionService.productToDto(product);
    }


    public ProductDto updateProduct(ProductDto productDto){
        Product savedProduct = productRepository.findById(productDto.getProductId()).orElseThrow(IllegalArgumentException::new);

        savedProduct.setProductName(productDto.getProductName());
        savedProduct.setProductDescription(productDto.getProductDescription());
        savedProduct.setPrice(productDto.getPrice());

        productRepository.save(savedProduct);

        return productDto;
    }


    public ProductDto findById(int id){
        return dtoConversionService.productToDto(productRepository.findById(id).orElseThrow(IllegalArgumentException::new));
    }


    public List<ProductDto> findAll(){
        List<Product> products = (List<Product>) productRepository.findAll();

        return products.stream().map(dtoConversionService::productToDto).collect(Collectors.toList());
    }


    public List<ProductDto> findByProductName(String productName){
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);

        List<ProductDto> dtoList = new ArrayList<>();

        products.forEach(product -> dtoList.add(dtoConversionService.productToDto(product)));

        return dtoList;
    }


}
