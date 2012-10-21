<%@ include file="header.jsp"%>
<h2>Scout List</h2>
<div id="scoutGWTBlock" />
<form:form commandName="eventFilter" >
    <label>Filter: </label>
    <form:select path="eventId" onchange='this.form.submit()'>
        <form:options items="${eventList}" itemLabel="description" itemValue="id"/>
    </form:select>
</form:form>
<p>
	
	<table class=campscribeList>
    <tr>
        <th>Name</th>
        <th>Rank</th>
        <th>Unit</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="scout" items="${scouts}">
      <tr>
        <td><c:out value="${scout.displayName}" /></td>
        <td><c:out value="${scout.rank}" /></td>
        <td><c:out value="${scout.unitType}" /> <c:out
						value="${scout.unitNumber}" /></td>
        <td style="text-align: center;"><a onclick="ScoutGWT.editScout('<c:out value="${scout.id}"/>');" ><img
                        src="images/16x16/edit.gif" alt="Edit" title="Edit"></a></td>
        <td style="text-align: center;"><a onclick="ScoutGWT.deleteScout('<c:out value="${scout.id}"/>');" ><img
                        src="images/16x16/delete.png" alt="Delete" title="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
  </p>
<%@ include file="footer.jsp"%>
