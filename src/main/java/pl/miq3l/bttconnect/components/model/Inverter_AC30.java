package pl.miq3l.bttconnect.components.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inverter_AC30 {
    private String part;
    private String output;
    private String filter;

    public static List<String> getFields() {
        return Arrays.stream(Inverter_AC30.class.getDeclaredFields())
                .map(Field::getName).collect(Collectors.toList());
    }
}
