package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.PhConnect;
import pl.miq3l.bttconnect.domain.OrderDetail;
import pl.miq3l.bttconnect.domain.OrderUnit;
import pl.miq3l.bttconnect.repo.OrderRepo;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final PhConnect ph;

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
        ph = PhConnect.getInstance();
        ph.login();
    }

    public List<OrderUnit> findAll() {
        return orderRepo.findAll();
    }

    public List<OrderUnit> loadAll() {
        return ph.getOrderUnits(3);
    }

    public List<OrderDetail> getOrderDetailByCustomerPo(String customerPo) {
        return ph.getOrderDetailByCustomerPo(customerPo);
    }
}
