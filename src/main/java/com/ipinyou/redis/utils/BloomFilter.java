package com.ipinyou.redis.utils;

import java.nio.ByteBuffer;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/******
 * 
 * 用redis实现的bloomfilter,用于过滤重复数据的场景
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class BloomFilter {
	
	//误差最小公式  ln2*m/n=k . from http://blog.csdn.net/jiaomeng/article/details/1495500
	static final int hashCount = 4;//hash函数的个数
	
	static final int MAX_OFF_SET  =  500000   ;//最长的bit位
	
	private String keySpace;	//用于[keySpace]的BloomFilter
	/*****
	 * 
	 * @param keySpace
	 */
	public BloomFilter(String keySpace) {
		this.keySpace = keySpace;
	}
	
	private Jedis jedis;
	
	/*****
	 * 放入key
	 * @param key
	 */
	public void insert(String key){
		Pipeline pipeline = jedis.pipelined();
		int[] offsets = getHashBuckets(key);
		for (int offset : offsets) {
			pipeline.setbit(keySpace, offset, true);
		}
		pipeline.sync(); 
	}
	
	/****
	 * 清空BloomFilter
	 * @param key
	 */
	public void clear(String key){
		jedis.set(keySpace, "0");
	}
	
	/*****
	 * 
	 * @param key
	 * @return
	 */
	public long numSet(String key){
		return jedis.strlen(keySpace);
	}
	/*****
	 * key是否已存在
	 * @param key
	 * @return
	 */
	public boolean include(String key){
		int[] offsets = getHashBuckets(key);
		for (int offset : offsets) {
			if(jedis.getbit(keySpace, offset)==false)return false;
		}
		return true;
	}
	/*****
	 * 删除存在的key
	 * @param key
	 */
	public void delete(String key){
		int[] offsets = getHashBuckets(key);
		Pipeline pipeline = jedis.pipelined();
		for (int offset : offsets) {
			pipeline.setbit(keySpace, offset, false);
		}
		pipeline.sync(); 
	}
	
	private int[] getHashBuckets(String key) {
        return BloomFilter.getHashBuckets(ByteBuffer.wrap(key.getBytes()), hashCount, MAX_OFF_SET );
    }

    static int[] getHashBuckets(ByteBuffer b, int hashCount, int max) {
    	int[] result = new int[hashCount];
    	int hash1 = MurmurHash.hash32(b, b.position(), b.remaining(), 0);
    	int hash2 = MurmurHash.hash32(b, b.position(), b.remaining(), hash1);
        for (int i = 0; i < hashCount; ++i){
            result[i] = (int) (Math.abs((hash1 + i * hash2) % max) );
        }
        return result;
    }

    public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}
    
    
    public static void main(String[] args) {
		System.out.println(~1);
	}
    
}
