package com.tshevchenko.app;

import java.net.DatagramPacket;
import java.nio.channels.SocketChannel;
import java.nio.ByteBuffer;
import java.util.Calendar;

/**
 * Class implements a server Time functionality (RFC 868)
 */
public class TimeServer extends Server implements IServerObserver, IService{
    public TimeServer(IServerRunner serverRunner){
        super(serverRunner);
        serverRunner.setService(this);
        System.out.println("TimeServer::TimeServer");
    }

    @Override
    public void updateRunningState(boolean state){
        serverRunner.updateRunningState(state);
        System.out.println("TimeServer::updateRunningState");
    }

    /**
     * Method makes the same processing for both UDP and TCP services
     * @return
     */
    @Override
    public ByteBuffer runService(ByteBuffer inputData){
        System.out.println("TimeServer::runService");
        ByteBuffer response = ByteBuffer.allocate(4);
        Calendar calendar = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"));
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0, 0);
        long resultSecs = ((System.currentTimeMillis() + Math.abs(calendar.getTime().getTime()))  /1000);
        response.putInt((int) ((resultSecs >> 24) & 0xFF));
        response.putInt((int) ((resultSecs >> 16) & 0xFF));
        response.putInt((int) ((resultSecs >> 8) & 0xFF));
        response.putInt((int) (resultSecs & 0xFF));
        return response;
    }
}
