<%@ include file="header.jsp"%>
<h2>Class Details</h2>
<div id="scoutClazzGWTBlock" />
<p>
<script type="text/javascript">
    var clazzId = "<c:out value="${clazz.id}"/>";
    var eventId = "<c:out value="${clazz.event.id}"/>";
</script>
<h2>
    <c:out value="${event.description}" /> 
    (<c:out value="${event.startDateDisplayStr}" /> - <c:out value="${event.endDateDisplayStr}" />)
    <br>
    <c:out value="${mbLookup[clazz.mbId].badgeName}" /> <c:out value="${clazz.description}" />
    <br>
	<c:out value="${staffLookup[clazz.staffId].name}" />
</h2>
    <table class=campscribeList>
    <tr>
        <th>Name</th>
        <th>Rank</th>
        <th>Unit</th>
        <th>Remove From Class</th>
    </tr>
    <c:forEach var="scout" items="${scouts}">
      <tr>
        <td><c:out value="${scout.displayName}" /></td>
        <td><c:out value="${scout.rank}" /></td>
        <td><c:out value="${scout.unitType}" /> <c:out
                        value="${scout.unitNumber}" /></td>
        <td style="text-align:center;"><a href="deleteScoutClazz.cs?clazzId=${clazz.id}&scoutId=${scout}"><img
                        src="images/16x16/delete.png" alt="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
</p>
<%@ include file="footer.jsp"%>
