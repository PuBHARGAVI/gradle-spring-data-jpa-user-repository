package com.hxs.service;

/**
 * @author HSteidel
 */
public interface NotificationService {

    void notify(String destination, String message);

    String getNotificationType();

}
