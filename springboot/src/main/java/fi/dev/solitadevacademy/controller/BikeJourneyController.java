package fi.dev.solitadevacademy.controller;

import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.service.BikeJourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    /**
     * create a new database entry, body follow bikeJourney entity structure as Json format
     * @param bikeJourney
     * @return http status response
     */
    @PostMapping
    public ResponseEntity<?> create(@RequestBody BikeJourney bikeJourney) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeJourneyService.save(bikeJourney));
    }

    /**
     * search bikeJourney entry base in the id
     * @param bikeJourneyId
     * @return http status response and the bikeJourneyObject
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> read(@PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oBikeJourney);
    }

    /**
     * delete a user from the database
     * @param bikeJourneyId
     * @return http status response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bikeJourneyService.deleteById(bikeJourneyId);
        return ResponseEntity.ok().build();
    }

    /**
     * update an existing database entry, details follow bikeJourney entity structure as Json format
     * @param bikeJourneyDetails
     * @param bikeJourneyId
     * @return http status response and the bikeJourneyObject
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody BikeJourney bikeJourneyDetails, @PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        oBikeJourney.get().setId(bikeJourneyDetails.getId());
        oBikeJourney.get().setDeparture(bikeJourneyDetails.getDeparture());
        oBikeJourney.get().set_return(bikeJourneyDetails.get_return());
        oBikeJourney.get().setDepartureStationId(bikeJourneyDetails.getDepartureStationId());
        oBikeJourney.get().setDepartureStationName(bikeJourneyDetails.getDepartureStationName());
        oBikeJourney.get().setReturnStationId(bikeJourneyDetails.getReturnStationId());
        oBikeJourney.get().setReturnStationName(bikeJourneyDetails.getReturnStationName());
        oBikeJourney.get().setCoveredDistance(bikeJourneyDetails.getCoveredDistance());
        oBikeJourney.get().setDuration(bikeJourneyDetails.getDuration());
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeJourneyService.save(oBikeJourney.get()));
    }

    /**
     * read all the database entries
     * @return a list as json object
     */
    @GetMapping
    public List<BikeJourney> readAll() {
        List<BikeJourney> bikeJourneys = StreamSupport
                .stream(bikeJourneyService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        return bikeJourneys;
    }

    /**
     * read all the database entries utilizing pageable
     * @param offset
     * @param size
     * @return a list as json object
     */
    @GetMapping("/pag/{offset}/{size}")
    public List<BikeJourney> readAll(@PathVariable(value = "offset") Integer offset,@PathVariable(value = "size") Integer size) {
        Page<BikeJourney> bikeJourneys = bikeJourneyService.findAll(PageRequest.of(offset, size));
        return bikeJourneys.getContent();
    }
}

