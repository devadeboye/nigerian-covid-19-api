package devadeboye.Nigeria.Covid19RestAPI;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NationalFigureRepository extends JpaRepository<NationalFigure, Integer> {
    @Query(value = "SELECT tested,confirmed,active,discharged,death FROM NationalFigure WHERE id = 1")
    NationalFigure getNationalStat();
}
