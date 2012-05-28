package com.ipinyou.redis.aof.natv;

import com.ipinyou.redis.aof.parser.NativeRedisClient;
import com.ipinyou.redis.aof.parser.Router;

public class NativeTestRouter implements Router {

	static NativeRedisClient nativeRedisClient = new NativeRedisClient("localhost", 16389);
	
	@Override
	public NativeRedisClient dispatch(String key) {
		return nativeRedisClient;
	}

}
