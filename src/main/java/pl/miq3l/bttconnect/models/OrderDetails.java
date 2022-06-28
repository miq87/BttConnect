package pl.miq3l.bttconnect.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lineNbr;
    private String part;
    private String orderQty;
    private String unitPrice;
    private String boQty;
    private String allocQty;
    private String shipQty;
    private String promiseDate;
    private String shipDate;
    private String trackingUrl;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderUnitId", referencedColumnName = "customerPo")
    @JsonIgnore
    private OrderUnit orderUnit;

    public static List<String> getFields() {
        return Arrays.stream(OrderDetails.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}
