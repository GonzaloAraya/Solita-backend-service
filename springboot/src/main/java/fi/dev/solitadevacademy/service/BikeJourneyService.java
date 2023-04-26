package fi.dev.solitadevacademy.service;

import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.repository.BikeJourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class BikeJourneyService  {

    @Autowired
    private BikeJourneyRepository bikeJourneyRepository;

    @Transactional(readOnly = true)
    public Page<BikeJourney> findAll(Pageable pageable) {
        return bikeJourneyRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<BikeJourney> findById(Integer id) {
        return bikeJourneyRepository.findById(id);
    }

    @Transactional
    public BikeJourney save(BikeJourney customer) {
        return bikeJourneyRepository.save(customer);
    }

    @Transactional
    public void deleteById(Integer Id) {
        bikeJourneyRepository.deleteById(Id);
    }
}