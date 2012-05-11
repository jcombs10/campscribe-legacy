<%@ include file="header.jsp" %>
<h2>Event Details</h2>
<table class="campscribeList">
    <tr>
        <th>Description</th>
        <th>Merit Badge</th>
        <th>Program Area</th>
        <th>Taught By</th>
        <th>Report Progress</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="clazz" items="${clazzes}">
        <tr>
            <td><c:out value="${clazz.description}" /></td>
            <td><c:out value="${mbLookup[clazz.mbId].badgeName}" /></td>
            <td><c:out value="${clazz.programArea}" /></td>
            <td><c:out value="${staffLookup[clazz.staffId].name}" /></td>
            <td style="text-align: center;"><a href="#" onclick="ClazzGWT.trackProgress('<c:out value="${clazz.id}"/>');" >Report Progress</a></td>
            <td style="text-align: center;"><a href="deleteClazz.cs?id=${clazz.id}"><img
                    src="images/16x16/delete.png" alt="Delete"></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
