package com.ipinyou.redis.aof;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ipinyou.redis.aof.exception.ParseAofException;
import com.ipinyou.redis.aof.utils.LineBufferedReader;

/******
 * 
 * 解析aof文件
 * 借助于jedis的高级api,通过反射
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class AofParser {
	
	private RedisCommandHandler redisCommandHandler;
	
	public AofParser(Router router) {
		redisCommandHandler  = new RedisCommandHandler(router);
	}
	
	public void parse(String... filenams){
    	for (String filename : filenams) {
			try {
				parse(filename);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    

	
	public void parse(String filename) throws IOException{
		LineBufferedReader br = new LineBufferedReader(new FileReader(filename));
        String line = null;
        while( (line = br.readLine())!=null){
        	List<String> args = new ArrayList<String>();
        	char flag = line.charAt(0);
        	if(flag!='*'){
        		throw new ParseAofException("格式错误! "+filename+" @line:" + br.getLineNum() );
        	}
        	int argc = NumberUtils.toInt(line.substring(1));
        	if(argc<0){
        		throw new ParseAofException("格式错误! "+filename+" @line:" + br.getLineNum() );
        	}
        	
        	for(int i =0 ; i < argc ;i++){
        		if( (line = br.readLine())==null){//获取参数
        			throw new ParseAofException("读取格式错误! "+filename+" @line:" + br.getLineNum() );
        		}
        		if(line.charAt(0)!='$'){
        			throw new ParseAofException("参数 格式错误! "+filename+" @line:" + br.getLineNum() );
        		}
        		int argLen = NumberUtils.toInt(line.substring(1));
        		if( (line = br.readLine())==null){
        			throw new ParseAofException("参数 内容错误! "+filename+" @line:" + br.getLineNum() );
        		}
        		String arg = line.substring(0, argLen);
    			args.add(arg);
        	}
        	
        	Argument argument = new Argument(args);
        	System.out.println(argument);
        	redisCommandHandler.exec(argument);
        	
        }
	}
	
}
