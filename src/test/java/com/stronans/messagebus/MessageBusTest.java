package com.stronans.messagebus;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the MessageBus
 *
 * Created by S.King on 08/10/2016.
 */
public class MessageBusTest implements MessageListener{
    private MessageBus messageBus;

    @Before
    public void setUp() throws Exception {
        messageBus = MessageBus.getInstance();
    }

    @Test
    public void addConsumerAndMessage() throws Exception {
        assertTrue(messageBus.addConsumer(1, this));
        assertTrue(messageBus.addMessage(1, "Test Message"));
    }

    @Override
    public void msgArrived(int size) {
        System.out.println("Message received");
        assert(size> 0);
        assertSame(messageBus.getMessage(1), "Test Message");
    }
}