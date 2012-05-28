package com.ipinyou.redis.aof.parser;

import java.util.List;

import com.google.inject.ImplementedBy;

import redis.clients.jedis.Jedis;

public interface Router {
	
	public NativeRedisClient dispatch(String key);

}
