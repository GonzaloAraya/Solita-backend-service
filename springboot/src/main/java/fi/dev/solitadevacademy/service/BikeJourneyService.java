package fi.dev.solitadevacademy.service;

import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.repository.BikeJourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BikeJourneyService  {

    @Autowired
    private BikeJourneyRepository bikeJourneyRepository;

    public String average(String location, String place) {
        List<BikeJourney> bikeJourneys = StreamSupport
                .stream(bikeJourneyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        String avg = "";
        switch (location){
            case "departure":
                avg = departureAvg(bikeJourneys, place);
                break;
            case "return":
                avg = returnAvg(bikeJourneys, place);
                break;
        }
        return avg;
    }

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


    //other functions
    /**
     * @param bikeJourneys as all bikeJourney entries from database
     * @param place as given argument in the get request
     * @return average distance of a journey ending at the station
     */
    private String returnAvg(List<BikeJourney> bikeJourneys, String place) {
        Integer avg = 0;
        for(int i=0; i<bikeJourneys.size();i++){
            if(bikeJourneys.get(i).getReturnStationName().equalsIgnoreCase(place)) avg += bikeJourneys.get(i).getCoveredDistance();
        }
        return avg.toString();
    }

    /**
     * @param bikeJourneys as all bikeJourney entries from database
     * @param place as given argument in the get request
     * @return average distance of a journey starting from the station
     */
    private String departureAvg(List<BikeJourney> bikeJourneys, String place) {
        Integer avg = 0;
        for(int i=0; i<bikeJourneys.size();i++){
            if(bikeJourneys.get(i).getDepartureStationName().equalsIgnoreCase(place)) {
                avg += bikeJourneys.get(i).getCoveredDistance();
            }
        }
        return avg.toString();
    }


}