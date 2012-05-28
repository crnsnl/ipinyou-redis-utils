package com.ipinyou.redis.aof.natv;

import java.io.IOException;

import org.junit.Test;

import com.ipinyou.redis.aof.parser.NativeAofParser;

import junit.framework.TestCase;

public class NativeRedisParserTest extends TestCase {
	
	@Test
	public void testAofFile(){
		NativeTestRouter nativeTestRouter = new NativeTestRouter();
		String filename = "F:\\redis\\64bit.16379\\appendonly.aof";
		NativeAofParser aofParser = new NativeAofParser(nativeTestRouter);
		try {
			aofParser.parse(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
