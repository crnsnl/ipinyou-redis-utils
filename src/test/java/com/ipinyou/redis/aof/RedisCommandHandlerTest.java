package com.ipinyou.redis.aof;

import static org.junit.Assert.*;

import org.junit.Test;

public class RedisCommandHandlerTest {

	@Test
	public void test() {
		RedisCommandHandler redisCommand = new RedisCommandHandler(new DefaultTestRouter());
		
		AofLogJedis jedis = new AofLogJedis("localhost", 6379);
		jedis.flushAll();
		
		redisCommand.exec(jedis, "set", new String[]{"foo","bar"});
		assertEquals("bar", jedis.get("foo"));
		
		redisCommand.exec(jedis, "set", new String[]{"key","value"});
		redisCommand.exec(jedis,"expire",new String[]{"key","1000"});//unit :  second
		assertTrue(jedis.ttl("key")>900);
		
		redisCommand.exec(jedis,"expireAt",new String[]{"foo",
				String.valueOf(System.currentTimeMillis()/1000 - 10000)});
		assertFalse(jedis.exists("foo"));
		
		redisCommand.exec(jedis, "set", new String[]{"foo","bar"});
		redisCommand.exec(jedis,"expireAt",new String[]{"foo",
				String.valueOf(System.currentTimeMillis()/1000 + 10000)});
		assertTrue(jedis.exists("foo"));
		
		redisCommand.exec(jedis, "setbit", new String[]{"mysetbit","10","1"});
		assertTrue(jedis.getbit("mysetbit", 10));
		
		redisCommand.exec(jedis, "setbit", new String[]{"mysetbit","9","0"});
		assertFalse(jedis.getbit("mysetbit", 9));
		
		redisCommand.exec(jedis, "set", new String[]{"hello","world"});
		redisCommand.exec(jedis, "setrange", new String[]{"hello","0","pp"});
		assertEquals("pprld", jedis.get("hello"));
		
		redisCommand.exec(jedis,"setnx",new String[]{"hello","redis"});
		assertEquals("pprld", jedis.get("hello"));
		
		//Set key to hold the string value and set key to timeout after a given number of seconds.
		redisCommand.exec(jedis,"setex",new String[]{"hello","10","redis"});
		assertEquals("redis", jedis.get("hello"));
		
		redisCommand.exec(jedis, "set", new String[]{"counter","10"});
		redisCommand.exec(jedis, "decrBy", new String[] {"counter","2"});
		assertEquals(8, Integer.valueOf(jedis.get("counter")));
		
		redisCommand.exec(jedis, "decr", new String[]{"counter"});
		assertEquals(7, Integer.valueOf(jedis.get("counter")));
		
		redisCommand.exec(jedis, "incrBy", new String[] {"counter","2"});
		assertEquals(9, Integer.valueOf(jedis.get("counter")));
		
		redisCommand.exec(jedis, "incr", new String[]{"counter"});
		assertEquals(10, Integer.valueOf(jedis.get("counter")));
		
		redisCommand.exec(jedis, "set", new String[]{"hi","world"});
		redisCommand.exec(jedis, "append", new String[]{"hi"," sky"});
		assertEquals("world sky", jedis.get("hi"));
		
		redisCommand.exec(jedis, "hset", new String[]{"people","name","lucas"});
		assertEquals("lucas", jedis.hget("people", "name"));
		
		redisCommand.exec(jedis, "hsetnx", new String[]{"people","name","lili"});
		assertEquals("lucas", jedis.hget("people", "name"));
		
		redisCommand.exec(jedis, "hsetnx", new String[]{"people","age","10"});
		assertEquals("10", jedis.hget("people", "age"));
		
		redisCommand.exec(jedis, "hmset", new String[]{"user","age","10","name","kevin"});
		assertEquals("10", jedis.hget("user", "age"));
		assertEquals("kevin", jedis.hget("user", "name"));
		
		redisCommand.exec(jedis, "hset", new String[]{"people1","age","10"});
		redisCommand.exec(jedis, "hincrBy", new String[]{"people1","age","2"});
		assertEquals("12", jedis.hget("people1", "age"));
		
		redisCommand.exec(jedis, "rpush", new String[]{"queue","jim"});
		redisCommand.exec(jedis, "rpush", new String[]{"queue","tom"});
		assertEquals("tom", jedis.rpop("queue"));
		
		redisCommand.exec(jedis, "lpush", new String[]{"queue","jim"});
		redisCommand.exec(jedis, "lpush", new String[]{"queue","tom"});
		assertEquals("tom", jedis.lpop("queue"));
		
		redisCommand.exec(jedis, "lpush", new String[]{"queue","tom"});
		redisCommand.exec(jedis, "lpush", new String[]{"queue","tom"});
		
		redisCommand.exec(jedis, "ltrim", new String[]{"queue","1","2"});
		assertTrue(2== jedis.llen("queue"));
		
		redisCommand.exec(jedis, "lset", new String[]{"queue","0","lfirst"});
		assertEquals("lfirst", jedis.lpop("queue"));
		
		redisCommand.exec(jedis, "sadd", new String[]{"myset","jim"});
		redisCommand.exec(jedis, "sadd", new String[]{"myset","jim"});
		redisCommand.exec(jedis, "sadd", new String[]{"myset","tom"});
		assertTrue(jedis.scard("myset")==2);
		
		redisCommand.exec(jedis, "srem", new String[]{"myset","tom"});
		assertTrue(jedis.scard("myset")==1);
		
		redisCommand.exec(jedis, "zadd", new String[]{"my_sort_set","10","tom"});
		redisCommand.exec(jedis, "zadd", new String[]{"my_sort_set","12","jim"});
		
		assertEquals(jedis.zrange("my_sort_set", 0, 0).toArray()[0],"tom");;
		
		redisCommand.exec(jedis, "zincrby", new String[]{"my_sort_set","3","tom"});
		
		assertEquals(jedis.zrange("my_sort_set", 0, 0).toArray()[0],"jim");;
		
		
		
	}

}
