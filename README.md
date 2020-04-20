# This is a Challenge Project
#### What I believe is missing:
- I could not do the tests specifically for RabbitMQ, I did overall testing for most methods that return proper response. 

#### The change I made in docker-compose.yml file:
- I had to add the ports for MySQL(line 24) and RabbitMQ(line 28) otherwise the port in my computer did not listen. It was simply not exposed before.  

#### Requirements not reached:
I could not import from docker-compose.yml the variable NUMBER_OF_VALIDATION_CONSUMERS so in order to change the number of validation consumers:
- Go to ChallengeApplication.java
- In line 76 you can change the setConcurrentConsumers value to the number of consumers you wish

#### RabbitMQ configuration:
- The response.exchange is a direct exchange.
- The routing.key for the validation message response is response.routing.key.
- The queues validation.queue and insertion.queue have no bindings with the response.exchange since the response.exchange is only for the response for validation requests.

All parameters from RabbitMQ configuration were set by SpringAMPQ or in the ConnectionFactory bean at ChallengeApplication.java.
