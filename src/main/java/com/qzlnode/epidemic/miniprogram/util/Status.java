package com.qzlnode.epidemic.miniprogram.util;

/**
 * @author qzlzzz
 */
public enum Status {

    UNSUCCESSFUL(302,"{\"code\": 302, \"message\": \"operation unsuccessful\"}"),

    SUCCESSFUL(200,"{\"code\": 200, \"message\": \"operation successful\"}");

    private final int value;

    private final String reasonPhrase;

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    private Status(int value, String reasonPhrase){
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    @Override
    public String toString() {
        return this.value + " " + this.name();
    }
}
