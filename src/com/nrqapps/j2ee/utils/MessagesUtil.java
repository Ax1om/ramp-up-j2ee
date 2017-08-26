package com.nrqapps.j2ee.utils;

import com.nrqapps.j2ee.pojos.Notification;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mauricio Enriquez on 8/25/17.
 * NrqApps © 2017
 */
@SuppressWarnings("unchecked")
public class MessagesUtil {
    private static final String MESSAGES = "messages";
    private static final String USER_NOTIFICATION = "userNotification";

    public static String getMessage(HttpServletRequest request, String key) {
        Map<String, Object> messages = getMessagesMap(request);
        String message = (String) messages.get(key);
        if (!StringUtils.isEmpty(message)) {
            messages.remove(key);
            saveMessageMap(request, messages);
            return message;
        }
        return null;
    }

    public static void loadNotifications(HttpServletRequest request) {
        Map<String, Object> messages = getMessagesMap(request);
        Notification notification = (Notification) messages.get(USER_NOTIFICATION);
        if (notification != null && !StringUtils.isEmpty(notification.getMessage())) {
            messages.remove(USER_NOTIFICATION);
            saveMessageMap(request, messages);
            request.setAttribute(USER_NOTIFICATION, notification.toString());
        }
    }

    private static void setMessageObject(HttpServletRequest request, String messageKey, Object object) {
        Map<String, Object> messages = getMessagesMap(request);
        messages.put(messageKey, object);
        saveMessageMap(request, messages);
    }

    public static void setMessageFromProp(HttpServletRequest request, String messageKey, String propKey) {
        Map<String, Object> messages = getMessagesMap(request);
        messages.put(messageKey, PropertiesUtil.getProp(propKey));
        saveMessageMap(request, messages);
    }

    public static void setNotification(HttpServletRequest request, Notification notification) {
        setMessageObject(request, USER_NOTIFICATION, notification);
    }

    private static Map getMessagesMap(HttpServletRequest request) {
        Map<String, Object> messages = (Map) request.getSession().getAttribute(MESSAGES);
        if (messages == null) {
            messages = new HashMap<>();
        }
        return messages;
    }

    private static void saveMessageMap(HttpServletRequest request, Map messages) {
        request.getSession().setAttribute(MESSAGES, messages);
    }
}
