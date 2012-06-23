<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/security/tags"
	prefix="sec"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" rel="stylesheet" href="campscribe.css" />
<title>CampScribe Advancement Tracker</title>
<!--                                           -->
<!-- This script loads your compiled module.   -->
<!-- If you add any GWT meta tags, they must   -->
<!-- be added before this line.                -->
<!--                                           -->
<script type="text/javascript" language="javascript"
	src="CampScribe/CampScribe.nocache.js"></script>
</head>
<body>
	<!-- RECOMMENDED if your web app will not function without JavaScript enabled -->
	<noscript>
		<div
			style="width: 22em; position: absolute; left: 50%; margin-left: -11em; color: red; background-color: white; border: 1px solid red; padding: 4px; font-family: sans-serif">
			Your web browser must have JavaScript enabled in order for this
			application to display correctly.</div>
	</noscript>
	<img src="images/campscribe.png" alt="CampScribe Logo"
		class="leftfloat marginBottom10" />
	<div class="logoTitle">Advancement Tracker</div>
	<div class="rightfloat">
        <sec:authorize access="isAuthenticated()">
            <span class="bold">Logged in as </span> <sec:authentication property="principal.username" />
        </sec:authorize>
	</div>
	<div id="menuBar">
		<sec:authorize access="hasAnyRole('camp_admin', 'system_admin')">
			<a href="admin.cs">Administration</a>
		</sec:authorize>
		<sec:authorize
			access="hasAnyRole('counselor', 'area_director', 'camp_admin', 'system_admin')">
			<a href="tracking.cs">Tracking</a>
		</sec:authorize>
		<sec:authorize
			access="hasAnyRole('area_director', 'camp_admin', 'system_admin')">
			<a href="reports.cs">Reports</a>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<a href="/j_spring_security_logout" class="rightfloat">Logout</a>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<a href="main.cs" class="rightfloat">Login</a>
		</sec:authorize>
		<div class="clear"></div>
	</div>
<div>
	
