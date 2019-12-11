package edu.kea.kab.repository;

import edu.kea.kab.model.Consumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption, Long> {

    public Consumption findByUserId(Long id);

    public Consumption findBySession(String session);

    public List<Consumption> findAllBySession(String session);
}
