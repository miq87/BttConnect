package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.models.OrderDetails;
import pl.miq3l.bttconnect.repo.OrderDetailsRepo;

import java.util.List;

@Service
public class OrderDetailsService {
    private final OrderDetailsRepo orderDetailsRepo;

    @Autowired
    public OrderDetailsService(OrderDetailsRepo orderDetailsRepo) {
        this.orderDetailsRepo = orderDetailsRepo;
    }

    public List<OrderDetails> findByCustomerPo(String customerPo) {
        return orderDetailsRepo.findOrderDetailsByCustomerPo(customerPo);
    }

}
