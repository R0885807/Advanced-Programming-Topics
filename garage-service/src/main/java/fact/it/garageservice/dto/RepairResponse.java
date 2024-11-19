package fact.it.garageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepairResponse {
    private Long id;
    private String carId;
    private Long mechanicId;
    private boolean isPaid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
