package com.example.starter;

import com.example.starter.redisConnection.RedisServiceImpl;
import com.example.starter.verticals.SomeOtherVerticle;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;

public class MainVerticle extends AbstractVerticle {

  @Override
  public void start() {
    // Deploy other verticles
    vertx.deployVerticle(new SomeOtherVerticle())
      .compose(id -> initRedis())
      .onComplete(unused -> {});
  }

  private Future<RedisAPI> initRedis() {
    RedisOptions redisOptions = new RedisOptions()
      .setConnectionString("redis://localhost:6379").setPassword("your_password");

    RedisServiceImpl redisService = RedisServiceImpl.getInstance();
    return redisService.init(vertx, redisOptions)
      .onSuccess(v -> {
        System.out.println("Redis initialized successfully");
      })
      .onFailure(err -> {
        System.err.println("Failed to initialize Redis: " + err.getMessage());
      });
  }
}
