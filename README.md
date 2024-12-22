# Advanced-Programming-Topics
Naam: Robbe Verbist</br>
Klas: 3APP02

## Thema
Het is een applicatie voor een autogarage (Porsche, Mercedes en audi). Hiermee kunnen de werknemers auto's registreren, monteurs registreren, en zo een herstellingsproces starten, stoppen en "betalen".

## Technische aspecten
- **3 API services**, met een **api gateway**.
- 2 x **MySQL** & 1 x **MongoDB** databanken
- Deployment via kubernetes **Manifest.yaml** (**docker-compose** ook aanwezig)
- **CI/CD pipeline** met **github actions**.
- **Authentication** op api gateway via **GCP OAuth2**, op alle endpoints (enkel medenwerkers van de autogarage mogen dingen uitlezen en aanpassen)
- **Unit testen** op de Service klassen van de 3 services

## Endpoints (enkel api gateway niet intern)
 - GET /cars/all (AUTH)  -> Get all cars
 - GET /cars/{id} (AUTH)  -> Get car by id
 - POST /cars (AUTH) -> Save a car
 - GET /mechanics/all (AUTH) -> Get all mechanics
 - POST /mechanics (AUTH) -> Save a mechanic
 - GET /garages/all (AUTH) -> Get all repairs
 - GET /garages/bill/{id} (AUTH) -> Get the bill for a finished repair
 - PUT /garages/repaired/{id} (AUTH) -> Update the repair to - finished
 - PUT /garages/pay/{id} (AUTH) -> Update a repair to paid 
- POST /garages/repair (AUTH) -> Save/start a repair to a car if there is an available mechanic

## Schema
![Schema](/images/APT%20schema%20Robbe%20Verbist.png)

## Verloop van gebruik
1. Een klant zijn **auto moet gerepareerd worden**, de werknemer vraagt de juiste informatie en voert die in (automerk, model, jaar en kilometerstand). Er gebeurd een **POST(/cars)**.
2. Nu de auto gerigstreerd is, kan er een **monteur op gezet** worden. De werknemer doet een **POST(/garages/repair)**, en als er een monteur vrij is met het automerk, dan worden de **auto en de monteur aan de herstelling gekoppeld**.
3. Wanneer de monteur **klaar is met de herstelling**, kan er een **PUT(/garages/repaired/{id})** gedaan worden om de **herstelling stop** te zetten.
4. De werknemer kan dan de **prijs opvragen** aan de garage-service
met een **GET(/garages/bill/{id})**, en dan wordt de prijs berekend, 25 euro/uur. En kan de factuur opgesteld worden.
5. Als de klant heeft **betaald**, kan de herstelling verwijderd afgerond worden met een **PUT(/garages/pay/{id})**.

### Extra
- Alle auto's kunnen opgevraagd worden **(GET /cars/all)**
- Een enkele auto kan opgevraagd worden **(GET /cars/{id})**
- Alle monteurs kunnen opgevraagd worden **(GET /mechanics/all)**
- Er kan een monteur toegevoegd worden **POST(mechanics)**
- Alle herstellingen kunnen opgervaagd worden **(GET /garages/all)**

## Postman screenshots (volgens verloop van gebruik en extra)

1. **POST /cars (AUTH)** Er wordt een auto geregistreerd
![POST /cars](/images/PostCar.png)
**GET /cars/{id} (AUTH)** We kunnen de auto opvragen met id
![GET /cars/{id}](/images/CarById.png)

2. **POST /garages/repair (AUTH)** Er wordt een herstelling geregistreerd voor de porsche
![POST /garages/repair](/images/PostRepair.png)
**GET /garages/all (AUTH)** We kunnen de herstelling bekijken (carId = de porsche, mechanicId = van een porsche monteur zie volgende screenshot, startdate maar geen enddate, herstelling is nog niet klaar en niet betaald)
![GET /garages/all](/images/AllRepairs.png)
**GET /mechanics/all (AUTH)** Hier kan je alle monteurs zien, 1 is een porsche-monteur en niet meer available.
![GET /mechanics/all](/images/AllMechanics2.png)

3. **PUT /garages/repaired/{id} (AUTH)** De herstelling wordt stop gezet
![PUT /garages/repaired/{id}](/images/RepairDone.png)


**GET /cars/all (AUTH)**
![GET /cars/all](/images/AllCars.png)




**POST /mechanics (AUTH)**
![POST /mechanics](/images/PostMechanic.png)



