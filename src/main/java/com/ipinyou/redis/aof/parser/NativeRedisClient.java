package com.ipinyou.redis.aof.parser;

import static redis.clients.jedis.Protocol.toByteArray;
import static redis.clients.jedis.Protocol.Command.AUTH;
import static redis.clients.jedis.Protocol.Command.SELECT;

import com.ipinyou.redis.aof.Argument;

import redis.clients.jedis.Connection;
import redis.clients.jedis.Protocol.Command;

public class NativeRedisClient extends Connection {
	
    private String password;
    
    private long db;
	
    public NativeRedisClient(final String host) {
        super(host);
    }

    public NativeRedisClient(final String host, final int port) {
        super(host, port);
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public void connect() {
        if (!isConnected()) {
            super.connect();
            if (password != null) {
                auth(password);
                getStatusCodeReply();
            }
            if (db > 0) {
                select(Long.valueOf(db).intValue());
                getStatusCodeReply();
            }
        }
    }
    
    public void execCommand(Argument argument){
    	Command cmd = transfer(argument.getCommand());
    	if(cmd==null)return;
    	String[] params = argument.getParams();
    	sendCommand(cmd, params);
    }
    
    
    public void select(final int index) {
        db = index;
        sendCommand(SELECT, toByteArray(index));
    }
    
    public void auth(final String password) {
        setPassword(password);
        sendCommand(AUTH, password);
    }
    
    private Command transfer(String command){
    	if(command==null)return null;
    	return Command.valueOf(command.toUpperCase());
    }

}
