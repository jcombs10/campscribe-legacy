<%@ include file="header.jsp"%>
<div class="leftfloat paddingTop10">
	<form:form commandName="reportFilter">
		<table>
			<tr>
				<td><label>Event: </label></td>
				<td><form:select path="eventId" onchange='this.form.submit()'>
						<form:options items="${eventList}" itemLabel="description"
							itemValue="id" />
					</form:select></td>
			</tr>
			<tr>
				<td><label>Group By: </label></td>
				<td><form:select path="groupBy" onchange='this.form.submit()'>
						<form:option value="Program Area">Program Area</form:option>
						<form:option value="Unit">Unit</form:option>
					</form:select></td>
			</tr>
			<tr>
				<td><label>Program Area: </label></td>
				<td><form:select path="programArea"
						onchange='this.form.submit()'>
						<form:option value="ALL">All Program Areas</form:option>
						<form:option value="Aquatics">Aquatics</form:option>
						<form:option value="COPE and Climbing">COPE and Climbing</form:option>
						<form:option value="Eagle Ridge">Eagle Ridge</form:option>
						<form:option value="Handicraft">Handicraft</form:option>
						<form:option value="Handyman">Handyman</form:option>
						<form:option value="Health Lodge">Health Lodge</form:option>
						<form:option value="Native American Village">Native American Village</form:option>
						<form:option value="NEST">NEST</form:option>
						<form:option value="Outdoor Skills">Outdoor Skills</form:option>
						<form:option value="Shooting Sports">Shooting Sports</form:option>
					</form:select></td>
			</tr>
			<tr>
				<td><label>Unit: </label></td>
				<td><form:select path="unit" onchange='this.form.submit()'>
						<form:option value="ALL">All Units</form:option>
						<form:options items="${unitSet}" />
					</form:select></td>
			</tr>
		</table>
	</form:form>
</div>
<c:url value="/reportServlet" var="reportUrl">
	<c:param name="eventId" value="${reportFilter.eventId}" />
	<c:param name="groupBy" value="${reportFilter.groupBy}" />
	<c:param name="programArea" value="${reportFilter.programArea}" />
	<c:param name="unit" value="${reportFilter.unit}" />
</c:url>
<c:if test="${reportFilter.groupBy eq 'Unit'}">
<div class="leftfloat paddingTop10 paddingLeft50">
    <a href="<c:out value="${reportUrl}"/>" target="_top"><img
        src="images/AdobeReaderIcon.png" alt="Print Report"
        title="Print Report"></a>
</div>
</c:if>
<div class="clear"></div>
<p>

	<c:if test="${reportFilter.groupBy eq 'Program Area'}">
		<c:forEach var="entry" items="${clazzByProgramAreaMap}">
			<h2>
				<c:out value="${entry.key}" />
			</h2>
			<div class="paddingLeft20">
				<c:forEach var="clazz" items="${entry.value}">
					<h3>
						<c:out value="${clazz.key.mbName}" />
						<c:out value="${clazz.key.description}" />
					</h3>
					<div class="paddingLeft40">
						<c:forEach var="tp" items="${clazz.value}">
							<c:out value="${scoutLookup[tp.scoutKey].displayName}" />
                    (
                    <c:out value="${scoutLookup[tp.scoutKey].unitType}" />
							<c:out value="${scoutLookup[tp.scoutKey].unitNumber}" />
                    ) <br> Status:
                        <c:choose>
								<c:when test="${tp.complete}">
                                Complete
                                <img src="images/16x16/accept.png"
										alt="Complete" title="Complete" />
								</c:when>
								<c:when test="${!tp.complete}">
                                Partial
                                <img src="images/16x16/warning.png"
										alt="Partial" title="Partial" />
								</c:when>
							</c:choose>
							<br> Completed Requirements:
                    <c:forEach var="rc" items="${tp.requirementList}">
								<c:if test="${rc.completed}">
									<c:out value="${rc.reqNumber}" />
								</c:if>
							</c:forEach>
							<c:if test="${!tp.complete}">
								<br> Incomplete Requirements:
                    <c:forEach var="rc" items="${tp.requirementList}">
									<c:if test="${!rc.completed}">
										<c:out value="${rc.reqNumber}" />
									</c:if>
								</c:forEach>
							</c:if>
							<p>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</c:if>
	<c:if test="${reportFilter.groupBy eq 'Unit'}">
		<c:forEach var="entry" items="${scoutByUnitMap}">
			<h2>
				<c:out value="${entry.key}" />
			</h2>
			<div class="paddingLeft20">
				<c:forEach var="scout" items="${entry.value}">
					<h3>
						<c:out value="${scout.key.displayName}" />
					</h3>
					<div class="paddingLeft40">
						<c:forEach var="tp" items="${scout.value}">
							<c:out value="${clazzLookup[tp.clazzKey].mbName}" />
							<c:out value="${clazzLookup[tp.clazzKey].description}" /> 
                    (<c:out
								value="${clazzLookup[tp.clazzKey].programArea}" />)
                     <br> Status:
                        <c:choose>
								<c:when test="${tp.complete}">
                                Complete
                                <img src="images/16x16/accept.png"
										alt="Complete" title="Complete" />
								</c:when>
								<c:when test="${!tp.complete}">
                                Partial
                                <img src="images/16x16/warning.png"
										alt="Partial" title="Partial" />
								</c:when>
							</c:choose>
							<br> Completed Requirements:
                    <c:forEach var="rc" items="${tp.requirementList}">
								<c:if test="${rc.completed}">
									<c:out value="${rc.reqNumber}" />
								</c:if>
							</c:forEach>
							<c:if test="${!tp.complete}">
								<br> Incomplete Requirements:
                    <c:forEach var="rc" items="${tp.requirementList}">
									<c:if test="${!rc.completed}">
										<c:out value="${rc.reqNumber}" />
									</c:if>
								</c:forEach>
							</c:if>
							<p>
						</c:forEach>
					</div>
				</c:forEach>
			</div>
		</c:forEach>
	</c:if>

	<%@ include file="footer.jsp"%>