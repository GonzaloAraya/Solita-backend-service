package fi.dev.solitadevacademy.service;

import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.repository.BikeJourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BikeJourneyService {

    @Autowired
    private BikeJourneyRepository bikeJourneyRepository;

    @Transactional
    public String average(String location, String place) {
        List<BikeJourney> bikeJourneys = StreamSupport
                .stream(bikeJourneyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        switch (location) {
            case "departure":
                return departureAvg(bikeJourneys, place);
            case "return":
                return returnAvg(bikeJourneys, place);
            default:
                return "option not available";
        }
    }

    @Transactional
    public List<String> popular(String location) {
        List<BikeJourney> bikeJourneys = StreamSupport
                .stream(bikeJourneyRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        switch (location) {
            case "departure":
                return  departurePopular(bikeJourneys);
            case "return":
                return returnPopular(bikeJourneys);
            default:
                return null;
        }
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


    //*********other functions*********

    /**
     * check concurrences and add distance if they match, after is calculated the average
     * @param bikeJourneys as all bikeJourney entries from database
     * @param place        as given argument in the get request
     * @return average distance of a journey ending at the station
     */
    private String returnAvg(List<BikeJourney> bikeJourneys, String place) {
        Integer totalDistance = 0;
        Integer counter = 0;
        Integer avg;
        for (int i = 0; i < bikeJourneys.size(); i++) {
            if (bikeJourneys.get(i).getReturnStationName().equalsIgnoreCase(place)) {
                totalDistance += bikeJourneys.get(i).getCoveredDistance();
                counter++;
            }
        }
        if(counter == 0) return "place not found";
        avg = totalDistance / counter;
        return avg.toString();
    }

    /**
     * check concurrences and add distance if they match, after is calculated the average
     * @param bikeJourneys as all bikeJourney entries from database
     * @param place        as given argument in the get request
     * @return average distance of a journey starting from the station
     */
    private String departureAvg(List<BikeJourney> bikeJourneys, String place) {
        Integer totalDistance = 0;
        Integer counter = 0;
        Integer avg;
        for (int i = 0; i < bikeJourneys.size(); i++) {
            if (bikeJourneys.get(i).getDepartureStationName().equalsIgnoreCase(place)) {
                totalDistance += bikeJourneys.get(i).getCoveredDistance();
                counter++;
            }
        }
        if(counter == 0) return "place not found";
        avg = totalDistance / counter;
        return avg.toString();
    }

    /**
     * Count the occurrences of each element
     * @param bikeJourneys
     * @return 5 most popular return stations for journeys starting from the station
     */
    private List<String> returnPopular(List<BikeJourney> bikeJourneys) {
        Map<String, Integer> countMap = new HashMap<>();
        for (BikeJourney b : bikeJourneys) {
            countMap.put(b.getReturnStationName(), countMap.getOrDefault(b.getReturnStationName(), 0) + 1);
        }
        return getTopFive(countMap);
    }

    /**
     * Count the occurrences of each element
     * @param bikeJourneys
     * @return String list of 5 most popular departure stations for journeys ending at the station
     */
    private List<String> departurePopular(List<BikeJourney> bikeJourneys) {
        // Count the occurrences of each element
        Map<String, Integer> countMap = new HashMap<>();
        for (BikeJourney b : bikeJourneys) {
            countMap.put(b.getDepartureStationName(), countMap.getOrDefault(b.getDepartureStationName(), 0) + 1);
        }
        return getTopFive(countMap);
    }

    /**
     * Sort the map entries by value in descending order and get the top five elements
     * @param countMap
     * @return top 5 values from the given map
     */
    private List<String> getTopFive(Map<String, Integer> countMap) {
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(countMap.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        List<String> topElements = new ArrayList<>();
        for (int i = 0; i < 5 && i < sortedEntries.size(); i++) {
            topElements.add(sortedEntries.get(i).getKey());
        }
        return topElements;
    }

}