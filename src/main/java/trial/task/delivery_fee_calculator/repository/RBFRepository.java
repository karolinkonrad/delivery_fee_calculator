package trial.task.delivery_fee_calculator.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import trial.task.delivery_fee_calculator.entity.RBF;

@Repository
public interface RBFRepository extends CrudRepository<RBF, Integer> {

}
