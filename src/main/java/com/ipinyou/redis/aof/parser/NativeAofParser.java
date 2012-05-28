package com.ipinyou.redis.aof.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.ipinyou.redis.aof.Argument;
import com.ipinyou.redis.aof.exception.ParseAofException;
import com.ipinyou.redis.aof.utils.LineBufferedReader;

/******
 * 解析aof文件
 * 直接使用协议提交.
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class NativeAofParser {
	
	private Router router ;
	
	public NativeAofParser(Router router) {
		this.router = router;
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
        	router.dispatch(argument.getKey()).execCommand(argument);
        	
        }
	}
	
}
