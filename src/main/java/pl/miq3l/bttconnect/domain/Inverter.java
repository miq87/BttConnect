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
public class Inverter {
    @Id
    private String part;
    private String power;
    private String current;
    private String dimensions;
    private String powerSupply;
    private String netto;

    public static List<String> getFields() {
        return Arrays.stream(Inverter.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}
