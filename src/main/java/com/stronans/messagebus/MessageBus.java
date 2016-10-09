package com.stronans.messagebus;

import java.util.*;

/**
 * Simple implementation of a in-memory message bus.
 * This allows several different components in a multithreaded application to send messages to each other.
 * <p>
 * Created by S.King on 08/10/2016.
 */
public final class MessageBus {
    private Map<Integer, Queue<String>> messages = new LinkedHashMap<>();
    private Map<Integer, List<MessageListener>> listeners = new HashMap<>();

    private static MessageBus messageBus = null;

    private MessageBus() {
    }

    public static MessageBus getInstance() {
        if (messageBus == null) {
            synchronized (MessageBus.class) {
                messageBus = new MessageBus();
            }
        }

        return messageBus;
    }

    public boolean addConsumer(Integer queue, MessageListener listener) {
        boolean result = false;

        if (listeners.containsKey(queue)) {
            List<MessageListener> listenerList = listeners.get(queue);
            listenerList.add(listener);
            result = true;
        } else {
            List<MessageListener> listenerList = new ArrayList<>();
            listenerList.add(listener);
            listeners.put(queue, listenerList);
            result = true;
        }

        return result;
    }

    private void notifyListeners(Integer queue, int queueSize) {
        if (!listeners.isEmpty()) {
            if (listeners.containsKey(queue)) {
                List<MessageListener> listenerList = listeners.get(queue);
                for (MessageListener listener : listenerList) {
                    listener.msgArrived(queueSize);
                }
            }
        }
        // else No listeners so no notifications.
    }

    /**
     * Messages are assumed to be JSON
     *
     * @param queue   ID of the queue that this message is added to the end of
     * @param message content of the message, assumed to be JSON
     * @return success if it was added to the queue successfully.
     */
    public synchronized boolean addMessage(Integer queue, String message) {
        boolean result = false;
        if (messages.containsKey(queue)) {
            Queue<String> messageQueue = messages.get(queue);
            messageQueue.add(message);
            result = true;
            notifyListeners(queue, messageQueue.size());
        } else {
            Queue<String> messageQueue = new LinkedList<>();
            messageQueue.add(message);
            messages.put(queue, messageQueue);
            result = true;
            notifyListeners(queue, messageQueue.size());
        }
        return result;
    }

    public synchronized String getMessage(Integer queue) {
        String result = "";
        if (messages.containsKey(queue)) {
            Queue<String> messageQueue = messages.get(queue);
            if (messageQueue.isEmpty()) {
                result = null;
            } else {
                result = messageQueue.remove();
            }
        } else {
            throw new MessageBusException("No Queue exists for this key: " + queue);
        }

        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cannot clone instance of this class");
    }
}
