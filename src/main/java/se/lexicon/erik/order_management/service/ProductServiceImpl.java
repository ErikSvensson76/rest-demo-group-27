package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.erik.order_management.data.ProductRepository;
import se.lexicon.erik.order_management.dto.ProductDto;
import se.lexicon.erik.order_management.entity.Product;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;
import se.lexicon.erik.order_management.exception.Exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

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


    /**
     *
     * @param productDto ProductDto
     * @return ProductDto of persisted Product
     * @throws IllegalArgumentException when param has an id of NOT 0
     */
    @Override
    public ProductDto createNewProduct(ProductDto productDto) throws IllegalArgumentException{
        if (productDto.getProductId() != 0){
            throw new IllegalArgumentException("Invalid Product ID need to be 0");
        }

        Product product = dtoConversionService.dtoToProduct(productDto);

        product = productRepository.save(product);

        return dtoConversionService.productToDto(product);
    }

    /**
     *
     * @param productDto ProductDto
     * @return ProductDto with Updated values
     * @throws IllegalArgumentException when param has an id of 0
     * @throws EntityNotFoundException when Product with the ID of param could not be found
     */
    @Override
    public ProductDto updateProduct(ProductDto productDto) throws IllegalArgumentException, EntityNotFoundException {
        if(productDto.getProductId() == 0){
            throw new IllegalArgumentException("Product had invalid id: " + productDto.getProductId());
        }

        Product savedProduct = productRepository.findById(productDto.getProductId())
                .orElseThrow(Exceptions.entityNotFoundException("Requested Product could not be found"));

        savedProduct.setProductName(productDto.getProductName());
        savedProduct.setProductDescription(productDto.getProductDescription());
        savedProduct.setPrice(productDto.getPrice());

        productRepository.save(savedProduct);

        return productDto;
    }

    /**
     *
     * @param ProductId int
     * @return ProductDto
     * @throws EntityNotFoundException when Product with passed in ProductId could not be found
     */
    @Override
    public ProductDto findById(int ProductId) throws EntityNotFoundException{
        return dtoConversionService.productToDto(productRepository.findById(ProductId)
                .orElseThrow(Exceptions.entityNotFoundException("Requested Product could not be found")));
    }

    /**
     *
     * @return List of all Products as DTO
     */
    @Override
    public List<ProductDto> findAll(){
        List<Product> products = (List<Product>) productRepository.findAll();

        return products.stream().map(dtoConversionService::productToDto).collect(Collectors.toList());
    }

    /**
     * This method finds by ProductName. <br> It's searching by <b>Containing</b> and <b>IgnoreCase</b>.
     * @param productName String
     * @return List of matching Products as DTO
     */
    @Override
    public List<ProductDto> findByProductName(String productName){
        List<Product> products = productRepository.findByProductNameContainingIgnoreCase(productName);

        List<ProductDto> dtoList = new ArrayList<>();

        products.forEach(product -> dtoList.add(dtoConversionService.productToDto(product)));

        return dtoList;
    }


}
