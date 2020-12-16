# Project Description

This project is implemented for **Takeaway** as an assignment project. It's a Spring Boot project which developed by using Java language.

## Build & Run

This project uses Mysql as database server and Kafka as messaging queue. You can follow the next instructions to run the application.

### Running the application

You can run the Spring Boot application and you will have the service up & running.<br>
**Note** : I do not know the concepts of docker and containers thing, so I have implemented the solution
using local mysql db and kafka with the configuration as mentioned in the boiler plate code


```
Eclipse -> Run As -> Spring Boot Application

OR

mvn clean package spring-boot:repackage
java -jar target/employeeService-0.0.1-SNAPSHOT.jar
```

For the above step to work, you should have a running mysql server at `localhost:3306` and kafka at `localhost:9092`


## REST APIs

### Create Department

Creates a department with giving name.


URL path: ``` POST /departments```


Request Model: 
```json
{
"name":"Engineering"
}
```

Response Model: API will return id and name of the created entity if successful.
```json
{
    "id": 1,
    "name": "Engineering"
}
```


### Create Employee

Creates an employee with giving information.

URL path: ``` POST /employees```

Request Model:
* unique email
* creates department if doesn't exist with given name

```json
{
	"name": "Test Name",
	"email": "test@email.com",
	"birthday": "1992-10-10",
	"department": "Engineering"
}
```

### Get Employee by Id

Get an employee for giving id.

URL path: ```GET employees/{id}```

Response Model:
```json
{
    "id": "68951edf-39df-47c9-809b-b9bc9cf7a75f",
    "name": "Test Name",
    "email": "test@email.com",
    "birthday": "1992-10-10",
    "department": {
        "id": 1,
        "name": "Engineering"
    }
}
```


### Update Employee

Updates employee for giving id.

URL path: ```PUT /employees/{id}```

Request Model:

* This API gets updated information and only update giving attributes. Returns the updated entity as a reponse object.
```json
{
    "name": "Update name",
    "email": "updated@email.com"
}
```

Response Model:
```json
{
    "id": "68951edf-39df-47c9-809b-b9bc9cf7a75f",
    "name": "Update name",
    "email": "updated@email.com",
    "birthday": "1992-10-10",
    "department": {
        "id": 1,
        "name": "Engineering"
    }
}
```

### Delete Employee

Deletes employee for giving id.

URL path: ```DELETE /employees/{id}```

Response Model:
* Returns 200 OK with no response body.


## Postman Collections

You can find the postman collection for each REST API call at the following link : 

```
https://www.getpostman.com/collections/c3f4b649dad2fea0b7c9
```

## Author

Amol Ekande