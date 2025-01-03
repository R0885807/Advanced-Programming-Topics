package fact.it.mechanicservice.service;

import fact.it.mechanicservice.dto.MechanicRequest;
import fact.it.mechanicservice.dto.MechanicResponse;
import fact.it.mechanicservice.model.BrandSpeciality;
import fact.it.mechanicservice.model.Mechanic;
import fact.it.mechanicservice.repository.MechanicRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fact.it.mechanicservice.model.BrandSpeciality.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MechanicService {

    private final MechanicRepository mechanicRepository;

    @PostConstruct
    public void loadData() {
        System.out.println(mechanicRepository.count());
        if(mechanicRepository.count() <= 0) {
            Mechanic mechanic = new Mechanic();
            mechanic.setExperience("5 years");
            mechanic.setName("Esther Beckers");
            mechanic.setAge("25");
            mechanic.setBrandSpeciality(Porsche);
            mechanic.setAvailable(true);
            mechanicRepository.save(mechanic);

            Mechanic mechanic2 = new Mechanic();
            mechanic2.setExperience("10 years");
            mechanic2.setName("Robbie");
            mechanic2.setAge("30");
            mechanic2.setBrandSpeciality(Mercedes);
            mechanic2.setAvailable(true);
            mechanicRepository.save(mechanic2);

            Mechanic mechanic3 = new Mechanic();
            mechanic3.setExperience("15 years");
            mechanic3.setName("Mark");
            mechanic3.setAge("35");
            mechanic3.setBrandSpeciality(Audi);
            mechanic3.setAvailable(true);
            mechanicRepository.save(mechanic3);
        }
    }

    public void createMechanic(MechanicRequest mechanicRequest) {
            Mechanic mechanic = Mechanic.builder()
                    .name(mechanicRequest.getName())
                    .age(mechanicRequest.getAge())
                    .experience(mechanicRequest.getExperience())
                    .isAvailable(true)
                    .brandSpeciality(BrandSpeciality.valueOf(mechanicRequest.getBrandSpeciality()))
                    .build();
            mechanicRepository.save(mechanic);

    }

    public void changeAvailability(String id) {
        if(mechanicRepository.findById(id).isPresent()){
            Mechanic mechanic = mechanicRepository.findById(id).get();
            mechanic.setAvailable(!mechanic.isAvailable());
            mechanicRepository.save(mechanic);
        }
    }


    public List<MechanicResponse> getAllMechanics() {
        List<Mechanic> mechanics = mechanicRepository.findAll();
        return mechanics.stream()
                .map(this::mapToMechanicResponse).toList();
    }

    public MechanicResponse findMechanicByBrandSpeciality(String brandSpeciality) {
        List<Mechanic> mechanics = mechanicRepository.findMechanicByBrandSpeciality(BrandSpeciality.valueOf(brandSpeciality));
        return mechanics.stream()
                .filter(Mechanic::isAvailable)
                .map(this::mapToMechanicResponse)
                .findFirst()
                .orElseThrow();
    }
    private MechanicResponse mapToMechanicResponse(Mechanic mechanic) {
        return MechanicResponse.builder()
                .id(mechanic.getId())
                .name(mechanic.getName())
                .age(mechanic.getAge())
                .experience(mechanic.getExperience())
                .isAvailable(mechanic.isAvailable())
                .brandSpeciality(String.valueOf(mechanic.getBrandSpeciality()))
                .build();
    }

}
