package com.stronans.messagebus;

/**
 * Created by S.King on 08/10/2016.
 */
public class MessageBusException extends RuntimeException {

    public MessageBusException() {
        super("No Queue exists for this key");
    }

    public MessageBusException(String message) {
        super(message);
    }
}
