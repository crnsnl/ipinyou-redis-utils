package com.ipinyou.redis.aof;

import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class AofParserTest extends TestCase{
	
	@Test
	public void testAofFile(){
		
		String filename = "F:\\redis\\64bit.16379\\appendonly.aof";
		Router router = new DefaultTestRouter();
		AofParser aofParser = new AofParser(router);
		try {
			aofParser.parse(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
