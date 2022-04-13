package pl.miq3l.bttconnect.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public static List<String> getFields() {
        return List.of("salesOrder", "customerPo", "supplyingLocation", "orderDate", "lastShipment", "status");
    }
}
