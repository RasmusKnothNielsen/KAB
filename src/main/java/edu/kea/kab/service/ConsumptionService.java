package edu.kea.kab.service;

import edu.kea.kab.model.Consumption;
import edu.kea.kab.model.User;
import edu.kea.kab.repository.ConsumptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumptionService {

    @Autowired
    ConsumptionRepository consumptionRepository;

    public void connectUserWithSession(User user, String session) {
        List<Consumption> consumptionList = consumptionRepository.findAllBySession(session);

        if (consumptionList != null) {
            for (int i = 0; i < consumptionList.size(); i++) {
                consumptionList.get(i).setUser(user);
            }
            consumptionRepository.saveAll(consumptionList);
        }
    }

}
