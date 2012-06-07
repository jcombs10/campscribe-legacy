<%@ include file="header.jsp"%>
<form:form commandName="reportFilter">
    <label>Event: </label>
    <form:select path="eventId" onchange='this.form.submit()'>
        <form:options items="${eventList}" itemLabel="description"
            itemValue="id" />
    </form:select>
</form:form>
<p>
    
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
            <td style="text-align: center;"><a href="#"
                onclick="ClazzGWT.trackProgress(<c:out value="${clazz.event.id}"/>, <c:out value="${clazz.id}"/>);">Report Progress</a></td>
        </tr>
    </c:forEach>
</table>
</body>
    </html>