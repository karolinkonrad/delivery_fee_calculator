DROP TABLE IF EXISTS RBF;
CREATE TABLE RBF (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      city VARCHAR(50) NOT NULL,
                      vehicle VARCHAR(50) NOT NULL,
                      rbf FLOAT NOT NULL
);