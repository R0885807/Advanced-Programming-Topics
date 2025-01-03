package fact.it.garageservice.service;

import fact.it.garageservice.dto.CarResponse;
import fact.it.garageservice.dto.MechanicResponse;
import fact.it.garageservice.dto.RepairRequest;
import fact.it.garageservice.dto.RepairResponse;
import fact.it.garageservice.model.Repair;
import fact.it.garageservice.repository.RepairRepository;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional

public class GarageService {

    @Value("${mechanicservice.baseurl}")
    private String mechanicServiceBaseUrl;
    @Value("${carservice.baseurl}")
    private String carServiceBaseUrl;
    private final WebClient webClient;
    private final RepairRepository repairRepository;
    public boolean handleRepair(RepairRequest repairRequest) {
        try {Repair repair = new Repair();
            repair.setMechanicId(getAvailableMechanicId(getBrand(repairRequest.getCarId())));
            repair.setCarId(repairRequest.getCarId());
            repair.setStartDate(LocalDateTime.now());
            changeAvailableMechanic(repair.getMechanicId());
            repairRepository.save(repair);
            return true;
        }

        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean repairHandled(Long id) {
        try{Repair repair = repairRepository.findById(id).get();
            repair.setEndDate(LocalDateTime.now());
            changeAvailableMechanic(repair.getMechanicId());
            repairRepository.save(repair);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public double getBill(Long id) {
        Repair repair = repairRepository.findById(id).get();
            double pricePerHour = 25;
            double workedHours = java.time.Duration.between(repair.getStartDate(), repair.getEndDate()).toMinutes() / 60.0;
            return workedHours * pricePerHour;
    }

    public boolean payBill(Long id) {
        try{Repair repair = repairRepository.findById(id).get();
            repair.setPaid(true);
            deleteCar(repair.getCarId());
            repairRepository.save(repair);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RepairResponse> getAllRepairs()
    {List<Repair> repairs = repairRepository.findAll();
        return repairs.stream().map(this::mapToRepairResponse).toList();}

    public Long getAvailableMechanicId(String brandSpeciality) {
        MechanicResponse mechanicResponse = webClient.get()
                .uri("http://" + mechanicServiceBaseUrl + "/api/mechanic/brandSpeciality/" + brandSpeciality)
                .retrieve().bodyToMono(MechanicResponse.class).block();
        return Long.parseLong(mechanicResponse.getId());
    }
    private String getBrand(String carId) {
        CarResponse carResponse = webClient.get()
                .uri("http://" + carServiceBaseUrl + "/api/car/" + carId)
                .retrieve().bodyToMono(CarResponse.class).block();
        return carResponse.getBrand();
    }

    public void changeAvailableMechanic(Long mechanicId) {
        webClient.put()
                .uri("http://" + mechanicServiceBaseUrl + "/api/mechanic/changeAvailability/" + mechanicId)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private void deleteCar(String id) {
        webClient.delete()
                .uri("http://" + carServiceBaseUrl + "/api/car/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private RepairResponse mapToRepairResponse(Repair repair) {
        return RepairResponse.builder()
                .id(repair.getId())
                .carId(repair.getCarId())
                .mechanicId(repair.getMechanicId())
                .isPaid(repair.isPaid())
                .startDate(repair.getStartDate())
                .endDate(repair.getEndDate())
                .build();
    }
}
