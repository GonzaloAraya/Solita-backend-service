package fi.dev.solitadevacademy.repository;

import fi.dev.solitadevacademy.entity.BikeJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeJourneyRepository extends JpaRepository<BikeJourney, Integer> {
}
