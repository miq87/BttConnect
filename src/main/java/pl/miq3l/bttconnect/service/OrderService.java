package pl.miq3l.bttconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.miq3l.bttconnect.PhConnect;
import pl.miq3l.bttconnect.domain.OrderDetail;
import pl.miq3l.bttconnect.domain.OrderUnit;
import pl.miq3l.bttconnect.repo.OrderRepo;

import java.util.ArrayList;
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

    public List<OrderUnit> loadAll(int limit) {
        orderRepo.deleteAll();
        orderRepo.saveAll(ph.getOrderUnits(limit));
        return findAll();
    }

    public List<OrderDetail> loadOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetail> orderDetails = ph.getOrderDetailByCustomerPo(customerPo);

        if(orderRepo.findById(customerPo).isPresent()) {
            OrderUnit orderUnit = orderRepo.findById(customerPo).get();
            orderUnit.getOrderDetails().clear();

            orderDetails.forEach(od -> od.setOrderUnit(orderUnit));

            orderUnit.getOrderDetails().addAll(orderDetails);
            orderRepo.save(orderUnit);
        }
        return orderRepo.findById(customerPo).get().getOrderDetails();
    }

    public List<OrderDetail> findOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(orderRepo.findById(customerPo).isPresent())
             orderDetails = orderRepo.findById(customerPo).get().getOrderDetails();
        return orderDetails;
    }
}
