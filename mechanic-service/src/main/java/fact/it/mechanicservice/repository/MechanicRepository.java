package fact.it.mechanicservice.repository;

import fact.it.mechanicservice.model.BrandSpeciality;
import fact.it.mechanicservice.model.Mechanic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MechanicRepository  extends JpaRepository<Mechanic, String> {

    List<Mechanic> findMechanicByBrandSpeciality(BrandSpeciality brandSpeciality);
}
