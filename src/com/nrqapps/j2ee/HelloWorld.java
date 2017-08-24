package com.nrqapps.j2ee;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mauricio Enriquez on 8/24/17.
 * NrqApps © 2017
 */
public class HelloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("message", "HelloWorld!!");

        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/index.jsp");
        view.forward(request, response);
    }
}
