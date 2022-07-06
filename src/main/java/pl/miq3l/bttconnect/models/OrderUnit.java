package pl.miq3l.bttconnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUnit {
    @Id
    private String customerPo;
    private String salesOrder;
    private String orderUrl;
    private String supplyingLocation;
    private String orderDate;
    private String lastShipment;
    private String status;
    @OneToMany(mappedBy = "orderUnit", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonIgnore
    private List<OrderDetails> orderDetails;

    public static List<String> getFields() {
        return List.of("salesOrder", "customerPo", "supplyingLocation",
                "orderDate", "lastShipment", "status");
    }

    public void addOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails.clear();
        this.orderDetails.addAll(orderDetails);
        orderDetails.forEach(od -> {
            od.setOrderUnit(this);
        });
    }

    public void removeOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails.clear();
        orderDetails.forEach(od -> {
            od.setOrderUnit(null);
        });
    }

}
