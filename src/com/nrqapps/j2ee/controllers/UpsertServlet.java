package com.nrqapps.j2ee.controllers;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.utils.HibernateUtils;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Mauricio Enriquez on 8/17/17.
 * NrqApps © 2017
 */
public class UpsertServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String employeeId = request.getParameter("employeeId");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String country = request.getParameter("country");
        String birthDate = request.getParameter("birthDate");

        Employee employee = new Employee();

        if ( employeeId != null && !employeeId.equals("")) {
            try {
                employee.setEmployeeId(Integer.valueOf(employeeId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        employee.setName(name);
        employee.setSurname(surname);
        employee.setCountry(country);

        try {
            employee.setBirthDate(simpleDateFormat.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
        session.close();
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/views/upsert.jsp");
        String employeeId = request.getParameter("employeeId");
        Employee employee = new Employee();

        if ( employeeId != null && !employeeId.equals("")) {
            try {
                Session session = HibernateUtils.getSession();
                employee = session.get(Employee.class, Integer.valueOf(employeeId));
                session.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("employee", employee);
        view.forward(request, response);
    }
}
