package com.ipinyou.redis.aof.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
/****
 * 
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class LineBufferedReader extends BufferedReader {

	//用于记录已读取的行数,方便定位日志的问题.
	int lineNumber  = 0 ;
	
	public LineBufferedReader(Reader in) {
		super(in);
	}
	
	@Override
	public String readLine() throws IOException {
		lineNumber ++ ;
		return super.readLine();
	}
	
	/*****
	 * 获取行号
	 * @return
	 */
	public int getLineNum() {
		return lineNumber;
	}

}
