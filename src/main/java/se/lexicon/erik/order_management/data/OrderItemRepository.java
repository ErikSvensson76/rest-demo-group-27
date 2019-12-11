package se.lexicon.erik.order_management.data;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.erik.order_management.entity.OrderItem;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {
}
