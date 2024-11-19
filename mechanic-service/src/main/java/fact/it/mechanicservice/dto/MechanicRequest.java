package fact.it.mechanicservice.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MechanicRequest {
    private String name;
    private String age;
    private String experience;
    private boolean isAvailable;
    private String brandSpeciality;
}
