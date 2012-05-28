package com.ipinyou.redis.aof;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.inject.Inject;

import redis.clients.jedis.Jedis;

/*****
 * 
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class RedisCommandHandler {
	
	private Router router;
	
	public static final ListMultimap<String, Method> commands =  ArrayListMultimap.create();
	
	@SuppressWarnings("rawtypes")
	public RedisCommandHandler(Router router){
		this.router = router;
		Method[] methods =  AofLogJedis.class.getDeclaredMethods();
		for (Method method : methods) {
			String name = method.getName();
			Class[] classes = method.getParameterTypes();
			boolean add = true;
			/*****
			for (Class clazz : classes) {
				if( !StringUtils.equalsIgnoreCase("java.lang.String", clazz.getName()) && 
						!StringUtils.equalsIgnoreCase("[Ljava.lang.String;", clazz.getName())){
					add = false;
					break;
				}
			}
			*****/
			if(add){
				commands.put(name.toLowerCase(),method);
			}
		}
	}
	
	private boolean isValid(String command){
		if(StringUtils.isBlank(command))return false;
		return commands.keySet().contains(command.toLowerCase());
	}
	
	public void exec(Argument args){
		String command = args.getCommand();
		String key = args.getKey();
		String[] params = args.getParams();
		if(StringUtils.isBlank(command) || StringUtils.isBlank(key)){
			return;
		}
		AofLogJedis jedis = router.dispatch(key);
		exec(jedis, command, params);
	}
	
	public void exec(AofLogJedis jedis,String command,String... args){
		if(!isValid(command)){
			return;
		}
		Object[] objs = new Object[1];
		objs[0] = args;
		List<Method> methods = commands.get(command.toLowerCase());
		for(int i = 0 ;  i < methods.size() ;i++){
			Method method = methods.get(i);
			try {
				method.invoke(jedis,objs);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}

}
