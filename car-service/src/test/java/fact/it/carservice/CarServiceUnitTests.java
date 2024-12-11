package fact.it.carservice;

import fact.it.carservice.dto.CarRequest;
import fact.it.carservice.dto.CarResponse;
import fact.it.carservice.model.Car;
import fact.it.carservice.repository.CarRepository;
import fact.it.carservice.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTests {

	@InjectMocks
	private CarService carService;

	@Mock
	private CarRepository carRepository;

	@Test
	public void testCreateCar() {
		// Arrange
		CarRequest carRequest = new CarRequest();
		carRequest.setBrand("BMW");
		carRequest.setModel("X5");
		carRequest.setYear(2022);
		carRequest.setMileage(10000);

		//Act
		carService.createCar(carRequest);

		//Assert
		verify(carRepository, times(1)).save(any(Car.class));
	}

	@Test public void testGetAllCars() {
		//Arrange
		CarRequest carRequest = new CarRequest();
		carRequest.setBrand("BMW");
		carRequest.setModel("X5");
		carRequest.setYear(2022);
		carRequest.setMileage(10000);

		carService.createCar(carRequest);
		//Act
		List<CarResponse> cars = carService.getAllCars();
		//Assert
		assertEquals(1, cars.size());
		assertEquals("BMW", cars.get(0).getBrand());
		assertEquals("X5", cars.get(0).getModel());
		assertEquals(2022, cars.get(0).getYear());
		assertEquals(10000, cars.get(0).getMileage());

		verify(carRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteCar() {
		//Arrange
		CarRequest carRequest = new CarRequest();
		carRequest.setBrand("Audi");
		carRequest.setModel("A4");
		carRequest.setYear(2022);
		carRequest.setMileage(10000);
		carService.createCar(carRequest);
		List<CarResponse> cars = carService.getAllCars();
		CarResponse car = cars.get(0);
		//Act
		carService.deleteCar(car.getId());
		//Assert
		verify(carRepository, times(1)).deleteById(car.getId());

	}
}
