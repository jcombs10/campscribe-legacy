<%@ include file="header.jsp"%>
<h2>Class Defaults</h2>
<div id="meritBadgeMetadataGWTBlock" />
<p>
<table class="campscribeList">
	<tr>
		<th>Merit Badge</th>
		<th>Program Area</th>
		<th>Counselor</th>
        <th>Edit</th>
	</tr>
	<c:forEach var="meritBadgeMetadata" items="${meritBadgeMetadata}">
		<tr>
			<td><c:out value="${meritBadgeLookup[meritBadgeMetadata.mbKey].badgeName}" /></td>
            <td><c:out value="${meritBadgeMetadata.programArea}" /></td>
            <td><c:out value="${staffLookup[meritBadgeMetadata.staffKey].name}" /></td>
            <td style="text-align: center;"><a href="#"
                onclick="MeritBadgeMetadataGWT.editMeritBadgeMetadata('<c:out value="${meritBadgeMetadata.id}"/>');"><img
                    src="images/16x16/edit.gif" alt="Edit"></a></td>
		</tr>
	</c:forEach>
</table>
</p>
<%@ include file="footer.jsp"%>
