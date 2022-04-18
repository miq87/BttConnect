package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.bttconnect.domain.OrderDetails;
import pl.miq3l.bttconnect.domain.OrderUnit;
import pl.miq3l.bttconnect.service.OrderUnitService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderUnitApi {
    private final OrderUnitService orderUnitService;

    @Autowired
    public OrderUnitApi(OrderUnitService orderUnitService) {
        this.orderUnitService = orderUnitService;
    }

    @GetMapping("/all")
    public List<OrderUnit> findAll() {
        return orderUnitService.findAll();
    }

    @GetMapping("/loadAll")
    public List<OrderUnit> loadAll() {
        return orderUnitService.loadAll(10);
    }

    @GetMapping("/load/{customerPo}")
    public List<OrderDetails> loadOrderDetailByCustomerPo(@PathVariable("customerPo") String customerPo) {
        return orderUnitService.loadOrderDetailByCustomerPo(customerPo.replaceAll("-", "/"));
    }

    @GetMapping("/{customerPo}")
    public List<OrderDetails> findOrderDetailByCustomerPo(@PathVariable("customerPo") String customerPo) {
        return orderUnitService.findOrderDetailByCustomerPo(customerPo.replaceAll("-", "/"));
    }

}
