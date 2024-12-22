package fact.it.garageservice.controller;

import fact.it.garageservice.dto.RepairRequest;
import fact.it.garageservice.dto.RepairResponse;
import fact.it.garageservice.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garage")
@RequiredArgsConstructor
public class RepairController {
    private final GarageService garageService;

    @PostMapping("/repair")
    @ResponseStatus(HttpStatus.OK)
    public String handleRepair(@RequestBody RepairRequest repairRequest) {
        boolean result = garageService.handleRepair(repairRequest);
        return (result ? "Repair started successfully" : "Repair start failed");
    }

    @PutMapping("/repaired/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String repairHandled(@PathVariable Long id) {
        boolean result = garageService.repairHandled(id);
        return (result ? "Repair finished successfully" : "Repair finish failed");
    }

    @GetMapping("/bill/{id}")
    @ResponseStatus(HttpStatus.OK)
    public double getBill(@PathVariable Long id) {
        return garageService.getBill(id);
    }

    @PutMapping("/pay/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String payBill(@PathVariable Long id) {
        boolean result = garageService.payBill(id);
        return (result ? "Bill paid successfully" : "Bill payment failed");
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RepairResponse> getAllRepairs(){
        List<RepairResponse> repairResponseList = garageService.getAllRepairs();
        return repairResponseList;
    }
}
