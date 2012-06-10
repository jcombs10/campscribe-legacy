<%@ include file="header.jsp"%>
<h2>Event List</h2>
<div id="eventGWTBlock" />
<p>
<table class="campscribeList">
	<tr>
		<th>Description</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="event" items="${events}">
		<tr>
			<td><a href="viewEvent.cs?id=${event.id}"><c:out
						value="${event.description}" /></a></td>
			<td><c:out value="${event.startDateDisplayStr}" /></td>
			<td><c:out value="${event.endDateDisplayStr}" /></td>
            <td style="text-align: center;"><a href="#"
                onclick="EventGWT.editEvent('<c:out value="${event.id}"/>');"><img
                    src="images/16x16/edit.gif" alt="Edit" title="Edit"></a></td>
            <td style="text-align: center;"><a href="#"
                onclick="EventGWT.deleteEvent('<c:out value="${event.id}"/>');"><img
                    src="images/16x16/delete.png" alt="Delete" title="Delete"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
<%@ include file="footer.jsp"%>
