package edu.kea.kab.repository;

import edu.kea.kab.model.Consumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption, Long> {
}
