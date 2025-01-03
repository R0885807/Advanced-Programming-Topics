package fact.it.carservice.service;

import fact.it.carservice.dto.CarRequest;
import fact.it.carservice.dto.CarResponse;
import fact.it.carservice.model.Brand;
import fact.it.carservice.model.Car;
import fact.it.carservice.repository.CarRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CarService {
    private final CarRepository carRepository;


    public void createCar(CarRequest carRequest) {
        Car car = Car.builder()
                .brand(Brand.valueOf(carRequest.getBrand()))
                .model(carRequest.getModel())
                .year(carRequest.getYear())
                .mileage(carRequest.getMileage())
                .build();
        carRepository.save(car);
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }


    public List<CarResponse> getAllCars() {
        List<Car> cars = carRepository.findAll();
        return cars.stream().map(this::mapToCarResponse).toList();
    }

    public List<CarResponse> findCarsByBrand(String brand) {
        List<Car> cars = carRepository.findByBrand(Brand.valueOf(brand));
        return cars.stream().map(this::mapToCarResponse).toList();
    }

    private CarResponse mapToCarResponse(Car car) {
        return CarResponse.builder()
                .id(car.getId())
                .brand(String.valueOf(car.getBrand()))
                .model(car.getModel())
                .year(car.getYear())
                .mileage(car.getMileage())
                .build();
    }

    public CarResponse findCarById(String id) {
        Car car = carRepository.findById(id).orElseThrow();
        return mapToCarResponse(car);
    }

}
