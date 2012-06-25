<%@ include file="header.jsp"%>
<h2>Event Details</h2>
<div id="clazzGWTBlock" />
<p>
	<script type="text/javascript">
    var eventId = "<c:out value="${event.id}"/>";
</script>
<h2>
	<c:out value="${event.description}" />
	(
	<c:out value="${event.startDateDisplayStr}" />
	-
	<c:out value="${event.endDateDisplayStr}" />
	)
</h2>
<table class="campscribeList">
	<tr>
		<th>Description</th>
		<th>Merit Badge</th>
		<th>Program Area</th>
		<th>Taught By</th>
		<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
			<th>Delete</th>
		</sec:authorize>
	</tr>
	<c:forEach var="clazz" items="${clazzes}">
		<tr>
			<td><a
				href="viewClazz.cs?eventId=${event.id}&clazzId=${clazz.id}"><c:out
						value="${clazz.description}" /></a></td>
			<td><c:out value="${mbLookup[clazz.mbId].badgeName}" /></td>
			<td><c:out value="${clazz.programArea}" /></td>
			<td><c:out value="${staffLookup[clazz.staffId].name}" /></td>
			<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
				<td style="text-align: center;"><a
					href="deleteClazz.cs?id=${clazz.id}"><img
						src="images/16x16/delete.png" alt="Delete"></a></td>
			</sec:authorize>
		</tr>
	</c:forEach>
</table>
</p>
<%@ include file="footer.jsp"%>
