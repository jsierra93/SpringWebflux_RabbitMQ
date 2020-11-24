# SpringWebflux_RabbitMQ
Microservicio reactivo desarrollado en Spring Webflux realizando conexión a una cola RabbitMQ (Lectura/Escritura)

En este repositorio encontrará varias implementaciones para el publicar y consumir en una cola RabbitMQ desde Spring Webflux.

1. **webflux-rabbitmq-reactor**: Se realiza implementación utilizando `reactor-rabbitmq:1.5.0` para el consumo reactivo de la cola.
    
    Para ejecutar el proyecto via docker se debe hacer lo siguiente:
     
     Limpiar y construir el jar:
     - `./gradlew clean build`
     
     Construir imagenes docker
     - `docker-compose build`
     
     Iniciar contenedores rabbitMQ y Spring
     - `docker-compose up`
     
     