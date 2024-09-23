package com.example.starter.verticals;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class SomeOtherVerticle extends AbstractVerticle {
  private final RedisServiceImpl redisService = RedisServiceImpl.getInstance();

  @Override
  public void start() {
    // Asynchronous call to get from Redis
    redisService.get("someKey").onSuccess(res -> {
      System.out.println("Got value from Redis: " + res);
    }).onFailure(err -> {
      System.err.println("Failed to get value: " + err.getMessage());
    });

    // Asynchronous call to set to Redis
    redisService.set("someKey", "someValue").onSuccess(v -> {
      System.out.println("Set value in Redis successfully");
    }).onFailure(err -> {
      System.err.println("Failed to set value: " + err.getMessage());
    });
  }
}
}
