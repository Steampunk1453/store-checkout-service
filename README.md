# store-checkout-service

Back-End service checkout store with Java 11, Spring Boot, JWT, H2 and Swagger 


* For testing and view the code in the project I recommend using the IntelliJ IDE (free Community or Ultimate edition)
     
     https://www.jetbrains.com/idea/

* How to run : 
     
     Install maven
     
     In terminal execute: `mvn spring-boot:run`  

* For testing endpoints navigate to:

    http://localhost:8080/swagger-ui.html

* You can use Postman

* For view H2 embedded data navigate to:

    http://localhost:8080/h2-console/

    Driver Class: org.h2.Driver

    JDBC URL: jdbc:h2:mem:storedb

    User Name: sa

    Without password
    
* Security:

    Service uses JWT and Spring Security for user authentication and endpoints securing
    
    With swagger: 
    
    Endpoint: /api/users {POST} Save user with password
    
    Endpoint: /login {POST} Login with the previous user and password in the response header copy authorization bearer: 
    (Bearer XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX) and paste it in Authorize button in value field and press Authorize
    
    After the next requests to the endpoints enabled for this user will be available
    
    Admin user:
    
    User = user1; password = password1
    

* Notes: 
  
  Project will be deployed with Docker and Kubernetes
  
  To prevent the service from being accessible from outside you can put it in Ingress configuration
  
  https://kubernetes.io/docs/concepts/services-networking/ingress/
  
  Service uses @Transient methods in some entities for calculated data, so there is no need to store it in the database
  
  Service uses synchronized for controller methods for only one thread executes method at same time
  
  Service implements unit and integration tests
  
* TODO

  Promotion discounts constants will be remove and use Spring Cloud Config for properties. With this option you can uses properties in  a repository like Github or Gitlab
    

  
  

