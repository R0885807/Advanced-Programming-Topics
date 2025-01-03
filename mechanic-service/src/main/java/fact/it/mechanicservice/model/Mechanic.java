package fact.it.mechanicservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "mechanic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Mechanic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String age;
    private String experience;
    private boolean isAvailable;
    @Enumerated(EnumType.STRING)
    private BrandSpeciality brandSpeciality;
}


