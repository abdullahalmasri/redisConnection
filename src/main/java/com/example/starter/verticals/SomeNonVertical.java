package com.example.starter.verticals;

public class SomeNonVertical {
  private final RedisServiceImpl redisService = RedisServiceImpl.getInstance();

  public void getFromRedis(String key) {
    try {
      // Blocking call, waiting for Redis response
      String value = redisService.getSync(key).get();
      System.out.println("Got value from Redis: " + value);
    } catch (Exception e) {
      System.err.println("Failed to get value: " + e.getMessage());
    }
  }

  public void setToRedis(String key, String value) {
    try {
      // Blocking call, waiting for Redis to set the value
      redisService.setSync(key, value).get();
      System.out.println("Set value in Redis successfully");
    } catch (Exception e) {
      System.err.println("Failed to set value: " + e.getMessage());
    }
  }
}
