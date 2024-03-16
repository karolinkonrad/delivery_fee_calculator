package trial.task.delivery_fee_calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import trial.task.delivery_fee_calculator.service.CalculatorService;

@RestController
public class CalculatorController {
    @Autowired
    private CalculatorService calculatorService;

    @ResponseBody
    @GetMapping("/deliveryfee/{city}/{vehicle}")
    public float getDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
        return calculatorService.calculate(city, vehicle);
    }


}
