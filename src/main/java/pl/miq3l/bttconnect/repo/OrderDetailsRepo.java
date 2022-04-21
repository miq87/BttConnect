package pl.miq3l.bttconnect.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.miq3l.bttconnect.domain.OrderDetails;

import java.util.List;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderDetails, String> {
    @Query("SELECT od FROM OrderDetails od WHERE od.orderUnit.customerPo = :customerPo")
    List<OrderDetails> findOrderDetailsByCustomerPo(@Param("customerPo") String customerPo);
}
