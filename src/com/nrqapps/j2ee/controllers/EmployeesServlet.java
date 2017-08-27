package com.nrqapps.j2ee.controllers;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.utils.HibernateUtils;
import com.nrqapps.j2ee.utils.MessagesUtil;
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

public class EmployeesServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Session session = HibernateUtils.getSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();

        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> rootEmployee = criteriaQuery.from(Employee.class);

        String query = request.getParameter("query");

        criteriaQuery
                .select(rootEmployee);

        if (query != null && !query.equals("")) {
            String param = "%" + query + "%";
            criteriaQuery
                .where(
                    criteriaBuilder.or(
                        criteriaBuilder.like(rootEmployee.get("name"), param),
                        criteriaBuilder.like(rootEmployee.get("surname"), param),
                        criteriaBuilder.like(rootEmployee.get("country"), param)
                    )
                );
        }

        List employees = session.createQuery(criteriaQuery).getResultList();
        session.close();
        request.setAttribute("employees", employees);
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/employees.jsp");
        MessagesUtil.loadNotifications(request);
        view.forward(request, response);

    }
}

