package fact.it.garageservice;

import fact.it.garageservice.dto.CarResponse;
import fact.it.garageservice.dto.MechanicResponse;
import fact.it.garageservice.dto.RepairRequest;
import fact.it.garageservice.model.Repair;
import fact.it.garageservice.repository.RepairRepository;
import fact.it.garageservice.service.GarageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static java.lang.Long.parseLong;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarageServiceUnitTests {

    @InjectMocks
    private GarageService garageService;

    @Mock
    private RepairRepository repairRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setup(){
        ReflectionTestUtils.setField(garageService, "mechanicServiceBaseUrl", "localhost:8082");
        ReflectionTestUtils.setField(garageService, "carServiceBaseUrl", "localhost:8080");
    }

    @Test
    public void testHandleRepair() {
        // Arrange
        RepairRequest repairRequest = new RepairRequest();
        repairRequest.setCarId(UUID.randomUUID().toString());

        CarResponse carResponse = new CarResponse();
        carResponse.setId(repairRequest.getCarId());
        carResponse.setBrand("Audi");
        carResponse.setModel("A4");
        carResponse.setYear(2015);
        carResponse.setMileage(10000);

        MechanicResponse mechanicResponse = new MechanicResponse();
        mechanicResponse.setId("1");
        mechanicResponse.setName("John Doe");
        mechanicResponse.setAge("30");
        mechanicResponse.setExperience("5 years");
        mechanicResponse.setAvailable(true);
        mechanicResponse.setBrandSpeciality("Audi");

        Repair repair = new Repair();
        repair.setId(1L);
        repair.setCarId(repairRequest.getCarId());
        repair.setMechanicId(1L);
        repair.setStartDate(LocalDateTime.now());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(contains("http://localhost:8080/api/car/"))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CarResponse.class)).thenReturn(Mono.just(carResponse));

        when(requestHeadersUriSpec.uri(contains("http://localhost:8082/api/mechanic/brandSpeciality/Audi")))
                .thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(MechanicResponse.class)).thenReturn(Mono.just(mechanicResponse));

        WebClient.RequestBodyUriSpec mockRequestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestBodySpec mockRequestBodySpec = mock(WebClient.RequestBodySpec.class);
        when(webClient.put()).thenReturn(mockRequestBodyUriSpec);
        when(mockRequestBodyUriSpec.uri(contains("http://localhost:8082/api/mechanic/changeAvailability/")))
                .thenReturn(mockRequestBodySpec);
        when(mockRequestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        when(repairRepository.save(any(Repair.class))).thenReturn(repair);

        // Act
        boolean result = garageService.handleRepair(repairRequest);

        // Assert
        assertTrue(result, "handleRepair should return true on success");
        verify(repairRepository, times(1)).save(any(Repair.class));
        verify(webClient, times(2)).get();
        verify(webClient, times(1)).put();
    }


    @Test
    public void testGetBill() {
        // Arrange
        Long repairId = 1L;
        Repair repair = new Repair();
        repair.setId(repairId);
        repair.setStartDate(LocalDateTime.now().minusHours(4));
        repair.setEndDate(LocalDateTime.now());

        when(repairRepository.findById(repairId)).thenReturn(Optional.of(repair));

        // Act
        double bill = garageService.getBill(repairId);

        // Assert
        assertEquals(100L, bill, "Bill should be calculated as 4 hours * $25/hour");
    }





    @Test
    public void getAvailableMechanicIdTest() {
        // Arrange
        String brandSpeciality = "Audi";

        // Mocked response from mechanicService
        MechanicResponse mechanic = new MechanicResponse();
        mechanic.setId("1");
        mechanic.setName("John Doe");
        mechanic.setAge("30");
        mechanic.setExperience("5 years");
        mechanic.setAvailable(true);
        mechanic.setBrandSpeciality("Audi");

        // Set up the WebClient mock chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec); // Mock webClient.get()
        when(requestHeadersUriSpec.uri(eq("http://localhost:8082/api/mechanic/brandSpeciality/Audi")))
                .thenReturn(requestHeadersSpec); // Use exact match for URI
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec); // Mock retrieve()
        when(responseSpec.bodyToMono(MechanicResponse.class)).thenReturn(Mono.just(mechanic)); // Mock bodyToMono()

        // Act
        Long mechanicId = garageService.getAvailableMechanicId(brandSpeciality);

        // Assert
        assertNotNull(mechanicId); // Ensure the mechanicId is not null
        assertEquals(Long.valueOf(mechanic.getId()), mechanicId); // Compare the IDs
    }


}