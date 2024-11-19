package fact.it.garageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MechanicResponse {
    private String id;
    private String name;
    private String age;
    private String experience;
    private boolean isAvailable;
    private String brandSpeciality;
}
