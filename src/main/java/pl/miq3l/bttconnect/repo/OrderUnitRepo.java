package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.miq3l.bttconnect.models.OrderUnit;

@Repository
public interface OrderUnitRepo extends JpaRepository<OrderUnit, String> {
}
