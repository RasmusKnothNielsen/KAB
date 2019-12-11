package edu.kea.kab.repository;

import edu.kea.kab.model.Consumption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption, Long> {

    Consumption findByUserId(Long id);

    Consumption findBySession(String session);

    @Query(value = "SELECT * FROM Consumption WHERE user_id = ? AND week = ? AND year = ?", nativeQuery = true)
    Consumption findByUserIdAndAndWeek(Long id, int week, int year);

}
