<%@ include file="header.jsp"%>
<h2>Merit Badge List</h2>
<div id="meritBadgeGWTBlock" />
<p>
<table class="campscribeList">
	<tr>
		<th></th>
		<th>Merit Badge</th>
		<th>BSA Advancement ID</th>
		<th>Edit</th>
		<th>Delete</th>
	</tr>
	<c:forEach var="meritBadge" items="${meritBadges}">
		<tr>
			<td><c:if test="${meritBadge.eagleRequired}">
					<img src="images/EagleBadge.png" alt="Eagle Required" />
				</c:if></td>
			<td><c:out value="${meritBadge.badgeName}" /></td>
			<td><c:out value="${meritBadge.bsaAdvancementId}" /></td>
            <td style="text-align: center;"><a href="#"
                onclick="MeritBadgeGWT.editMeritBadge('<c:out value="${meritBadge.id}"/>');"><img
                    src="images/16x16/edit.gif" alt="Edit" title="Edit"></a></td>
            <td style="text-align: center;"><a href="#"
                onclick="MeritBadgeGWT.deleteMeritBadge('<c:out value="${meritBadge.id}"/>');"><img
                    src="images/16x16/delete.png" alt="Delete" title="Delete"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
<%@ include file="footer.jsp"%>
