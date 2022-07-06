package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.miq3l.bttconnect.models.OrderUnit;

import java.util.List;

@Repository
public interface OrderUnitRepo extends JpaRepository<OrderUnit, String> {
//    @Override
//    @Query("SELECT DISTINCT o FROM OrderUnit o JOIN FETCH o.orderDetails")
//    List<OrderUnit> findAll();
}
