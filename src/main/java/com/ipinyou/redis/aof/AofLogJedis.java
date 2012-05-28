package com.ipinyou.redis.aof;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
/*****
 * 
 * 转换所有的参数形式L[String
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class AofLogJedis extends Jedis {
	
	public String set(String[] args){
		String key = args[0];
		String value = args[1];
		return super.set(key, value);
	}
	
	public Long expire(String[] args){
		String key = args[0];
		String seconds = args[1];
		int sec = NumberUtils.toInt(seconds);
		return super.expire(key, sec);
	}

	public Long expireAt(String[] args){
		String key = args[0];
		String unixTime = args[1];
		long uTime = NumberUtils.toLong(unixTime);
		return super.expireAt(key, uTime);
	}
    //[SETBIT, set-bit, 10, 0]
	public boolean setbit(String[] args){
		String key = args[0];
		String offset = args[1];
		String value = args[2];
		long off = NumberUtils.toLong(offset);
		boolean val = StringUtils.equals("0", value)?false:true;
		return super.setbit(key, off, val);
	}
    
	public long setrange(String[] args){
		String key = args[0];
		String offset = args[1];
		String value = args[2];
		long off = NumberUtils.toLong(offset);
		return super.setrange(key, off, value);
	}
	
    public Long setnx(String[] args){
    	String key = args[0];
    	String value = args[1];
    	return super.setnx(key, value);
    }

	public String setex(String[] args){
		String key = args[0];
		String seconds = args[1];
		String value = args[2];
		int sec = NumberUtils.toInt(seconds);
		return super.setex(key, sec, value);
	}

	public Long decrBy(String[] args){
		String key = args[0];
		String integer = args[1];
		int inte = NumberUtils.toInt(integer);
		return super.decrBy(key, inte);
	}
	
	public Long decr(String[] args){
		String key = args[0];
		return super.decr(key);
	}
    
	public Long incrBy(String[] args){
		String key = args[0];
		String integer = args[1];
		int inte = NumberUtils.toInt(integer);
		return super.incrBy(key, inte);
	}
	
	public Long incr(String[] args){
		String key = args[0];
		return super.incr(key);
	}
    
	public Long append(String[] args){
		String key = args[0];
		String value = args[1];
		return super.append(key, value);
	}
	
	 public Long hset(String[] args){
		 String key = args[0];
		 String field = args[1];
		 String value = args[2];
		 return super.hset(key, field, value);
	 }
	 
	 public Long hsetnx(String[] args){
		 String key = args[0];
		 String field = args[1];
		 String value = args[2];
		 return super.hsetnx(key, field, value);
	 }
	
	// [HMSET, hm, key1, val1, key2, val2]
	public String hmset(String[] hash){
		String key = hash[0];
		Map<String, String> vals = new HashMap<String, String>();
		for(int i = 1 ; i < hash.length ;i+=2){
			vals.put(hash[i], hash[i+1]);
		}
		return super.hmset(key, vals);
	}
    
	public Long hincrBy(String[] args){
		String key = args[0];
		String field = args[1];
		String value = args[2];
		long val = NumberUtils.toLong(value);
		return super.hincrBy(key, field, val);
	}
	
    public Long rpush(String[] args){
    	String key = args[0];
    	String string = args[1];
    	return super.rpush(key, string);
    }
    
    public Long lpush(String[] args){
    	String key = args[0];
    	String string = args[1];
    	return super.lpush(key, string);
    }
    
	public String ltrim(String[] args){
		String key = args[0];
		String start = args[1];
		String end = args[2];
		long s = NumberUtils.toLong(start);
		long e = NumberUtils.toLong(end);
		return super.ltrim(key, s, e);
	}
    
	public String lset(String[] args){
		String key = args[0];
		String index = args[1];
		String value = args[2];
		long idx = NumberUtils.toLong(index);
		return super.lset(key, idx, value);
	}

	public Long lrem(String[] args){
		String key = args[0];
		String count = args[1];
		String value = args[2];
		long c = NumberUtils.toLong(count);
		return super.lrem(key, c, value);
	}
	
    public String lpop(String[] args){
    	String key = args[0];
    	return super.lpop(key);
    }

    public String rpop(String[] args){
    	String key = args[0];
    	return super.rpop(key);
    }

    public Long sadd(String[] args){
    	String key = args[0];
    	String member = args[1];
    	return super.sadd(key, member);
    }
    
    public Long srem(String[] args){
    	String key = args[0];
    	String member = args[1];
    	return super.srem(key, member);
    }
    
     public String spop(String[] args){
    	 String key = args[0];
    	 return super.spop(key);
     }
    
	public Long zadd(String[] args){
		String key = args[0];
		String score = args[1];
		String member = args[2];
		double s = NumberUtils.toDouble(score);
		return super.zadd(key, s, member);
	}
	
	public Long zrem(String[] args){
		String key = args[0];
		String member = args[1];
		return super.zrem(key, member);
	}

	public Double zincrby(String[] args){
		String key = args[0];
		String score = args[1];
		String member = args[2];
		double s = NumberUtils.toDouble(score);
		return super.zincrby(key, s, member);
	}
    

	public AofLogJedis(JedisShardInfo shardInfo) {
		super(shardInfo);
	}

	public AofLogJedis(final String host) {
		super(host);
	}

	public AofLogJedis(final String host, final int port) {
		super(host, port);
	}

	public AofLogJedis(final String host, final int port, final int timeout) {
		super(host, port, timeout);
	}
	
	

}
