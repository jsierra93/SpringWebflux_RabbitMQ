# SpringWebflux_RabbitMQ
Microservicio reactivo desarrollado en Spring Webflux realizando conexión a una cola RabbitMQ (Lectura/Escritura)

En este repositorio encontrará varias implementaciones para el publicar y consumir en una cola RabbitMQ desde Spring Webflux.

**webflux-rabbitmq-reactor**: Se realiza implementación utilizando `reactor-rabbitmq:1.5.0` para el consumo reactivo de la cola.
    
    Para ejecutar el proyecto via docker se debe hacer lo siguiente:
     
     Limpiar y construir el jar:
     - `./gradlew clean build`
     
     Construir imagenes docker
     - `docker-compose build`
     
     Iniciar contenedores rabbitMQ y Spring
     - `docker-compose up`
        
   **Endpoint**
   - http://localhost:8081/sendmessage
   - http://localhost:8081/readfirts
   - http://localhost:8081/enablelistener
   - http://localhost:8081/purge
   
   
 **webflux-rabbitmq-jms**: Se realiza implementación utilizando Spring JMS para el consumo de la cola.
    
    Para ejecutar el proyecto via docker se debe hacer lo siguiente:
     
     Limpiar y construir el jar:
     - `./gradlew clean build`
     
     Construir imagenes docker
     - `docker-compose build`
     
     Iniciar contenedores rabbitMQ y Spring
     - `docker-compose up`
     
    
 **Endpoint**
   - http://localhost:8081/sendmessage