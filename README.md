# Solita-backend-service

services examples:

## CRUD 

<**POST**>

add new entry ---> bikeJourney/private

format example

    {
    "id": 58,
    "departure": "22",
    "_return": "20:24",
    "departureStationId": 46,
    "departureStationName": "jkl",
    "returnStationId": 77,
    "returnStationName": "jkl",
    "coveredDistance": 1670,
    "duration": 477
    }

<**PUT**>

modify and entry ---> bikeJourney/private/4001

format example

    {
    "id": 4001,    
    "departure": "22",    
    "_return": "20:24",    
    "departureStationId": 46,    
    "departureStationName": "jkasdasdl",    
    "returnStationId": 77,    
    "returnStationName": "jkl",    
    "coveredDistance": 1670,    
    "duration": 477    
    }


<**DELETE**>

to delete an entry provide id which is to be deleted (in this case is 59)
---> bikeJourney/private/59

<**GET**>

authenticate
---> bikeJourney/public/average/return/jkl

get by id
---> bikeJourney/public/60

get all information with page (this example show 1 as page number and 5 as number of entries for pages)
---> bikeJourney/public/pag/1/5

The average distance of a journey starting from the station
---> bikeJourney/public/average/departure/Niittymaa

The average distance of a journey ending at the station
---> bikeJourney/public/average/return/jkl

Top 5 most popular return stations for journeys starting from the station
---> bikeJourney/public/popular/return

Top 5 most popular departure stations for journeys ending at the station
---> bikeJourney/public/popular/departure

NOTE: All GET are public, Post, Put and Delete need previous authentication
