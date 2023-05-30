# Solita-backend-service
This project is a backend server built with Spring Boot, a Java-based web application framework. It utilizes MariaDB, an open-source relational database management system, for efficient data storage and retrieval. The server serves as the backend for the Solita Dev Academy project, providing necessary APIs for handling bike data.

## Features
- Data Management: The server offers APIs to handle bike data, including CRUD operations (Create, Read, Update, Delete).

- JSON Web Token (JWT) Authentication: The server implements JWT authentication to secure the API endpoints and restrict access to authorized users.

- MariaDB Integration: The server connects to a MariaDB database to store and retrieve bike data efficiently.

- Deployment with Docker: The project has been deployed in a VM cloud environment using Docker. Docker containers ensure easy deployment, management, and portability of the server.

- Linux Environment: The server is deployed on a Linux operating system, providing stability and performance benefits.


## CRUD (Service list)

**POST**

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

**PUT**

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


**DELETE**

to delete an entry provide id which is to be deleted (in this case is 59)
---> bikeJourney/private/59

**GET**

- authenticate
---> bikeJourney/public/average/return/jkl

- get by id
---> bikeJourney/public/60

- get all information with page (this example show 1 as page number and 5 as number of entries for pages)
---> bikeJourney/public/pag/1/5

- The average distance of a journey starting from the station
---> bikeJourney/public/average/departure/Niittymaa

- The average distance of a journey ending at the station
---> bikeJourney/public/average/return/jkl

- Top 5 most popular return stations for journeys starting from the station
---> bikeJourney/public/popular/return

- Top 5 most popular departure stations for journeys ending at the station
---> bikeJourney/public/popular/departure

**NOTE**: All GET are public, Post, Put and Delete need previous authentication

**NOTE 2**: not all services can be use in the current VM due to VM Cloud RAM memory is 1 GB
