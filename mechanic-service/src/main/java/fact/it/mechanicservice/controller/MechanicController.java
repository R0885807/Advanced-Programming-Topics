package fact.it.mechanicservice.controller;

import fact.it.mechanicservice.dto.MechanicRequest;
import fact.it.mechanicservice.dto.MechanicResponse;
import fact.it.mechanicservice.service.MechanicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mechanic")
@RequiredArgsConstructor
public class MechanicController {
    private final MechanicService mechanicService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createMechanic
            (@RequestBody MechanicRequest mechanicRequest) {
        mechanicService.createMechanic(mechanicRequest);
    }
    @PutMapping("/changeAvailability/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMechanic(@PathVariable String id) {
        mechanicService.changeAvailability(id);
    }

    @GetMapping("/brandSpeciality/{brandSpeciality}")
    @ResponseStatus(HttpStatus.OK)
    public MechanicResponse getAllMechanicsByBrandSpeciality
            (@PathVariable String brandSpeciality) {
        return mechanicService.findMechanicByBrandSpeciality(brandSpeciality);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<MechanicResponse> getAllMechanics() {
        return mechanicService.getAllMechanics();
    }



}
