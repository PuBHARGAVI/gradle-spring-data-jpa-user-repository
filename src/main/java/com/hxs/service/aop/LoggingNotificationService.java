package com.hxs.service.aop;

import com.hxs.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author HSteidel
 */
@Service
public class LoggingNotificationService implements NotificationService {

    public static final String TYPE = "LOGGING";

    private static final Logger logger = LoggerFactory.getLogger(LoggingNotificationService.class);

    @Override
    public void notify(String destination, String message) {
        if(destination.equals(getNotificationType())){
            logger.info("NOTIFICATION: " + message);
        }
    }

    @Override
    public String getNotificationType() {
        return TYPE;
    }
}
