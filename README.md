# store-checkout-service

Back-End service checkout store with Java, Spring Boot, H2 and Swagger 


* For testing and view the code in the project I recommend using the IntelliJ IDE (free Community or Ultimate edition)
     
     https://www.jetbrains.com/idea/
     

* For testing navigate to:

    http://localhost:8080/swagger-ui.html

* You can use Postman

* For view H2 embedded data navigate to:

    http://localhost:8080/h2-console/

    Driver Class: org.h2.Driver

    JDBC URL: jdbc:h2:mem:storedb

    User Name: sa

    Without password

* Notes: 
  
  Service has not security because it is only accessible by the client
  
  Project will be deployed with Docker and Kubernetes
  
  To prevent the service from being accessible from outside only service client is going to put in Ingress configuration
  
  https://kubernetes.io/docs/concepts/services-networking/ingress/
  
  Service uses @Transient methods in some entities for calculated data, so there is no need to store it in the database
  
  Service uses synchronized for controller methods for only one thread executes method at same time
  
  Service has got unit and integration tests
  
* TODO

  Promotion discounts constants will be remove and use Spring Cloud Config for properties. With this option you can uses properties in  a repository like Github or Gitlab
    

  
  

