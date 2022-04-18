package pl.miq3l.bttconnect.domain;

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
    @OneToMany(mappedBy = "orderUnit", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    private List<OrderDetails> orderDetails;

    public static List<String> getFields() {
        return List.of("salesOrder", "customerPo", "supplyingLocation",
                "orderDate", "lastShipment", "status");
    }
}
