package pl.miq3l.bttconnect.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.miq3l.bttconnect.models.OrderDetails;
import pl.miq3l.bttconnect.service.OrderDetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
public class OrderDetailsApi {
    private final OrderDetailsService orderDetailsService;

    @Autowired
    public OrderDetailsApi(OrderDetailsService orderDetailsService) {
        this.orderDetailsService = orderDetailsService;
    }

    @GetMapping("/{customerPo}")
    public List<OrderDetails> findById(@PathVariable("customerPo") String customerPo) {
        customerPo = customerPo.replaceAll("-", "/");
        return orderDetailsService.findByCustomerPo(customerPo);
    }

}
