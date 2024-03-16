package trial.task.delivery_fee_calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class ForbiddenVehicleTypeException extends RuntimeException{
    public ForbiddenVehicleTypeException() {
        super("Usage of selected vehicle type is forbidden");
    }
}
