package com.project.cfgames.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FreteResponse {
    private List<ShippingOption> options;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class ShippingOption {
    private int id;
    private String name;
    private String price;
    private String custom_price;
    private String discount;
    private String currency;
    @JsonProperty("delivery_time")
    private int deliveryTime;
    @JsonProperty("delivery_range")
    private DeliveryRange deliveryRange;
    @JsonProperty("custom_delivery_time")
    private int customDeliveryTime;
    @JsonProperty("custom_delivery_range")
    private DeliveryRange customDeliveryRange;
    private List<PackageInfo> packages;
    @JsonProperty("additional_services")
    private AdditionalServices additionalServices;
    private Company company;
    private String error;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class DeliveryRange {
    private int min;
    private int max;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PackageInfo {
    private String price;
    private String discount;
    private String format;
    private String weight;
    @JsonProperty("insurance_value")
    private String insuranceValue;
    private Dimensions dimensions;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Dimensions {
    private int height;
    private int width;
    private int length;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AdditionalServices {
    private boolean receipt;
    @JsonProperty("own_hand")
    private boolean ownHand;
    private boolean collect;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Company {
    private int id;
    private String name;
    private String picture;
}
