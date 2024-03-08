package trial.task.delivery_fee_calculator.entity;

import jakarta.persistence.*;

@Entity
@Table
public class RBF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column
    private String city;
    @Column
    private String vehicle;
    @Column
    private float rbf;
}
