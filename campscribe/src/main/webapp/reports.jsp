<%@ include file="header.jsp"%>
<form:form commandName="reportFilter">
	<label>Event: </label>
	<form:select path="eventId" onchange='this.form.submit()'>
		<form:options items="${eventList}" itemLabel="description"
			itemValue="id" />
	</form:select>
</form:form>
<p>

	<c:forEach var="entry" items="${clazzByProgramAreaMap}">
		<h2>
			<c:out value="${entry.key}" />
		</h2>
		<c:forEach var="clazz" items="${entry.value}">
			<h3>
				<c:out value="${clazz.key.mbName}" />
				<c:out value="${clazz.key.description}" />
			</h3>
			<c:forEach var="tp" items="${clazz.value}">
				<c:out value="${scoutLookup[tp.scoutKey].displayName}" />  (<c:out
					value="${scoutLookup[tp.scoutKey].unitType}" />
				<c:out value="${scoutLookup[tp.scoutKey].unitNumber}" />)
                    <br>
                    Status: 
                    <br>
                    Completed Requirements: 
                    <c:forEach var="rc" items="${tp.requirementList}">
					   <c:if test="${rc.completed}">
						  <c:out value="${rc.reqNumber}" />
					   </c:if>
				    </c:forEach>
    				<br>
                    Incomplete Requirements: 
                    <c:forEach var="rc" items="${tp.requirementList}">
                       <c:if test="${!rc.completed}">
                          <c:out value="${rc.reqNumber}" />
                       </c:if>
                    </c:forEach>
                    <p>
			</c:forEach>
		</c:forEach>
	</c:forEach>
	</body>
	</html>