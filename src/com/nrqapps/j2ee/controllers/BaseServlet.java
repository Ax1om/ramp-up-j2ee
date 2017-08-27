package com.nrqapps.j2ee.controllers;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mauricio Enriquez on 8/27/17.
 * NrqApps © 2017
 */
public class BaseServlet extends HttpServlet {

    String getHomePageUrl(HttpServletRequest request) {
        return request.getContextPath().equals("") ? "/" : request.getContextPath();
    }

}
