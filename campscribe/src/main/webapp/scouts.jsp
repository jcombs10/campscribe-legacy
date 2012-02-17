<%@ include file="header.jsp"%>
<h2>Scout List</h2>
<div id="scoutGWTBlock" />
   
	<p>
  
	<table class=campscribeList>
    <tr>
        <th>Name</th>
        <th>Rank</th>
        <th>Unit</th>
        <th></th>
    </tr>
    <c:forEach var="scout" items="${scouts}">
      <tr>
        <td><c:out value="${scout.displayName}" /></td>
        <td><c:out value="${scout.rank}" /></td>
        <td><c:out value="${scout.unitType}" /> <c:out
						value="${scout.unitNumber}" /></td>
        <td><a href="deleteScout.cs?id=${scout.id}"><img
						src="images/16x16/delete.png" alt="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
  </p>
</body>
</html>
