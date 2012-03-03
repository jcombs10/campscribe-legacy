<%@ include file="header.jsp"%>
<h2>Staff List</h2>
<div id="staffGWTBlock" />
   
	<p>
  
	<table class=campscribeList>
    <tr>
        <th>Name</th>
        <th>User Id</th>
        <th>Roles</th>
        <th>Program Area</th>
        <th></th>
    </tr>
    <c:forEach var="staffMember" items="${staff}">
      <tr>
        <td><c:out value="${staffMember.name}" /></td>
        <td><c:out value="${staffMember.userId}" /></td>
        <td>
            <c:forEach var="role" items="${staffMember.roles}">
                <c:out value="${role}" />
                <br>
            </c:forEach>
        </td>
        <td><c:out value="${staffMember.programArea}" /> </td>
        <td><a href="deleteStaff.cs?id=${staffMember.id}"><img
						src="images/16x16/delete.png" alt="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
  </p>
</body>
</html>
