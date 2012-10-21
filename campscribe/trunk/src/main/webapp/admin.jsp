<%@ include file="header.jsp"%>
<sec:authorize access="hasRole('system_admin')">
	<a href="meritBadges.cs">Merit Badges</a>
	<br />
</sec:authorize>
<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
	<a href="campInfo.cs">Camp Info</a>
	<br />
	<a href="meritBadgeMetadata.cs">Class Defaults</a>
	<br />
</sec:authorize>
<sec:authorize
	access="hasAnyRole('area_director', 'camp_admin', 'system_admin')">
	<a href="events.cs">Events</a>
	<br />
</sec:authorize>
<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
	<a href="scouts.cs">Scouts</a>
	<br />
	<a href="staff.cs">Staff</a>
	<hr>
	<a href="upload.cs">Upload from DoubleKnot</a>
</sec:authorize>
<%@ include file="footer.jsp"%>
