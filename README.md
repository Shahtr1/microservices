# microservices

## Branch Strategy

- The `main` branch contains the codebase configured to connect to a MySQL database, showcasing the typical setup for production environments.
- The `after-main` branch demonstrates a fallback to an H2 in-memory database, which is often used for development or testing purposes. It also reintroduces the use of RabbitMQ for message queuing.
