package pl.miq3l.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.domain.OrderDetail;
import pl.miq3l.domain.OrderUnit;
import pl.miq3l.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/parker/orders")
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
        return orderService.loadAll();
    }

    @GetMapping("/load/{customerPo}")
    public List<OrderDetail> getOrderDetailByCustomerPo(@PathVariable("customerPo") String customerPo) {
        return orderService.getOrderDetailByCustomerPo(customerPo.replaceAll("-", "/"));
    }
}
