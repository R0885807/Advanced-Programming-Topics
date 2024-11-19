package fact.it.carservice.model;

import fact.it.carservice.model.Brand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value="car")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Car {
    private String id;
    private Brand brand;
    private String model;
    private Integer year;
    private Integer mileage;
}
