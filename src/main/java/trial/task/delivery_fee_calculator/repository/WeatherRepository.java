package trial.task.delivery_fee_calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trial.task.delivery_fee_calculator.entity.Weather;

import java.util.List;

@Repository
public interface WeatherRepository extends CrudRepository<Weather, Long> {
    List<Weather> findByNameStartingWithOrderByTimestampDesc(String name);

}
