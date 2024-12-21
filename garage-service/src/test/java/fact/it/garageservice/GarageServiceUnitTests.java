package fact.it.garageservice;

import fact.it.garageservice.dto.CarResponse;
import fact.it.garageservice.dto.MechanicResponse;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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
        ReflectionTestUtils.setField(garageService, "mechanicServiceBaseUrl", "http://localhost:8082");
        ReflectionTestUtils.setField(garageService, "carServiceBaseUrl", "http://localhost:8080");
    }

    @Test
    public void testHandleRepair(){
        //Arrange

        CarResponse car = new CarResponse();
        car.setId(UUID.randomUUID().toString());
        car.setBrand("Audi");
        car.setModel("A4");
        car.setYear(2015);
        car.setMileage(10000);

        MechanicResponse mechanic = new MechanicResponse();
        mechanic.setId("1");
        mechanic.setName("John Doe");
        mechanic.setAge("30");
        mechanic.setExperience("5 years");
        mechanic.setAvailable(true);
        mechanic.setBrandSpeciality("Audi");
    }

}
