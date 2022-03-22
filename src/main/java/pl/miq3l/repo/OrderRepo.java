package pl.miq3l.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.miq3l.domain.OrderUnit;

@Repository
public interface OrderRepo extends JpaRepository<OrderUnit, String> {
}
