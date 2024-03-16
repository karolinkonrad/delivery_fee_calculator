package trial.task.delivery_fee_calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AssertionErrors;
import trial.task.delivery_fee_calculator.entity.Weather;
import trial.task.delivery_fee_calculator.repository.WeatherRepository;
import trial.task.delivery_fee_calculator.service.WeatherService;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WeatherServiceTests {
    @Autowired
    WeatherService weatherService;
    @Autowired
    WeatherRepository weatherRepository;

    @Test
    void testWeatherRequest() throws XMLStreamException, IOException {
        List<Weather> list = (List<Weather>) weatherRepository.findAll();
        AssertionErrors.assertEquals("Database size on startup", 0, list.size());

        weatherService.weatherRequest();

        list = (List<Weather>) weatherRepository.findAll();
        AssertionErrors.assertEquals("Database size after weatherRequest", 3, list.size());

    }
}
