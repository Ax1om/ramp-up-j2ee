package com.nrqapps.j2ee.controllers;

import com.nrqapps.j2ee.models.Employee;
import com.nrqapps.j2ee.models.MaritalStatus;
import com.nrqapps.j2ee.models.Skill;
import com.nrqapps.j2ee.pojos.Notification;
import com.nrqapps.j2ee.utils.HibernateUtils;
import com.nrqapps.j2ee.utils.MessagesUtil;
import com.nrqapps.j2ee.utils.StringUtils;
import org.hibernate.Session;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Mauricio Enriquez on 8/17/17.
 * NrqApps © 2017
 */
public class UpsertServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // this parameter is available from the url, forwarded from GET request.
        String employeeId = request.getParameter("employeeId");

        Session session = HibernateUtils.getSession();
        session.beginTransaction();
        try {
            session.saveOrUpdate(
                bindEmployee(request, response)
            );
            String successPropKey = StringUtils.isEmpty(employeeId) ? "crud.insert.success":"crud.update.success";
            MessagesUtil.setNotification(request, new Notification(Notification.SUCCESS, null, successPropKey));
        } catch (Exception e) {
            if (!e.getMessage().equals("invalid.employee")) {
                e.printStackTrace();
            }
        }
        session.getTransaction().commit();
        session.close();
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        Employee employee = new Employee();
        Session session = HibernateUtils.getSession();

        if ( employeeId != null && !employeeId.equals("")) {
            try {
                employee = session.get(Employee.class, Integer.valueOf(employeeId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.invalid.id"));
            } finally {
                if (employee == null ) {
                    employee = new Employee();
                    MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.notFound.id"));
                }
            }
        }

        session.close();
        loadUpsertPage(request, response, employee);

    }

    private void loadUpsertPage(HttpServletRequest request, HttpServletResponse response, Employee employee) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/upsert.jsp");

        Session session = HibernateUtils.getSession();
        // retrieve MaritalStatus and Skill list with HQL
        List maritalStatusList = session.createQuery("from MaritalStatus").list();
        List skills = session.createQuery("from Skill").list();
        session.close();

        // creates the array with the skills id format: ['id1', 'id2' , ... ]
        // here we could use GSON to create a JSONArray and use toString.
        StringBuilder skillsIdsBuilder = new StringBuilder("[");
        if (employee.getSkills() != null && employee.getSkills().size() > 0 ) {
            for (Skill skill : employee.getSkills()) {
                skillsIdsBuilder
                        .append("'")
                        .append(skill.getSkillId()).append("-").append(skill.getName())
                        .append("'")
                        .append(",");
            }
            // remove last comma
            skillsIdsBuilder.deleteCharAt(skillsIdsBuilder.length()-1);
        }
        skillsIdsBuilder.append("]");

        request.setAttribute("employee", employee);
        request.setAttribute("maritalStatusList", maritalStatusList);
        request.setAttribute("skills", skills);
        request.setAttribute("skillsIds", skillsIdsBuilder.toString());
        MessagesUtil.loadNotifications(request);
        view.forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private Employee bindEmployee(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Employee employee = new Employee();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        boolean invalidEmployee = false;

        String employeeId = request.getParameter("employeeId");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String country = request.getParameter("country");
        String birthDate = request.getParameter("birthDate");
        String maritalStatusId = request.getParameter("maritalStatusId");
        String skillsIds = request.getParameter("skillsIds");

        if (!StringUtils.isEmpty(employeeId) && StringUtils.isNaturalNumber(employeeId)) {
            employee.setEmployeeId(Integer.valueOf(employeeId));
        }

        if (!StringUtils.isEmpty(name)) {
            employee.setName(name);
        } else {
            MessagesUtil.setMessageFromProp(request, "error_name", "error.validation.empty");
            invalidEmployee = true;
        }

        if (!StringUtils.isEmpty(surname)) {
            employee.setSurname(surname);
        } else {
            MessagesUtil.setMessageFromProp(request, "error_surname", "error.validation.empty");
            invalidEmployee = true;
        }

        employee.setCountry(country);

        if (!StringUtils.isEmpty(birthDate) && StringUtils.isDateFormated(birthDate)) {
            employee.setBirthDate(simpleDateFormat.parse(birthDate));
        } else {
            MessagesUtil.setMessageFromProp(request, "error_birthDate", "error.validation.date");
            invalidEmployee = true;
        }

        Session session = HibernateUtils.getSession();

        if (!StringUtils.isEmpty(maritalStatusId) && StringUtils.isNaturalNumber(maritalStatusId)) {
            MaritalStatus maritalStatus = session.get(MaritalStatus.class, Integer.valueOf(maritalStatusId));
            if (maritalStatus != null ) {
                employee.setMaritalStatus(maritalStatus);
            }
        }

        if(!StringUtils.isEmpty(skillsIds)) {
            List skills =  session.createQuery("from Skill where skillId in (" + skillsIds + ")").getResultList();
            employee.setSkills(skills);
        }

        session.close();

        if(invalidEmployee) {
            MessagesUtil.setNotification(request, new Notification(Notification.ERROR, null, "error.form.invalid"));
            loadUpsertPage(request, response, employee);
            throw new Exception("invalid.employee");
        }

        return employee;
    }
}
