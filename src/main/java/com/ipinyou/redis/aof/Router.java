package com.ipinyou.redis.aof;

import java.util.List;

import com.google.inject.ImplementedBy;

import redis.clients.jedis.Jedis;

public interface Router {
	
	public AofLogJedis dispatch(String key);

}
