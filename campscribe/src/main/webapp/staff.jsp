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
        <th>Email Address</th>
        <th>Edit</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="staffMember" items="${staff}">
      <tr>
        <td><c:out value="${staffMember.name}" /></td>
        <td><c:out value="${staffMember.userId}" /></td>
        <td>
            <c:forEach var="role" items="${staffMember.roles}">
                <c:out value="${roleLookup[role]}" />
                <br>
            </c:forEach>
        </td>
        <td><c:out value="${staffMember.programArea}" /> </td>
        <td><c:out value="${staffMember.emailAddress}" /> </td>
        <td style="text-align: center;"><a href="#" onclick="StaffGWT.editStaff('<c:out value="${staffMember.id}"/>');" ><img
                        src="images/16x16/edit.gif" alt="Edit" title="Edit"></a></td>
        <td style="text-align: center;"><a href="#" onclick="StaffGWT.deleteStaff('<c:out value="${staffMember.id}"/>');" ><img
                        src="images/16x16/delete.png" alt="Delete" title="Delete"></a></td>
      </tr>
    </c:forEach>
  </table>
  </p>
</body>
</html>
