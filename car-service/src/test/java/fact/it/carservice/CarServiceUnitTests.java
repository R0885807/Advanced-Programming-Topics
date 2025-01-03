package fact.it.carservice;

import fact.it.carservice.dto.CarRequest;
import fact.it.carservice.dto.CarResponse;
import fact.it.carservice.model.Brand;
import fact.it.carservice.model.Car;
import fact.it.carservice.repository.CarRepository;
import fact.it.carservice.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
		carRequest.setBrand("Audi");
		carRequest.setModel("A4");
		carRequest.setYear(2022);
		carRequest.setMileage(10000);

		//Act
		carService.createCar(carRequest);

		//Assert
		verify(carRepository, times(1)).save(any(Car.class));
	}

	@Test
	public void testGetAllCars() {
		//Arrange
		Car car = new Car();
		car.setBrand(Brand.Audi);
		car.setModel("A4");
		car.setYear(2022);
		car.setMileage(10000);

		when(carRepository.findAll()).thenReturn(Arrays.asList(car));
		//Act
		List<CarResponse> cars = carService.getAllCars();
		//Assert
		assertEquals(1, cars.size());
		assertEquals("Audi", cars.get(0).getBrand());
		assertEquals("A4", cars.get(0).getModel());
		assertEquals(2022, cars.get(0).getYear());
		assertEquals(10000, cars.get(0).getMileage());

		verify(carRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteCar() {
		//Arrange
		Car car = new Car();
		car.setBrand(Brand.Audi);
		car.setModel("A4");
		car.setYear(2022);
		car.setMileage(10000);

		when(carRepository.findAll()).thenReturn(Arrays.asList(car));


		List<CarResponse> cars = carService.getAllCars();
		CarResponse carResponse = cars.get(0);
		//Act
		carService.deleteCar(car.getId());
		//Assert
		verify(carRepository, times(1)).deleteById(car.getId());

	}

	@Test
	public void testFindCarsByBrand() {
		//Arrange
		Car car = new Car();
		car.setBrand(Brand.Audi);
		car.setModel("A4");
		car.setYear(2022);
		car.setMileage(10000);
		when(carRepository.findByBrand(Brand.Audi)).thenReturn(Arrays.asList(car));

		//Act
		List<CarResponse> cars = carService.findCarsByBrand("Audi");
		//Assert
		assertEquals(1, cars.size());
		assertEquals("Audi", cars.get(0).getBrand());
		assertEquals("A4", cars.get(0).getModel());
		assertEquals(2022, cars.get(0).getYear());
		assertEquals(10000, cars.get(0).getMileage());

	}

	@Test
	public void testFindCarById() {
		//Arrange
		Car car = new Car();
		car.setBrand(Brand.Audi);
		car.setModel("A4");
		car.setYear(2022);
		car.setMileage(10000);
		when(carRepository.findById(car.getId())).thenReturn(java.util.Optional.of(car));

		//Act
		CarResponse carResponse = carService.findCarById(car.getId());
		//Assert
		assertEquals("Audi", carResponse.getBrand());
		assertEquals("A4", carResponse.getModel());
		assertEquals(2022, carResponse.getYear());
		assertEquals(10000, carResponse.getMileage());
	}
}