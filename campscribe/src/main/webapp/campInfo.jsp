<%@ include file="header.jsp"%>
<h2>Camp Info</h2>
<div id="campInfoGWTBlock" />
<p>
	
	<table class=campscribeList>
    <tr>
        <td>Camp Name</td>
        <td><c:out value="${campInfo.campName}" /></td>
    </tr>
    <tr>
        <td>Address</td>
        <td>
            <c:out value="${campInfo.address}" /><br>
            <c:out value="${campInfo.city}" />, <c:out value="${campInfo.state}" /> <c:out value="${campInfo.zip}" />
        </td>
    </tr>
    <tr>
        <td>Phone Number</td>
        <td><c:out value="${campInfo.phoneNbr}" /></td>
    </tr>
    <tr>
        <td>Merit Badge Signer</td>
        <td><c:out value="${campInfo.meritBadgeSigner}" /></td>
    </tr>
  </table>
  </p>
<%@ include file="footer.jsp"%>
