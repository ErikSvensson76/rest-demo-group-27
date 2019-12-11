package se.lexicon.erik.order_management.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.erik.order_management.entity.Product;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByProductNameContainingIgnoreCase(String productName);
}
