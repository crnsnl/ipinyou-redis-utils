package com.ipinyou.redis.aof.exception;
/*****
 * 
 * @author zhenchuan.liu@ipinyou.com
 *
 */
public class ParseAofException extends RuntimeException{
	
	private Throwable cause;
	
	public ParseAofException(String message) {
		super(message);
	}
	
	public ParseAofException(Throwable t){
		super(t);
		this.cause = t;
	}
	
	public Throwable getCause() {
        return this.cause;
    }
	

}
