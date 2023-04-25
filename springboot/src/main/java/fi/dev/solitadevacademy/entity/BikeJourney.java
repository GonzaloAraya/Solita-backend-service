package fi.dev.solitadevacademy.entity;

import javax.persistence.*;

@Entity
@Table(name = "bikeJourney")
public class BikeJourney {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Departure", length = 50)
    private String departure;

    @Column(name = "`Return`", length = 50)
    private String _return;

    @Column(name = "Departure_station_id")
    private Integer departureStationId;

    @Column(name = "Departure_station_name", length = 50)
    private String departureStationName;

    @Column(name = "Return_station_id")
    private Integer returnStationId;

    @Column(name = "Return_station_name", length = 50)
    private String returnStationName;

    @Column(name = "Covered_distance")
    private Integer coveredDistance;

    @Column(name = "Duration")
    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getCoveredDistance() {
        return coveredDistance;
    }

    public void setCoveredDistance(Integer coveredDistance) {
        this.coveredDistance = coveredDistance;
    }

    public String getReturnStationName() {
        return returnStationName;
    }

    public void setReturnStationName(String returnStationName) {
        this.returnStationName = returnStationName;
    }

    public Integer getReturnStationId() {
        return returnStationId;
    }

    public void setReturnStationId(Integer returnStationId) {
        this.returnStationId = returnStationId;
    }

    public String getDepartureStationName() {
        return departureStationName;
    }

    public void setDepartureStationName(String departureStationName) {
        this.departureStationName = departureStationName;
    }

    public Integer getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(Integer departureStationId) {
        this.departureStationId = departureStationId;
    }

    public String get_return() {
        return _return;
    }

    public void set_return(String _return) {
        this._return = _return;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}