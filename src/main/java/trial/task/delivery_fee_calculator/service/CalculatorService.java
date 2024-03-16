package trial.task.delivery_fee_calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trial.task.delivery_fee_calculator.entity.Weather;
import trial.task.delivery_fee_calculator.exception.ForbiddenVehicleTypeException;

import java.util.ArrayList;
import java.util.List;


@Service
public class CalculatorService {
    @Autowired
    private WeatherService weatherService;

    private final ArrayList<String> CITIES = new ArrayList<>(List.of(new String[]{"tallinn", "tartu", "pärnu"}));
    private final ArrayList<String> VEHICLES = new ArrayList<>(List.of(new String[]{"car", "scooter", "bike"}));
    private final float[][] RBF = {{4f, 3.5f, 3f},
                                  {3.5f, 3f, 2.5f},
                                  {3f, 2.5f, 2f}};

    /**
     * Calculates the delivery fee according to parameters.
     * @param city tallinn || tartu || pärnu
     * @param vehicle car || scooter || bike
     * @return delivery fee
     */
    public float calculate(String city, String vehicle) {
        city = city.toLowerCase();
        vehicle = vehicle.toLowerCase();

        if (!CITIES.contains(city)) throw new IllegalArgumentException(city);
        if (!VEHICLES.contains(vehicle)) throw new IllegalArgumentException(vehicle);

        Weather weather = weatherService.getLatestWeatherByName(city);

        return RBF(city, vehicle) + ATEF(weather.getAirtemperature(), vehicle)
                + WSEF(weather.getWindspeed(), vehicle) + WPEF(weather.getPhenomenon(), vehicle);
    }

    private float RBF(String city, String vehicle) {
        return RBF[CITIES.indexOf(city)][VEHICLES.indexOf(vehicle)];
    }

    private float ATEF(float airTemp, String vehicle) {
        if (vehicle.equals("scooter") || vehicle.equals("bike")){
            if (airTemp < -10) return 1;
            if (airTemp < 0) return 0.5f;
        }
        return 0;
    }

    private float WSEF(float wind, String vehicle) {
        if (vehicle.equals("bike")){
            if (20 < wind) throw new ForbiddenVehicleTypeException();
            if (10 < wind) return 0.5f;
        }
        return 0;
    }

    private float WPEF(String phenomenon, String vehicle) {
        if (phenomenon != null && (vehicle.equals("scooter") || vehicle.equals("bike"))){
            if (phenomenon.contains("snow") || phenomenon.contains("sleet")) return 1;
            if (phenomenon.contains("rain")) return 0.5f;
            if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder"))
                throw new ForbiddenVehicleTypeException();
        }
        return 0;
    }

}
