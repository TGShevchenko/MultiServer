package com.tshevchenko.app;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * Class implements a server Daytime functionality (RFC 867)
 */
public class DayTimeServer extends Server implements IService{
    // Get an encoder for converting strings to bytes
    protected final CharsetEncoder encoder = Charset.forName("US-ASCII").newEncoder();
    public DayTimeServer(IServerRunner serverRunner){
        super(serverRunner);
        serverRunner.setService(this);
        System.out.println("DayTimeServer::DayTimeServer");
    }

    /**
     * Method makes the Daytime processing
     * @param receivedData
     * @return
     */
    @Override
    public ByteBuffer runService(ByteBuffer receivedData){
        System.out.println("DayTimeServer::runService");
        ByteBuffer response = null;
        try{
            // Builds a response string, wrap, and encode to bytes
            String date = new java.util.Date().toString() + "\r\n";
            response = encoder.encode(CharBuffer.wrap(date));
        }catch(Throwable e){}
        return response;
    }
}
