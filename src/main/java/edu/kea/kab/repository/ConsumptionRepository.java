package edu.kea.kab.repository;

import edu.kea.kab.model.Consumption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption, Long> {

    @Query(value = "SELECT SUM(mobile_hours + music_hours + video_hours) FROM consumption WHERE user_id = ?",
            nativeQuery = true)
    double sumOfTotalConsumption(Long userId);


    public Consumption findByUserId(Long id);

    public Consumption findBySession(String session);

    public List<Consumption> findAllBySession(String session);

    public Consumption findFirstBySessionOrderById(String session);
}
