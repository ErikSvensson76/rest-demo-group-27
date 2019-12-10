package se.lexicon.erik.order_management.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.lexicon.erik.order_management.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    List<AppUser> findByActive(boolean active);
    Optional<AppUser> findByEmail(String email);

}
