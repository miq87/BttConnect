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
        orderRepo.saveAll(ph.getOrderUnits(limit));
        return findAll();
    }

    public List<OrderDetail> loadOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetail> orderDetails = ph.getOrderDetailByCustomerPo(customerPo);
        if(orderRepo.findById(customerPo).isPresent())
            orderRepo.findById(customerPo).get().setOrderDetails(orderDetails);
        System.out.println("Zwrot z loadOrderDetailByCustomerPo");
        return orderRepo.findById(customerPo).get().getOrderDetails();
    }

    public List<OrderDetail> findOrderDetailByCustomerPo(String customerPo) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(orderRepo.findById(customerPo).isPresent())
             orderDetails = orderRepo.findById(customerPo).get().getOrderDetails();
        System.out.println("Zwrot z findOrderDetailByCustomerPo");
        return orderDetails;
    }
}
