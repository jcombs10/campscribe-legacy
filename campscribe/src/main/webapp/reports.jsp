<%@ include file="header.jsp"%>
<p>
	<form:form commandName="reportFilter">
        <label>Event: </label>
        <form:select path="eventId" onchange='this.form.submit()'>
            <form:options items="${eventList}" itemLabel="description"
                itemValue="id" />
        </form:select>
        <br>
        <label>Program Area: </label>
        <form:select path="programArea" onchange='this.form.submit()'>
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
        </form:select>
	</form:form>
<p>

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
					<c:out value="${completionLookup[tp.id]}" />
						<c:choose>
							<c:when test="${completionLookup[tp.id] eq 'Complete'}">
								<img src="images/16x16/accept.png" alt="Complete"
									title="Complete" />
							</c:when>
							<c:when test="${completionLookup[tp.id] eq 'Partial'}">
								<img src="images/16x16/warning.png" alt="Partial"
									title="Partial" />
							</c:when>
						</c:choose>
						<br> Completed Requirements:
					<c:forEach var="rc" items="${tp.requirementList}">
							<c:if test="${rc.completed}">
								<c:out value="${rc.reqNumber}" />
							</c:if>
						</c:forEach>
						<br> Incomplete Requirements:
					<c:forEach var="rc" items="${tp.requirementList}">
							<c:if test="${!rc.completed}">
								<c:out value="${rc.reqNumber}" />
							</c:if>
						</c:forEach>
						<p>
					</c:forEach>
				</div>
			</c:forEach>
		</div>
	</c:forEach>
<%@ include file="footer.jsp"%>
