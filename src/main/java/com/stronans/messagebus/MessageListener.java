package com.stronans.messagebus;

/**
 * Listener Interface
 *
 * Created by S.King on 08/10/2016.
 */
public interface MessageListener {
    void msgArrived(int size);
}
