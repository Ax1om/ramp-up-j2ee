package com.nrqapps.j2ee.pojos;


import com.nrqapps.j2ee.utils.PropertiesUtil;
import com.nrqapps.j2ee.utils.StringUtils;

import java.io.Serializable;

/**
 * Created by Mauricio Enriquez on 8/25/17.
 * NrqApps © 2017
 */
public class Notification implements Serializable {
    public static final String SUCCESS = "success";
    public static final String INFO = "info";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

    private String type;
    private String message;
    private String propKey;
    private Integer time;

    public Notification(String type, String message, String propKey) {
        this.type = type;
        setMessage(message);
        setPropKey(propKey);
    }

    public Notification(String type, String message) {
        this.type = type;
        setMessage(message);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getPropKey() {
        return propKey;
    }

    private void setPropKey(String propKey) {
        this.propKey = propKey;
        if(StringUtils.isEmpty(message)) {
            this.message = PropertiesUtil.getProp(propKey);
        }
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return '{' +
                "type: '" + type + '\'' +
                ", message: '" + message + '\'' +
                ", time: " + time +
                '}';
    }
}
