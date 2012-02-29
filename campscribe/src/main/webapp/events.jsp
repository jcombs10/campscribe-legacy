<%@ include file="header.jsp"%>
<h2>Event List</h2>
<div id="eventGWTBlock" />
<p>
<table class="campscribeList">
	<tr>
		<th>Description</th>
		<th>Start Date</th>
		<th>End Date</th>
		<th></th>
	</tr>
	<c:forEach var="event" items="${events}">
		<tr>
			<td><a href="viewEvent.cs?id=${event.id.id}"><c:out value="${event.description}" /></a></td>
			<td><c:out value="${event.startDateDisplayStr}" /></td>
			<td><c:out value="${event.endDateDisplayStr}" /></td>
			<td><a href="deleteEvent.cs?id=${event.id}"><img
					src="images/16x16/delete.png" alt="Delete"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
</body>
</html>
