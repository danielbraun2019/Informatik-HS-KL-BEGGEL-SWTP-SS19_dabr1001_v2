# Einführung 
Der [SQL-Coach](https://sqlcoach.informatik.hs-kl.de/sqlcoach/index) ist eine Anwendung, 
die zum Üben von SQL dient. Die Studenten sollen ihre in der Vorlesung bzw. 
Übung erworbenen Kentnisse selbständig vertiefen können. 

Im Rahmen der SWTP-Vorlesung soll der SQL-Coach auf eine neue Architektur, 
basierend auf [JAX-RS](https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services) 
portiert werden.  

# SQLcoach-Service

Der SQLcoach-service stellt im Moment nur eine Verwaltung der Szenarien
in Form von JSON via REST-Schnittstelle bereit. Es sollen aber Aufgabengruppen 
und einzelnen Aufgaben sowie der Zugriff auf eine Trainingsdatenbank 
realisiert werden. 

Der Service bedient sich einer Postgres Datenbank 
zur Verwaltung der Stammdaten.
Die Trainingsdatensätzte sollen in einer zweiten Postgres 
Instanz realisiert werden. Diese soll natürlich mit docker-compose
realisiert werden.  

# Installation
## Voraussetzungen:
    1. Docker installiert.
    2. Maven installiert (incl. Setzen von Umgebungsvariablen)
    3. JDK 1.8 installiert (incl. Setzen von Umgebungsvariablen) 

**Beachten Sie, dass nur Java 8 erlaubt ist. Java 9, 10 und 11 sind unzulässig!**
  
## Starten des Services via Docker
**Ihre fertige Anwendung muss sich genau so bauen und starten lassen!**    
    
Bauen mit maven:
``` 
mvn clean package
```

Starten mit docker-compose:

``` 
docker-compose  build 

docker-compose  up 

```
     


## Starten des Services via Intellij
Während der Entwicklungsphase macht es oft Sinn TomCat direkt in Intellij zu starten. 
Hierzu müssen Sie Folgendes konfigurieren: 

- Konfiguriere Tomcat application server mit dem Pfad der Tomcat Installation
- Konfiguriere Tomcat mit `sqlcoach:war exploded` (Deployment tab)
und ändere den Application Context zu `/sqlcoachservice`.  
- Start docker-compose um die Postgres bereit zu stellen 
- `postgres_properties`=`postgres_local.properties` muss als env Variable im Tomcat konfiguriert warden 
- Ändere den Port von Tomcat zu 8000



#Spezifikation des Interfaces. 
**Ihre fertige Anwendung muss genau diese Schnittstellen bereitstellen!**


### Get all scenarios
 
```http://localhost:8001/sqlcoachservice/api/v1/catalog```

GET

### Get all groups for scenario 1 

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1```

GET

### Get all exercises for group 1 

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1/1```

GET


### Add new scenario
 
```http://localhost:8001/sqlcoachservice/api/v1/catalog/add```

POST

    {
        "scenarioName": "New Scenario",
        "scenarioOwner": "Ich",
        "datasetId": 1
    } 
  
  
### Add new group within scenario 1

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1/add```

POST

    {
         "groupName": "New Group"
     }   

### Add new Exercise to scenario 1, group 1 

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1/1/add```

POST

       {       
          "exerciseText": "Description of the exercise",
          "exerciseSolution": "exerciseSolution query: select ..."
       }


### Update Szenario information

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1```

PUT

      {      
           "scenarioName": "Neuer Szenarienname",
           "scenarioOwner": "scenarioOwner nach update ",
           "datasetId": 2
       }



### Update Group information

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1/1```

PUT

      {      
           "groupName": "Neuer Gruppenname"                                   
       }



### Update Exercise information

```http://localhost:8001/sqlcoachservice/api/v1/catalog/1/1/1```

PUT

       {       
          "exerciseText": "Neue Aufgabenbeschreibung",
          "exerciseSolution": "neue exerciseSolution query: select ..."
       }


### Delete scenario 

```http://localhost:8001/sqlcoachservice/api/v1/catalog/2```

DELETE

### Delete Group  

```http://localhost:8001/sqlcoachservice/api/v1/catalog/2/1```

DELETE

### Delete Exercise  

```http://localhost:8001/sqlcoachservice/api/v1/catalog/2/1/1```

DELETE

### Get Table Definitions  

Obtain table information (tables, columns, primary keys, foreign keys) for all tables in dataset. Return results in suitable JSON data structure. 

```http://localhost:8001/sqlcoachservice/api/v1/dataset/1/tables```

GET

### Execute Query  

Execute Query on dataset. Return results in suitable JSON data structure.   

```http://localhost:8001/sqlcoachservice/api/v1/dataset/1/execute?query=select * from personal where persnr < 4```

GET






