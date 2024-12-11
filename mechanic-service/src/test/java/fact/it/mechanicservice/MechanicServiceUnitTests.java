package fact.it.mechanicservice;

import fact.it.mechanicservice.dto.MechanicRequest;
import fact.it.mechanicservice.dto.MechanicResponse;
import fact.it.mechanicservice.model.BrandSpeciality;
import fact.it.mechanicservice.model.Mechanic;
import fact.it.mechanicservice.repository.MechanicRepository;
import fact.it.mechanicservice.service.MechanicService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MechanicServiceUnitTests {

	@InjectMocks
	private MechanicService mechanicService;

	@Mock
	private MechanicRepository mechanicRepository;

	@Test
	public void testCreateMechanic() {
		MechanicRequest mechanicRequest = new MechanicRequest();
		mechanicRequest.setName("John Doe");
		mechanicRequest.setAge("30");
		mechanicRequest.setExperience("5 years");
		mechanicRequest.setBrandSpeciality("Porsche");

		mechanicService.createMechanic(mechanicRequest);

		verify(mechanicRepository, times(1)).save(any(Mechanic.class));
	}

	@Test
	public void testGetAllMechanics() {
		//Arrange

		Mechanic mechanic = new Mechanic();
		mechanic.setName("John Doe");
		mechanic.setAge("30");
		mechanic.setExperience("5 years");
		mechanic.setBrandSpeciality(BrandSpeciality.Porsche);
		mechanic.setAvailable(true);

		when(mechanicRepository.findAll()).thenReturn(Arrays.asList(mechanic));
		//Act
		List<MechanicResponse> mechanics = mechanicService.getAllMechanics();

		//Assert
		assertEquals(1, mechanics.size());
		assertEquals("John Doe", mechanics.get(0).getName());
		assertEquals("30", mechanics.get(0).getAge());
		assertEquals("5 years", mechanics.get(0).getExperience());
		assertEquals("Porsche", mechanics.get(0).getBrandSpeciality());
		assertEquals(true, mechanics.get(0).isAvailable());

	}

	@Test
	public void testChangeAvailability() {
		//Arrange
		Mechanic mechanic = new Mechanic();
		mechanic.setId(1L);
		mechanic.setName("John Doe");
		mechanic.setAge("30");
		mechanic.setExperience("5 years");
		mechanic.setBrandSpeciality(BrandSpeciality.Porsche);
		mechanic.setAvailable(true);
		mechanicRepository.save(mechanic);
		when(mechanicRepository.findById("1")).thenReturn(Optional.of(mechanic));
		when(mechanicRepository.findAll()).thenReturn(Arrays.asList(mechanic));

		List<MechanicResponse> mechanics = mechanicService.getAllMechanics();
		//Act
		mechanicService.changeAvailability(mechanics.get(0).getId().toString());


		MechanicResponse mechanicResponse = mechanicService.getAllMechanics().get(0);
		assertEquals("John Doe", mechanicResponse.getName());
		assertEquals("30", mechanicResponse.getAge());
		assertEquals("5 years", mechanicResponse.getExperience());
		assertEquals("Porsche", mechanicResponse.getBrandSpeciality());
		assertEquals(false, mechanicResponse.isAvailable());
	}

	@Test
	public void testFindMechanicByBrandSpeciality() {
		//Arrange
		Mechanic mechanic = new Mechanic();
		mechanic.setId(1L);
		mechanic.setName("John Doe");
		mechanic.setAge("30");
		mechanic.setExperience("5 years");
		mechanic.setBrandSpeciality(BrandSpeciality.Porsche);
		mechanic.setAvailable(true);

		when(mechanicRepository.findMechanicByBrandSpeciality(BrandSpeciality.Porsche)).thenReturn(Arrays.asList(mechanic));

		//Act
		MechanicResponse mechanicResponse = mechanicService.findMechanicByBrandSpeciality("Porsche");

		//Assert
		assertEquals("John Doe", mechanicResponse.getName());
		assertEquals("30", mechanicResponse.getAge());
		assertEquals("5 years", mechanicResponse.getExperience());
		assertEquals("Porsche", mechanicResponse.getBrandSpeciality());
		assertEquals(true, mechanicResponse.isAvailable());
	}
}