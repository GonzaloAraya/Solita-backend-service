package fi.dev.solitadevacademy.controller;

import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.service.BikeJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/bikeJourney")
public class BikeJourneyController {

    @Autowired
    private BikeJourneyService bikeJourneyService;

    //*********CRUD********

    //create a new user
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BikeJourney bikeJourney) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeJourneyService.save(bikeJourney));
    }

    //read a user
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oBikeJourney);
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        bikeJourneyService.deleteById(bikeJourneyId);
        return ResponseEntity.ok().build();
    }

    //read all
    @GetMapping
    public List<BikeJourney> readAll() {
        List<BikeJourney> bikeJourneys = StreamSupport
                .stream(bikeJourneyService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return bikeJourneys;
    }
}

