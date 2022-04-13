package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.bttconnect.domain.OrderDetail;
import pl.miq3l.bttconnect.domain.OrderUnit;
import pl.miq3l.bttconnect.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderApi {
    private final OrderService orderService;

    @Autowired
    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<OrderUnit> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/loadAll")
    public List<OrderUnit> loadAll() {
        return orderService.loadAll(10);
    }

    @GetMapping("/load/{customerPo}")
    public List<OrderDetail> loadOrderDetailByCustomerPo(@PathVariable("customerPo") String customerPo) {
        return orderService.loadOrderDetailByCustomerPo(customerPo.replaceAll("-", "/"));
    }

    @GetMapping("/{customerPo}")
    public List<OrderDetail> findOrderDetailByCustomerPo(@PathVariable("customerPo") String customerPo) {
        return orderService.findOrderDetailByCustomerPo(customerPo.replaceAll("-", "/"));
    }

}
