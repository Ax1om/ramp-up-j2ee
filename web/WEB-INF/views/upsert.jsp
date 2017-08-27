<%--@elvariable id="skillsIds" type="java.lang.String"--%>
<%--@elvariable id="employee" type="it.si2001.servlets.models.Employee"--%>
<%--@elvariable id="skill" type="it.si2001.servlets.models.Skill"--%>
<%--@elvariable id="maritalStatus" type="it.si2001.servlets.models.MaritalStatus"--%>
<%--@elvariable id="maritalStatusList" type="java.util.List"--%>
<%--@elvariable id="skills" type="java.util.List"--%>
<%--@elvariable id="messages" type="java.util.Map"--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tmpl" tagdir="/WEB-INF/tags/templates" %>
<%@ taglib prefix="error" tagdir="/WEB-INF/tags/error" %>


<tmpl:main>
    <form id="upsert-form" method="post">
        <fieldset class="upsert__fieldset">
            <legend class="upsert__legend">${not empty employee.employeeId ? 'Edit ' : 'Insert '} Employee</legend>
            <label class="upsert__label ${not empty messages.get('error_name') ? 'upsert__label--error':''}">
                <span class="upsert__label-text">Name*:</span>
                <input class="upsert__input" name="name" type="text" placeholder="Name" value="${employee.name}">
            </label>
            <error:message propertyName="name" />
            <label class="upsert__label ${not empty messages.get('error_surname') ? 'upsert__label--error':''}">
                <span class="upsert__label-text">Surname*:</span>
                <input class="upsert__input" name="surname" type="text" placeholder="Surname"
                       value="${employee.surname}">
            </label>
            <error:message propertyName="surname" />
            <label class="upsert__label">
                <span class="upsert__label-text">Country:</span>
                <input class="upsert__input" name="country" type="text" placeholder="Country"
                       value="${employee.country}">
            </label>
            <label class="upsert__label ${not empty messages.get('error_birthDate') ? 'upsert__label--error':''}">
                <span class="upsert__label-text">Birth Date*:</span>
                <input class="upsert__input" name="birthDate" type="date" placeholder="yyyy-MM-dd"
                       value="<fmt:formatDate pattern="yyyy-MM-dd" value = "${employee.birthDate}" />">
            </label>
            <error:message propertyName="birthDate" />
            <label class="upsert__label">
                <span class="upsert__label-text">Marital Status:</span>
                <select class="upsert__input" name="maritalStatusId">
                    <c:forEach var="maritalStatus" items="${maritalStatusList}">
                        <option
                                value="${maritalStatus.maritalStatusId}"
                            ${employee.maritalStatus.status eq maritalStatus.status ? 'selected':''}
                        >
                                ${maritalStatus.status}
                        </option>
                    </c:forEach>
                </select>
            </label>
            <fieldset class="upsert__skills">
                <legend>Skills</legend>
                <label>
                    <span>Skills to add:</span>
                    <select id="skills" name="skillsId">
                        <c:forEach var="skill" items="${skills}">
                            <option value="${skill.skillId}-${skill.name}">${skill.name}</option>
                        </c:forEach>
                    </select>
                </label>
                <button type="button" class="link-as-button" onclick="addSkill()">Add</button>
                <div id="skills-container" class="upsert__skills-container">
                </div>
            </fieldset>
            <div class="upsert__buttons-container">
                <input onclick="submitForm()" class="link-as-button" type="button" value="Save">
                <a href="<c:url value="/"/>" class="link-as-button">Cancel</a>
            </div>
        </fieldset>
    </form>
    <script type="text/javascript">
      window.initialSkills = ${skillsIds};
    </script>
    <c:url var="upsertScriptUrl" value="/static/js/upsert.js"/>
    <script src="${upsertScriptUrl}" type="text/javascript"></script>
</tmpl:main>
