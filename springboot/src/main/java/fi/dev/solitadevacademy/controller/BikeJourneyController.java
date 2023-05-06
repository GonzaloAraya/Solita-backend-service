package fi.dev.solitadevacademy.controller;

import fi.dev.solitadevacademy.config.model.AuthenticationReq;
import fi.dev.solitadevacademy.config.model.TokenInfo;
import fi.dev.solitadevacademy.entity.BikeJourney;
import fi.dev.solitadevacademy.service.BikeJourneyService;
import fi.dev.solitadevacademy.service.JwtUtilService;
import fi.dev.solitadevacademy.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bikeJourney")
@CrossOrigin(origins = "http://localhost:3000")
public class BikeJourneyController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilService jwtUtilService;

    @Autowired
    UsersDetailsService usersDetailsService;

    @Autowired
    private BikeJourneyService bikeJourneyService;

    //*********CRUD********

    /**
     * create a new database entry, body follow bikeJourney entity structure as Json format
     * @param bikeJourney
     * @return http status response
     */
    @PostMapping("/private")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> create(@RequestBody BikeJourney bikeJourney) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bikeJourneyService.save(bikeJourney));
    }

    /**
     * delete a user from the database
     * @param bikeJourneyId
     * @return http status response
     */
    @DeleteMapping("/private/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer bikeJourneyId) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("--->" + auth.getPrincipal() + "--->" + auth.getAuthorities() + ".----" + auth.isAuthenticated());
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
    @PutMapping("/private/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
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
     * search bikeJourney entry base in the id
     * @param bikeJourneyId
     * @return http status response and the bikeJourneyObject
     */
    @GetMapping("/public/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<?> read(@PathVariable(value = "id") Integer bikeJourneyId) {
        Optional<BikeJourney> oBikeJourney = bikeJourneyService.findById(bikeJourneyId);
        if (!oBikeJourney.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oBikeJourney);
    }

    /**
     * read all the database entries utilizing pageable
     * @param offset
     * @param size
     * @return a list as json object
     */
    @GetMapping("/public/pag/{offset}/{size}")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<BikeJourney> readAll(@PathVariable(value = "offset") Integer offset,@PathVariable(value = "size") Integer size) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        Page<BikeJourney> bikeJourneys = bikeJourneyService.findAll(PageRequest.of(offset, size));
        return bikeJourneys.getContent();
    }

    /**
     * The average distance of a journey starting from the station or ending at the station
     * @return a string with the average distance
     */
    @GetMapping("/public/average/{location}/{place}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String average(@PathVariable(value = "location") String location,@PathVariable(value = "place") String place) {
        return bikeJourneyService.average(location, place);
    }

    /**
     * Top 5 most popular return stations for journeys starting from the station or
     * Top 5 most popular departure stations for journeys ending at the station, depending on the given parameter
     * @param location
     * @return return a string with the 5 top location
     */
    @GetMapping("/public/popular/{location}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String popular(@PathVariable(value = "location") String location) {
        return bikeJourneyService.popular(location).toString();
    }

    /**
     * auth and token generation
     * @param authenticationReq (user and password from client)
     * @return generated token information
     */

    @PostMapping("/public/authenticate")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TokenInfo> authenticate(@RequestBody AuthenticationReq authenticationReq) {
        System.out.println("authenticate user" + authenticationReq.getUser());

        //credentials received from client
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationReq.getUser(),
                        authenticationReq.getPassword()));

        //get extra information from the client
        final UserDetails userDetails = usersDetailsService.loadUserByUsername(
                authenticationReq.getUser());

        final String jwt = jwtUtilService.generateToken(userDetails);

        TokenInfo tokenInfo = new TokenInfo(jwt);

        return ResponseEntity.ok(tokenInfo);
    }


}

