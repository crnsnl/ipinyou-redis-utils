package com.ipinyou.redis.aof;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class Argument {
	
	private String command;
	
	private String key ;
	
	private String[] params = new String[]{};
	

	public Argument(List<String> args) {
		if(args.size()>1){
			command = args.get(0);
			key = args.get(1);
			
			int left = args.size() - 1;
			params = new String[left];
			for(int i = 0 ; i < params.length ;i++){
				params[i] = args.get(1+i);
			}
		}
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "Argument [command=" + command + ", key=" + key + ", params="
				+ Arrays.toString(params) + "]";
	}
	
	

}
