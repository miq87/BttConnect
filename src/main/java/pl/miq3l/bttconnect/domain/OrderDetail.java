package pl.miq3l.bttconnect.domain;

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
public class OrderDetail {
    @Id
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

    public static List<String> getFields() {
        return Arrays.stream(OrderDetail.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}