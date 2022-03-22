package pl.miq3l.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;
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
    private String salesOrder;
    private String orderUrl;
    private String customerPo;
    private String supplyingLocation;
    private String orderDate;
    private String lastShipment;
    private String status;

    public static List<String> getFields() {
        return Arrays.stream(OrderUnit.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}
