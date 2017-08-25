package com.nrqapps.j2ee.controllers;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.pojos.Notification;
import com.nrqapps.j2ee.utils.HibernateUtils;
import com.nrqapps.j2ee.utils.MessagesUtil;
import com.nrqapps.j2ee.utils.StringUtils;
import org.hibernate.Session;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Mauricio Enriquez on 8/17/17.
 * NrqApps © 2017
 */
public class DeleteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        if (!StringUtils.isEmpty(employeeId)) {
            try {
                Session session = HibernateUtils.getSession();
                session.beginTransaction();
                Employee employee = session.get(Employee.class, Integer.valueOf(employeeId));
                if ( employee != null ) {
                    session.delete(employee);
                    MessagesUtil.setNotification(request, new Notification(Notification.SUCCESS, null, "crud.delete.success"));
                } else {
                    MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.notFound.id"));
                }
                session.getTransaction().commit();
                session.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.invalid.id"));
            }
        } else {
            MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.invalid.id"));
        }
        response.sendRedirect("/");
    }
}
