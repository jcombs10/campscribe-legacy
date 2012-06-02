<%@ include file="header.jsp" %>
<h2>
<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">Tracking for Entire Camp</sec:authorize>
<sec:authorize access="hasRole('area_director') and !hasRole('system_admin')">Tracking for <c:out value="${programArea}" /></sec:authorize>
<sec:authorize access="hasRole('counselor') and !hasRole('system_admin')">Tracking for <c:out value="${fullName}" /></sec:authorize>

</h2>
<form:form commandName="event" >
    <form:select path="eventId" onchange='this.form.submit()'>
        <form:options items="${eventList}" itemLabel="description" itemValue="id"/>
    </form:select>
</form:form>
<table class="campscribeList">
    <tr>
        <th>Description</th>
        <th>Merit Badge</th>
        <th>Program Area</th>
        <th>Taught By</th>
        <th>Report Progress</th>
    </tr>
    <c:forEach var="clazz" items="${clazzes}">
        <tr>
            <td><c:out value="${clazz.description}" /></td>
            <td><c:out value="${mbLookup[clazz.mbId].badgeName}" /></td>
            <td><c:out value="${clazz.programArea}" /></td>
            <td><c:out value="${staffLookup[clazz.staffId].name}" /></td>
            <td style="text-align: center;"><a href="#" onclick="ClazzGWT.trackProgress(<c:out value="${clazz.event.id}"/>, <c:out value="${clazz.id}"/>);" >Report Progress</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
