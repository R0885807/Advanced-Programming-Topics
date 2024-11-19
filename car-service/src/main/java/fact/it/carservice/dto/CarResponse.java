package fact.it.carservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarResponse {
    private String id;
    private String brand;
    private String model;
    private Integer year;
    private Integer mileage;
}
