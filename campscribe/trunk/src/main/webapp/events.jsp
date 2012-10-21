<%@ include file="header.jsp"%>
<h2>Event List</h2>
<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
	<div id="eventGWTBlock" />
</sec:authorize>
<p>
<table class="campscribeList">
	<tr>
		<th>Description</th>
		<th>Start Date</th>
		<th>End Date</th>
		<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
			<th>Edit</th>
			<th>Delete</th>
		</sec:authorize>
	</tr>
	<c:forEach var="event" items="${events}">
		<tr>
			<td><a href="viewEvent.cs?id=${event.id}"><c:out
						value="${event.description}" /></a></td>
			<td><c:out value="${event.startDateDisplayStr}" /></td>
			<td><c:out value="${event.endDateDisplayStr}" /></td>
			<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
				<td style="text-align: center;"><a href="#"
					onclick="EventGWT.editEvent('<c:out value="${event.id}"/>');"><img
						src="images/16x16/edit.gif" alt="Edit" title="Edit"></a></td>
				<td style="text-align: center;"><a href="#"
					onclick="EventGWT.deleteEvent('<c:out value="${event.id}"/>');"><img
						src="images/16x16/delete.png" alt="Delete" title="Delete"></a></td>
			</sec:authorize>
		</tr>
	</c:forEach>
</table>
</p>
<%@ include file="footer.jsp"%>
