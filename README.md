# Redis Singleton Service with Vert.x

This project demonstrates how to implement a Singleton Redis connection using the Vert.x framework. The Redis connection is established asynchronously, with methods to interact with the Redis database using the `RedisAPI`. The connection is only initialized once (singleton pattern) and is shared across all services.

## Features

- Singleton Redis connection for multiple Vert.x verticles
- Asynchronous initialization of Redis using `Future`
- Redis `get` and `set` operations with `RedisAPI`
- Docker setup for a 3-node Redis cluster using Bitnami's Redis cluster image

## Installation and Setup

### Prerequisites

- Java 11 or higher
- Maven
- Docker and Docker Compose

### Clone the Repository

```bash
git clone https://github.com/your-username/redis-vertx-singleton.git
cd redis-vertx-singleton
```
General Approach

    Classes with Vert.x Context:
        These classes can use Vertx directly to interact with Redis using Future and AsyncResult because they already have an event loop.
        For these classes, you pass the RedisAPI or the singleton RedisServiceImpl instance from the Vert.x context.

    Classes Without Vert.x Context:
        For non-Vert.x classes, you’ll still use the same Redis Singleton instance (RedisServiceImpl). However, instead of using Future, you can use blocking Redis operations or adapt asynchronous calls with CompletableFuture to handle results in a non-Vert.x context.


Summary:

    Classes with Vert.x: Use Future-based Redis operations.
    Classes without Vert.x: Use CompletableFuture-based Redis operations, either blocking or non-blocking.
    The Singleton Redis instance (RedisServiceImpl) can be shared across all classes, ensuring a single Redis connection is used in both Vert.x and non-Vert.x contexts.

This approach keeps Redis management centralized and efficient, regardless of whether your classes use Vert.x or not.
