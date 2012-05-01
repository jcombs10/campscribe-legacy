<%@ include file="header.jsp"%>
<h2>Event Details</h2>
<div id="clazzGWTBlock" />
<p>
<script type="text/javascript">
    var eventId = "<c:out value="${event.id}"/>";
</script>
<h2>
	<c:out value="${event.description}" />
	<c:out value="${event.startDateDisplayStr}" />
	-
	<c:out value="${event.endDateDisplayStr}" />
</h2>
<table class="campscribeList">
	<tr>
		<th>Description</th>
        <th>Merit Badge</th>
        <th>Taught By</th>
        <th></th>
        <th></th>
	</tr>
	<c:forEach var="clazz" items="${event.clazzes}">
		<tr>
			<td><a href="viewClazz.cs?id=${clazz.encodedKey}"><c:out value="${clazz.description}" /></a></td>
            <td><c:out value="${mbLookup[clazz.mbId].badgeName}" /></td>
            <td><c:out value="${staffLookup[clazz.staffId].name}" /></td>
            <td><a href="#" onclick="ClazzGWT.trackProgress('<c:out value="${clazz.encodedKey}"/>');" >Report Progress</a></td>
			<td><a href="deleteClazz.cs?id=${clazz.id.id}"><img
					src="images/16x16/delete.png" alt="Delete"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
</body>
</html>
