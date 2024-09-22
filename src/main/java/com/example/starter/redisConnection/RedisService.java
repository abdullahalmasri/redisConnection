package com.example.starter.redisConnection;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.redis.client.RedisAPI;
import io.vertx.redis.client.RedisOptions;

public interface RedisService {
  Future<RedisAPI> init(Vertx vertx, RedisOptions redisOptions);  // Initialize Redis
  Future<String> get(String key);
  Future<Void> set(String key, String value);
}
