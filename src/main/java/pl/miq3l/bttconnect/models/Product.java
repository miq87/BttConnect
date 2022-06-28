package pl.miq3l.bttconnect.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @Column(nullable = false)
    private String manufacturerPart;
    private String leadTime;
    private String discount;
    private String tariffCode;
    private String locationOfStock;
    private String stockingPolicy;
    private String weightUom;
    private String itemPriceGroup;
    private String stockingUom;
    private String supplier;
    private String countryOfOrigin;
    private Double available;
    private Double netPrice;
    private Double weight;
    private String unitPrice;
    private Integer boxQty;
    private String minOrderQuantity;
    private String maxOrderQuantity;
    private Timestamp dateAdded;
}
