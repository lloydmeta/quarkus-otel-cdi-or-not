package com.beachape.logging;

public class MessageUtils {

    public static String formatMessageWithThread(String message) {
        return String.format("[Thread:%s] %s", Thread.currentThread().getName(), message);
    }
}
