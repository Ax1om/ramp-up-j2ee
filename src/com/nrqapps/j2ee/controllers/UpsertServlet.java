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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String country = request.getParameter("country");
        String birthDate = request.getParameter("birthDate");
        String maritalStatusId = request.getParameter("maritalStatusId");
        String skillsIds = request.getParameter("skillsIds");

        Employee employee = new Employee();

        if (!StringUtils.isEmpty(employeeId)) {
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

        if(!StringUtils.isEmpty(skillsIds)) {
            String[] skillsIdsArray =   skillsIds.split(",");
            List<Skill> skills = new ArrayList<>();
            for (String skillId : skillsIdsArray) {
                Skill skill = new Skill();
                skill.setSkillId(Integer.parseInt(skillId));
                skills.add(skill);
            }
            if(skills.size() > 0) {
                employee.setSkills(skills);
            }
        }

        try {
            employee.setBirthDate(simpleDateFormat.parse(birthDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Session session = HibernateUtils.getSession();

        if (!StringUtils.isEmpty(maritalStatusId)) {
            try {
                MaritalStatus maritalStatus = session.get(MaritalStatus.class, Integer.valueOf(maritalStatusId));
                if (maritalStatus != null ) {
                    employee.setMaritalStatus(maritalStatus);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
        session.close();

        String successPropKey = StringUtils.isEmpty(employeeId) ? "crud.insert.success":"crud.update.success";
        MessagesUtil.setNotification(request, new Notification(Notification.SUCCESS, null, successPropKey));
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/upsert.jsp");
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
}
