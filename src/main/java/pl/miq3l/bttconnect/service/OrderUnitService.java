package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.components.PhConnect;
import pl.miq3l.bttconnect.models.OrderDetails;
import pl.miq3l.bttconnect.models.OrderUnit;
import pl.miq3l.bttconnect.repo.OrderUnitRepo;

import java.util.ArrayList;
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
        List<OrderDetails> orderDetails = new ArrayList<>();
        if(orderUnitRepo.findById(customerPo).isPresent()) {
            OrderUnit orderUnit = orderUnitRepo.findById(customerPo).get();
            orderUnit.getOrderDetails().clear();

            orderDetails = ph.getOrderDetailsByOrderUrl(orderUnit.getOrderUrl());

            orderDetails.forEach(od -> od.setOrderUnit(orderUnit));

            orderUnit.getOrderDetails().addAll(orderDetails);
            orderUnitRepo.save(orderUnit);
            orderDetails = orderUnitRepo.findById(customerPo).get().getOrderDetails();
        }
        return orderDetails;
    }

    public List<OrderDetails> findOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        if(orderUnitRepo.findById(customerPo).isPresent())
             orderDetails = orderUnitRepo.findById(customerPo).get().getOrderDetails();
        return orderDetails;
    }
}
