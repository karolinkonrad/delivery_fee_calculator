package trial.task.delivery_fee_calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import trial.task.delivery_fee_calculator.controller.CalculatorController;
import trial.task.delivery_fee_calculator.service.CalculatorService;
import trial.task.delivery_fee_calculator.service.WeatherService;

@SpringBootTest
class DeliveryFeeCalculatorApplicationTests {

    @Autowired
    CalculatorController calculatorController;
    @Autowired
    CalculatorService calculatorService;
    @Autowired
    WeatherService weatherService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(calculatorController);
        Assertions.assertNotNull(calculatorService);
        Assertions.assertNotNull(weatherService);

    }

}
