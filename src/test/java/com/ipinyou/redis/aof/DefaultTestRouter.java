package com.ipinyou.redis.aof;

import redis.clients.jedis.Jedis;

public class DefaultTestRouter implements Router {
	
	private static final AofLogJedis JEDIS = new AofLogJedis("localhost", 6379);

	public AofLogJedis dispatch(String key) {
		return JEDIS;
	}

}
