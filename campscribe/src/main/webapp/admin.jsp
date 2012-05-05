<%@ include file="header.jsp"%>
<sec:authorize access="hasRole('system_admin')">
	<a href="meritBadges.cs">Merit Badges</a>
	<br />
</sec:authorize>
<a href="events.cs">Events</a>
<br />
<a href="scouts.cs">Scouts</a>
<br />
<a href="staff.cs">Staff</a>
<hr>
<a href="upload.cs">Upload</a>
</body>
</html>
