package fi.dev.solitadevacademy.repository;

import fi.dev.solitadevacademy.entity.BikeJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeJourneyRepository extends JpaRepository<BikeJourney, Integer> {

    @Query("select count(coveredDistance) from BikeJourney where departureStationName = 'Viiskulma'")
    String returnCount();

    @Query("select avg(coveredDistance) from BikeJourney where departureStationName = 'Viiskulma'")
    String returnAvg();


}
