package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.components.PhConnect;
import pl.miq3l.bttconnect.exceptions.OrderDetailsNotFound;
import pl.miq3l.bttconnect.exceptions.OrderNotFoundException;
import pl.miq3l.bttconnect.models.OrderDetails;
import pl.miq3l.bttconnect.models.OrderUnit;
import pl.miq3l.bttconnect.repo.OrderUnitRepo;

import java.util.List;

@Service
public class OrderUnitService {
    private final OrderUnitRepo orderUnitRepo;
    private final PhConnect ph;

    @Autowired
    public OrderUnitService(OrderUnitRepo orderUnitRepo, PhConnect ph) {
        this.orderUnitRepo = orderUnitRepo;
        this.ph = ph;
    }

    public List<OrderUnit> findAll() {
        return orderUnitRepo.findAll();
    }

    public List<OrderUnit> loadAll(int limit) {
        orderUnitRepo.deleteAll();
        orderUnitRepo.saveAll(ph.getOrderUnits(limit));
        return findAll();
    }

    public List<OrderDetails> loadOrderDetailByCustomerPo(String customerPo) {
        OrderUnit orderUnit = orderUnitRepo.findById(customerPo).orElseThrow(OrderNotFoundException::new);
        List<OrderDetails> orderDetails = ph.getOrderDetailsByOrderUrl(orderUnit.getOrderUrl());
        orderUnit.addOrderDetails(orderDetails);
        orderUnitRepo.save(orderUnit);
        return orderUnitRepo.findById(customerPo).orElseThrow(OrderNotFoundException::new).getOrderDetails();
    }

    public List<OrderDetails> findOrderDetailByCustomerPo(String customerPo) {
        return orderUnitRepo.findById(customerPo).orElseThrow(OrderDetailsNotFound::new).getOrderDetails();
    }
}
