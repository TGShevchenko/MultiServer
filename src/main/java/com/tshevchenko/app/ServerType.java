package com.tshevchenko.app;

/**
 * A ServerType enum, which adds two types for servers in this solution:
 * TCP and UDP
 */
public enum ServerType {
    TCP(1), UDP(2);
    private int serverType;

    private ServerType(int serverType){
        this.serverType = serverType;
    }
}
