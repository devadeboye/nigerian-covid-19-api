package devadeboye.Nigeria.Covid19RestAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StateFigureRepository extends JpaRepository<StateFigure, Integer> {
    @Query(value = "SELECT state,confirmed,onAdmission,discharged,death FROM StateFigure WHERE state = ?1")
    StateFigure findByState(String state);
}
