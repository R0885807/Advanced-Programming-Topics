package fact.it.garageservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Repair")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String carId;
    private Long mechanicId;
    private boolean isPaid;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
