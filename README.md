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
1. Een klant zijn **auto moet gerepareerd worden**, de werknemer vraagt de juiste informatie en voert die in (automerk, model, jaar en kilometerstand). Er gebeurd een **POST(/cars)** naar **car-service**.
2. Nu de auto gerigstreerd is, kan er een **monteur op gezet** worden. De werknemer doet een **POST(/garages/repair)** naar **garage-service**, en als er een monteur vrij is met het automerk, dan worden de **auto en de monteur aan de herstelling gekoppeld**. Er kan ook een **nieuwe monteur** geregistreerd worden met een **POST(mechanics)** naar **mechanic-service**.
3. Wanneer de monteur **klaar is met de herstelling**, kan er een **PUT(/garages/repaired/{id})** naar **garage-service** gedaan worden om de **herstelling stop** te zetten.
4. De werknemer kan dan de **prijs opvragen** aan de garage-service
met een **GET(/garages/bill/{id})**, en dan wordt de prijs berekend, 25 euro/uur. En kan de factuur opgesteld worden.
5. Als de klant heeft **betaald**, kan de herstelling verwijderd afgerond worden met een **PUT(/garages/pay/{id})** naar **garage-service**

### Extra
- Alle auto's kunnen opgevraagd worden **(GET /cars/all)**
- Een enkele auto kan opgevraagd worden **(GET /cars/{id})**
- Alle monteurs kunnen opgevraagd worden **(GET /mechanics/all)**
- Alle herstellingen kunnen opgervaagd worden **(GET /garages/all)**
