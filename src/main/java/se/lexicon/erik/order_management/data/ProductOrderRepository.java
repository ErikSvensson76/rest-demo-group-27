package se.lexicon.erik.order_management.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.erik.order_management.entity.ProductOrder;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductOrderRepository extends CrudRepository<ProductOrder,Long> {

    List<ProductOrder> findByAppUserAppUserId(int appUserId);

    List<ProductOrder> findByContentProductProductId(int productId);

    List<ProductOrder> findByOrderDateTimeBetween(LocalDateTime start, LocalDateTime end);

}
