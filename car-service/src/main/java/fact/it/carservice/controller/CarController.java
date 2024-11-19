package fact.it.carservice.controller;

import fact.it.carservice.dto.CarRequest;
import fact.it.carservice.dto.CarResponse;
import fact.it.carservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createCar(@RequestBody CarRequest carRequest) {
        carService.createCar(carRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<CarResponse> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/brand/{brand}")
    @ResponseStatus(HttpStatus.OK)
    public List<CarResponse> findCarsByBrand(@PathVariable String brand) {
        return carService.findCarsByBrand(brand);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarResponse findCarById(@PathVariable String id) {
        return carService.findCarById(id);
    }
}
