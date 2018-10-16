package com.hxs.service.aop;

import com.hxs.data.models.User;
import com.hxs.service.NotificationService;
import com.hxs.web.model.request.UserCreateRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  Sample Aspect
 *
 * @author HSteidel
 */
@Aspect
@Component
public class NotificationAspect {

    private static final Logger logger = LoggerFactory.getLogger(NotificationAspect.class);

    /*This is just a sample service, nothing serious, to demonstrate doing something from the Aspect*/
    private final NotificationService notificationService;

    @Autowired
    public NotificationAspect(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /*This annotation is mostly for core-readability. It let's others know, this method notifies something*/
    @Pointcut("@annotation(com.hxs.service.aop.Notifies)")
    public void notifiesAnnotation() {}

    @Pointcut("execution(* com.hxs.service.UserService.createUser(..))")
    public void userCreation(){}


    @Around(value = "(notifiesAnnotation() && userCreation()) && args(userRequest)")
    public Object notifyUserCreateEvent(ProceedingJoinPoint joinPoint, UserCreateRequest userRequest) throws Throwable{
        logger.debug("Attempting to create new user: " + userRequest.getUsername());
        User user = (User) joinPoint.proceed(joinPoint.getArgs());
        notificationService.notify(LoggingNotificationService.TYPE, "User " + user.getUsername() + " was created.");
        return user;
    }
}
