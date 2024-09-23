package com.example.starter.redisConnection;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisConnection;
import io.vertx.redis.client.RedisOptions;
import io.vertx.redis.client.Request;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class RedisServiceImpl implements RedisService {

  private static RedisServiceImpl instance;
  private RedisAPI redisAPI;
  private RedisConnection client;

  // Private constructor for Singleton
  private RedisServiceImpl() {}

  // Public method to get the singleton instance
  public static synchronized RedisServiceImpl getInstance() {
    if (instance == null) {
      instance = new RedisServiceImpl();
    }
    return instance;
  }

  // Asynchronous initialization for Vert.x classes
  @Override
  public Future<RedisAPI> init(Vertx vertx, RedisOptions redisOptions) {
    if (redisAPI == null) {
      return Redis.createClient(vertx, redisOptions)
        .connect()
        .compose(connection -> {
          client = connection;
          redisAPI = RedisAPI.api(client);

          // Send PING command
          return client.send(Request.cmd(Command.PING)).map(pingResponse -> {
            System.out.println("Ping Response: " + pingResponse.toString());
            return redisAPI; // Return RedisAPI
          });
        })
        .onFailure(err -> {
          System.err.println("Failed to connect to Redis: " + err.getMessage());
        });
    }

    return Future.succeededFuture(redisAPI); // Return if already initialized
  }

  // Blocking method for non-Vert.x classes using CompletableFuture
  public CompletableFuture<String> getSync(String key) {
    CompletableFuture<String> future = new CompletableFuture<>();

    redisAPI.get(key)
      .onSuccess(response -> future.complete(response != null ? response.toString() : null))
      .onFailure(future::completeExceptionally);

    return future; // Return CompletableFuture
  }

  public CompletableFuture<Void> setSync(String key, String value) {
    CompletableFuture<Void> future = new CompletableFuture<>();

    redisAPI.set(Arrays.asList(key, value))
      .onSuccess(response -> future.complete(null))
      .onFailure(future::completeExceptionally);

    return future; // Return CompletableFuture
  }

  // Asynchronous methods for Vert.x-based classes
  @Override
  public Future<String> get(String key) {
    return redisAPI.get(key).map(response -> response != null ? response.toString() : null);
  }

  @Override
  public Future<Void> set(String key, String value) {
    return redisAPI.set(Arrays.asList(key, value)).mapEmpty();
  }
}
