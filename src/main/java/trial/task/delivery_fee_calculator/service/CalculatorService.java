package trial.task.delivery_fee_calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trial.task.delivery_fee_calculator.entity.Weather;

@Service
public class CalculatorService {
    @Autowired
    private WeatherService weatherService;

    public float calculator(String city, String vehicle) {
        Weather weather = weatherService.getLatestWeatherByName(city);

        return RBF(city, vehicle) + ATEF(weather.getAirtemperature(), vehicle)
                + WSEF(weather.getWindspeed(), vehicle) + WPEF(weather.getPhenomenon(), vehicle);
    }

    private float RBF(String city, String vehicle) {
        if (city.equals("tallinn")) {
            switch (vehicle) {
                case "car": return 4f;
                case "scooter": return 3.5f;
                case "bike": return 3f;
            }
        }
        else if (city.equals("tartu")) {
            switch (vehicle) {
                case "car": return 3.5f;
                case "scooter": return 2f;
                case "bike": return 2.5f;
            }
        }
        else if (city.equals("pärnu")) {
            switch (vehicle) {
                case "car": return 3f;
                case "scooter": return 2.5f;
                case "bike": return 2f;
            }
        }
        throw new RuntimeException("Invalid argument");
    }

    private float ATEF(float airTemp, String vehicle) {
        if (vehicle.equals("scooter") || vehicle.equals("bike")){
            if (airTemp < -10) return 1;
            if (airTemp <= 0) return 0.5f;
        }
        return 0;
    }

    private float WSEF(float wind, String vehicle) {
        if (vehicle.equals("bike")){
            if (10 <= wind && wind <= 20) return 0.5f;
            if (20 < wind) throw  new Error("Usage of selected vehicle type is forbidden");
        }
        return 0;
    }

    private float WPEF(String phenomenon, String vehicle) {
        if (vehicle.equals("scooter") || vehicle.equals("bike")){
            if (phenomenon.contains("snow") || phenomenon.contains("sleet")) return 1;
            if (phenomenon.contains("rain")) return 0.5f;
            if (phenomenon.contains("glaze") || phenomenon.contains("hail") || phenomenon.contains("thunder"))
                throw  new Error("Usage of selected vehicle type is forbidden");
        }
        return 0;
    }

}
