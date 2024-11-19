package fact.it.garageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private String id;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
}
