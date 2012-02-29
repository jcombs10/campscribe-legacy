<%@ include file="header.jsp"%>
<h2>Event List</h2>
<div id="clazzGWTBlock" />
<p>
<script type="text/javascript">
    var eventId = <c:out value="${event.id.id}"/>;
</script>
<h3>
	<c:out value="${event.description}" />
	<c:out value="${event.startDateDisplayStr}" />
	-
	<c:out value="${event.endDateDisplayStr}" />
</h3>
<table class="campscribeList">
	<tr>
		<th>Description</th>
		<th>Merit Badge</th>
		<th></th>
	</tr>
	<c:forEach var="clazz" items="${event.clazzes}">
		<tr>
			<td><c:out value="${clazz.description}" /></td>
			<td><c:out value="${mbLookup[clazz.mbId].badgeName}" /></td>
			<td><a href="deleteClazz.cs?id=${clazz.id}"><img
					src="images/16x16/delete.png" alt="Delete"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
</body>
</html>
