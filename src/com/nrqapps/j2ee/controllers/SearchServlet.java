package com.nrqapps.j2ee.controllers;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.utils.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mauricio Enriquez on 8/17/17.
 * NrqApps © 2017
 */

public class SearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Session session = HibernateUtils.getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);

        criteriaQuery
                .select(rootEmployee);

        List employees = session.createQuery(criteriaQuery).getResultList();
        session.close();
        request.setAttribute("employees", employees);
        RequestDispatcher view = request.getRequestDispatcher("/views/employees.jsp");
        view.include(request, response);

    }
}
