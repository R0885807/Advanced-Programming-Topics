package fact.it.mechanicservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicResponse {

    private Long id;
    private String name;
    private String age;
    private String experience;
    private boolean isAvailable;
    private String brandSpeciality;
}
