package trial.task.delivery_fee_calculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;
import trial.task.delivery_fee_calculator.entity.Weather;
import trial.task.delivery_fee_calculator.exception.ForbiddenVehicleTypeException;
import trial.task.delivery_fee_calculator.service.CalculatorService;
import trial.task.delivery_fee_calculator.service.WeatherService;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CalculatorServiceTests {
    @InjectMocks
    CalculatorService calculatorService                             ;

    @Mock
    WeatherService weatherService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRBF() {
        Weather weather = new Weather();
        weather.setAirtemperature(-11);
        weather.setWindspeed(11);

        when(weatherService.getLatestWeatherByName("tallinn")).thenReturn(weather);

        AssertionErrors.assertEquals("Tallinn, car, ATEF=-11, WSEF=11",
                4f, calculatorService.calculate("tallinn", "car"));
    }

    @Test
    void testTemperatureBelowZeroRain() {
        Weather weather = new Weather();
        weather.setAirtemperature(-1);
        weather.setWindspeed(11);
        weather.setPhenomenon("rain");

        when(weatherService.getLatestWeatherByName("tartu")).thenReturn(weather);

        AssertionErrors.assertEquals("Tartu, scooter, ATEF=-1, WSEF=11, WPEF=rain",
                4f, calculatorService.calculate("tartu", "scooter"));

    }

    @Test
    void testTemperatureBelow10HighWindSnow() {
        Weather weather = new Weather();
        weather.setAirtemperature(-11);
        weather.setWindspeed(11);
        weather.setPhenomenon("snow");

        when(weatherService.getLatestWeatherByName("pärnu")).thenReturn(weather);

        AssertionErrors.assertEquals("Pärnu, bike, ATEF=-11, WSEF=11, WPEF=snow",
                4.5f, calculatorService.calculate("pärnu", "bike"));

    }

    @Test
    void testWindSpeedError() {
        Weather weather = new Weather();
        weather.setAirtemperature(-11);
        weather.setWindspeed(21);

        when(weatherService.getLatestWeatherByName("tallinn")).thenReturn(weather);

        Assertions.assertThatExceptionOfType(ForbiddenVehicleTypeException.class)
                .isThrownBy(() -> calculatorService.calculate("tallinn", "bike"));

    }

    @Test
    void testPhenomenonError() {
        Weather weather = new Weather();
        weather.setAirtemperature(-11);
        weather.setWindspeed(10);
        weather.setPhenomenon("glaze");

        when(weatherService.getLatestWeatherByName("tallinn")).thenReturn(weather);

        Assertions.assertThatExceptionOfType(ForbiddenVehicleTypeException.class)
                .isThrownBy(() -> calculatorService.calculate("tallinn", "bike"));

    }
}
