
## Task Description
Build a catalog API service. 

### Prerequisites
* Java 1.8 or above
* Spring Boot
* Database H2 (In-Memory)
* Maven
* Docker

## Running


##### Clone the github repository and run using docker
```
$  git clone https://github.com/shridhar-hitnalli/product-catalog
```

##### Build project with Maven
```
$ mvn clean install
```

##### Build Docker image
```
$ docker build -t="catalog-api" .
```

##### Run Docker image
```
$ docker run -p 9999:9999 -it --rm catalog-api
```
## Built With

* [Java 1.8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) - The language used
* [Spring Boot 2.1.5](http://spring.io/projects/spring-boot) - The web framework used
* [Lombok](https://projectlombok.org/) - Lombok is used to reduce boilerplate code for model/data objects,
* [Maven](https://maven.apache.org/) - Dependency Management
* [Swagger](https://swagger.io/) - API Documentation
* [Rest Assured](http://rest-assured.io/) - Testing


### Backend consists following restful apis
##### Fetch all products in a single request (batch fetch)
```
 GET /v1/products
```

##### Create more than one product in a single request (batch create)
```
 POST /v1/products/batch
```

##### Create a single product
```
 POST /v1/products
```

##### Fetch product by 'id'
```
 GET /v1/products/{id}
```

##### Search by title and description
Search by title and description including pagination and sorting (Default ASC sort and '-' for DESC for e.g: price,-brand).
This API fetch all the records if title and description fields are not provided
```
 GET /v1/products/search?title=product&description=desc&pageNumber=1&pageSize=5&sort=price,-brand 
```

##### Update product by id
```
PUT /v1/products/{id}
```

##### Delete product by id
```
DELETE /v1/products/{id}
```
    
 
### Security - Basic Authentication
Please check *application.properties* file *'spring.security.basic.username'* and *'spring.security.basic.password'* fields for login credentials


Swagger specs :

Swagger usually helps in the smart way of having technical documentation of the API. Please refer to the Swagger specs at http://localhost:9999/swagger-ui.html. URL .
At this URL you will know the structure of the request and the JSON response structure as well. You will also be able to hit the request from the swagger and get the json response back on the UI and play with it.
The app is currently configured to run on the port 9999 of the localhost ( In case you are running it on the local server ), however you are free to change it anytime you need ( In case you wish to deploy it to a different server ) by updating it in the application.properties file.


How to access the API?

Also as mentioned on the swagger specs. You can access these APIs through Swagger ui
```
http://localhost:9999/swagger-ui.html
```