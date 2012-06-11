<%@ include file="header.jsp"%>
<sec:authorize access="hasRole('system_admin')">
	<a href="meritBadges.cs">Merit Badges</a>
	<br />
</sec:authorize>
<a href="campInfo.cs">Camp Info</a>
<br />
<a href="meritBadgeMetadata.cs">Class Defaults</a>
<br />
<a href="events.cs">Events</a>
<br />
<a href="scouts.cs">Scouts</a>
<br />
<a href="staff.cs">Staff</a>
<hr>
<a href="upload.cs">Upload from DoubleKnot</a>
<%@ include file="footer.jsp"%>
